package com.example.yun.aulamobile2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.renderscript.Float3;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.animation.LinearInterpolator;

import java.io.Console;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static java.lang.System.in;

/**
 * Created by Yun on 05/04/2018.
 */

class DrawView extends SurfaceView implements Runnable, SensorEventListener{

    Canvas canvas;
    SurfaceHolder surfaceHolder;
    Thread gameLoop;
    boolean running = false;
    DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

    SensorManager sensorManager;
    List<Sensor> sensorList;
    Sensor sensorAccel;
    Float3 accelMotion = new Float3();
    boolean endGame = false;

    Square square1;
    Square[][] squares;
    int lines = 6, columns = 6;

    Circle circle;
    Circle end;



    public DrawView(Context context){
        super(context);

        surfaceHolder = getHolder();

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        circle = new Circle(25,Color.RED,new Point(350,400));
        end = new Circle(5,Color.BLUE,new Point(550,700));

        //square1 = new Square(new Point(100,100),50);
        squares = new Square[columns][lines];
        for (int x = 0; x < columns;x++ ){
            for (int y = 0; y < lines ; y++){
                squares[x][y] = new Square(new Point(190*x +75, 190*y +95),40);
            }
        }
    }



    public void Stop(){
        running = false;


        try {
            gameLoop.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sensorManager.unregisterListener(this,sensorAccel);
    }

    public void Resume(){
        running = true;
        gameLoop = new Thread(this);
        gameLoop.start();
        sensorManager.registerListener(this,sensorAccel,SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void run() {

        while(running){
            Update();
            Draw();
            try {
                gameLoop.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



        }
    }

    private void Update(){

        circle.speedX += -accelMotion.x/10;
        circle.speedY += accelMotion.y/10;

        if(accelMotion.x < 1f && accelMotion.x > 0 && circle.speedX > 0){
            circle.speedX += accelMotion.x/10;
        }
        if(accelMotion.x > -1f && accelMotion.x <0 && circle.speedX < 0 ){
            circle.speedX += -accelMotion.x/10;
        }
        if(accelMotion.y < 1f && accelMotion.y > 0 && circle.speedY > 0){
            circle.speedY += accelMotion.y/10;
        }
        if(accelMotion.y > -1f && accelMotion.y <0 && circle.speedY < 0 ){
            circle.speedY += -accelMotion.y/10;
        }
        OnCollision();
        circle.Update();


    }

    void OnCollision(){
        for (int x = 0; x < columns;x++ ) {
            for (int y = 0; y < lines; y++) {
                if (circle.position.x + circle.radius > squares[x][y].rect.left &&
                        circle.position.x - circle.radius < squares[x][y].rect.right &&
                        circle.position.y - circle.radius < squares[x][y].rect.bottom &&
                        circle.position.y + circle.radius > squares[x][y].rect.top) {
                    if(!endGame){
                        circle.position = new Point(350,400);
                        circle.speedX = 0;
                        circle.speedY = 0;
                    }
                }
            }
        }
        if (circle.position.x + circle.radius > end.position.x - end.radius &&
                circle.position.x - circle.radius < end.position.x + end.radius &&
                circle.position.y - circle.radius < end.position.y + end.radius &&
                circle.position.y + circle.radius > end.position.y - end.radius)
        {
            endGame = true;
        }


    }



    @Override
    public void onSensorChanged(SensorEvent sensorEvent){
        switch(sensorEvent.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                accelMotion.x = sensorEvent.values[0];
                accelMotion.y = sensorEvent.values[1];
                accelMotion.z = sensorEvent.values[2];
                break;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }




    private void Draw(){

        Paint p = new Paint();
        p.setColor(Color.BLUE);
        if(!surfaceHolder.getSurface().isValid()){
            return;
        }

        canvas = surfaceHolder.lockCanvas();

        canvas.drawColor(Color.WHITE);

        //adrian.Draw(canvas);
        circle.Draw(canvas);
        if(endGame){
            canvas.drawText("Você venceu",150,150,p);

        }else{
            end.Draw(canvas);
            for (int x = 0; x < columns;x++ ){
                for (int y = 0; y < lines ; y++){
                    squares[x][y].Draw(canvas);
                }
            }

        }
        //square1.Draw(canvas);



        surfaceHolder.unlockCanvasAndPost(canvas);

    }

}
