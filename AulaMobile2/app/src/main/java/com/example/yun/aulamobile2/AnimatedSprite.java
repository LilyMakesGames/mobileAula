package com.example.yun.aulamobile2;

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
    int frameCount= 3;
    int line = 1;
    public float timer, timerMax = 80;
    int widthFrame,heightFrame;
    Rect frame;
    RectF frameDraw;


    int x,y;



    public AnimatedSprite(Bitmap bmp, int x, int y, int width, int height){

        this.bmp = bmp;
        this.x = x;
        this.y = y;

        widthFrame = width;
        heightFrame = height;

        frame  = new Rect(0,0,widthFrame,heightFrame);
        frameDraw = new RectF(x,y,x+widthFrame,y+heightFrame);


    }


    public void Update(){

        if(timer  >= timerMax){
            currentFrame++;
            ChangeFrame(currentFrame);
        }

    }


    public void ChangeFrame(int frame){
        currentFrame = frame;
        if (currentFrame > frameCount){
            currentFrame = 0;
            if(line == 0){
                line = 1;
            }
            else{
                line = 0;
            }
        }
        this.frame.left = currentFrame * widthFrame;
        this.frame.right = this.frame.left + widthFrame;
        this.frame.top = heightFrame * line;
        this.frame.bottom = heightFrame + (heightFrame * line);
        if(line >0){
            y = 590;
        }else{
            y = 570;
        }
        timer = 0;


    }




    public void Draw(Canvas canvas){
        Paint paint = new Paint();

        frameDraw.set(x,y,x + widthFrame,y+heightFrame);



        canvas.drawBitmap(bmp,frame,frameDraw,paint);

    }
}
