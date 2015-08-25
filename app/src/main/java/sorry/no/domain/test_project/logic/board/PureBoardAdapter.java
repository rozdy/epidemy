package sorry.no.domain.test_project.logic.board;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import sorry.no.domain.test_project.R;
import sorry.no.domain.test_project.logic.cell.Cell;
import sorry.no.domain.test_project.logic.cell.CellView;
import sorry.no.domain.test_project.logic.game.Game;

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
        int cellWidth = (int) mContext.getResources().getDimension(R.dimen.cell_width);
        CellView cellView;
        if (!(convertView instanceof CellView)) {
            cellView = new CellView(mContext);
            cellView.setLayoutParams(new GridView.LayoutParams(cellWidth, cellWidth));
            cellView.setPadding(0, 0, 0, 0);
            cellView.setBackgroundColor(Color.WHITE);
        } else {
            cellView = (CellView) convertView;
        }
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

    protected boolean isPositionOnBoard(int position) {
        return (position >= 0 || position <= getCount());
    }

    public int getXByPosition(int position) throws InvalidPositionException {
        if (isPositionOnBoard(position)) {
            return position / Game.getInstance().getBoard().getWidth();
        } else {
            throw new InvalidPositionException(position);
        }
    }

    public int getYByPosition(int position) throws InvalidPositionException {
        if (isPositionOnBoard(position)) {
            return position % Game.getInstance().getBoard().getWidth();
        } else {
            throw new InvalidPositionException(position);
        }
    }
}
