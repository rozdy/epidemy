package sorry.no.domain.test_project;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

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

        // Top-left corner: blank cell
        if (position == 0) {
            TextView textView = new TextView(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(40, 40));
            textView.setPadding(2, 2, 2, 2);
            textView.setBackgroundColor(Color.WHITE);
            textView.setText("");
            return textView;
        }

        // Top row: a, b, c ...
        if (position > 0 && position <= Game.getInstance().getBoard().getWidth()) {
            TextView textView = new TextView(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(40, 40));
            textView.setPadding(2, 2, 2, 2);
            textView.setBackgroundColor(Color.WHITE);
            textView.setText("" + (char) ((int) 'a' + position - 1));
            return textView;
        }

        // left column: 1, 2 ,3 ...
        if (position % (Game.getInstance().getBoard().getWidth() + 1) == 0) {
            TextView textView = new TextView(mContext);
            textView.setLayoutParams(new GridView.LayoutParams(40, 40));
            textView.setPadding(2, 2, 2, 2);
            textView.setBackgroundColor(Color.WHITE);
            textView.setText("" + position / (Game.getInstance().getBoard().getWidth() + 1));
            return textView;
        }

        // Board cells
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new GridView.LayoutParams(40, 40));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setPadding(2, 2, 2, 2);
        imageView.setBackgroundColor(Color.WHITE);
        imageView.setImageResource(R.drawable.empty);
        return imageView;

    }


}
