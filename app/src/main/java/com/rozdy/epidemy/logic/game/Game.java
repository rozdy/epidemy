package com.rozdy.epidemy.logic.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rozdy.epidemy.Options;
import com.rozdy.epidemy.logic.board.Board;
import com.rozdy.epidemy.bus.EventBus;
import com.rozdy.epidemy.bus.GameFinishEvent;
import com.rozdy.epidemy.logic.cell.InvalidCellException;
import com.rozdy.epidemy.logic.player.Player;

/**
 * The Game class would be a Singletone. It means we have a single instance of a game available through the whole app
 */
public class Game {

    private static Game instance;

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
            players.add(new Player(options.getUsersOptions().getPlayer(i)));
        }
        activePlayer = 0;
        refillNumberOfMoves();
        currentTurn = 0;
    }

    public static Game getInstance() {
        if (instance == null) {
            init();
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

    public void nextActivePlayer() {
        if (getActivePlayer() == getPlayersNumber() - 1) {
            currentTurn++;
        }
        activePlayer = getNextPlayer();
        refillNumberOfMoves();
        if (activePlayerHasNoMoves()) {
            nextActivePlayer();
        }
    }

    private int getNextPlayer() {
        return (getActivePlayer() + 1) % getPlayersNumber();
    }

    public boolean isGameFinished() {
        int inGamePlayers = 0;
        for (int player = 0; player < getPlayersNumber(); player++) {
            if (getPlayer(player).isInGame()) {
                inGamePlayers++;
            }
        }
        return (inGamePlayers == 1);
    }

    public int makeAMove(int activePlayer, int x, int y) throws InvalidMoveException, InvalidCellException {
        if (activePlayer != this.activePlayer) {
            throw new InvalidMoveException(activePlayer, x, y, "wrong Active Player, the correct one is " + this.activePlayer);
        }
        int moveState = getBoard().markCell(activePlayer, x, y);
        switch (moveState) {
            case Board.MARK_PLACED:
            case Board.WALL_PLACED:
                decNumberOfMovesAndCheckFinished();
                return moveState;
            default:
                return moveState;
        }
    }

    private void decNumberOfMovesAndCheckFinished() throws InvalidMoveException {
        decNumberOfMoves();
        if (playersHasNoMarks()) {
            EventBus.getInstance().post(new GameFinishEvent());
        }
        if (getNumberOfMoves() == 0) {
            nextActivePlayer();
        } else {
            if (activePlayerHasNoMoves()) {
                getPlayer(getActivePlayer()).lose(getCurrentTurn(), Player.PLAYER_HAS_NO_MOVES);
                nextActivePlayer();
            }
        }
    }

    public void finish() {
        Player.resetIdCounter();
    }

    public static void stop() {
        instance = null;
    }

    public boolean activePlayerHasNoMoves() {
        if (!getPlayer(getActivePlayer()).isInGame()) {
            return true;
        } else {
            Integer[][] map = getBoard().buildMovesMap(getActivePlayer());
            boolean noMoves = true;
            for (Integer[] row : map) {
                if (Arrays.asList(row).contains(Board.MARK_AVAILABLE) || Arrays.asList(row).contains(Board.WALL_AVAILABLE)) {
                    noMoves = false;
                    break;
                }
            }
            if (noMoves) {
                getPlayer(getActivePlayer()).lose(currentTurn, Player.PLAYER_HAS_NO_MOVES);
            }
            return noMoves;
        }
    }

    private boolean playersHasNoMarks() {
        boolean allPlayersHasNoMarks = true;
        if (currentTurn == 0) {
            return false;
        }
        for (int player = 0; player < players.size(); player++) {
            if (player == activePlayer) {
                continue;
            }
            if (getCurrentTurn() > 0 && getPlayer(player).isInGame()
                    && getBoard().getMarksList(player).size() == 0) {
                getPlayer(player).lose(currentTurn, Player.PLAYER_HAS_NO_MARKS);
            } else if (getBoard().getMarksList(player).size() != 0) {
                allPlayersHasNoMarks = false;
            }
        }
        return allPlayersHasNoMarks;
    }
}
