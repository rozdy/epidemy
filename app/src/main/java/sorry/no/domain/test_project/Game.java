package sorry.no.domain.test_project;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hex on 7/22/2015.
 */
public class Game {

    public static final int[] DEFAULT_PLAYER_COLORS = {Color.RED, Color.BLUE,
            Color.GREEN, Color.YELLOW};


    public static final int DEFAULT_MOVES_NUMBER = 3;
    public static final int DEFAULT_PLAYERS_NUMBER = 2;

    private List<Player> players;
    private Board board;

    private int activePlayer, numberOfMoves;

    public Game() {
        board = new Board();
        players = new ArrayList<Player>();
        for (int i = 0; i < DEFAULT_PLAYERS_NUMBER; i++) {
            players.add(new Player(DEFAULT_PLAYER_COLORS[i % DEFAULT_PLAYER_COLORS.length]));
        }
        activePlayer = 0;
        numberOfMoves = DEFAULT_MOVES_NUMBER;
    }
}
