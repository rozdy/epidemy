package sorry.no.domain.test_project;

/**
 * Created by hex on 7/22/2015.
 */
public class InvalidCellException extends Exception {

    public InvalidCellException(Cell cell, String description) {
        super(description + "\nOn cell: " + cell);
    }
}