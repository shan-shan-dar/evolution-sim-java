import java.util.HashMap;
import java.util.Map;

public class Neuron {
    private float inputSum;
    private float activationValue;

    private final HashMap<Neuron, Float> outputConnections;

    public Neuron(){
        this.inputSum = 0.0f;
        this.activationValue = 0.0f;
        this.outputConnections = new HashMap<>();
    }

    public void addOutputConnection(Neuron target, float weight) {
        outputConnections.put(target, weight);
    }

    public void receiveInput(float input, float weight) {
        inputSum += input * weight;
    }

    public void activate() {
        activationValue = (float) Math.tanh(inputSum); // Example activation function
        inputSum = 0.0f;
    }

    public void propagate() {
        for (Map.Entry<Neuron, Float> connection : outputConnections.entrySet()) {
            Neuron targetNeuron = connection.getKey();
            float weight = connection.getValue();
            targetNeuron.receiveInput(activationValue, weight);
        }
    }

    public float getActivationValue() {
        return activationValue;
    }

    public void printConnections() {
        for (Map.Entry<Neuron, Float> entry : outputConnections.entrySet()) {
            System.out.println("Connected to Neuron@" + entry.getKey().hashCode() + " with weight " + entry.getValue());
        }
    }

    @Override
    public String toString() {
        return "Neuron@" + this.hashCode() + " [activation=" + activationValue + "]";
    }
}
