package com.rozdy.epidemy.logic.ai;

import com.rozdy.epidemy.logic.cell.Cell;
import com.rozdy.epidemy.logic.cell.InvalidCellException;
import com.rozdy.epidemy.logic.game.Game;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * Created by Sergejj on 07/11/2015 in the name of the Emperor!
 */
public class AITest {

    @Test
    public void testDistanceToEnemyCrossZero() throws InvalidCellException {
        Game.initWithAI();
        Cell[][] cells = Game.getInstance().getBoard().getCells();
        int activePlayer = Game.getInstance().getActivePlayer();
        cells[0][0].mark(activePlayer);
        cells[0][1].mark(Game.getInstance().getPlayersNumber() - 1);
        assertEquals(AI.getDistanceToClosestEnemyCross(activePlayer, 0, 1), 0, "Distance to closest enemy cross is not 0");
    }

    @Test
    public void testDistanceToEnemyCrossOne() throws InvalidCellException {
        Game.initWithAI();
        Cell[][] cells = Game.getInstance().getBoard().getCells();
        int activePlayer = Game.getInstance().getActivePlayer();
        cells[0][0].mark(activePlayer);
        cells[0][2].mark(Game.getInstance().getPlayersNumber() - 1);
        cells[0][3].mark(Game.getInstance().getPlayersNumber() - 1);
        assertEquals(AI.getDistanceToClosestEnemyCross(activePlayer, 0, 1), 1, "Distance to closest enemy cross is not 1");
    }

    @Test
    public void testDistanceToEnemyCrossOneMultiple() throws InvalidCellException {
        Game.initWithAI();
        Cell[][] cells = Game.getInstance().getBoard().getCells();
        int activePlayer = Game.getInstance().getActivePlayer();
        cells[0][0].mark(activePlayer);
        cells[0][2].mark(Game.getInstance().getPlayersNumber() - 1);
        cells[1][1].mark(Game.getInstance().getPlayersNumber() - 1);
        assertEquals(AI.getDistanceToClosestEnemyCross(activePlayer, 0, 1), 1, "Distance to closest enemy cross is not 1");
    }

    @Test
    public void testDistanceToEnemyCross2() throws InvalidCellException {
        Game.initWithAI();
        Cell[][] cells = Game.getInstance().getBoard().getCells();
        int activePlayer = Game.getInstance().getActivePlayer();
        cells[0][0].mark(activePlayer);
        cells[2][2].mark(Game.getInstance().getPlayersNumber() - 1);
        assertEquals(AI.getDistanceToClosestEnemyCross(activePlayer, 1, 1), 1, "Distance to closest enemy cross is not sqrt of 2");
    }

    @Test
    public void testDistanceToEnemy() throws InvalidCellException {
        Game.initWithAI();
        Game.getInstance().setCurrentTurn(10);
        Cell[][] cells = Game.getInstance().getBoard().getCells();
        int activePlayer = Game.getInstance().getActivePlayer();
        cells[5][5].mark(activePlayer);
        cells[0][5].mark(Game.getInstance().getPlayersNumber() - 1);
        cells[0][0].mark(Game.getInstance().getPlayersNumber() - 1);
        assertEquals(AI.getDistanceToClosestEnemyCross(activePlayer, 4, 4), AI.getDistanceToClosestEnemyCross(activePlayer, 4, 5), "Distance is calculated incorrectly (not in crosses)?");
    }

    @Test
    public void testSubmoves() throws InvalidCellException {
        Game.initWithAI();
        Game.getInstance().setCurrentTurn(10);
        Cell[][] cells = Game.getInstance().getBoard().getCells();
        int activePlayer = Game.getInstance().getActivePlayer();
        cells[5][5].mark(activePlayer);
        cells[0][5].mark(Game.getInstance().getPlayersNumber() - 1);
        cells[0][0].mark(Game.getInstance().getPlayersNumber() - 1);

        List<AI.SubMove> expected = new ArrayList<>();
        expected.add(new AI.SubMove(4, 4, 4));
        expected.add(new AI.SubMove(4, 5, 4));
        expected.add(new AI.SubMove(4, 6, 4));

        assertEquals(AI.getSubMovesToRandomizeFrom(activePlayer), expected, "Submoves to randomize were counted incorrectly");
    }
}
