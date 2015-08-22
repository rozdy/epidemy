package sorry.no.domain.test_project.logic.game;

/**
 * Created by Sergejj on 01/08/2015 in the name of the Emperor!
 */
public class InvalidMoveException extends Exception {
    public InvalidMoveException(int activePlayer, int x, int y, String message) {
        super("Can't perform move for player #" + activePlayer + " on position " + x + ", " + y + " because of: " + message);
    }

    public InvalidMoveException(int activePlayer) {
        super("Can't perform move for player #" + activePlayer +
                " because of: Active Player is out of moves and the turn wasn't passed forward.");
    }
}
