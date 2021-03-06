package com.rozdy.epidemy;

import org.testng.annotations.Test;

import com.rozdy.epidemy.logic.board.Board;
import com.rozdy.epidemy.logic.board.BoardOptions;
import com.rozdy.epidemy.logic.cell.Cell;
import com.rozdy.epidemy.logic.cell.InvalidCellException;
import com.rozdy.epidemy.logic.game.Game;

import static org.testng.Assert.assertEquals;

/**
 * Created by Sergejj on 01/08/2015 in the name of the Emperor!
 */
public class BoardTest {
    @Test
    public void testOneCross() throws InvalidCellException {
        Game.init();
        Game.getInstance().setCurrentTurn(5);
        Board board = new Board(new BoardOptions(8, 8));
        board.getCells()[0][0].mark(1);
        Integer[][] actual = board.buildMovesMap(1);
        Integer[][] expected = board.getMovesMapTemplate();
        expected[0][0] = Board.OWN_CROSS_HIT;
        expected[0][1] = Board.MARK_AVAILABLE;
        expected[1][1] = Board.MARK_AVAILABLE;
        expected[1][0] = Board.MARK_AVAILABLE;
        Game.stop();

        assertEquals(actual, expected);
    }

    @Test
    public void testOneCrossAndWall() throws InvalidCellException {
        Game.init();
        Game.getInstance().setCurrentTurn(5);
        Board board = new Board(new BoardOptions(8, 8));
        board.getCells()[0][0].mark(1);
        board.getCells()[0][1].setState(Cell.WALL_CELL);
        board.getCells()[0][1].setOwnerId(1);
        Integer[][] actual = board.buildMovesMap(1);
        Integer[][] expected = board.getMovesMapTemplate();
        expected[0][0] = Board.OWN_CROSS_HIT;
        expected[0][1] = Board.OWN_WALL_HIT;
        expected[1][1] = Board.MARK_AVAILABLE;
        expected[1][0] = Board.MARK_AVAILABLE;
        expected[0][2] = Board.MARK_AVAILABLE;
        expected[1][2] = Board.MARK_AVAILABLE;
        Game.stop();

        assertEquals(actual, expected);
    }

    @Test
    public void testMarkCell() throws InvalidCellException {
        Board board = new Board(new BoardOptions(8, 8));
        board.markCell(0, 0, 0);
        Cell actual = board.getCells()[0][0];
        Cell expected = new Cell(0, 0);
        expected.setState(Cell.MARK_CELL);
        expected.setOwnerId(0);

        assertEquals(actual, expected);
    }

    @Test
    public void testMarkCellOnTheWall() throws InvalidCellException {
        Game.init();
        Game.getInstance().setCurrentTurn(5);
        Board board = new Board(new BoardOptions(8, 8));
        board.getCells()[0][0].setState(Cell.MARK_CELL);
        board.getCells()[0][0].setOwnerId(0);
        board.getCells()[1][1].setState(Cell.MARK_CELL);
        board.getCells()[1][1].setOwnerId(1);
        board.markCell(0, 1, 1);
        Cell actual = board.getCells()[1][1];
        Cell expected = new Cell(1, 1);
        expected.setState(Cell.WALL_CELL);
        expected.setOwnerId(0);
        Game.stop();

        assertEquals(actual, expected);
    }

    @Test
    public void testStartPositions() {
        Game.init();
        Board board = Game.getInstance().getBoard();
        Integer[][] actual = board.buildMovesMap(0);
        Integer[][] expected = board.getMovesMapTemplate();
        expected[0][0] = Board.MARK_AVAILABLE;

        assertEquals(actual, expected);

        actual = board.buildMovesMap(1);
        expected = board.getMovesMapTemplate();
        expected[board.getWidth() - 1][board.getHeight() - 1] = Board.MARK_AVAILABLE;

        assertEquals(actual, expected);
    }
}
