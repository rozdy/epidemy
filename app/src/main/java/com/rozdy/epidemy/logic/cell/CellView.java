package com.rozdy.epidemy.logic.cell;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.rozdy.epidemy.R;

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
        canvas.save();
        canvas.scale(scaleFactor, scaleFactor);
        paint.setColor(color);
        canvas.drawBitmap(background, CellView.getCellPadding(), CellView.getCellPadding(), paint);
        switch (state) {
            case Cell.EMPTY_CELL:
                break;
            case Cell.MARK_CELL:
                drawMark(canvas, CellView.getCellWidth(), CellView.getCellPadding(), paint);
                break;
            case Cell.WALL_CELL:
                drawWall(canvas, CellView.getCellWidth(), CellView.getCellPadding(), paint);
                break;
            default:
                drawErrorCell(canvas, CellView.getCellPadding(), paint);
                break;
        }
        canvas.restore();
    }

    private void drawMark(Canvas canvas, int width, int padding, Paint paint) {
        paint.setStrokeWidth(width / 10);
        int shift = width / 8;
        canvas.drawLine(padding + shift, padding + shift, width + padding - shift, width + padding - shift, paint);
        canvas.drawLine(padding + shift, width + padding - shift, width + padding - shift, padding + shift, paint);
    }

    private void drawWall(Canvas canvas, int width, int padding, Paint paint) {
        canvas.drawRect(padding, padding, width + padding, width + padding, paint);
    }

    private void drawErrorCell(Canvas canvas, int padding, Paint paint) {
        paint.setColor(Color.RED);
        canvas.drawText("!", padding, padding, paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int) (CellView.getCellWidth() * getScaleFactor() + CellView.getCellPadding() * 2),
                (int) (CellView.getCellWidth() * getScaleFactor() + CellView.getCellPadding() * 2));
    }
}
