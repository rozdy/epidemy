package sorry.no.domain.test_project;

import org.testng.annotations.Test;

import sorry.no.domain.test_project.logic.board.Board;
import sorry.no.domain.test_project.logic.board.BoardOptions;
import sorry.no.domain.test_project.logic.cell.Cell;
import sorry.no.domain.test_project.logic.cell.InvalidCellException;
import sorry.no.domain.test_project.logic.game.Game;
import sorry.no.domain.test_project.logic.game.InvalidMoveException;

import static org.testng.Assert.assertEquals;

/**
 * Created by hex on 8/14/2015 in the name of the Emperor?
 */
public class GameTest {
    @Test
    public void testMakeAMove() throws InvalidMoveException, InvalidCellException {
        Game.init();
        Game.getInstance().makeAMove(0, 0, 0);
        Board actual = Game.getInstance().getBoard();
        Board expected = new Board(new BoardOptions(8, 8));
        expected.getCells()[0][0].setState(Cell.MARK_CELL);
        expected.getCells()[0][0].setOwnerId(0);
        Game.stop();

        assertEquals(actual, expected);
    }

    @Test
    public void testChangeActivePlayer() throws InvalidMoveException, InvalidCellException {
        Game.init();
        Game.getInstance().makeAMove(0, 0, 0);
        Game.getInstance().makeAMove(0, 1, 1);
        Game.getInstance().makeAMove(0, 2, 2);
        int actual = Game.getInstance().getActivePlayer();
        int expected = 1;
        Game.stop();

        assertEquals(actual, expected);
    }
}
