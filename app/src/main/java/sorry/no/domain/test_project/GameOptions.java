package sorry.no.domain.test_project;

/**
 * Created by hex on 8/16/2015.
 */
public class GameOptions {
    private int numberOfMoves, numberOfPlayers;

    public static final int DEFAULT_NUMBER_OF_MOVES = 3;
    public static final int DEFAULT_NUMBER_OF_PLAYERS = 2;

    public GameOptions() {
        numberOfMoves = DEFAULT_NUMBER_OF_MOVES;
        numberOfPlayers = DEFAULT_NUMBER_OF_PLAYERS;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
