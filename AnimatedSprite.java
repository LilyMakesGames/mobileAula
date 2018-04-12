package com.example.yun.aulamobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.io.Console;
import java.util.Timer;

/**
 * Created by Yun on 28/09/2017.
 */

public class AnimatedSprite {


    Bitmap bmp;
    int currentFrame;
    int frameCount= 5;
    public float timer, timerMax = 128;
    int widthFrame = 150,heightFrame = 150;
    Rect frame;
    RectF frameDraw;

    int x;
    int y;

    public float timeToReach , t;

    float speedX;
    float speedY;
    float totalSpeed = 7;



    enum State{
        idle,
        walking,
    }
    boolean selected;

    State state = State.idle;


    public AnimatedSprite(Bitmap bmp, int x, int y){

        this.bmp = bmp;



        frame  = new Rect(0,0,widthFrame,heightFrame/2);
        frameDraw = new RectF(x,y,x+widthFrame,y+heightFrame);

        ChangeFrame(4);

        this.x = x;
        this.y = y;
    }


    public void Update(){
        if(t < timeToReach || state == State.walking){
            x += speedX;
            y += speedY;
            t+= totalSpeed;
            if(timer >= timerMax){
                currentFrame++;
                if(currentFrame >= frameCount -1){
                    currentFrame = 0;
                }
                ChangeFrame(currentFrame);
            }
        }



        if(t > timeToReach){
            t = 0;
            timeToReach = 0;
            state = State.idle;
            ChangeFrame(4);

        }

    }


    public void ChangeFrame(int frame){
        currentFrame = frame;
        this.frame.left = currentFrame * widthFrame;
        this.frame.right = this.frame.left + widthFrame;
        timer = 0;


    }




    public void SetNewPosition(int x, int y){
        double deltaX, deltaY;

        double direction;


        deltaX = (x - widthFrame/2) - this.x;
        deltaY = (y - heightFrame/2) - this.y;

        double distance = Math.sqrt(
                (deltaX * deltaX)
                        + (deltaY * deltaY));

        t=0;
        timeToReach = (float)distance;



        direction = Math.atan2(deltaY,deltaX);


        speedX = (float)Math.cos(direction) * totalSpeed;
        speedY = (float)Math.sin(direction) * totalSpeed;

    }

    public void Draw(Canvas canvas){
        Paint paint = new Paint();
        if(selected){
            frame.top = (heightFrame/2) + 1;
            frame.bottom = heightFrame;


        }else{
            frame.top = 0;
            frame.bottom = heightFrame/2;

        }
        frameDraw.set(x,y,x + widthFrame,y+heightFrame);



        canvas.drawBitmap(bmp,frame,frameDraw,paint);

    }
}
