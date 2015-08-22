package sorry.no.domain.test_project.logic.cell;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import sorry.no.domain.test_project.R;

/**
 * Created by hex on 8/13/2015.
 */
public class CellView extends View {
    private int color, state;
    Paint paint;
    private Bitmap background;

    public CellView(Context context) {
        super(context);
        paint = new Paint();
        Resources resources = getResources();
        int width = (int) resources.getDimension(R.dimen.cell_width);
        Bitmap backgroundSrc = BitmapFactory.decodeResource(resources, R.drawable.empty);
        background = Bitmap.createScaledBitmap(backgroundSrc, width, width, false);
    }

    public int getColor() {
        return color;
    }

    public int getState() {
        return state;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(color);
        canvas.drawBitmap(background, 0, 0, paint);
        switch (state) {
            case Cell.EMPTY_CELL:
                break;
            case Cell.MARK_CELL:
                canvas.drawLine(0, 0, background.getWidth(), background.getHeight(), paint);
                canvas.drawLine(0, background.getWidth(), background.getHeight(), 0, paint);
                break;
            case Cell.WALL_CELL:
                canvas.drawRect(0, 0, background.getWidth(), background.getHeight(), paint);
                break;
            default:
                paint.setColor(Color.RED);
                canvas.drawText("!", 0, 0, paint);
                break;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.setMeasuredDimension((int) getResources().getDimension(R.dimen.cell_width),
                (int) getResources().getDimension(R.dimen.cell_width));
    }
}
