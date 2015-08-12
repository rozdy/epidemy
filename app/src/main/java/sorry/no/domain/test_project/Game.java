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

    public static final int GAME_FINISH_SURRENDER = 0;

    private int gameState;
    private GameStats stats;

    {
        gameState = GAME_STATE_NOT_STARTED;
    }

    private List<Player> players;
    private Board board;
    private int currentTurn;

    private int activePlayer, numberOfMoves;

    private Game() {
        board = new Board();
        players = new ArrayList<>();
        for (int i = 0; i < DEFAULT_PLAYERS_NUMBER; i++) {
            players.add(new Player(DEFAULT_PLAYER_COLORS[i % DEFAULT_PLAYER_COLORS.length]));
        }
        activePlayer = 0;
        numberOfMoves = DEFAULT_MOVES_NUMBER;
        currentTurn = 0;
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

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void makeAMove(int activePlayer, int x, int y) throws InvalidMoveException {
        if (activePlayer != this.activePlayer) {
            throw new InvalidMoveException(activePlayer, x, y, "wrong Active Player, the correct one is " + this.activePlayer);
        }
        if (numberOfMoves <= 0) {
            throw new InvalidMoveException(activePlayer, x, y, "Active Player is out of moves and the turn wasn't passed forward.");
        }
    }

    public static void finish(int reason) {
        Game.instance.stats = new GameStats(Game.getInstance(), reason);
        Game.instance.gameState = GAME_STATE_FINISHED;
        Player.resetIdCounter();
    }

    public GameStats getStats() {
        return stats;
    }
}
