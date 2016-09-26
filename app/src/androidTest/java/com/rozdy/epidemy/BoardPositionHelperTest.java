package com.rozdy.epidemy;

import com.rozdy.epidemy.logic.board.BoardPositionHelper;
import com.rozdy.epidemy.logic.game.Game;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by Sergejj on 05/12/2015 in the name of the Emperor!
 */
public class BoardPositionHelperTest {
    BoardPositionHelper boardPositionHelper = new BoardPositionHelper();
    int boardWidth = 8;
    int boardHeight = 8;

    @BeforeClass
    public void doStuff() {
        Game.init();
        Options.getInstance().getBoardOptions().setWidth(boardWidth);
        Options.getInstance().getBoardOptions().setHeight(boardHeight);
    }

    @Test
    public void zeroZeroShowTest() {
        Options.getInstance().getBoardOptions().setShowCellNumeration(true);
        assertEquals(boardPositionHelper.calculatePositionByXY(0, 0), boardWidth + 2, "position is incorrect with numeration on");
    }

    @Test
    public void zeroZeroNotShowTest() {
        Options.getInstance().getBoardOptions().setShowCellNumeration(false);
        assertEquals(boardPositionHelper.calculatePositionByXY(0, 0), 0, "position is incorrect with numeration on");
    }

    @Test
    public void widthHeightShowTest() {
        Options.getInstance().getBoardOptions().setShowCellNumeration(true);
        assertEquals(boardPositionHelper.calculatePositionByXY(boardWidth - 1, boardHeight - 1), 80, "position is incorrect with numeration on");
    }

    @Test
    public void widthHeightNotShowTest() {
        Options.getInstance().getBoardOptions().setShowCellNumeration(false);
        assertEquals(boardPositionHelper.calculatePositionByXY(boardWidth - 1, boardHeight - 1), 63, "position is incorrect with numeration on");
    }
}
