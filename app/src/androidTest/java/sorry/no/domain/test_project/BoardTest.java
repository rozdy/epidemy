package sorry.no.domain.test_project;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by Sergejj on 01/08/2015 in the name of the Emperor!
 */
public class BoardTest {
    @Test
    public void testOneCross() throws InvalidCellException {
        Board board = new Board();
        board.getCells()[0][0].mark(1);
        int[][] actual = board.buildMovesMap(1);
        int[][] expected = board.getMovesMapTemplate();
        expected[0][0] = Board.OWN_CROSS_HIT;
        expected[0][1] = Board.MARK_AVAILABLE;
        expected[1][1] = Board.MARK_AVAILABLE;
        expected[1][0] = Board.MARK_AVAILABLE;

        assertEquals(actual, expected);
    }

    @Test
    public void testOneCrossAndWall() throws InvalidCellException {
        Board board = new Board();
        board.getCells()[0][0].mark(1);
        board.getCells()[0][1].setState(Cell.WALL_CELL);
        board.getCells()[0][1].setOwnerId(1);
        int[][] actual = board.buildMovesMap(1);
        int[][] expected = board.getMovesMapTemplate();
        expected[0][0] = Board.OWN_CROSS_HIT;
        expected[0][1] = Board.OWN_WALL_HIT;
        expected[1][1] = Board.MARK_AVAILABLE;
        expected[1][0] = Board.MARK_AVAILABLE;
        expected[0][2] = Board.MARK_AVAILABLE;
        expected[1][2] = Board.MARK_AVAILABLE;

        assertEquals(actual, expected);
    }
}
