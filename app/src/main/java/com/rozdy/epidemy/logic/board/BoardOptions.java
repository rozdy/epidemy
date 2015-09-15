package com.rozdy.epidemy.logic.board;

import android.app.Activity;
import android.content.SharedPreferences;

import com.rozdy.epidemy.R;

/**
 * Created by hex on 8/16/2015 in the name of the Emperor!
 */
public class BoardOptions {
    private int width, height;
    private boolean showCellNumeration;
    private boolean squareBoard;

    public static final int MIN_WIDTH = 8;
    public static final int MAX_WIDTH = 20;
    public static final int MIN_HEIGHT = 8;
    public static final int MAX_HEIGHT = 20;

    public BoardOptions() {
        width = MIN_WIDTH;
        height = MIN_HEIGHT;
        squareBoard = false;
    }

    public BoardOptions(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public String toString() {
        return width + "x" + height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean getShowCellNumeration() {
        return showCellNumeration;
    }

    public void setShowCellNumeration(boolean showCellNumeration) {
        this.showCellNumeration = showCellNumeration;
    }

    public boolean getSquareBoard() {
        return squareBoard;
    }

    public void setSquareBoard(boolean squareBoard) {
        this.squareBoard = squareBoard;
    }

    public void load(Activity activity, SharedPreferences sharedPref) {
        setWidth(sharedPref.getInt(activity.getString(R.string.sharedPrefs_board_width), getWidth()));
        setHeight(sharedPref.getInt(activity.getString(R.string.sharedPrefs_board_height), getHeight()));
        setSquareBoard(sharedPref.getBoolean(activity.getString(R.string.sharedPrefs_square_board), getSquareBoard()));
        setShowCellNumeration(sharedPref.getBoolean(activity.getString(R.string.sharedPrefs_show_cell_numbers), getShowCellNumeration()));
    }

    public void save(Activity activity, SharedPreferences sharedPref) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(activity.getString(R.string.sharedPrefs_board_width), getWidth());
        editor.putInt(activity.getString(R.string.sharedPrefs_board_height), getHeight());
        editor.putBoolean(activity.getString(R.string.sharedPrefs_square_board), getSquareBoard());
        editor.putBoolean(activity.getString(R.string.sharedPrefs_show_cell_numbers), getShowCellNumeration());
        editor.apply();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardOptions)) return false;

        BoardOptions boardOptions = (BoardOptions) o;

        if (boardOptions.getWidth() != this.getWidth()) return false;
        if (boardOptions.getHeight() != this.getHeight()) return false;
        if (boardOptions.getSquareBoard() != this.getSquareBoard()) return false;
        return (boardOptions.getShowCellNumeration() == this.getShowCellNumeration());
    }
}
