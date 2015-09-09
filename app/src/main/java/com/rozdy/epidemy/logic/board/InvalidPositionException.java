package com.rozdy.epidemy.logic.board;

/**
 * Created by hex on 8/13/2015 in the name of the Emperor!
 */
public class InvalidPositionException extends Exception {
    public InvalidPositionException(int position) {
        super("Can't calculate coordinates with position " + position + " on the board.");
    }
}
