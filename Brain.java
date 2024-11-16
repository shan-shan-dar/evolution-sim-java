

public class Brain {
    public static final int INPUT = 1;
    public static final int OUTPUT = 1;
    public static final int INTERNAL = 0;

    /*
     * 0. x position
     * 1. y position
     * 2. clock (simulation step #)
     */
    public static final int NUM_INPUTS = 3;

    /*
     * 0. move randomly
     * 1. x movement
     * 2. y movement
     */
    public static final int NUM_OUTPUTS = 3;

    public static final int NUM_INTERNALS = 2;


    private final Neuron[] inputs;
    private final Neuron[] internals;
    private final Neuron[] outputs;
    
    public Brain(Genome genome){
        inputs = new Neuron[NUM_INPUTS];
        internals = new Neuron[NUM_INTERNALS];
        outputs = new Neuron[NUM_OUTPUTS];

        connect(genome);
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
                case INPUT -> sinkArray = outputs;
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
        }
    }

}
