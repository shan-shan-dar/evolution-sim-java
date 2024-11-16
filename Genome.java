
import java.util.ArrayList;
import java.util.Random;

public class Genome {

    public static final Random random = new Random();

    public static final int MAX_GENES = 12;

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

    public Genome randomGenome(){
        Genome genome = new Genome();
        for (int i = 0; i < random.nextInt(MAX_GENES); i++) {
            addGene(Gene.randomGene());
        }
        return genome;
    }
}
