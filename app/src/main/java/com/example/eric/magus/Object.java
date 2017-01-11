package com.example.eric.magus;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Eric on 09/02/2016.
 */
public class Object {
    private Bitmap spritesheet;
    private Bitmap res;
    private Bitmap res2;
    private Animation animation = new Animation();
    private int x;
    private int y;
    private int height;
    private int width;
    private int moveSpeed;
    private int weaponSize;
    private int range;
    private Unit unit;
    private boolean ranged;
    private long startTime;

    public Object(Bitmap res, int x, int y, int w, int h, int numFrames, int delay, int moveSpeed) {
        this.x = x;
        this.y = y;
        height = h;
        width = w;
        this.moveSpeed = moveSpeed;
        spritesheet = res;
        Bitmap[] image = new Bitmap[numFrames];

        for (int i = 0; i < image.length; i++) {

            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(delay);

    }

    public Object(Unit unit, int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        height = h;
        width = w;
        this.unit = unit;
    }

    public Object(Unit unit, Bitmap res, int x, int y, int w, int h, int numFrames, int delay) {
        this.x = x;
        this.y = y;
        height = h;
        width = w;
        this.unit = unit;
        spritesheet = res;
        Bitmap[] image = new Bitmap[numFrames];

        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(delay);
    }


    public Object(Bitmap res, Bitmap res2, Unit unit, int x, int y, int w, int h, int numFrames, int delay, int moveSpeed , boolean ranged) {
        this.unit = unit;
        this.res = res;
        this.res2 = res2;
        this.x = x;
        this.y = y;
        height = h;
        width = w;
        weaponSize = unit.getWeaponRange();
        range = unit.getRange();
        this.ranged = ranged;
        this.moveSpeed = moveSpeed;
        changetoWalk(numFrames, delay);

    }


    public void update() {
        animation.update();
    }

    public boolean playedOnce() {
        return animation.playedOnce();
    }
    /*public Bitmap scaleDouble (Bitmap original) {
        return Bitmap.createScaledBitmap(original, width*2, height*2, true);
    }*/

    public void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }

    public void drawMoving(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(), x, y, null);
        x += moveSpeed;
    }

    public void drawEnemyMoving(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(), x, y, null);
        x -= moveSpeed;
    }

    public Rect getHitbox(int type) {
        if (type == 0) { // ally
            return new Rect(x, y, x+width - weaponSize * 4 - 50, y+height);
        }
        else { // enemy
            return new Rect(x + weaponSize *4 + 50, y, x+width, y+height);
        }
    }

    public Rect getRangebox(int type) {
        if (type == 0) { // ally
            return new Rect(x, y, x+width + range, y+height);
        }
        else { // enemy
            return new Rect(x - range, y, x+width, y+height);
        }
    }

    public void setStartTime(long time) {
        startTime = time;
    }

    public long getStartTime() {
        return startTime;
    }

    public boolean getifRanged() {
        return ranged;
    }

    public void changetoFight(int numFrames, int delay) {
        spritesheet = res2;
        Bitmap[] image = new Bitmap[numFrames];

        for (int i = 0; i < image.length; i++) {

            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(delay);
    }

    public void changetoWalk(int numFrames, int delay) {
        spritesheet = res;
        Bitmap[] image = new Bitmap[numFrames];

        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(delay);
    }

    public Unit getUnit() {
        return unit;
    }

    public int getX () {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
