import java.util.ArrayList;
import java.util.Random;

public class SimStep {
    private static final Random random = new Random();

    public static void run(World world, ArrayList<Dot> dots){
        // do all running logic based on world and dots, and consequently modify world and dots as needed
        for (Dot dot : dots) {
            dot.sense(world);
            dot.process();
            dot.act(world);
        }
    }
}
