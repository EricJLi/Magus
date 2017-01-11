package com.example.eric.magus;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Eric on 17/02/2016.
 */
public class Projectile {

    private Bitmap res;
    private int x;
    private int y;
    private int w;
    private int h;
    private int dmg;
    private Rect collisionBox;
    private int speed;

    public Projectile (Bitmap res, int x, int y, int w, int h, int dmg) {
        this.res = res;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.dmg = dmg;
        collisionBox = new Rect(x, y, x + w, y + h);
        speed = 8;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(res, x, y, null);
        x += speed;
        collisionBox = new Rect(x, y, x + w, y + h);
    }

    public void drawEnemy(Canvas canvas) {
        canvas.drawBitmap(res, x, y, null);
        x -= speed;
        collisionBox = new Rect(x, y, x + w, y + h);
    }

    public int getDMG() {
        return dmg;
    }

    public Rect getCollisionBox() {
        return collisionBox;
    }

    public int getX () {
        return x;
    }

    public int getY () {
        return y;
    }

}
