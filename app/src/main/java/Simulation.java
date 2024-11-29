import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.json.JSONException;

public class Simulation {
    public static final int WORLD_SIZE_X = 150;
    public static final int WORLD_SIZE_Y = 150;
    public static final int STARTING_POPULATION = 100;

    public static final int SIM_STEPS_PER_GEN = 100;
    public static final int MAX_GEN = 100;

    private static final Random random = new Random();

    private World world;
    private ArrayList<Dot> dots;
    private int generation;
    private int simStep;

    public Simulation() throws IOException{
        world = new World(WORLD_SIZE_X, WORLD_SIZE_Y);
        dots = new ArrayList<>(STARTING_POPULATION);

        for (int i = 0; i < STARTING_POPULATION; i++) {
            dots.add(new Dot(i, world.findRandomEmptyLocation(), Genome.randomGenome()));
        }

        generation = 0;
        simStep = 0;
    }

    public void run() throws JSONException{
        for (int i = 0; i < MAX_GEN; i++) {
            VisualizationData gen = Generation.run(world, dots);
            // save gen to file and send it wherever for later rendering
        }
    }

    

    public static void main(String[] args) throws IOException, JSONException {
        
    }
}
