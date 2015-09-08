package com.rozdy.epidemy.logic.game;

/**
 * Created by hex on 8/16/2015 in the name of the Emperor!
 */
public class GameOptions {
    private int numberOfMoves, numberOfPlayers;

    public static final int DEFAULT_NUMBER_OF_MOVES = 3;
    public static final int MIN_NUMBER_OF_MOVES = 3;
    public static final int MAX_NUMBER_OF_MOVES = 10;
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

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }
}
