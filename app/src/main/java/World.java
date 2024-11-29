import java.util.Random;

public class World{

    private final Random random = new Random();
    
    public static final int EMPTY = 0;

    /*  
     *      3   03  13  23  33 
     *      2   02  12  22  32
     *  ^   1   01  11  21  31
     *  y   0   00  10  20  30
     *        | 0 | 1 | 2 | 3 | 
     *          x >
     */
    private final int sizeX, sizeY;
    private final int[][] data;

    public World(int sizeX, int sizeY){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.data = new int[sizeX][sizeY];
    }

    public boolean isInBounds(Coord pos) {
        return pos.x >= 0 && pos.x < sizeX && pos.y >= 0 && pos.y < sizeY;
    }

    public boolean isEmptyAt(Coord pos) {
        return isInBounds(pos) && get(pos) == EMPTY;
    }

    public int get(Coord pos) {
        if (!isInBounds(pos)) {
            throw new IndexOutOfBoundsException("Coordinates out of bounds");
        }
        return data[pos.x][pos.y];
    }

    // Set a cell value
    public void set(Coord pos, int value) {
        if (!isInBounds(pos)) {
            throw new IndexOutOfBoundsException("Coordinates out of bounds");
        }
        data[pos.x][pos.y] = value;
    }

    public Coord findRandomEmptyLocation() {
        int x, y;
        Coord pos;
        do {
            x = random.nextInt(sizeX);
            y = random.nextInt(sizeY);
            pos = new Coord(x, y);
        } while (!isEmptyAt(pos));
        return pos;
    }

    // Getters for grid dimensions
    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}