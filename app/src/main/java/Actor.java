public class Actor {
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
        CONTINUE_LAST_DIR, //reverse if negative
        SET_OSCILLATOR,
        SET_RESPONSIVENESS;
        // Add more action types as needed

        public int getOutputId() {
            return this.ordinal();
        }
    }

    public static void act(Dot dot, World world, ActionType action, float activationValue) {
        
    }
}
