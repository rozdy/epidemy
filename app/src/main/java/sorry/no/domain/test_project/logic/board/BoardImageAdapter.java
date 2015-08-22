package sorry.no.domain.test_project.logic.board;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import sorry.no.domain.test_project.logic.cell.Cell;
import sorry.no.domain.test_project.logic.cell.CellView;
import sorry.no.domain.test_project.logic.game.Game;
import sorry.no.domain.test_project.R;

/**
 * Created by hex on 7/28/2015.
 */
public class BoardImageAdapter extends BaseAdapter {
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

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        int cellWidth = (int) mContext.getResources().getDimension(R.dimen.cell_width);

        // Top-left corner: blank cell
        if (position == 0) {
            TextView textView = new TextView(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(cellWidth, cellWidth));
            textView.setPadding(0, 0, 0, 0);
            textView.setBackgroundColor(Color.WHITE);
            textView.setText("");
            return textView;
        }

        // Top row: a, b, c ...
        if (position > 0 && position <= Game.getInstance().getBoard().getWidth()) {
            TextView textView = new TextView(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(cellWidth, cellWidth));
            textView.setPadding(0, 0, 0, 0);
            textView.setBackgroundColor(Color.WHITE);
            textView.setText("" + (char) ((int) 'a' + position - 1));
            return textView;
        }

        // left column: 1, 2 ,3 ...
        if (position % (Game.getInstance().getBoard().getWidth() + 1) == 0) {
            TextView textView = new TextView(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(cellWidth, cellWidth));
            textView.setPadding(0, 0, 0, 0);
            textView.setBackgroundColor(Color.WHITE);
            textView.setText("" + position / (Game.getInstance().getBoard().getWidth() + 1));
            return textView;
        }

        // Board cells
        CellView cellView = new CellView(mContext);
        cellView.setLayoutParams(new GridView.LayoutParams(cellWidth, cellWidth));
        cellView.setPadding(0, 0, 0, 0);
        cellView.setBackgroundColor(Color.WHITE);
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

    private boolean isPositionOnBoard(int position) {
        if (position < (Game.getInstance().getBoard().getWidth() + 1) ||
                position % (Game.getInstance().getBoard().getWidth() + 1) == 0 ||
                position > getCount()) {
            return false;
        } else {
            return true;
        }
    }

    public int getXByPosition(int position) throws InvalidPositionException {
        if (isPositionOnBoard(position)) {
            return position / (Game.getInstance().getBoard().getWidth() + 1) - 1;
        } else {
            throw new InvalidPositionException(position);
        }
    }

    public int getYByPosition(int position) throws InvalidPositionException {
        if (isPositionOnBoard(position)) {
            return position % (Game.getInstance().getBoard().getWidth() + 1) - 1;
        } else {
            throw new InvalidPositionException(position);
        }
    }


}
