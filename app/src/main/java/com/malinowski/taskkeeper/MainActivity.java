package com.malinowski.taskkeeper;

import android.drm.DrmStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {
    int screenWidth, screenHeight;
    static int activeSquare = -1;
    float corX,corY;
    static Square squares[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        screenWidth = getApplicationContext().getResources().getDisplayMetrics().widthPixels;
        screenHeight = getApplicationContext().getResources().getDisplayMetrics().heightPixels;
        squares = new Square[5][5];
        int size = screenWidth/6;
        for(int i = 0;i<squares.length;i++){
            for(int j = 0;j<squares[0].length;j++)
                squares[i][j] = new Square(this,
                        size*j+(screenWidth-size*squares[0].length)/2f,
                            size*i+(screenHeight-size*squares.length)/2f,size,i*5 + j);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (activeSquare != -1) {
            Square sq = squares[activeSquare / 5][activeSquare % 5];
            switch (event.getAction()) {
                case (MotionEvent.ACTION_DOWN):
                    corX = event.getX()-sq.x;
                    corY = event.getY()-sq.y;
                    sq.delete();
                    sq.create(sq.imgNum);
                    sq.setSize(1.2f);
                case (MotionEvent.ACTION_MOVE):
                    int onSquareNum;
                    onSquareNum = findSquare(event.getX(),event.getY());
                    if(onSquareNum!= -1){
                        swapSquares(activeSquare,onSquareNum);
                        activeSquare = onSquareNum;
                    }

                    sq.setXY(event.getX() - corX, event.getY() - corY);
                    break;
                case (MotionEvent.ACTION_UP):
                    sq.setSize(1);
                    activeSquare = -1;
                    sq.animateXY(event.getX() - corX,sq.x, event.getY()-corY,sq.y);
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    static int findSquare(float x, float y){
        for(int i =0; i<squares.length;i++){
            for (int j = 0;j<squares[0].length;j++){
                if(i*5+j == activeSquare)
                    continue;
                if(squares[i][j].checkBorders(x,y)!=-1)
                    return squares[i][j].checkBorders(x,y);
            }
        }
        return -1;
    }

    void swapSquares(int a, int b){
        final Square sqA = squares[a/5][a%5];
        final Square sqB = squares[b/5][b%5];

        sqB.animateXY(sqB.x,sqA.x,sqB.y,sqA.y);

        squares[a/5][a%5] = sqB;
        squares[b/5][b%5] = sqA;

        float c;
        c = sqA.x;
        sqA.x = sqB.x;
        sqB.x = c;
        c = sqA.y;
        sqA.y = sqB.y;
        sqB.y = c;
        c = sqA.id;
        sqA.id = sqB.id;
        sqB.id = (int) c;

        //sqA.setXY(-1,-1);
        //sqB.setXY(-1,-1);
    }
}
