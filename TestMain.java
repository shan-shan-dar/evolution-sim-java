import java.io.IOException;

public class TestMain {

    public static void main(String[] args) throws IOException, InterruptedException {
        
        Genome g = Genome.randomGenome();
        Brain b1 = new Brain(g);
        b1.toGraphviz("afterSoftPruning");
        Brain b2 = new Brain(g, true);
        b2.toGraphviz("afterHardPruning");
        
        renderDotFile("beforePruning");
        renderDotFile("afterSoftPruning");
        renderDotFile("afterHardPruning");
    }

    private static void renderDotFile(String inputDotFile, String outputPngFile) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("dot", "-Tpng", inputDotFile, "-o", outputPngFile);
        Process process = processBuilder.start();
        int exitCode = process.waitFor(); // Wait for the process to finish
        if (exitCode != 0) {
            System.err.println("Error rendering " + inputDotFile + ". Exit code: " + exitCode);
        }
    }

    private static void renderDotFile(String fileName) throws IOException, InterruptedException {
        renderDotFile("brainviz/dot/" + fileName + ".dot", "brainviz/" + fileName + ".png");
    }
    
}
