package com.rozdy.epidemy.logic.board;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import com.rozdy.epidemy.logic.cell.Cell;
import com.rozdy.epidemy.logic.cell.CellView;
import com.rozdy.epidemy.logic.game.Game;

/**
 * Created by hex on 7/28/2015 in the name of the Emperor!
 */
public class BoardImageAdapter extends BoardAdapter {
    private Context mContext;

    public BoardImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return (Game.getInstance().getBoard().getHeight() + 1) *
                (Game.getInstance().getBoard().getWidth() + 1);
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Top-left corner: blank cell
        if (position == 0) {
            TextView textView = new TextView(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return textView;
        }

        // Top row: a, b, c ...
        if (position > 0 && position <= Game.getInstance().getBoard().getWidth()) {
            TextView textView = new TextView(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setText("" + (char) ((int) 'a' + position - 1));
            return textView;
        }

        // left column: 1, 2 ,3 ...
        if (position % (Game.getInstance().getBoard().getWidth() + 1) == 0) {
            TextView textView = new TextView(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setText("" + position / (Game.getInstance().getBoard().getWidth() + 1));
            return textView;
        }

        CellView cellView;
        if (!(convertView instanceof CellView)) {
            // Board cells
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
        return !(position < (Game.getInstance().getBoard().getWidth() + 1) ||
                position % (Game.getInstance().getBoard().getWidth() + 1) == 0 ||
                position > getCount());
    }

    @Override
    public int getXByPosition(int position) throws InvalidPositionException {
        if (isPositionOnBoard(position)) {
            return position / (Game.getInstance().getBoard().getWidth() + 1) - 1;
        } else {
            throw new InvalidPositionException(position);
        }
    }

    @Override
    public int getYByPosition(int position) throws InvalidPositionException {
        if (isPositionOnBoard(position)) {
            return position % (Game.getInstance().getBoard().getWidth() + 1) - 1;
        } else {
            throw new InvalidPositionException(position);
        }
    }
}
