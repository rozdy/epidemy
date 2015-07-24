package sorry.no.domain.test_project;

/**
 * Created by hex on 7/22/2015.
 */
public class InvalidCellException extends Exception {
    private Cell cell;
    private String descripton;

    public InvalidCellException(Cell cell, String description) {
        super();
        this.cell = cell;
        this.descripton = description;
    }
}