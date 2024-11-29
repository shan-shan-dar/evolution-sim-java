
import java.util.Random;

public class Actor {
    private static final Random r = new Random();

    /*
     * 0. x movement
     * 1. y movement
     * 2. continue last direction of movement
     * 3. set oscillator period
     * 4. set responsiveness
     */

    public enum ActionType {
        MOVE_X,
        MOVE_Y,
        CONTINUE_LAST_DIR,
        SET_OSCILLATOR,
        SET_RESPONSIVENESS;
        // Add more action types as needed

        public int getOutputId() {
            return this.ordinal();
        }
    }

    public static void act(Dot dot, World world, Neuron[] outputLayer) {

        // RESPONSIVENESS

        float responsivenessRateFactor = 0.08f; //when both responsiveness and neuron activation are at their maximum, the responsiveness will change by this much per step

        if (outputLayer[ActionType.SET_RESPONSIVENESS.getOutputId()] != null) {

            float activation = outputLayer[ActionType.SET_RESPONSIVENESS.getOutputId()].getActivationValue();

            System.out.println(ActionType.SET_RESPONSIVENESS + ":");
            System.out.println(activation);

            float dotResponsiveness = dot.getResponsiveness(); // Value between 0 and 1
            float change = dotResponsiveness * activation * responsivenessRateFactor;

            // Update the responsiveness, ensuring it stays within [0, 1]
            float newResponsiveness = Math.min(1, Math.max(0, dot.getResponsiveness() + change));

            System.out.println(dotResponsiveness);
            System.out.println(newResponsiveness);
            System.out.println();

            dot.setResponsiveness(newResponsiveness);
        }

        // MOVEMENT

        int dx = 0;
        int dy = 0;

        if (outputLayer[ActionType.CONTINUE_LAST_DIR.getOutputId()] != null) {
            float prevProb = outputLayer[ActionType.CONTINUE_LAST_DIR.getOutputId()].getActivationValue();

            System.out.println(ActionType.CONTINUE_LAST_DIR + ":");
            System.out.println(prevProb);

            int dxPrev = dot.getPosition().x - dot.getLastPosition().x;
            System.out.println(dxPrev);
            int dyPrev = dot.getPosition().y - dot.getLastPosition().y;
            System.out.println(dyPrev);

            if (r.nextFloat() < Math.abs(prevProb)){
                dx = dxPrev * (int) Math.signum(dxPrev);
                dy = dyPrev * (int) Math.signum(dyPrev);
            }
        }
        System.out.println("After CONTINUE_LAST_DIR");
        System.out.println(dx);
        System.out.println(dy);

        if (outputLayer[ActionType.MOVE_X.getOutputId()] != null) {
            float xProb = outputLayer[ActionType.MOVE_X.getOutputId()].getActivationValue();

            System.out.println(ActionType.MOVE_X+ ":");
            System.out.println(xProb);

            if (r.nextFloat() < Math.abs(xProb)){
                dx = (1 * (int) Math.signum(xProb));
            }
        }
        System.out.println("After MOVE_X");
        System.out.println(dx);

        if (outputLayer[ActionType.MOVE_Y.getOutputId()] != null) {
            float yProb = outputLayer[ActionType.MOVE_Y.getOutputId()].getActivationValue();

            System.out.println(ActionType.MOVE_Y + ":");
            System.out.println(yProb);

            if (r.nextFloat() < Math.abs(yProb)){
                dy = (1 * (int) Math.signum(yProb));
            }
        }
        System.out.println("After MOVE_Y");
        System.out.println(dy);

        dot.move(dx, dy);

        // OSCILLATOR

        float oscillatorRateFactor = 5; //when both responsiveness and neuron activation are at their maximum, the oscillatorPeriod will change by this much per step

        if (outputLayer[ActionType.SET_OSCILLATOR.getOutputId()] != null) {

            float activation = outputLayer[ActionType.SET_OSCILLATOR.getOutputId()].getActivationValue();

            System.out.println(ActionType.SET_OSCILLATOR + ":");
            System.out.println(activation);

            float dotResponsiveness = dot.getResponsiveness(); // Value between 0 and 1
            float change = dotResponsiveness * activation * oscillatorRateFactor;

            System.out.println(dot.getOscillatorPeriod());

            // Update the oscillator period, ensuring it stays within [minPeriod, maxPeriod]
            float newOscillatorPeriod = dot.getOscillatorPeriod() + change;
            
            System.out.println(newOscillatorPeriod);
            System.out.println();

            dot.setOscillatorPeriod(newOscillatorPeriod);
        }

    }
}
