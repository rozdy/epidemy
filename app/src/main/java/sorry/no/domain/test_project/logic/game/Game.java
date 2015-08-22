package sorry.no.domain.test_project.logic.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import sorry.no.domain.test_project.Options;
import sorry.no.domain.test_project.logic.board.Board;
import sorry.no.domain.test_project.logic.cell.InvalidCellException;
import sorry.no.domain.test_project.logic.player.Player;

/**
 * The Game class would be a Singletone. It means we have a single instance of a game available through the whole app
 */
public class Game {

    private static Game instance;

    public static final int GAME_STATE_NOT_STARTED = 0;
    public static final int GAME_STATE_STARTED = 1;
    public static final int GAME_STATE_FINISHED = 2;
    public static final int GAME_FINISH_SURRENDER = 0;
    public static final int GAME_FINISH_NO_MOVES = 1;
    public static final int GAME_FINISH_NO_MARKS = 2;
    public static final int GAME_NOT_FINISHED = -1;

    private int gameState;
    private GameStats stats;

    {
        gameState = GAME_STATE_NOT_STARTED;
    }

    private List<Player> players;
    private Board board;
    private int currentTurn;

    private int activePlayer, numberOfMoves, playersNumber, maxNumberOfMoves;

    private Game() {
        Options options = Options.getInstance();
        board = new Board(options.getBoardOptions());
        players = new ArrayList<>();
        playersNumber = options.getGameOptions().getNumberOfPlayers();
        maxNumberOfMoves = options.getGameOptions().getNumberOfMoves();
        for (int i = 0; i < playersNumber; i++) {
            players.add(options.getUsersOptions().getPlayer(i)); //Todo change to deep copying here
        }
        activePlayer = 0;
        refillNumberOfMoves();
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

    private void refillNumberOfMoves() {
        numberOfMoves = maxNumberOfMoves;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int turn) {
        currentTurn = turn;
    }

    public int getMaxNumberOfMoves() {
        return maxNumberOfMoves;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    private void decNumberOfMoves() throws InvalidMoveException {
        if (numberOfMoves > 0) {
            numberOfMoves--;
        } else {
            throw new InvalidMoveException(Game.getInstance().getActivePlayer());
        }
    }

    private void nextActivePlayer() {
        if (activePlayer == playersNumber - 1) {
            currentTurn++;
        }
        activePlayer = getNextPlayer();
        refillNumberOfMoves();
    }

    private int getNextPlayer() {
        return (activePlayer + 1) % playersNumber;
    }

    private int checkGameFinish() {
        Integer[][] map = getBoard().buildMovesMap(activePlayer);
        boolean noMoves = true;
        for (Integer[] row : map) {
            if (Arrays.asList(row).contains(Board.MARK_AVAILABLE) || Arrays.asList(row).contains(Board.WALL_AVAILABLE)) {
                noMoves = false;
                break;
            }
        }
        if (noMoves) {
            return GAME_FINISH_NO_MOVES;
        }
        if (getCurrentTurn() > 0 && getBoard().getMarksList(getNextPlayer()).size() == 0) {
            nextActivePlayer();
            return GAME_FINISH_NO_MARKS;
        }
        return GAME_NOT_FINISHED;
    }

    public int makeAMove(int activePlayer, int x, int y) throws InvalidMoveException, InvalidCellException {
        if (activePlayer != this.activePlayer) {
            throw new InvalidMoveException(activePlayer, x, y, "wrong Active Player, the correct one is " + this.activePlayer);
        }
        int moveState = getBoard().markCell(activePlayer, x, y);
        switch (moveState) {
            case Board.MARK_PLACED:
            case Board.WALL_PLACED:
                int finishedState = decNumberOfMovesAndCheckFinished();
                if (finishedState != GAME_NOT_FINISHED) {
                    return finishedState;
                } else {
                    return moveState;
                }
            default:
                return moveState;
        }
    }

    private int decNumberOfMovesAndCheckFinished() throws InvalidMoveException {
        decNumberOfMoves();
        if (getNumberOfMoves() == 0) {
            nextActivePlayer();
        }
        return checkGameFinish();
    }

    public static void finish(int reason) {
        Game.instance.stats = new GameStats(Game.getInstance(), reason);
        Game.instance.gameState = GAME_STATE_FINISHED;
        Player.resetIdCounter();
    }

    public GameStats getStats() {
        return stats;
    }

    public static void stop() {
        instance = null;
    }
}
