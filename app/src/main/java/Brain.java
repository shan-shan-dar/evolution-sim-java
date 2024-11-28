import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Brain {
    public static final int INPUT = 1;
    public static final int OUTPUT = 1;
    public static final int INTERNAL = 0;

    /*
     * 0. x position
     * 1. y position
     * 2. last x movement
     * 3. last y movement
     * 4. age
     * 5. random
     * 6. oscillator
     * 7. blockage in forward (most recent) direction in sensing radius
     * 8. distance from x borders
     * 9. distance from y borders
     * 10. population density in sensing radius
     */
    public static final int NUM_INPUTS = 11;

    /*
     * 0. x movement
     * 1. y movement
     * 2. continue last direction of movement
     * 3. reverse last direction of movement
     * 4. set oscillator period
     * 5. set responsiveness
     */
    public static final int NUM_OUTPUTS = 6;

    public static final int NUM_INTERNALS = 3;

    public static final int MAX_POSSIBLE_CONNECTIONS = (NUM_INPUTS + NUM_INTERNALS) * (NUM_OUTPUTS + NUM_INTERNALS);


    private final Neuron[] inputs;
    private final Neuron[] internals;
    private final Neuron[] outputs;
    
    public Brain(Genome genome) throws IOException{
        
        // System.out.println(genome);

        inputs = new Neuron[NUM_INPUTS];
        internals = new Neuron[NUM_INTERNALS];
        outputs = new Neuron[NUM_OUTPUTS];

        connect(genome);

        toGraphviz("beforePruning");

        prune();

        toGraphviz("afterPruning");

        System.out.println(Arrays.toString(inputs));
        System.out.println(Arrays.toString(internals));
        System.out.println(Arrays.toString(outputs));
    }

    private void connect(Genome genome){
        for (Gene gene : genome.getGenes()) {

            Neuron[] sourceArray;
            Neuron[] sinkArray;

            switch (gene.getSourceType()) {
                case INPUT -> sourceArray = inputs;
                case INTERNAL -> sourceArray = internals;
                default -> throw new AssertionError();
            }

            if (gene.getSourceId() < 0 || gene.getSourceId() >= sourceArray.length) {
                throw new IndexOutOfBoundsException("Invalid source neuron ID: " + gene.getSourceId());
            }

            if (sourceArray[gene.getSourceId()] == null) {
                sourceArray[gene.getSourceId()] = new Neuron();
            }
            Neuron sourceNeuron = sourceArray[gene.getSourceId()];

            //

            switch (gene.getSinkType()) {
                case OUTPUT -> sinkArray = outputs;
                case INTERNAL -> sinkArray = internals;
                default -> throw new AssertionError();
            }

            if (gene.getSinkId() < 0 || gene.getSinkId() >= sinkArray.length) {
                throw new IndexOutOfBoundsException("Invalid sink neuron ID: " + gene.getSinkId());
            }

            if (sinkArray[gene.getSinkId()] == null) {
                sinkArray[gene.getSinkId()] = new Neuron();
            }
            Neuron sinkNeuron = sinkArray[gene.getSinkId()];

            // 

            sourceNeuron.addOutputConnection(sinkNeuron, gene.getWeight());
            sinkNeuron.addInputConnection(sourceNeuron);
        }
    }

    public void feedForward() {
        // assumes that input neurons have the correct inputSum from their corresponding input streams

        for (Neuron neuron : inputs) {
            if (neuron != null){
                neuron.propagate();
            }
        }

        for (Neuron neuron : internals) {
            if (neuron != null){
                neuron.propagate();
            }
        }
    }

    // Pruning

    private boolean canReachOutput(Neuron neuron, Set<Neuron> visited) {
        if (neuron == null) return false;

        // If we've already visited this neuron, skip exploring its connections to avoid infinite loops
        if (visited.contains(neuron)) {
            return false;
        }

        // Mark the neuron as visited to prevent cycles
        visited.add(neuron);

        // If this neuron is an output, it can reach an output
        for (Neuron output : outputs) {
            if (neuron == output) {
                return true;
            }
        }

        // Explore all outgoing connections
        for (Neuron next : neuron.getOutputConnections().keySet()) {
            if (canReachOutput(next, visited)) {
                return true; // Found a path to an output neuron
            }
        }

        // If no path to an output neuron was found, return false
        return false;
    }

    private boolean inputCanReach(Neuron neuron, Set<Neuron> visited) {
        if (neuron == null) return false;

        // If we've already visited this neuron, skip exploring its connections to avoid infinite loops
        if (visited.contains(neuron)) {
            return false;
        }

        // Mark the neuron as visited to prevent cycles
        visited.add(neuron);

        // If this neuron is an input, an input can reach it
        for (Neuron input : inputs) {
            if (neuron == input) {
                return true;
            }
        }

        // Explore all incoming connections
        for (Neuron prev : neuron.getInputConnections()) {
            if (inputCanReach(prev, visited)) {
                return true; // Found a path to an input neuron
            }
        }

        // If no path to an input neuron was found, return false
        return false;
    }

    private void prune() {
    // Remove all internal neurons that do not have a path to output neurons or to input neurons
    for (int i = 0; i < internals.length; i++) {
        if (internals[i] == null) continue;

        if (!canReachOutput(internals[i], new HashSet<>()) || !inputCanReach(internals[i], new HashSet<>())) {
            // Remove all connections into it
            for (Neuron sourceNeuron : internals[i].getInputConnections()) {
                System.out.println("Removing connection from " + sourceNeuron + " to " + internals[i]);
                sourceNeuron.getOutputConnections().remove(internals[i]);
            }

            // Remove all connections from it
            for (Neuron sinkNeuron : internals[i].getOutputConnections().keySet()) {
                System.out.println("Removing connection from " + internals[i] + " to " + sinkNeuron);
                System.out.println("Before:");
                sinkNeuron.printInputConnections();
                sinkNeuron.getInputConnections().remove(internals[i]);
                System.out.println("After:");
                sinkNeuron.printInputConnections();
                System.out.println();
            }

            // Remove the neuron itself
            System.out.println("Removing " + internals[i]);
            internals[i] = null;
        }
    }

    // Prune input neurons that cannot reach any output
    for (int i = 0; i < inputs.length; i++) {
        if (inputs[i] == null) continue;

        if (inputs[i].getOutputConnections().isEmpty()) {
            System.out.println("Removing " + inputs[i]);
            inputs[i] = null;
        }
    }

    // Prune output neurons that cannot be reached by any input
    for (int i = 0; i < outputs.length; i++) {
        if (outputs[i] == null) continue;

        if (outputs[i].getInputConnections().isEmpty()) {
            System.out.println("Removing " + outputs[i]);
            outputs[i] = null;
        }
    }
}

    // visualization

    public void toGraphviz(String fileName) throws IOException {
        String fullName = "build/generated/brainviz/dot/" + fileName + ".dot";
        StringBuilder dot = new StringBuilder();
        dot.append("digraph Brain {\n");
    
        // Add neurons based on their existence in the genome
        Map<Neuron, String> neuronLabels = new HashMap<>();
    
        // Add input neurons
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i] != null) {
                // String label = "In" + i + "\\n" + String.format("%.2f", inputs[i].getActivationValue());
                String label = "In" + i + "\\n" + inputs[i].toShortString();
                neuronLabels.put(inputs[i], "In" + i);
                dot.append("  ").append("In" + i)
                    .append(" [label=\"").append(label)
                    .append("\", shape=circle, style=filled, color=lightblue, fixedsize=true, width=0.5, fontsize=8];\n");
            }
        }
    
        // Add internal neurons
        for (int i = 0; i < internals.length; i++) {
            if (internals[i] != null) {
                // String label = i + "\\n" + String.format("%.2f", internals[i].getActivationValue());
                String label = "" + i + "\\n" + internals[i].toShortString();
                neuronLabels.put(internals[i], "" + i);
                dot.append("  ").append(i)
                    .append(" [label=\"").append(label)
                    .append("\", shape=circle, style=filled, color=gray, fixedsize=true, width=0.5, fontsize=8];\n");
            }
        }
    
        // Add output neurons
        for (int i = 0; i < outputs.length; i++) {
            if (outputs[i] != null) {
                // String label = "Out" + i + "\\n" + String.format("%.2f", outputs[i].getActivationValue());
                String label = "Out" + i + "\\n" + outputs[i].toShortString();
                neuronLabels.put(outputs[i], "Out" + i);
                dot.append("  ").append("Out" + i)
                    .append(" [label=\"").append(label)
                    .append("\", shape=circle, style=filled, color=orange, fixedsize=true, width=0.5, fontsize=8];\n");
            }
        }
    
        // Add connections for all neurons
        for (Neuron neuron : neuronLabels.keySet()) {
            for (Map.Entry<Neuron, Float> connection : neuron.getOutputConnections().entrySet()) {
                Neuron target = connection.getKey();
                float weight = Float.parseFloat(String.format("%.2f", connection.getValue()));
                if (neuronLabels.containsKey(target)) {
                    // Determine color and thickness based on weight
                    String color = weight > 0 ? "lightgreen" : "orangered1";
                    float thickness = Math.max(0.1f, Math.min(5.0f, Math.abs(weight) * 2)); // Normalize thickness
    
                    dot.append("  ")
                        .append(neuronLabels.get(neuron))
                        .append(" -> ")
                        .append(neuronLabels.get(target))
                        .append(" [color=")
                        .append(color)
                        .append(", penwidth=")
                        .append(thickness)
                        .append(", label=\"")
                        .append(weight)
                        .append("\", fontsize=6")
                        .append("];\n");
                }
            }
        }
    
        dot.append("}\n");
    
        // Write to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullName))) {
            writer.write(dot.toString());
        }
    }    
}
