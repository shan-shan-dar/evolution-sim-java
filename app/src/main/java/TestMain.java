import java.io.IOException;

import org.json.JSONException;

public class TestMain {

    public static void main(String[] args) throws IOException, InterruptedException, JSONException {
        
        Simulation s = new Simulation();

        s.run();

        /* 
        World w = new World(10, 10);

        Genome g = Genome.randomGenome();
        // System.out.println(g);

        Dot d = new Dot(0, w.findRandomEmptyLocation(), g);

        Brain b = d.getBrain();

        d.sense(w);
        b.toGraphviz("afterSensing1");

        d.process();
        b.toGraphviz("afterFF1");

        d.act(w);
        b.toGraphviz("afterActing1");

        //

        d.sense(w);
        b.toGraphviz("afterSensing2");

        d.process();
        b.toGraphviz("afterFF2");

        d.act(w);
        b.toGraphviz("afterActing2");

        renderDotFile("afterPruning");
        renderDotFile("afterSensing1");
        renderDotFile("afterFF1");
        renderDotFile("afterActing1");
        renderDotFile("afterSensing2");
        renderDotFile("afterFF2");
        renderDotFile("afterActing2");

        */
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
