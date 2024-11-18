import java.io.IOException;

public class TestMain {
    public static void main(String[] args) throws IOException {
        Brain b = new Brain(Genome.randomGenome());

        b.toGraphviz("test");
    }
    
}
