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

    private static float scaleFactor = 1.0f;
    private static Bitmap background;
    private static int cellWidth, cellPadding;

    public static void initCellViewBackground(Context context) {
        Resources resources = context.getResources();
        background = BitmapFactory.decodeResource(resources, R.drawable.empty);
        cellPadding = (int) resources.getDimension(R.dimen.cell_spacing);
        cellWidth = background.getWidth();
    }

    public static int getCellWidth() {
        return cellWidth;
    }

    public static int getCellPadding() {
        return cellPadding;
    }

    public static void setScaleFactor(float scale) {
        scaleFactor = scale;
    }

    public static float getScaleFactor() {
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
        int p = CellView.getCellPadding();
        int w = CellView.getCellWidth();
        canvas.save();
        canvas.scale(scaleFactor, scaleFactor);
        paint.setColor(color);
        paint.setStrokeWidth(w / 10);
        canvas.drawBitmap(background, p, p, paint);
        switch (state) {
            case Cell.EMPTY_CELL:
                break;
            case Cell.MARK_CELL:
                canvas.drawLine(p, p, w + p, w + p, paint);
                canvas.drawLine(p, w + p, w + p, p, paint);
                break;
            case Cell.WALL_CELL:
                canvas.drawRect(p, p, w + p, w + p, paint);
                break;
            default:
                paint.setColor(Color.RED);
                canvas.drawText("!", p, p, paint);
                break;
        }
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) (CellView.getCellWidth() * getScaleFactor() + CellView.getCellPadding() * 2),
                (int) (CellView.getCellWidth() * getScaleFactor() + CellView.getCellPadding() * 2));
    }
}
