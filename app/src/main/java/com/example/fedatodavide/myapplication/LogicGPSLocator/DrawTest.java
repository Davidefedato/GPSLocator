package com.example.fedatodavide.myapplication.LogicGPSLocator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by fedatodavide on 25/03/2017.
 */

public class DrawTest extends View {
    Paint paint = new Paint();

    public DrawTest(Context context) {
        super(context);
    }

    @Override
    public void onDraw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        canvas.drawRect(30, 30, 80, 80, paint);
        paint.setStrokeWidth(0);
        paint.setColor(Color.CYAN);
        canvas.drawRect(33, 60, 77, 77, paint );
        paint.setColor(Color.YELLOW);
        canvas.drawRect(33, 33, 77, 60, paint );
//ciao
    }

}