import java.io.IOException;

public class TestMain {

    public static void main(String[] args) throws IOException, InterruptedException {
        Brain b = new Brain(Genome.randomGenome());
        b.feedForward();
        b.toGraphviz("brainviz/ffOnce.dot");
        b.feedForward();
        b.toGraphviz("brainviz/ffTwice.dot");
        
        renderDotFile("brainviz/afterPruning.dot", "brainviz/afterPruning.png");
        renderDotFile("brainviz/ffOnce.dot", "brainviz/ffOnce.png");
        renderDotFile("brainviz/ffTwice.dot", "brainviz/ffTwice.png");
    }

    private static void renderDotFile(String inputDotFile, String outputPngFile) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("dot", "-Tpng", inputDotFile, "-o", outputPngFile);
        Process process = processBuilder.start();
        int exitCode = process.waitFor(); // Wait for the process to finish
        if (exitCode != 0) {
            System.err.println("Error rendering " + inputDotFile + ". Exit code: " + exitCode);
        }
    }
    
}
