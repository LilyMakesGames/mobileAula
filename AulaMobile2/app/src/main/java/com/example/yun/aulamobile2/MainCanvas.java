package com.example.yun.aulamobile2;

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

        handler = new TouchHandler();
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



