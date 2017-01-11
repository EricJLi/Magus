package com.example.eric.magus;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Eric on 16/03/2016.
 */
public class Spell {

    private int x;
    private int y;
    private int height;
    private int width;
    private Bitmap spritesheet;
    private Animation animation = new Animation();
    private int type;
    private Random random;
    private int spellPower;
    private long startTime;
    private long endTime;
    private boolean hitOnce;

    public Spell(Bitmap res,int x, int y, int w, int h, int numFrames, int delay, int type, int sp) {
        this.x = x;
        this.y = y;
        height = h;
        width = w;
        spellPower = sp;
        this.type = type;
        random = new Random();
        spritesheet = res;
        Bitmap[] image = new Bitmap[numFrames];

        for (int i = 0; i < image.length; i++) {

            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }
        startTime = System.nanoTime();
        animation.setFrames(image);
        animation.setDelay(delay);

    }

    public long getStartTime() {
        return startTime;
    }

    public boolean getHitOnce() {
        return hitOnce;
    }

    public void setHitOnce(boolean n) {
        hitOnce = n;
    }

    public void setStartTime(long s) {
        startTime = s;
    }

    public void setEndTime(long s) {
        endTime = s;
    }

    public long getEndTime() {
        return endTime;
    }

    public Rect getCollisionbox() {
        return new Rect(x, y, x + width, y + height);
    }

    public boolean playedOnce() {
        return animation.playedOnce();
    }

    public int getSpellPower() {
        return spellPower;
    }

    public void update() {
        animation.update();
    }

    public void draw(Canvas canvas) {
        if (type == 4) {
            canvas.drawBitmap(animation.getImage(), x, y, null);
            y += 10;
        }
        else if (type == 3){
            canvas.drawBitmap(animation.getImage(), x, y, null);
            x += 2;
        }
        else if (type == 1) {
            canvas.drawBitmap(animation.getImage(), x, y, null);
            x += 1;
        }
        else {
            canvas.drawBitmap(animation.getImage(), x, y, null);
        }

    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

}
