/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 5/7/21 11:06 AM
 * project file last modified : 5/7/21 11:03 AM
 */

package org.rmj.g3appdriver.etc.ProgressBar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import org.rmj.g3appdriver.R;

public class VerticalProgressBar extends View {

    private Paint paint;// brush
    private int progress;// progress value
    private int width;// width value
    private int height;// height value

    public VerticalProgressBar(Context context, AttributeSet attrs,
                               int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public VerticalProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VerticalProgressBar(Context context) {
        super(context);
        init();
    }

    private void init() {
        paint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //The height and width of the canvas
        width = getMeasuredWidth()-1;// Get the width value set by the layout
        height = getMeasuredHeight()-1;// Get the height value set by the layout
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Draw the background
        paint.setColor(getResources().getColor(R.color.color_nine_seven));// Set the background brush color
        canvas.drawRect(0, 0, width, height, paint);// draw background rectangle

        //Drawing progress
        paint.setColor(getResources().getColor(R.color.guanzon_orange));// Set the progress brush color
        canvas.drawRect(0, height-progress / 100f * height, width, height, paint);// draw a rectangle

        //Draw four sides
        //canvas.drawLine(0, 0, width, 0, paint);// draw the top side
        //canvas.drawLine(0, 0, 0, height, paint);// draw the left
        //canvas.drawLine(width, 0, width, height, paint);// draw right
        //canvas.drawLine(0, height, width, height, paint);// draw bottom

        //Set the middle font
        //paint.setColor(Color.RED);// Set the brush color to red
        //paint.setTextSize(width / 3);// Set text size
        //canvas.drawText(progress + "%",(width-getTextWidth(progress + "%")) / 2, height / 2, paint);// draw text

        super.onDraw(canvas);
    }

    /**
     * Get the text width
     *
     * @param str The string passed in
     * return width
     */
    private int getTextWidth(String str) {
        // Calculate the rectangle where the text is, and get the width and height
        Rect rect = new Rect();
        paint.getTextBounds(str, 0, str.length(), rect);
        return rect.width();
    }

    /**
     * Set progressbar progress
     */
    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }

}
