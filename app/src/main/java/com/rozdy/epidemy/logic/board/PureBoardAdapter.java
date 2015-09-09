package com.rozdy.epidemy.logic.board;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import com.rozdy.epidemy.logic.cell.Cell;
import com.rozdy.epidemy.logic.cell.CellView;
import com.rozdy.epidemy.logic.game.Game;

/**
 * Created by hex on 8/25/2015 in the name of the Emperor!
 */
public class PureBoardAdapter extends BoardAdapter {
    private Context mContext;

    public PureBoardAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return Game.getInstance().getBoard().getHeight() * Game.getInstance().getBoard().getWidth();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        CellView cellView;
        if (!(convertView instanceof CellView)) {
            cellView = new CellView(mContext);
            cellView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            cellView.setBackgroundColor(Color.BLACK);
        } else {
            cellView = (CellView) convertView;
        }
        CellView.setScaleFactor(((BoardView) parent).getScaleFactor());
        try {
            int x = getXByPosition(position);
            int y = getYByPosition(position);
            Cell cell = Game.getInstance().getBoard().getCells()[x][y];
            cellView.setState(cell.getState());
            if (!cell.isEmpty()) {
                cellView.setColor(Game.getInstance().getPlayer(cell.getOwnerId()).getColor());
            } else {
                cellView.setColor(Color.BLACK);
            }
        } catch (InvalidPositionException e) {
            cellView.setState(Cell.ERROR_CELL);
        }
        return cellView;
    }

    @Override
    protected boolean isPositionOnBoard(int position) {
        return (position >= 0 || position <= getCount());
    }

    @Override
    public int getXByPosition(int position) throws InvalidPositionException {
        if (isPositionOnBoard(position)) {
            return position / Game.getInstance().getBoard().getWidth();
        } else {
            throw new InvalidPositionException(position);
        }
    }

    @Override
    public int getYByPosition(int position) throws InvalidPositionException {
        if (isPositionOnBoard(position)) {
            return position % Game.getInstance().getBoard().getWidth();
        } else {
            throw new InvalidPositionException(position);
        }
    }
}
