package com.rozdy.epidemy.logic.cell;

/**
 * Created by hex on 7/22/2015 in the name of the Emperor!
 */
public class InvalidCellException extends Exception {

    public InvalidCellException(Cell cell, String description) {
        super(description + "\nOn cell: " + cell);
    }
}