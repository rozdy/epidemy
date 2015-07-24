package sorry.no.domain.test_project;

/**
 * Created by hex on 7/22/2015.
 */
public class Cell {

    public static final int EMPTY_CELL = 0;
    public static final int MARK_CELL = 1;
    public static final int WALL_CELL = 2;

    private int state;
    private int ownerId;

    public Cell() {
        state = EMPTY_CELL;
        ownerId = -1;
    }

    public boolean isEmpty() {
        return (state == EMPTY_CELL);
    }

    public boolean isMarked() {
        return (state == MARK_CELL);
    }

    public boolean isWall() {
        return (state == WALL_CELL);
    }

    public int getOwnerId() {
        return ownerId;
    }


    public void mark(int newOwnerId) throws InvalidCellException {
        if (isEmpty()) {
            state = MARK_CELL;
            ownerId = newOwnerId;
        } else {
            throw new InvalidCellException(this, "Can't mark cell. It's not empty!");
        }
    }

    public void wall(int newOwnerId) throws InvalidCellException {
        if (isMarked() && ownerId != newOwnerId) {
            state = WALL_CELL;
            ownerId = newOwnerId;
        } else {
            throw new InvalidCellException(this, "Can't build a wall in this cell. It's not marked by other players!");
        }

    }

}
