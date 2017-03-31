package com.example.fedatodavide.myapplication.Logic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.Settings;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;

/**
 * Created by fedatodavide on 27/03/2017.
 */


public class CanvasView extends SurfaceView {

    //public int width;
    //public int height;
    //private Bitmap mBitmap;
    //private Canvas mCanvas;
    //private Path mPath;
    //private Context context;
    //private Paint mPaint;
    //private float mX, mY;
    //private static final float TOLERANCE = 5;
    //private final SurfaceHolder surfaceHolder;

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    //D
    private final int POINT_RADIUS = 20;
    private int pointColor = Color.RED;
    private float dx;
    private float dy;

    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        //context = c;

        //surfaceHolder = getHolder();
        paint.setColor(pointColor);
        paint.setStyle(Paint.Style.FILL);

        // we set a new Path
        //mPath = new Path();

        // and we set a new Paint with the desired attributes
        /*mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);*/
    }

    //override onSizeChanged
    /*@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // your Canvas will draw onto the defined Bitmap
        //mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        //mCanvas = new Canvas(mBitmap);
    }*/


    // override onDraw
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //canvas.drawPath(mPath, mPaint);
        canvas.drawCircle(dx, dy, POINT_RADIUS, paint);
        //canvas = new Canvas();
    }

    //D
    public void drawPoint(float x, float y){
        System.out.println("X: " + x + " Y: " + y);
        dx = x;
        dy = y;
        invalidate();
    }

    /*public void clearCanvas() {
        mPath.reset();
        invalidate();
    }*/

    // when ACTION_DOWN start touch according to the x,y values
    /*private void startTouch(float x, float y) {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }*/

    // when ACTION_MOVE move touch according to the x,y values
    /*private void moveTouch(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOLERANCE || dy >= TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }*/

    // when ACTION_UP stop touch
    /*private void upTouch() {
        mPath.lineTo(mX, mY);
    }*/

    //override the onTouchEvent
    /*@Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                //moveTouch(x, y);
                drawPoint(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                //upTouch();
                invalidate();
                break;
        }
        return true;
    }*/

}