package sorry.no.domain.test_project;

/**
 * Created by Sergejj on 01/08/2015 in the name of the Emperor!
 */
public class InvalidMoveException extends Exception {
    public InvalidMoveException (int activePlayer, int x, int y, String message) {
        super("Can't perform move for player #" + activePlayer + " on position " + x + ", " + y + " because of: " + message);
    }
}
