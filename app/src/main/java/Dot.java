
import java.io.IOException;

public class Dot {
    private final int id;
    private final Genome genome;
    private final Brain brain;

    private Coord position;
    private Coord lastPosition;
    private int age;
    private int sensingRadius;
    private float responsiveness;
    private float oscillatorPeriod;

    public Dot(int id, Coord birthPlace, Genome genome) throws IOException{
        this.genome = genome;
        this.brain = new Brain(genome);

        this.id = id;
        this.position = birthPlace;
        lastPosition = birthPlace;
        sensingRadius = 10;
        responsiveness = 0.5f;
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

    public void move(int dx, int dy) {
        lastPosition = position;
        position = position.move(dx, dy);
    }  

    public boolean isSameAs(Dot another){
        return this.id == another.id;
    }
}
