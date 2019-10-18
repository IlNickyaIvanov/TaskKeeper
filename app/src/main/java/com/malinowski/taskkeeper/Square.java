package com.malinowski.taskkeeper;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;


public class Square {
    ImageView img;
    float x,y;
    int size;
    int id;
    int imgNum;
    Activity activity;
    TextView text;
    @SuppressLint("ClickableViewAccessibility")
    Square(Activity activity, final float x, final float y, int size, final int id){
        img = new ImageView(activity);
        text = new TextView(activity);
        text.setText(""+id);
        text.setTextSize(30);
        text.setX(x);
        text.setY(y);
        this.activity = activity;
        this.x = x;
        this.y = y;
        this.id  = id;
        this.size = size;
        img.setX(x);
        img.setY(y);
        Random random = new Random();
        create(random.nextInt(4));
        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                shareID();
                return false;
            }
        });
    }

    void shareID(){
        MainActivity.activeSquare = id;
    }

    int checkBorders(float x, float y){
        if(x>= this.x+size*0.01 && x<this.x + size*0.99 && y>this.y + size*0.01 && y<this.y+size*0.99)
            return id;
        return -1;
    }

    void animateXY(float x1,float x2, float y1,float y2){
        ValueAnimator va = ValueAnimator.ofFloat(x1, x2);
        int mDuration = 700; //in millis
        va.setDuration(mDuration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                img.setTranslationX((float)animation.getAnimatedValue());
                text.setTranslationX((float)animation.getAnimatedValue());
            }
        });
        va.start();
        va = ValueAnimator.ofFloat(y1, y2);
        mDuration = 500; //in millis
        va.setDuration(mDuration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                img.setTranslationY((float)animation.getAnimatedValue());
                text.setTranslationY((float)animation.getAnimatedValue());
            }
        });
        va.start();
    }

    void setXY(float x, float y){
        //this.x = x;
        //this.y = y;
        if(x==-1 && y == -1){
            img.setX(this.x);
            img.setY(this.y);
        }
        else {
            img.setX(x); text.setX(x);
            img.setY(y);text.setY(y);
        }
    }

    void setSize(float n){
        ValueAnimator va = ValueAnimator.ofFloat(img.getScaleX(), n);
        int mDuration = 700; //in millis
        va.setDuration(mDuration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                img.setScaleX((float)animation.getAnimatedValue());
                text.setScaleX((float)animation.getAnimatedValue());
                img.setScaleY((float)animation.getAnimatedValue());
                text.setScaleY((float)animation.getAnimatedValue());
            }
        });
        va.start();
    }

    void delete(){
        FrameLayout parent = (FrameLayout) img.getParent(); // получаем родителя для вьюхи
        parent.removeView(img); // удалить вьюху
        parent = (FrameLayout) text.getParent(); // получаем родителя для вьюхи
        parent.removeView(text); // удалить вьюху
    }
     void create(int n){
        imgNum = n;
         switch (n) {
             case(0):
                 img.setImageResource(R.drawable.pink_square);
                 break;
             case(1):
                 img.setImageResource(R.drawable.blue_square);
                 break;
             case(2):
                 img.setImageResource(R.drawable.green_square);
                 break;
             case(3):
                 img.setImageResource(R.drawable.yellow_square);
                 break;
         }
         activity.addContentView(img,new RelativeLayout.LayoutParams(size,size));
         activity.addContentView(text,new RelativeLayout.LayoutParams(size,size));
     }
}
