package com.rozdy.epidemy.logic.board;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.rozdy.epidemy.logic.cell.CellView;

/**
 * Created by hex on 8/25/2015 in the name of the Emperor!
 */
public class BoardView extends GridView {
    private float scaleFactor = 1.0f;
    private ScaleGestureDetector scaleDetector;

    public BoardView(Context context) {
        super(context);
        scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
        scaleDetector.onTouchEvent(ev);
        return super.onTouchEvent(ev);
    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor *= detector.getScaleFactor();
            scaleFactor = Math.max(0.3f, Math.min(scaleFactor, 3.0f));

            setColumnWidth((int) (CellView.getCellWidth() * scaleFactor) + 2 * CellView.getCellPadding());
            BaseAdapter adapter = (BaseAdapter) getAdapter();
            adapter.notifyDataSetChanged();
            invalidate();
            return true;
        }
    }
}
