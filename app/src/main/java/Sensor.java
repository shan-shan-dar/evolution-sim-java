public class Sensor {

    /*
     * 0. x position
     * 1. y position
     * 2. last x movement
     * 3. last y movement
     * 4. age
     * 5. random
     * 6. oscillator
     * 7. blockage in forward (most recent) direction in sensing radius
     * 8. distance from x borders
     * 9. distance from y borders
     * 10. population density in sensing radius
     */

    public enum SenseType {
        X_POSITION,
        Y_POSITION,
        LAST_X_POSITION,
        LAST_Y_POSITION,
        AGE,
        RANDOM,
        OSCILLATOR,
        X_BORDER_DISTANCE,
        Y_BORDER_DISTANCE;

        // FORWARD_BLOCKAGE,
        // POPULATION_DENSITY;

        // Add more sense types as needed

        public int getInputId() {
            return this.ordinal();
        }
    }

    public static float getSenseValue(Dot dot, World world, SenseType sense) {

        System.out.println(sense);

        switch (sense) {
            case X_POSITION: {
                System.out.println(dot.getPosition().x);
                System.out.println(normalize(dot.getPosition().x, 0, world.getSizeX()));
                System.out.println();
                return normalize(dot.getPosition().x, 0, world.getSizeX());
            }

            case Y_POSITION: {
                System.out.println(dot.getPosition().y);
                System.out.println(normalize(dot.getPosition().y, 0, world.getSizeY()));
                System.out.println();
                return normalize(dot.getPosition().y, 0, world.getSizeY());
            }

            case LAST_X_POSITION: {
                System.out.println(dot.getLastPosition().x);
                System.out.println(normalize(dot.getLastPosition().x, 0, world.getSizeX()));
                System.out.println();
                return normalize(dot.getLastPosition().x, 0, world.getSizeX());
            }

            case LAST_Y_POSITION: {
                System.out.println(dot.getLastPosition().y);
                System.out.println(normalize(dot.getLastPosition().y, 0, world.getSizeY()));
                System.out.println();
                return normalize(dot.getLastPosition().y, 0, world.getSizeY());
            }

            case AGE: {
                System.out.println(dot.getAge());
                System.out.println(normalize(dot.getAge(), 0, Simulation.SIM_STEPS_PER_GEN));
                System.out.println();
                return normalize(dot.getAge(), 0, Simulation.SIM_STEPS_PER_GEN);
            }

            case RANDOM: {
                float f = (float) (Math.random() * 2 - 1);
                System.out.println(f);
                System.out.println();
                return f;
            }

            case OSCILLATOR: {
                float f = (float) Math.sin(2 * Math.PI * dot.getAge() / dot.getOscillatorPeriod());
                System.out.println(f);
                System.out.println();
                return f; 
            }

            case X_BORDER_DISTANCE: {
                float x = dot.getPosition().x;
                float leftDistance = x; // Distance to left border (0)
                float rightDistance = world.getSizeX() - x; // Distance to right border
                float rawDistance = Math.min(leftDistance, rightDistance); // Closest distance
                float normalized = rawDistance / (world.getSizeX() / 2); // Normalize to [0, 1]

                System.out.println(x);
                System.out.println(rawDistance);
                System.out.println((leftDistance < rightDistance ? -1 : 1) * normalized);
                System.out.println();

                // Make it negative if closer to left border, positive if closer to right
                return (leftDistance < rightDistance ? -1 : 1) * normalized;
            }

            case Y_BORDER_DISTANCE: {
                float y = dot.getPosition().y;
                float downDistance = y; // Distance to down border (0)
                float upDistance = world.getSizeY() - y; // Distance to up border
                float rawDistance = Math.min(downDistance, upDistance); // Closest distance
                float normalized = rawDistance / (world.getSizeY() / 2); // Normalize to [0, 1]

                System.out.println(y);
                System.out.println(rawDistance);
                System.out.println((downDistance < upDistance ? -1 : 1) * normalized);
                System.out.println();
                
                // Make it negative if closer to left border, positive if closer to right
                return (downDistance < upDistance ? -1 : 1) * normalized;
            }

            default: throw new IllegalArgumentException("Unknown, unselected, or unimplemented sense: " + sense);
        }
    }

    private static float normalize(float value, float min, float max) {
        if (max == min) return 0; // Avoid division by zero
        return 2 * ((value - min) / (max - min)) - 1;
    }
}
