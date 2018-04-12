package com.example.yun.aulamobile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.Console;

/**
 * Created by Yun on 25/09/2017.
 */

public class MainCanvas extends AppCompatActivity{

    DrawView drawView;
    TouchHandler handler;

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        drawView = new DrawView(this);
        setContentView(drawView);

        handler = new TouchHandler(drawView);
        drawView.setOnTouchListener(handler);
    }

    @Override
    protected void onResume(){
        super.onResume();

        drawView.Resume();
    }

    protected void onPause(){
        super.onPause();

        drawView.Stop();
    }


}



class DrawView extends SurfaceView implements Runnable{

    Canvas canvas;
    SurfaceHolder surfaceHolder;
    Thread gameLoop;
    boolean running = false;





    Bitmap megaman;
    int widthFrame = 150, heightFrame = 150;
    int frameCount = 5;
    float timer;



    AnimatedSprite megamanSprite, megamanSprite2;




    public DrawView(Context context){
        super(context);

        surfaceHolder = getHolder();

        megaman = BitmapFactory.decodeResource(this.getResources(), R.drawable.megaman);
        megaman = Bitmap.createScaledBitmap(megaman,widthFrame * frameCount,heightFrame,false);

        megamanSprite = new AnimatedSprite(this.megaman,50,250);
        megamanSprite2 = new AnimatedSprite(this.megaman,50,400);
    }

    public void Stop(){
        running = false;

        try {
            gameLoop.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void Resume(){
        running = true;
        gameLoop = new Thread(this);
        gameLoop.start();
    }


    @Override
    public void run() {

        while(running){
            long time = System.currentTimeMillis();
            megamanSprite.Update();
            megamanSprite2.Update();
            Update();
            Draw();
            try {
                gameLoop.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            megamanSprite.timer += System.currentTimeMillis() - time;
            megamanSprite2.timer += System.currentTimeMillis() - time;
            timer += System.currentTimeMillis() - time;


        }
    }

    private void Update(){

    }





    private void Draw(){

        if(!surfaceHolder.getSurface().isValid()){
            return;
        }
        canvas = surfaceHolder.lockCanvas();

        canvas.drawColor(Color.WHITE);

        megamanSprite.Draw(canvas);
        megamanSprite2.Draw(canvas);


        surfaceHolder.unlockCanvasAndPost(canvas);

    }

}
