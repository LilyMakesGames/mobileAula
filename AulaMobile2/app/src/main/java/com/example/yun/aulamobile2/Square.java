package com.example.yun.aulamobile2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import static android.R.attr.radius;

/**
 * Created by Yun on 18/04/2018.
 */

public class Square implements ICollision {

    public Point position;
    int size;
    Paint p;
    public Rect rect;

    public Square(Point position,int size){
        this.position = position;
        this.size = size;
        p = new Paint();
        p.setColor(Color.BLACK);
        rect = new Rect(position.x - size, position.y - size, position.x + size,position.y +size);

    }

    public void Draw(Canvas canvas){


        canvas.drawRect(rect,p);
    }

    @Override
    public void OnCollision(ICollision col) {

    }
}
