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
 * Created by hex on 8/13/2015 in the name of the Emperor!
 */
public class CellView extends View {
    private int color, state;
    private Paint paint;
    private float scaleFactor = 1.0f;

    private static Bitmap background;

    public static void initCellViewBackground(Context context) {
        Resources resources = context.getResources();
        background = BitmapFactory.decodeResource(resources, R.drawable.empty);
    }

    public static int getCellWidth() {
        if (background != null) {
            return background.getWidth();
        } else {
            return 0;
        }
    }

    public void setScaleFactor(float scale) {
        scaleFactor = scale;
    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    public CellView(Context context) {
        super(context);
        paint = new Paint();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.scale(scaleFactor, scaleFactor);
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
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) (CellView.getCellWidth() * getScaleFactor()),
                (int) (CellView.getCellWidth() * getScaleFactor()));
    }
}
