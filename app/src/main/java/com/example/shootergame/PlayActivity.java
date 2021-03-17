package com.example.shootergame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {

    public static int x1 = 1480;
    public static int y1 = 945;
    public static int x2 = 1560;
    public static int y2 = 1025;

    UserVO user;
    String skin;
    int score = 0;
    int point = 0;
    public static String direction = "right";
    public static int coolTime = 0;
    ImageButton leftBtn, rightBtn, flashBtn;
    boolean isLeftBtnDown, isRightBtnDown;
    public static boolean isPlaying = true;
    public static List<Bomb> bombList;
    public static List<Point> pointList;
    Iterator<Bomb> iter;
    Iterator<Point> iter2;
    TextView scoreView, pointView, coolView;
    MyView myView;
    public static Paint pnt;
    public static Paint bombPnt;
    public Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        x1 = 1480;
        y1 = 945;
        x2 = 1560;
        y2 = 1025;
        score = 0;
        point = 0;
        isPlaying = true;
        bombList = Collections.synchronizedList(new ArrayList());
        pointList = Collections.synchronizedList(new ArrayList());


        myView = findViewById(R.id.canvasView);
        pnt = new Paint();
        bombPnt = new Paint();
        bombPnt.setColor(Color.RED);

        skin = (String) getIntent().getSerializableExtra("skin");
        user = (UserVO) getIntent().getSerializableExtra("user");

        if (skin.equals("white")){
            //me.setImageResource(R.drawable.white_me);
            pnt.setColor(Color.rgb(255, 255, 255));
        } else if (skin.equals("green")) {
            //me.setImageResource(R.drawable.green_me);
            pnt.setColor(Color.rgb(177, 241, 121));
        } else if (skin.equals("blue")) {
            //me.setImageResource(R.drawable.blue_me);
            pnt.setColor(Color.rgb(146, 195, 254));
        } else if (skin.equals("yellow")) {
            //me.setImageResource(R.drawable.yellow_me);
            pnt.setColor(Color.rgb(248, 238, 103));
        } else if (skin.equals("purple")) {
            //me.setImageResource(R.drawable.purple_me);
            pnt.setColor(Color.rgb(208, 132, 231));
        } else if (skin.equals("cyan")) {
            //me.setImageResource(R.drawable.cyan_me);
            pnt.setColor(Color.rgb(118, 226, 212));
        }

        scoreView = findViewById(R.id.scoreView);
        pointView = findViewById(R.id.play_point);
        coolView = findViewById(R.id.coolView);
        coolView.setVisibility(View.INVISIBLE);

        leftBtn = findViewById(R.id.leftBtn);
        leftBtn.setOnTouchListener(onLeftBtnTouchListener);

        rightBtn = findViewById(R.id.rightBtn);
        rightBtn.setOnTouchListener(onRightBtnTouchListener);

        flashBtn = findViewById(R.id.flashBtn);
        flashBtn.setOnTouchListener(onFlashBtnTouchListener);

        BombThread bombThread = new BombThread();
        bombThread.start();

        BombMoveThread bombMoveThread = new BombMoveThread();
        bombMoveThread.start();

        FlashThread flashThread = new FlashThread();
        flashThread.start();

        PointThread pointThread = new PointThread();
        pointThread.start();

        PointMoveThread pointMoveThread = new PointMoveThread();
        pointMoveThread.start();
    }

    private class LeftThread extends Thread {
        @Override
        public void run() {
            super.run();
            while(isLeftBtnDown && isPlaying) {
                if (x1 > 0) {
                    x1 -= 3;
                    x2 -= 3;
                    myView.invalidate();
                    try {
                        synchronized (this) {
                            Thread.sleep(5);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private View.OnTouchListener onLeftBtnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    direction = "left";
                    isLeftBtnDown = true;
                    LeftThread leftThread = new LeftThread();
                    leftThread.start();
                    break;

                case MotionEvent.ACTION_UP:
                    isLeftBtnDown = false;
                    break;

                default:
                    break;
            }
            return false;
        }
    };

    private class RightThread extends Thread {
        @Override
        public void run() {
            super.run();
            while(isRightBtnDown && isPlaying) {
                if (x2 < 2900) {
                    x1 += 3;
                    x2 += 3;
                    myView.invalidate();
                    try {
                        synchronized (this) {
                            Thread.sleep(5);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private View.OnTouchListener onRightBtnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    direction = "right";
                    isRightBtnDown = true;
                    RightThread rightThread = new RightThread();
                    rightThread.start();
                    break;

                case MotionEvent.ACTION_UP:
                    isRightBtnDown = false;
                    break;

                default:
                    break;
            }
            return false;
        }
    };

    private View.OnTouchListener onFlashBtnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (coolTime == 0) {
                        if (direction.equals("left")) {
                            x1 -= 350;
                            x2 -= 350;
                            myView.invalidate();
                            coolTime = 20;
                            coolView.setText("" + coolTime);
                            coolView.setVisibility(View.VISIBLE);
                        } else if (direction.equals("right")) {
                            x1 += 350;
                            x2 += 350;
                            myView.invalidate();
                            coolTime = 20;
                            coolView.setText("" + coolTime);
                            coolView.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                default:
                    break;
            }
            return false;

        }
    };

    private class FlashThread extends Thread {
        @Override
        public void run() {
            super.run();
            while(isPlaying) {
                if (coolTime > 0) {
                    coolTime -= 1;
                    coolView.setText(""+coolTime);
                } else {
                    coolView.setVisibility(View.INVISIBLE);
                }
                score += 1;
                scoreView.setText(""+score);

                try {
                    synchronized (this) {
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class BombThread extends Thread {
        @Override
        public void run() {
            super.run();
            while(isPlaying) {
                synchronized (bombList) {
                    Bomb bomb = new Bomb();
                    bombList.add(bomb);
                    iter = bombList.iterator();
                }
                try {
                    synchronized (this) {
                        Thread.sleep(300);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class BombMoveThread extends Thread {
        @Override
        public void run() {
            super.run();
            while(isPlaying) {
                synchronized (bombList) {
                    try {
                        Thread.sleep(5);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            iter = bombList.iterator();
                            while (iter.hasNext()) {
                                Bomb b = iter.next();

                                b.move();
                                myView.invalidate();

                                if (b.y1 > 1025) {
                                    iter.remove();
                                }

                                if (b.y2 >= y1) {
                                    if (b.x1 >= x1 && b.x1 <= x2) {
                                        Log.d("끝", "game over");
                                        isPlaying = false;
                                        Intent intent = new Intent(PlayActivity.this, ResultActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.putExtra("user", user);
                                        intent.putExtra("score", score);
                                        intent.putExtra("point", point);
                                        startActivity(intent);
                                        break;
                                    } else if (b.x2 >= x1 && b.x2 <= x2) {
                                        Log.d("끝", "game over");
                                        isPlaying = false;
                                        Intent intent = new Intent(PlayActivity.this, ResultActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        intent.putExtra("user", user);
                                        intent.putExtra("score", score);
                                        intent.putExtra("point", point);
                                        startActivity(intent);
                                        break;
                                    }
                                }
                            }
                        }
                    });
                }
            }
        }
    }

    private class PointThread extends Thread {
        @Override
        public void run() {
            super.run();
            while(isPlaying) {
                Point point = new Point();
                pointList.add(point);
                iter2 = pointList.iterator();

                try {
                    synchronized (this) {
                        Thread.sleep(8000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class PointMoveThread extends Thread {
        @Override
        public void run() {
            super.run();
            while(isPlaying) {
                iter2 = pointList.iterator();
                while (iter2.hasNext()){
                    Point p = iter2.next();

                    p.move();
                    myView.invalidate();

                    if (p.y1 > 1025){
                        iter2.remove();
                    }

                    if (p.y2 >= y1){
                        if (p.x1 >= x1 && p.x1 <= x2) {
                            iter2.remove();
                            point += 10;
                            pointView.setText(""+point);

                        } else if (p.x2 >= x1 && p.x2 <= x2){
                            iter2.remove();
                            point += 10;
                            pointView.setText(""+point);
                        }
                    }
                }
                try {
                    synchronized (this) {
                        Thread.sleep(5);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class MyView extends View {
    public MyView(Context context){
        super(context);
    }
    public MyView(Context context, AttributeSet att){
        super(context,att);
    }
    public MyView(Context context, AttributeSet att, int a){
        super(context,att,a);
    }

    @Override
    public void onDraw(Canvas canvas){
        canvas.drawRect(PlayActivity.x1,
                PlayActivity.y1,
                PlayActivity.x2,
                PlayActivity.y2,
                PlayActivity.pnt);

        Iterator<Bomb> iter = PlayActivity.bombList.iterator();
        while (iter.hasNext()){
            Bomb bomb = iter.next();
            canvas.drawRect(bomb.x1, bomb.y1, bomb.x2, bomb.y2, PlayActivity.bombPnt);
        }

        Resources r = getResources();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) r.getDrawable(R.drawable.be_icon);
        Bitmap bitmap = bitmapDrawable.getBitmap();
        Iterator<Point> iter2 = PlayActivity.pointList.iterator();
        while (iter2.hasNext()){
            Point point = iter2.next();
            Rect dst = new Rect(point.x1, point.y1, point.x2, point.y2);
            canvas.drawBitmap(bitmap, null, dst, null);
        }
    }
}