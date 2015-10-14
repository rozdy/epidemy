package com.rozdy.epidemy.logic;

import com.rozdy.epidemy.bus.AIMoveEvent;
import com.rozdy.epidemy.bus.EventBus;
import com.rozdy.epidemy.logic.board.Board;
import com.rozdy.epidemy.logic.game.Game;

/**
 * Created by Sergejj on 11/10/2015 in the name of the Emperor!
 */
public class AI {
    public static void makeAIMove(int player) {
        for (int index = 0; index < Game.getInstance().getMaxNumberOfMoves(); index++) {
            Integer[][] movesMap = Game.getInstance().getBoard().buildMovesMap(player);
            for (int i = 0; i < movesMap.length; i++) {
                boolean moveMade = false;
                for (int j = 0; j < movesMap[i].length; j++) {
                    if (movesMap[i][j] == Board.WALL_AVAILABLE || movesMap[i][j] == Board.MARK_AVAILABLE) {
                        EventBus.getInstance().post(new AIMoveEvent((i + 1) * (movesMap[i].length + 1) + j + 1)); //numbers/letters row/column
                        moveMade = true;
                        break;
                    }
                }
                if (moveMade) {
                    break;
                }
            }
        }
    }
}
