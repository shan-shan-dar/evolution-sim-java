import java.io.IOException;

public class TestMain {

    public static void main(String[] args) throws IOException, InterruptedException {
        
        Genome g = Genome.randomGenome();

        System.out.println(g);
        Brain b = new Brain(g);
        
        renderDotFile("beforePruning");
        renderDotFile("afterPruning");
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
        renderDotFile("build/generated/brainviz/dot/" + fileName + ".dot", "build/generated/brainviz/" + fileName + ".png");
    }
    
}
