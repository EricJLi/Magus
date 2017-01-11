package com.example.eric.magus;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by Eric on 07/02/2016.
 */
public class Unit implements Serializable{
    private int level;
    private int exp;
    private boolean equipped;
    private String name;
    private int res;
    private int smallres;
    private int damage;
    private int hp;
    private int originalhp;
    private int moveSpeed;
    private int weaponSize;
    private int range;
    private int manacost;
    private double requiredExp;
    private double attackSpeed;
    private int expScaling; //lower the better
    private int spawn;
    private int moveInterval; //higher the more faster


    public Unit(String name, int level) {
        this.name = name;
        this.level = level;
        if (name.matches("skeleton")) {
            originalhp = 120 + 5 * level;
            hp = originalhp;
            damage = 10 + level;
            moveSpeed = 1;
            weaponSize = 4;
            range = 0;
            attackSpeed = 2;
        }
        else if (name.matches("skeletonLancer")) {
            originalhp = 180 + 3 * level;
            hp = originalhp;
            damage = 10 + 2 * level;
            moveSpeed = 1;
            weaponSize = 8;
            range = 0;
            attackSpeed = 1;
        }
        else if (name.matches("skeletonArcher")) {
            originalhp = 100 + 2 * level;
            hp = originalhp;
            damage = 10 + 2 * level;
            moveSpeed = 1;
            weaponSize = 6;
            range = 60;
            attackSpeed = 1;
        }
        else if (name.matches("AllyTower")) {
            originalhp = 1000;
            hp = originalhp;
            weaponSize = 5;
        }
        else if (name.matches("EnemyTower")) {
            originalhp = 1000 + 100 * level;
            hp = originalhp;
            weaponSize = 5;
        }
        moveInterval = 0;
    }

    public Unit (String name, int level, int exp, boolean equipped) { //when getting info from a save
        this.level = level;
        this.exp = exp;
        this.name = name;
        spawn = 0;
        this.equipped = equipped;
        if (name.matches("peasant")) {
            res = R.drawable.peasanticon;
            smallres = R.drawable.peasantbutton;
            originalhp = 120 + level * 8;
            hp = originalhp;
            damage = 20 + level * 5;
            moveSpeed = 1;
            weaponSize = 8;
            range = 0;
            manacost = 10;
            attackSpeed = 1;
            expScaling = 0;
        }
        else if (name.matches("knight")) {
            res = R.drawable.knighticon;
            smallres = R.drawable.knightbutton;
            originalhp = 150 + level * 12;
            hp = originalhp;
            damage = 60 + level * 4;
            moveSpeed = 1;
            weaponSize = 7;
            range = 0;
            manacost = 50;
            attackSpeed = 1;
            expScaling = 3;
        }
        else if (name.matches("slingshot")) {
            res = R.drawable.peasantslingicon;
            smallres = R.drawable.slingbutton;
            originalhp = 100 + level * 5;
            hp = originalhp;
            damage = 10 + level * 4;
            moveSpeed = 1;
            weaponSize = 6;
            range = 60;
            manacost = 20;
            attackSpeed = 1;
            expScaling = 3;
        }
        else if (name.matches("minigolem")) {
            if (level >= 5) {
                this.name = "megagolem";
                res = R.drawable.megagolemicon;
                smallres = R.drawable.megagolembutton;
                originalhp = 700 + level * 2 - 5*2;
                hp = originalhp;
                damage = 40 + level*2 - 5*2;
                moveSpeed = 2;
                weaponSize = 24;
                range = 0;
                manacost = 10;
                attackSpeed = 0.5;
                expScaling = 6;
            }
            else {
                res = R.drawable.minigolemicon;
                smallres = R.drawable.minigolembutton;
                originalhp = 500 + level * 2;
                hp = originalhp;
                damage = 30 + level * 2;
                moveSpeed = 2;
                weaponSize = 9;
                range = 0;
                manacost = 10;
                attackSpeed = 0.5;
                expScaling = 5;
            }

        }
        moveInterval = 0;
        requiredExp = Math.pow(level, 2) * 150 * (1 + 0.1 * expScaling);
        // res2 = ContextCompat.getDrawable(context, R.drawable.peasant);  try this if it is lagging

    }

    public void addMoveInterval() {
        moveInterval++;
    }
    public void setMoveInterval(int n) {
        moveInterval = n;
    }

    public int getMoveInterval() {
        return moveInterval;
    }

    public int getHP () {
        return hp;
    }

    public int getOriginalhp () {
        return originalhp;
    }

    public int getDMG() {
        return damage;
    }

    public void setHP(int dmg) {
        hp = hp - dmg;
    }

    public String getName () {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }

    public int getExptoNextLevel() {
        return (int)(requiredExp - exp);
    }

    public void addExp(int amount) {
        int newExp = exp + amount + (5 * spawn);
        if (newExp >= requiredExp) {
            level ++;
            exp = (int)((newExp) - requiredExp);
            requiredExp = Math.pow(level, 2) * 150 * (1 - expScaling/10);
        }
        else {
            exp = newExp;
        }
    }

    public void addSpawn() {
        spawn++;
    }

    public double getRequiredEXP() {
        return requiredExp;
    }

    public boolean getEquipped() {
        return equipped;
    }

    public int getWeaponRange() {
        return weaponSize;
    }

    public int getRange() {
        return range;
    }

    public void setEquipped (Boolean t) {
        equipped = t;
    }

    public int getImage() {
        return res;
    }

    public int getSmallImage() {
        return smallres;
    }

    public int getManacost () {
        return manacost;
    }

    public double getMoveSpeed () {
        return moveSpeed;
    }

    public double getAttackSpeed () {
        return attackSpeed;
    }

}

