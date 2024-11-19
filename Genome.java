
import java.util.ArrayList;
import java.util.Random;

public class Genome {

    public static final Random random = new Random();

    // inclusive range
    public static final int MAX_GENES = 12;
    public static final int MIN_GENES = 4;

    private final ArrayList<Gene> genes;

    public Genome(){
        this.genes = new ArrayList<>();
    }

    public ArrayList<Gene> getGenes() {
        return genes;
    }

    public void addGene(Gene gene){
        if (genes.size() < MAX_GENES){
            genes.add(gene);
        }
    }

    public static Genome randomGenome(){
        Genome genome = new Genome();
        for (int i = 0; i < MIN_GENES + random.nextInt(MAX_GENES - MIN_GENES + 1); i++) {
            genome.addGene(Gene.randomGene());
        }
        return genome;
    }

    @Override
    public String toString(){
        String genome = "";
        for (Gene gene : genes) {
            genome += gene.toString();
            genome += '\n';
        }
        return genome;
    }
}
