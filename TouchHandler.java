package com.example.yun.aulamobile2;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Yun on 09/04/2018.
 */

public class TouchHandler implements View.OnTouchListener {




    public TouchHandler(){

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        DrawView drawView = (DrawView)view;
        int action = motionEvent.getActionMasked();
        int pointerIndex = motionEvent.getActionIndex();
        int id = motionEvent.getPointerId(pointerIndex);


        return false;
    }
}
