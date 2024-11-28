import java.util.Random;

public class Gene {
    private static final Random random = new Random();

    // Shift constants for encoding
    private static final int SOURCE_TYPE_SHIFT = 31; // 32nd bit
    private static final int SOURCE_ID_SHIFT = 24;   // 25th-31st bits
    private static final int SINK_TYPE_SHIFT = 23;   // 24th bit
    private static final int SINK_ID_SHIFT = 16;     // 17th-23rd bits
    private static final int WEIGHT_SHIFT = 0;       // 1st-16th bits

    private final int encodedGene;

    // Constructor with individual fields
    public Gene(byte sourceType, byte sourceId, byte sinkType, byte sinkId, short weight) {
        encodedGene = (sourceType << SOURCE_TYPE_SHIFT) |
                      ((sourceId & 0x7F) << SOURCE_ID_SHIFT) | // Ensure 7 bits
                      (sinkType << SINK_TYPE_SHIFT) |
                      ((sinkId & 0x7F) << SINK_ID_SHIFT) | // Ensure 7 bits
                      (weight & 0xFFFF); // Ensure 16 bits
    }

    // Constructor with encoded value
    public Gene(int encodedGene) {
        this.encodedGene = encodedGene;
    }

    // Generate a random gene
    public static Gene randomGene() {
        return new Gene(random.nextInt());
    }

    // Decode methods
    public byte getSourceType() {
        return (byte) ((encodedGene >> SOURCE_TYPE_SHIFT) & 0x01);
    }

    public byte getSourceId() {
        int numNeurons = getSourceType() == Brain.INPUT ? Brain.NUM_INPUTS : Brain.NUM_INTERNALS;
        return (byte) (((encodedGene >> SOURCE_ID_SHIFT) & 0x7F) % numNeurons); // Ensure 7 bits
    }

    public byte getSinkType() {
        return (byte) ((encodedGene >> SINK_TYPE_SHIFT) & 0x01);
    }

    public byte getSinkId() {
        int numNeurons = getSinkType() == Brain.OUTPUT ? Brain.NUM_OUTPUTS : Brain.NUM_INTERNALS;
        return (byte) (((encodedGene >> SINK_ID_SHIFT) & 0x7F) % numNeurons); // Ensure 7 bits
    }

    public float getWeight() {
        short rawWeight = (short) (encodedGene & 0xFFFF); // Extract 16 bits as signed
        return rawWeight / 9000.0f; // Scale to smaller floating-point range
    }

    // Getter for encoded value
    public int getEncodedGene() {
        return encodedGene;
    }

    @Override
    public String toString() {
        return String.format(
            "Gene{SourceType=%d, SourceId=%d, SinkType=%d, SinkId=%d, Weight=%.4f, Encoded=0x%s}",
            getSourceType(),
            getSourceId(),
            getSinkType(),
            getSinkId(),
            getWeight(),
            getEncodedGene()
        );
    }
}
