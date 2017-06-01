package com.example.fedatodavide.myapplication.LogicGPSLocator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceView;


public class CanvasView extends SurfaceView {

    //DICHIARAZIONE ED EVENTUALE INIZIALIZZAZIONE DELLE VARIABILI
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final int POINT_RADIUS = 5;
    private int pointColor = Color.RED;
    private float dx;
    private float dy;
    public int altezza = 0;
    public int larghezza = 0;

    //SI IMPOSTANO LE MISURE DEL CANVAS
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        altezza = h;
        larghezza = w;
    }

    //COSTRUTTORE DELLA CLASSE CANVASVIEW
    public CanvasView(Context c, AttributeSet attrs) {
        super(c, attrs);
        paint.setColor(pointColor);
        paint.setStyle(Paint.Style.FILL);
    }

    //METODO CHE PERMETTE DI DISEGNARE IL PUNTINO SULLA MAPPA
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(dx, dy, POINT_RADIUS, paint);
    }

    //METODO CHE DA LE INDICAZIONI PER DISEGNARE IL PUNTO
    public void drawPoint(float x, float y) {
        dx = x;
        dy = y;
        invalidate();
    }
}