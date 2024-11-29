import java.util.ArrayList;
import java.util.Random;

import org.json.JSONException;

public class Generation {
    private static final Random random = new Random();

    public static VisualizationData run(World world, ArrayList<Dot> dots) throws JSONException{

        VisualizationData vizData = new VisualizationData();
        int simStep = 0;

        // add simStep 0
        vizData.addStep(simStep, dots);

        for (int i = 0; i < Simulation.SIM_STEPS_PER_GEN; i++) {
            SimStep.run(world, dots);
            vizData.addStep(++simStep, dots);
        }

        return vizData;
    }
}
