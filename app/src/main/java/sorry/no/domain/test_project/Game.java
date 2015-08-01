package sorry.no.domain.test_project;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * The Game class would be a Singletone. It means we have a single instance of a game available through the whole app
 */
public class Game {

    public static final int[] DEFAULT_PLAYER_COLORS = {Color.RED, Color.BLUE,
            Color.GREEN, Color.YELLOW};

    private static Game instance;

    public static final int DEFAULT_MOVES_NUMBER = 3;
    public static final int DEFAULT_PLAYERS_NUMBER = 2;

    public static final int GAME_STATE_NOT_STARTED = 0;
    public static final int GAME_STATE_STARTED = 1;
    public static final int GAME_STATE_FINISHED = 2;

    private int gameState;
    private GameStats stats;

    {
        gameState = GAME_STATE_NOT_STARTED;
    }

    private List<Player> players;
    private Board board;

    private int activePlayer, numberOfMoves;

    private Game() {
        board = new Board();
        players = new ArrayList<>();
        for (int i = 0; i < DEFAULT_PLAYERS_NUMBER; i++) {
            players.add(new Player(DEFAULT_PLAYER_COLORS[i % DEFAULT_PLAYER_COLORS.length]));
        }
        activePlayer = 0;
        numberOfMoves = DEFAULT_MOVES_NUMBER;
        gameState = GAME_STATE_STARTED;
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public static void init() {
        instance = new Game();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(int number) {
        return players.get(number);
    }

    public Board getBoard() {
        return board;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    public static void finish() {
        Game.instance.stats = new GameStats(Game.instance);
        Game.instance.gameState = GAME_STATE_FINISHED;
        Player.resetIdCounter();
    }

    public GameStats getStats() {
        return stats;
    }
}
