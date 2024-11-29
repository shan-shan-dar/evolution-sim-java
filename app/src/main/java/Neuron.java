import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Neuron {
    private static final float DEFAULT_ACTIVATION = 0f;
    
    private float activation;

    private final HashMap<Neuron, Float> inputConnections;
    private final ArrayList<Neuron> outputConnections;

    public Neuron(){
        this.activation = DEFAULT_ACTIVATION;
        this.inputConnections = new HashMap<>();
        this.outputConnections = new ArrayList<>();
    }

    public void addOutputConnection(Neuron sink) {
        if (!outputConnections.contains(sink)) {
            outputConnections.add(sink);
        }
    }

    public void addInputConnection(Neuron from, float weight) {
        inputConnections.put(from, weight);
    }

    public void setActivation(float desiredActivation) {
        activation = desiredActivation;
    }

    public float getActivationValue() {
        return (float) activation;
    }

    public ArrayList<Neuron> getOutputConnections() {
        return outputConnections;
    }

    public Map<Neuron, Float> getInputConnections() {
        return inputConnections;
    }

    public void printInputConnections() {
        for (Map.Entry<Neuron, Float> entry : inputConnections.entrySet()) {
            System.out.println(this + "Connected from " + entry + " with weight " + entry.getValue());
        }
    }

    public void printOutputConnections() {
        for (Neuron entry : outputConnections) {
            System.out.println(this + "Connected to " + entry.hashCode());
        }
    }

    @Override
    public String toString() {
        return "Neuron@" + this.hashCode();// + " [inputSum=" + inputSum + ", activation=" + getActivationValue() + "]";
    }

    public String toShortString() {
        return "" + this.hashCode();
    }

    public static float activationFunction (float x){
        return (float) Math.tanh((float)x);
    }

    public static float reverseActivationFunction(float x) {
        //arctanh
        if (x <= -1 || x >= 1) {
            throw new IllegalArgumentException("Input for arctanh must be in the range (-1, 1).");
        }
        return (float) (0.5 * Math.log((1 + x) / (1 - x)));
    }

}
