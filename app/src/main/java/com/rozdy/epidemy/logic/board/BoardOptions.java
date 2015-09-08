package com.rozdy.epidemy.logic.board;

import java.util.Arrays;
import java.util.List;

/**
 * Created by hex on 8/16/2015 in the name of the Emperor!
 */
public class BoardOptions {
    private int width, height;
    private boolean showCellNumeration;

    public static final List<BoardOptions> standardBoardSizes =
            Arrays.asList(new BoardOptions(8, 8), new BoardOptions(10, 10),
                    new BoardOptions(14, 14), new BoardOptions(20, 20));

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardOptions)) return false;

        BoardOptions boardOptions = (BoardOptions) o;

        if (boardOptions.getWidth() != this.getWidth()) return false;
        if (boardOptions.getHeight() == this.getHeight()) return false;
        return (boardOptions.getShowCellNumeration() == this.getShowCellNumeration());
    }
}
