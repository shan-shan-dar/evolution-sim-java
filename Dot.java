public class Dot {
    private final int id;
    private final Genome genome;
    private final Brain brain;

    private boolean alive;
    private Coord position;

    public Dot(int id, Coord birthPlace, Genome genome){
        this.genome = genome;
        this.brain = new Brain(genome);

        alive = true;
        this.id = id;
        this.position = birthPlace;
    }

    public int getId() {
        return id;
    }

    public Coord getPosition() {
        return position;
    }

    public void setPosition(Coord position) {
        this.position = position;
    }
}
