import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Neuron {
    private static final float DEFAULT_ACTIVATION = 0.5f;
    
    private float inputSum;

    private final ArrayList<Neuron> inputConnections;
    private final HashMap<Neuron, Float> outputConnections;

    public Neuron(){
        this.inputSum = arctanh(DEFAULT_ACTIVATION);
        this.inputConnections = new ArrayList<>();
        this.outputConnections = new HashMap<>();
    }

    public void addInputConnection(Neuron source) {
        inputConnections.add(source);
    }

    public void addOutputConnection(Neuron target, float weight) {
        outputConnections.put(target, weight);
    }

    public void addToInputSum(float input) {
        inputSum += input;
    }

    // ONLY FOR INPUT NEURONS!!
    public void directSetInputSum(float input) {
        inputSum = input;
    }

    public void propagate() {
        for (Map.Entry<Neuron, Float> connection : outputConnections.entrySet()) {
            Neuron targetNeuron = connection.getKey();
            float weight = connection.getValue();
            targetNeuron.addToInputSum(getActivationValue() * weight);
        }
    }

    public float getActivationValue() {
        return (float) Math.tanh(inputSum);
    }

    public ArrayList<Neuron> getInputConnections() {
        return inputConnections;
    }

    public Map<Neuron, Float> getOutputConnections() {
        return outputConnections;
    }

    public void printInputConnections() {
        for (Neuron entry : inputConnections) {
            System.out.println("Connected from Neuron@" + entry);
        }
    }

    public void printOutputConnections() {
        for (Map.Entry<Neuron, Float> entry : outputConnections.entrySet()) {
            System.out.println("Connected to Neuron@" + entry.getKey().hashCode() + " with weight " + entry.getValue());
        }
    }

    @Override
    public String toString() {
        return "Neuron@" + this.hashCode() + " [inputSum=" + inputSum + ", activation=" + getActivationValue() + "]";
    }

    public static float arctanh(float x) {
        if (x <= -1 || x >= 1) {
            throw new IllegalArgumentException("Input for arctanh must be in the range (-1, 1).");
        }
        return (float) (0.5 * Math.log((1 + x) / (1 - x)));
    }

}
