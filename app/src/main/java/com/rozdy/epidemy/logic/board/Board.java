package com.rozdy.epidemy.logic.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.rozdy.epidemy.logic.cell.Cell;
import com.rozdy.epidemy.logic.cell.InvalidCellException;
import com.rozdy.epidemy.logic.game.Game;

/**
 * Created by hex on 7/22/2015 in the name of the Emperor!
 */
public class Board {
    public static final int MARK_AVAILABLE = 100;
    public static final int WALL_AVAILABLE = 101;
    public static final int MARK_PLACED = 200;
    public static final int WALL_PLACED = 201;
    public static final int UNREACHABLE_CELL = 400;
    public static final int ENEMY_WALL_HIT = 401;
    public static final int OWN_CROSS_HIT = 402;
    public static final int OWN_WALL_HIT = 403;

    private int width, height;
    private Cell[][] cells;

    public Board(BoardOptions boardOptions) {
        width = boardOptions.getWidth();
        height = boardOptions.getHeight();
        cells = new Cell[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public int markCell(int player, int x, int y) throws InvalidCellException {
        Cell cell = getCells()[x][y];
        Integer[][] movesMap = buildMovesMap(player);
        switch (movesMap[x][y]) {
            case MARK_AVAILABLE:
                cell.mark(player);
                return MARK_PLACED;
            case WALL_AVAILABLE:
                cell.wall(player);
                return WALL_PLACED;
            default:
                return movesMap[x][y];
        }
    }

    public Integer[][] buildMovesMap(int player) {
        List<Cell> activeCells = getMarksList(player);
        List<Cell> newActiveCells;
        Integer[][] movesMap = getMovesMapTemplate();
        do {
            newActiveCells = new ArrayList<>();
            for (Cell activeCell : activeCells) {
                for (int i = activeCell.getX() - 1; i <= activeCell.getX() + 1; i++) {
                    for (int j = activeCell.getY() - 1; j <= activeCell.getY() + 1; j++) {
                        if (i < 0 || i >= getHeight() || j < 0 || j >= getWidth()) {
                            continue; //border marks
                        }
                        Cell neighbour = getCells()[i][j];
                        if (movesMap[i][j] == UNREACHABLE_CELL) {
                            switch (neighbour.getState()) {
                                case Cell.WALL_CELL: {
                                    if (neighbour.getOwnerId() == player) {
                                        newActiveCells.add(neighbour);
                                        movesMap[i][j] = OWN_WALL_HIT;
                                    } else {
                                        movesMap[i][j] = ENEMY_WALL_HIT;
                                    }
                                    break;
                                }
                                case Cell.MARK_CELL: {
                                    if (neighbour.getOwnerId() == player) {
                                        movesMap[i][j] = OWN_CROSS_HIT;
                                    } else {
                                        movesMap[i][j] = WALL_AVAILABLE;
                                    }
                                    break;
                                }
                                case Cell.EMPTY_CELL: {
                                    movesMap[i][j] = MARK_AVAILABLE;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            activeCells = newActiveCells;
        } while (!newActiveCells.isEmpty());
        //Modify moves map for the first move
        if (Game.getInstance().getCurrentTurn() == 0 &&
                Game.getInstance().getNumberOfMoves() == Game.getInstance().getMaxNumberOfMoves()) {
            movesMap[getStartPositionX(Game.getInstance().getPlayersNumber(), player)]
                    [getStartPositionY(Game.getInstance().getPlayersNumber(), player)]
                    = MARK_AVAILABLE;
        }
        return movesMap;
    }

    private int getStartPositionX(int playersNumber, int activePlayer) {
        switch (playersNumber) {
            case 2:
                if (activePlayer == 0) {
                    return 0;
                } else {
                    return (getHeight() - 1);
                }
            case 3:
            case 4:
                switch (activePlayer) {
                    case 0:
                    case 1:
                        return 0;
                    case 2:
                    case 3:
                        return getHeight() - 1;
                }
            default:
                return 0;
        }
    }

    private int getStartPositionY(int playersNumber, int activePlayer) {
        switch (playersNumber) {
            case 2:
                if (activePlayer == 0) {
                    return 0;
                } else {
                    return (getWidth() - 1);
                }
            case 3:
            case 4:
                switch (activePlayer) {
                    case 0:
                    case 3:
                        return 0;
                    case 1:
                    case 2:
                        return getWidth() - 1;
                }
            default:
                return 0;
        }
    }

    public Integer[][] getMovesMapTemplate() {
        Integer[][] movesMap = new Integer[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                movesMap[i][j] = UNREACHABLE_CELL;
            }
        }
        return movesMap;
    }

    public List<Cell> getMarksList(int player) {
        List<Cell> marks = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (getCells()[i][j].getOwnerId() == player && getCells()[i][j].isMarked()) {
                    marks.add(getCells()[i][j]);
                }
            }
        }
        return marks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Board)) return false;

        Board board = (Board) o;

        if (getWidth() != board.getWidth()) return false;
        if (getHeight() != board.getHeight()) return false;
        return Arrays.deepEquals(getCells(), board.getCells());

    }

    @Override
    public int hashCode() {
        int result = getWidth();
        result = 31 * result + getHeight();
        result = 31 * result + (getCells() != null ? Arrays.deepHashCode(getCells()) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Board{" +
                "width=" + width +
                ", height=" + height +
                ", cells=" + Arrays.toString(cells) +
                '}';
    }
}
