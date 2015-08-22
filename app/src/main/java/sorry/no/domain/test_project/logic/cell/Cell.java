package sorry.no.domain.test_project.logic.cell;

/**
 * Created by hex on 7/22/2015 in the name of the Emperor!
 */
public class Cell {

    public static final int ERROR_CELL = -1;
    public static final int EMPTY_CELL = 0;
    public static final int MARK_CELL = 1;
    public static final int WALL_CELL = 2;

    private int state;
    private int ownerId;
    private int x;
    private int y;

    public Cell(int x, int y) {
        state = EMPTY_CELL;
        ownerId = -1;
        this.x = x;
        this.y = y;
    }

    public boolean isEmpty() {
        return (state == EMPTY_CELL);
    }

    public boolean isMarked() {
        return (state == MARK_CELL);
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

    public void setState(int state) {
        this.state = state;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getState() {
        return state;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;

        Cell cell = (Cell) o;

        if (getState() != cell.getState()) return false;
        if (getOwnerId() != cell.getOwnerId()) return false;
        if (getX() != cell.getX()) return false;
        return getY() == cell.getY();

    }

    @Override
    public int hashCode() {
        int result = getState();
        result = 31 * result + getOwnerId();
        result = 31 * result + getX();
        result = 31 * result + getY();
        return result;
    }

    @Override
    public String toString() {
        return "Cell{" +
                "state=" + state +
                ", ownerId=" + ownerId +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
