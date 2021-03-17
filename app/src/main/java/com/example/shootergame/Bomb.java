package com.example.shootergame;

import java.util.Random;

public class Bomb {
    public int x1, y1, x2, y2;
    private int dis;
    private int size;

    public Bomb (){
        Random random = new Random();
        size = (random.nextInt(30)) + 30;

        this.x1 = random.nextInt(2900);
        this.y1 = 0;
        this.x2 = x1 + size;
        this.y2 = y1 + size;
        this.dis = (int) (Math.random() * 10) + 1;
    }

    public void move(){
        y1 += dis;
        y2 += dis;
    }
}
