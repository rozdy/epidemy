package com.rozdy.epidemy.logic.board;

import com.rozdy.epidemy.Options;
import com.rozdy.epidemy.logic.game.Game;

/**
 * Created by Sergejj on 05/12/2015 in the name of the Emperor!
 */
public class BoardPositionHelper {

    public int calculatePositionByXY(int x, int y) {
        if (Options.getInstance().getBoardOptions().getShowCellNumeration()) {
            return (x + 1) * (Game.getInstance().getBoard().getWidth() + 1) + y + 1;
        } else {
            return x * Game.getInstance().getBoard().getWidth() + y;
        }
    }
}
