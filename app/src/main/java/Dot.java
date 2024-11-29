
import java.io.IOException;

public class Dot {
    private final int id;
    private final Genome genome;
    private final Brain brain;

    private Coord position;
    private Coord lastPosition;
    private int age;
    private int sensingRadius;
    private float responsiveness; // between 0 and 1
    private float oscillatorPeriod;

    public Dot(int id, Coord birthPlace, Genome genome) throws IOException{
        this.genome = genome;
        this.brain = new Brain(genome);

        this.id = id;
        this.position = birthPlace;
        lastPosition = birthPlace;
        sensingRadius = 10;
        responsiveness = 0.5f;
        oscillatorPeriod = 10;
    }

    public void sense(World world) {
        brain.sense(this, world);
    }

    public void process(){
        brain.feedForward();
    }

    public void act(World world) {
        brain.act(this, world);
    }

    public int getId() {
        return id;
    }

    public Genome getGenome() {
        return genome;
    }

    public Brain getBrain() {
        return brain;
    }

    public Coord getPosition() {
        return position;
    }
    
    public Coord getLastPosition() {
        return lastPosition;
    }

    public int getAge() {
        return age;
    }

    public float getOscillatorPeriod() {
        return oscillatorPeriod;
    }

    public void setOscillatorPeriod(float newOscillatorPeriod) {
        oscillatorPeriod = newOscillatorPeriod;
    }

    public void move(int dx, int dy) {

        if (position.x + dx >= Simulation.WORLD_SIZE_X || position.x + dx < 0 || position.y + dy >= Simulation.WORLD_SIZE_Y ||position.y + dy < 0) {
            return;
        }

        lastPosition = position;
        position = position.move(dx, dy);
    }  

    public boolean isSameAs(Dot another){
        return this.id == another.id;
    }

    public void setSensingRadius(int sensingRadius) {
        this.sensingRadius = sensingRadius;
    }

    public int getSensingRadius() {
        return sensingRadius;
    }

    public void setResponsiveness(float responsiveness) {
        this.responsiveness = responsiveness;
    }

    public float getResponsiveness() {
        return responsiveness;
    }
}
