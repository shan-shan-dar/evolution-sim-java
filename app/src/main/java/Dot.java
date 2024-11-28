
import java.io.IOException;

public class Dot {
    private final int id;
    private final Genome genome;
    private final Brain brain;

    private boolean alive;
    private Coord position;
    private Coord lastPosition;
    private int sensingRadius;
    private float responsiveness;

    public Dot(int id, Coord birthPlace, Genome genome) throws IOException{
        this.genome = genome;
        this.brain = new Brain(genome);

        alive = true;
        this.id = id;
        this.position = birthPlace;
        lastPosition = birthPlace;
        sensingRadius = 10;
        responsiveness = 0.5f;
    }

    public void sense() {

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

    public boolean isAlive() {
        return alive;
    }

    public void die() {
        alive = false;
    }

    public Coord getPosition() {
        return position;
    }
    
    public Coord getLastPosition() {
        return lastPosition;
    }

    public void move(int dx, int dy) {
        lastPosition = position;
        position = position.move(dx, dy);
    }  

    public boolean isSameAs(Dot another){
        return this.id == another.id;
    }
}
