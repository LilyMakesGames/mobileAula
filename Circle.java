package com.example.yun.aulamobile2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.shapes.RectShape;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by Yun on 16/04/2018.
 */

public class Circle implements ICollision {


    Point position = new Point();
    Point startPosition;
    public float radius = 0;
    Paint p = new Paint();
    public float speedX;
    public float speedY;



    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Circle(int radius, int color, Point position) {
        startPosition = position;
        this.position = position;
        this.radius = radius;
        p.setColor(color);
    }


    public void Update()
    {
        if (speedX > 4)
            speedX = 4;
        if (speedX < -4)
            speedX = -4;
        if (speedY > 4)
            speedY = 4;
        if (speedY < -4)
            speedY = -4;
        position.x += speedX;
        position.y += speedY;
    }

    @Override
    public void OnCollision(ICollision col) {

        position = startPosition;
        speedX = 0;
        speedY = 0;
    }

    public void Draw(Canvas canvas){

        canvas.drawCircle(position.x,position.y,radius,p);
    }

}
