package com.example.yun.aulamobile;

import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Yun on 02/10/2017.
 */

public class TouchHandler implements View.OnTouchListener {

    DrawView drawView;
    AnimatedSprite sprite1,sprite2;

    public TouchHandler(DrawView drawView) {

        this.drawView = drawView;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        sprite1 = drawView.megamanSprite;
        sprite2 = drawView.megamanSprite2;



        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(sprite1.selected && !sprite2.selected && !CheckCollision(sprite2.frameDraw,motionEvent)){
                    sprite1.state = AnimatedSprite.State.walking;
                    sprite1.SetNewPosition((int) motionEvent.getX(), (int) motionEvent.getY());
                    sprite1.selected = false;
                    break;
                }else if(sprite1.selected && !sprite2.selected && CheckCollision(sprite2.frameDraw,motionEvent)){

                    sprite1.selected = false;
                    sprite2.selected = true;
                    break;
                }
                if(sprite2.selected && !sprite1.selected && !CheckCollision(sprite1.frameDraw,motionEvent)){
                    sprite2.state = AnimatedSprite.State.walking;
                    sprite2.SetNewPosition((int)motionEvent.getX(),(int)motionEvent.getY());
                    sprite2.selected = false;
                    break;
                }else if(sprite2.selected && !sprite1.selected && CheckCollision(sprite1.frameDraw,motionEvent)){

                    sprite2.selected = false;
                    sprite1.selected = true;
                    break;
                }
                if(CheckCollision(sprite1.frameDraw, motionEvent)){
                    if(!sprite1.selected){
                        sprite1.selected = true;
                    }
                    if(sprite2.selected){
                        sprite2.selected = false;
                    }

                }
                if(CheckCollision(sprite2.frameDraw, motionEvent)) {
                    if (!sprite2.selected) {
                        sprite2.selected = true;
                    }
                    if(sprite1.selected){
                        sprite1.selected = false;
                    }

                }


        }

        return true;
    }


    public boolean CheckCollision(RectF r, MotionEvent motionEvent){
        if(motionEvent.getX() > r.left && motionEvent.getX() < r.right && motionEvent.getY() > r.top && motionEvent.getY() < r.bottom){
            return true;
        }
        else{
            return false;
        }

    }
}
