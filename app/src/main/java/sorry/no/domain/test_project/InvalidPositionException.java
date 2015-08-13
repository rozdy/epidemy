package sorry.no.domain.test_project;

/**
 * Created by hex on 8/13/2015.
 */
public class InvalidPositionException extends Exception {
    public InvalidPositionException (int position) {
        super("Can't calculate coordinates with position " + position + " on the board.");
    }
}
