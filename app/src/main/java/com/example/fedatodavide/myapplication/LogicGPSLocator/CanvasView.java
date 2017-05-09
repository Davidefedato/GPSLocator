package com.example.fedatodavide.myapplication.LogicGPSLocator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by fedatodavide on 27/03/2017.
 */


public class CanvasView extends SurfaceView {

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //D
    private final int POINT_RADIUS = 5;
    private int pointColor = Color.RED;
    private float dx;
    private float dy;

    public int altezza = 0;
    public int larghezza = 0;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        System.out.println("On size changed!");
        System.out.println("Width: " + w + ", Height: " + h);
        altezza = h;
        larghezza = w;
    }

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        paint.setColor(pointColor);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(dx, dy, POINT_RADIUS, paint);
    }

    //D
    public void drawPoint(float x, float y){
        System.out.println("X: " + x + " Y: " + y);
        dx = x;
        dy = y;
        invalidate();
    }
}