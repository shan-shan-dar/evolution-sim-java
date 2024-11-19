import java.util.HashMap;
import java.util.Map;

public class Neuron {
    private float inputSum;

    private final HashMap<Neuron, Float> outputConnections;

    public Neuron(){
        this.inputSum = arctanh(0.5f);
        this.outputConnections = new HashMap<>();
    }

    public void addOutputConnection(Neuron target, float weight) {
        outputConnections.put(target, weight);
    }

    public void receiveInput(float input, float weight) {
        inputSum += input * weight;
    }

    public void propagate() {
        for (Map.Entry<Neuron, Float> connection : outputConnections.entrySet()) {
            Neuron targetNeuron = connection.getKey();
            float weight = connection.getValue();
            targetNeuron.receiveInput(getActivationValue(), weight);
        }
    }

    public float getActivationValue() {
        return (float) Math.tanh(inputSum);
    }

    public Map<Neuron, Float> getOutputConnections() {
        return outputConnections;
    }

    public void printConnections() {
        for (Map.Entry<Neuron, Float> entry : outputConnections.entrySet()) {
            System.out.println("Connected to Neuron@" + entry.getKey().hashCode() + " with weight " + entry.getValue());
        }
    }

    @Override
    public String toString() {
        return "Neuron@" + this.hashCode() + " [inputSum=" + inputSum + ", activation=" + getActivationValue() + "]";
    }

    private static float arctanh(float x) {
        if (x <= -1 || x >= 1) {
            throw new IllegalArgumentException("Input for arctanh must be in the range (-1, 1).");
        }
        return (float) (0.5 * Math.log((1 + x) / (1 - x)));
    }

}
