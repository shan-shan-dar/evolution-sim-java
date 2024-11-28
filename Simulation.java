
import java.util.ArrayList;
import java.util.Random;

public class Simulation {
    private static final int WORLD_SIZE_X = 150;
    private static final int WORLD_SIZE_Y = 150;
    private static final int STARTING_POPULATION = 100;

    private static final int SIM_STEPS_PER_GEN = 100;

    private static final Random random = new Random();

    private World world;
    private ArrayList<Dot> dots;
    private int generation;
    private int simStep;

    public Simulation(){
        world = new World(WORLD_SIZE_X, WORLD_SIZE_Y);
        dots = new ArrayList<>(STARTING_POPULATION);
        generation = 0;
        simStep = 0;
    }

    public void initializeGeneration(){

    }

    public void playGeneration(){

    }

    public void playSimStep(){

    }

    public static void main(String[] args) {
        Simulation sim = new Simulation();
    }
}
