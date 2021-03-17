package com.example.shootergame;

import java.util.Random;

public class Point {
    public int x1, y1, x2, y2;
    private int dis;

    public Point (){
        Random random = new Random();

        this.x1 = random.nextInt(2900);
        this.y1 = 0;
        this.x2 = x1 + 70;
        this.y2 = y1 + 70;
        this.dis = (int) (Math.random() * 10) + 1;
    }

    public void move(){
        y1 += dis;
        y2 += dis;
    }
}
