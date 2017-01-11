package com.example.eric.magus;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Eric on 07/02/2016.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    public static final int WIDTH = 480;
    public static final int HEIGHT = 270;
    float scaleFactorX;
    float scaleFactorY;
    private MainThread thread;
    private int currentMana;
    private Context context;
    private long manastartTime;
    private long manacurrentTime;
    private long allycurrentTime;
    private long enemycurrentTime;
    private long startTime;
    private long currentTime;
    private ArrayList<Unit> Roster = new ArrayList<Unit>();
    private ArrayList<Unit> Equipped = new ArrayList<Unit>();
    private int level;
    private Inventory inventory;
    private int costume;
    private int upgrades;
    private Object pcostume;
    private Object unit;
    private Object skeleton;
    SharedPreferences prefs;
    private Player player;
    private ArrayList<Object> AllyWalk = new ArrayList<Object>();
    private ArrayList<Object> AllyFight = new ArrayList<Object>();
    private ArrayList<Object> deathAnim = new ArrayList<Object>();
    private ArrayList<Object> Ally = new ArrayList<Object>();
    private ArrayList<Object> Enemy = new ArrayList<Object>();
    private ArrayList<Object> EnemyWalk = new ArrayList<Object>();
    private ArrayList<Object> EnemyFight = new ArrayList<Object>();
    private ArrayList<Object> toRemoveAllyWalk = new ArrayList<Object>();
    private ArrayList<Object> toRemoveAllyFight = new ArrayList<Object>();
    private ArrayList<Object> toRemoveAlly = new ArrayList<Object>();
    private ArrayList<Object> toRemoveEnemy = new ArrayList<Object>();
    private ArrayList<Object> toRemoveEnemyWalk = new ArrayList<Object>();
    private ArrayList<Object> toRemoveEnemyFight = new ArrayList<Object>();
    private Rect unit1;
    private Rect unit2;
    private Rect unit3;
    private Rect unit4;
    private Rect unit5;
    private Random random;
    private int zone;
    private int stage;
    private ArrayList<Projectile> rocks = new ArrayList<Projectile>();
    private ArrayList<Projectile> toRemoverocks = new ArrayList<Projectile>();
    private ArrayList<Projectile> arrows = new ArrayList<Projectile>();
    private ArrayList<Projectile> toRemovearrows = new ArrayList<Projectile>();
    private ArrayList<Spell> fireSpell = new ArrayList<Spell>();
    private ArrayList<Spell> explosionSpell = new ArrayList<Spell>();
    private ArrayList<Spell> waterSpell = new ArrayList<Spell>();
    private ArrayList<Spell> earthSpell = new ArrayList<Spell>();
    private ArrayList<Spell> toRemovefireSpell = new ArrayList<Spell>();
    private ArrayList<Spell> toRemoveexplosionSpell = new ArrayList<Spell>();
    private ArrayList<Spell> toRemovewaterSpell = new ArrayList<Spell>();
    private ArrayList<Spell> toRemoveearthSpell = new ArrayList<Spell>();
    private boolean initialize;
    private Object AllyTower;
    private Object EnemyTower;
    private boolean lose;
    private boolean win;
    private int kills;
    private int manaSpent;
    private int exp;
    private int expGain;
    private int gold;
    private int goldGain;
    private int tower;
    private boolean once;

    public GamePanel (Context context) {
        super(context);
        this.context = context;
        inventory = new Inventory(context);
        prefs = context.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        level = prefs.getInt("level", 1);
        prefs = context.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        costume = prefs.getInt("costume", R.drawable.bluemage);
        prefs = context.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        tower = prefs.getInt("tower", R.drawable.bluetower);
        prefs = context.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        upgrades = prefs.getInt("playerupgrades", 0);
        Roster = inventory.getRoster();
        for (Unit o: Roster) {
            if (o.getEquipped()) {
                Equipped.add(o);
            }
        }
        player = new Player (upgrades);
        random = new Random();
        //add the callback to surfaceholder to get events
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        //make gamepanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (Roster.size() > 0) {
            thread.setRunning(true);
            thread.start();
            currentMana = 0;
            scaleFactorX = getWidth()/(WIDTH*1.f);
            scaleFactorY = getHeight()/(HEIGHT * 1.f);
            unit1 = new Rect(WIDTH/4, HEIGHT - 50, (WIDTH/4) + 50, HEIGHT);
            unit2 = new Rect(WIDTH/4 + 50, HEIGHT - 50, (WIDTH/4) + 100, HEIGHT);
            unit3 = new Rect(WIDTH/4 + 100, HEIGHT - 50, (WIDTH/4) + 150, HEIGHT);
            unit4 = new Rect(WIDTH/4 + 150, HEIGHT - 50, (WIDTH/4) + 200, HEIGHT);
            unit5 = new Rect(WIDTH/4 + 200, HEIGHT - 50, (WIDTH/4) + 250, HEIGHT);
            manastartTime = System.nanoTime();
            pcostume = new Object(BitmapFactory.decodeResource(context.getResources(), costume), 0, HEIGHT - (150 + 35) - 70, 70, 70, 4, 250, 0);
            AllyTower = new Object(new Unit("AllyTower", 0), -40, HEIGHT - 150 - 70, 100, 150);
            EnemyTower = new Object(new Unit("EnemyTower", 0), BitmapFactory.decodeResource(context.getResources(), R.drawable.enemytower), WIDTH-80, HEIGHT - 200 - 70, 100, 200, 2, 1000/2);
            Ally.add(AllyTower);
            skeleton = new Object(BitmapFactory.decodeResource(context.getResources(), R.drawable.skeletonwalking), BitmapFactory.decodeResource(context.getResources(), R.drawable.skeletonattacking), new Unit("skeleton", level),WIDTH - 30 - 70, HEIGHT - 70 - 88, 70, 70, 4, (1000/4), 1, false);
            EnemyWalk.add(skeleton);
            Enemy.add(skeleton);
            Enemy.add(EnemyTower);
            initialize = true;
            lose = false;
            win = false;
            once = false;
        }

    }

    public void update() {
        if (player.getPlaying()) {
            //generates mana
            manacurrentTime = System.nanoTime();
            if ((manacurrentTime - manastartTime) > (1000000000 / player.getManaregen())) {
                if (currentMana < player.getMana()) {
                    currentMana++;
                }
                manastartTime = System.nanoTime();
            }
            //spawns enemy
            currentTime = System.nanoTime() / 1000;
            int spawnTime = 7000000 - level * 100000;
            if (spawnTime < 2000000) {
                spawnTime = 2000000;
            }
            if ((currentTime - startTime) > spawnTime) {
                int offsetx = random.nextInt(6); // 0 - 5
                int offsety = random.nextInt(4) * 5; // 0 - 3
                int spawn = random.nextInt(3);
                if (spawn == 0) {
                    skeleton = new Object(BitmapFactory.decodeResource(context.getResources(), R.drawable.skeletonwalking), BitmapFactory.decodeResource(context.getResources(), R.drawable.skeletonattacking), new Unit("skeleton", level),WIDTH - offsetx - 30 - 70, HEIGHT - 70 - 82 - (offsety), 70, 70, 4, (1000/4), 1, false);
                }
                else if (spawn == 1) {
                    skeleton = new Object(BitmapFactory.decodeResource(context.getResources(), R.drawable.skeletonlancerwalk), BitmapFactory.decodeResource(context.getResources(), R.drawable.skeletonlancerattack), new Unit("skeletonLancer", level),WIDTH - offsetx - 30 - 70, HEIGHT - 70 - 82 - (offsety), 70, 70, 4, (1000/4), 1, false);
                }
                else if (spawn == 2) {
                    offsety = random.nextInt(2) * 5;
                    skeleton = new Object(BitmapFactory.decodeResource(context.getResources(), R.drawable.skeletonarcherwalk), BitmapFactory.decodeResource(context.getResources(), R.drawable.skeletonarcherattack), new Unit("skeletonArcher", level),WIDTH - offsetx - 30 - 70, HEIGHT - 70 - 82 - (offsety), 70, 70, 4, (1000/4), 1, true);
                }
                EnemyWalk.add(skeleton);
                Enemy.add(skeleton);
                startTime = System.nanoTime() / 1000;
            }

            if (AllyTower.getUnit().getHP() <= 0) { //if your tower dies
                lose = true;
                player.setPlaying(false);
            }

            if (EnemyTower.getUnit().getHP() <= 0) { //if your enemy tower dies
                win = true;
                player.setPlaying(false);
            }

            for (Object m: deathAnim) {
                if (!m.playedOnce()) {
                    m.update();
                }
                else {
                    deathAnim.remove(m);
                }
            }

            for (Spell s: earthSpell) {
                s.update();
                s.setEndTime(System.nanoTime());
                if (s.getX() < WIDTH) {
                    if (s.getEndTime() - s.getStartTime() > 500000000) {
                        for (Object e : Enemy) {
                            if (!e.getUnit().getName().matches("EnemyTower")) {
                                if (Rect.intersects(s.getCollisionbox(), e.getHitbox(1))) {
                                    e.getUnit().setHP(s.getSpellPower());
                                    if (e.getUnit().getHP() <= 0) {
                                        kills++;
                                        deathAnim.add(new Object(BitmapFactory.decodeResource(context.getResources(), R.drawable.deathanimation), e.getX() + e.getWidth()/2 - 32, e.getY() + e.getHeight() - 64, 64, 64, 10, 100, 0));
                                        toRemoveEnemyFight.add(e);
                                        toRemoveEnemy.add(e);
                                        toRemoveEnemyWalk.add(e);
                                    }
                                }
                            }
                        }
                        Enemy.removeAll(toRemoveEnemy);
                        EnemyFight.removeAll(toRemoveEnemyFight);
                        EnemyWalk.removeAll(toRemoveEnemyWalk);
                        clearALists();
                        s.setStartTime(System.nanoTime());
                    }
                }
                else {
                    toRemoveearthSpell.add(s);
                }
            }
            earthSpell.removeAll(toRemoveearthSpell);
            toRemoveearthSpell.clear();

            for (Spell s: waterSpell) {
                s.update();
                s.setEndTime(System.nanoTime());
                if (s.getX() < WIDTH) {
                    if (s.getEndTime() - s.getStartTime() > 300000000) {
                        for (Object e : Enemy) {
                            if (!e.getUnit().getName().matches("EnemyTower")) {
                                if (Rect.intersects(s.getCollisionbox(), e.getHitbox(1))) {
                                    e.getUnit().setHP(s.getSpellPower());
                                    if (e.getUnit().getHP() <= 0) {
                                        kills++;
                                        deathAnim.add(new Object(BitmapFactory.decodeResource(context.getResources(), R.drawable.deathanimation), e.getX() + e.getWidth()/2 - 32, e.getY() + e.getHeight() - 64, 64, 64, 10, 100, 0));
                                        toRemoveEnemyFight.add(e);
                                        toRemoveEnemy.add(e);
                                        toRemoveEnemyWalk.add(e);
                                    }
                                }
                            }
                        }
                        Enemy.removeAll(toRemoveEnemy);
                        EnemyFight.removeAll(toRemoveEnemyFight);
                        EnemyWalk.removeAll(toRemoveEnemyWalk);
                        clearALists();
                        s.setStartTime(System.nanoTime());
                    }
                }

                else {
                    toRemovewaterSpell.add(s);
                }
            }
            waterSpell.removeAll(toRemovewaterSpell);
            toRemovewaterSpell.clear();

            for (Spell s: fireSpell) {
                s.update();
                int offsety = random.nextInt(3) * 5 * 2;
                if (s.getY() > (HEIGHT - 140 - offsety)) {
                    explosionSpell.add(new Spell(BitmapFactory.decodeResource(context.getResources(), R.drawable.fireblastground), s.getX(), s.getY(), 70, 70, 13, 1000/13, 2, s.getSpellPower()));
                    toRemovefireSpell.add(s);
                }
            }
            fireSpell.removeAll(toRemovefireSpell);
            toRemovefireSpell.clear();

            for (Spell s: explosionSpell) {
                if (!s.playedOnce()) {
                    s.update();
                    if (!s.getHitOnce()) {
                        for (Object e : Enemy) {
                            if (Rect.intersects(s.getCollisionbox(), e.getHitbox(1))) {
                                if (!e.getUnit().getName().matches("EnemyTower")) {
                                    e.getUnit().setHP(s.getSpellPower());
                                    if (e.getUnit().getHP() <= 0) {
                                        kills++;
                                        deathAnim.add(new Object(BitmapFactory.decodeResource(context.getResources(), R.drawable.deathanimation), e.getX() + e.getWidth() / 2 - 32, e.getY() + e.getHeight() - 64, 64, 64, 10, 100, 0));
                                        toRemoveEnemyFight.add(e);
                                        toRemoveEnemy.add(e);
                                        toRemoveEnemyWalk.add(e);
                                    }
                                }
                            }
                        }
                        Enemy.removeAll(toRemoveEnemy);
                        EnemyFight.removeAll(toRemoveEnemyFight);
                        EnemyWalk.removeAll(toRemoveEnemyWalk);
                        clearALists();
                        s.setHitOnce(true);
                    }
                }
                else {
                    toRemoveexplosionSpell.add(s);
                }

            }
            explosionSpell.removeAll(toRemoveexplosionSpell);
            toRemoveexplosionSpell.clear();

            for (Object o: AllyWalk) {
                o.update();
                if (Enemy.size() != 0) {
                    sortArray(Enemy, 0);
                    if (Rect.intersects(o.getRangebox(0), Enemy.get(0).getHitbox(1))) {
                        toRemoveAllyWalk.add(o);
                        AllyFight.add(o);
                        o.setStartTime(System.nanoTime());
                        o.changetoFight(3, (int) (333 / o.getUnit().getAttackSpeed())); //these lines will cause lag
                    }
                }
            }
            AllyWalk.removeAll(toRemoveAllyWalk);
            clearALists();

            for (Object f: Ally) {
                for (Projectile m: arrows) {
                    if (f.getX() > m.getX()) {
                        f.getUnit().setHP(m.getDMG());
                        toRemovearrows.add(m);
                        if (f.getUnit().getHP() <= 0) {
                            deathAnim.add(new Object(BitmapFactory.decodeResource(context.getResources(), R.drawable.deathanimation), f.getX() + f.getWidth()/2 - 32, f.getY() + f.getHeight() - 64, 64, 64, 10, 100, 0));
                            toRemoveAlly.add(f);
                            toRemoveAllyWalk.add(f);
                            toRemoveAllyFight.add(f);
                            break;
                        }
                    }
                }
                arrows.removeAll(toRemovearrows);
            }
            AllyFight.removeAll(toRemoveAllyFight);
            AllyWalk.removeAll(toRemoveAllyWalk);
            Ally.removeAll(toRemoveAlly);
            clearALists();

            for (Object f: AllyFight) {
                f.update();
                if (Enemy.isEmpty()) {
                    toRemoveAllyFight.add(f);
                    AllyWalk.add(f);
                    f.changetoWalk(4, 250);
                } else {
                    sortArray(Enemy, 0);
                    if (Rect.intersects(f.getRangebox(0), Enemy.get(0).getHitbox(1))) {
                        //enemy takes damage
                        allycurrentTime = System.nanoTime();
                        if (allycurrentTime - f.getStartTime() > 1000000000/f.getUnit().getAttackSpeed()) { //attack speed
                            int Allydmg = f.getUnit().getDMG();
                            if (f.getifRanged()) {
                                rocks.add(new Projectile(BitmapFactory.decodeResource(context.getResources(), R.drawable.rock), f.getX() + 40, f.getY() + 20, 10, 10, Allydmg));
                            }
                            else {
                                for (Object e: Enemy) { //Ally AOE
                                    if (Rect.intersects(f.getRangebox(0), e.getHitbox(1))) {
                                        e.getUnit().setHP(Allydmg);
                                        if (e.getUnit().getHP() <= 0) {
                                            deathAnim.add(new Object(BitmapFactory.decodeResource(context.getResources(), R.drawable.deathanimation), e.getX() + e.getWidth()/2 - 32, e.getY() + e.getHeight() - 64, 64, 64, 10, 100, 0));
                                            kills++;
                                            toRemoveEnemy.add(e);
                                            toRemoveEnemyFight.add(e);
                                            toRemoveEnemyWalk.add(e);
                                        }
                                    }
                                }
                            }
                            f.setStartTime(System.nanoTime());
                        }
                    }
                    else if (!Rect.intersects(f.getRangebox(0), Enemy.get(0).getHitbox(1))){
                        toRemoveAllyFight.add(f);
                        AllyWalk.add(f);
                        f.changetoWalk(4, 250);
                    }
                }
                Enemy.removeAll(toRemoveEnemy);
                EnemyFight.removeAll(toRemoveEnemyFight);
                EnemyWalk.removeAll(toRemoveEnemyWalk);
                toRemoveEnemy.clear();
                toRemoveEnemyWalk.clear();
                toRemoveEnemyFight.clear();
            }
            AllyFight.removeAll(toRemoveAllyFight);
            clearALists();

            for (Object e: EnemyWalk) {
                e.update();
                if (Ally.size() != 0) {
                    sortArray(Ally, 1);
                    if (Rect.intersects(e.getRangebox(1), Ally.get(0).getHitbox(0))) {
                        toRemoveEnemyWalk.add(e);
                        EnemyFight.add(e);
                        e.setStartTime(System.nanoTime());
                        e.changetoFight(3, (int)(333/e.getUnit().getAttackSpeed())); //these lines will cause lag
                    }
                }
            }
            EnemyWalk.removeAll(toRemoveEnemyWalk);
            clearALists();

            for (Object e: Enemy) {
                for (Projectile p: rocks) {
                    if ((e.getX() + e.getWidth()/2) < p.getX()) {
                        e.getUnit().setHP(p.getDMG());
                        toRemoverocks.add(p);
                        if (e.getUnit().getHP() <= 0) {
                            deathAnim.add(new Object(BitmapFactory.decodeResource(context.getResources(), R.drawable.deathanimation), e.getX() + e.getWidth()/2 - 32, e.getY() + e.getHeight() - 64, 64, 64, 10, 100, 0));
                            kills++;
                            toRemoveEnemy.add(e);
                            toRemoveEnemyFight.add(e);
                            toRemoveEnemyWalk.add(e);
                            break;
                        }
                    }
                }
                rocks.removeAll(toRemoverocks);
            }
            EnemyFight.removeAll(toRemoveEnemyFight);
            EnemyWalk.removeAll(toRemoveEnemyWalk);
            Enemy.removeAll(toRemoveEnemy);
            clearALists();

            for (Object e: EnemyFight) {
                e.update();
                if (Ally.isEmpty()) {
                    toRemoveEnemyFight.add(e);
                    EnemyWalk.add(e);
                    e.changetoWalk(4, 250);
                }
                else {
                    sortArray(Ally, 1);
                    if (Rect.intersects(e.getRangebox(1), Ally.get(0).getHitbox(0))) {
                        //ally takes damage
                        enemycurrentTime = System.nanoTime();
                        if (enemycurrentTime - e.getStartTime() > 1000000000/e.getUnit().getAttackSpeed()) { //attack speed
                            int Enemydmg = e.getUnit().getDMG();
                            if (e.getifRanged()) {
                                arrows.add(new Projectile(BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow), e.getX() + 40, e.getY() + 20, 10, 3, Enemydmg));
                            }
                            else {
                                for (Object a: Ally) { //enemy AOE
                                    if (Rect.intersects(e.getRangebox(1), a.getHitbox(0))) {
                                        a.getUnit().setHP(Enemydmg);
                                        if (a.getUnit().getHP() <= 0) {
                                            deathAnim.add(new Object(BitmapFactory.decodeResource(context.getResources(), R.drawable.deathanimation), a.getX() + a.getWidth()/2 - 32, a.getY() + a.getHeight() - 64, 64, 64, 10, 100, 0));
                                            toRemoveAlly.add(a);
                                            toRemoveAllyFight.add(a);
                                            toRemoveAllyWalk.add(a);
                                        }
                                    }
                                }
                            }
                            e.setStartTime(System.nanoTime());
                        }
                    }
                    else if (!Rect.intersects(e.getRangebox(1), Ally.get(0).getHitbox(0))) {
                        toRemoveEnemyFight.add(e);
                        EnemyWalk.add(e);
                        e.changetoWalk(4, 250);
                    }
                }
                AllyFight.removeAll(toRemoveAllyFight);
                AllyWalk.removeAll(toRemoveAllyWalk);
                Ally.removeAll(toRemoveAlly);
                toRemoveAllyFight.clear();
                toRemoveAlly.clear();
                toRemoveAllyWalk.clear();
            }
            EnemyFight.removeAll(toRemoveEnemyFight); // <---- this
            clearALists();

            pcostume.update();
            EnemyTower.update();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (initialize) {
            zone = level/10;
            stage = level - (zone * 10);
            String str = zone + " - " + stage;
            //height is  the height - half of the font size
            //width is the width - half of the font size times string - 3
            drawText(canvas, str, (int)(WIDTH*scaleFactorX / 2) - (int)(60*scaleFactorX)*(str.length()-3)/2, (int)(HEIGHT*scaleFactorY / 2) - ((int)(60*scaleFactorX)/2), (int)(60*scaleFactorX), Color.WHITE);
            drawText(canvas, "Tap to Begin", (int)(WIDTH*scaleFactorX / 2) - (int)(80*scaleFactorX), (int)(HEIGHT*scaleFactorY / 2) - ((int)(30*scaleFactorX)/2) + (int)(60*scaleFactorX), (int)(30*scaleFactorX), Color.WHITE);
        }
        else if (win) {
            if (!once) {
                exp = kills * 50 + manaSpent + (5 * level);
                gold = kills * 10 + manaSpent/2 + (5 * level);
                inventory.addGold(gold);
                inventory.saveGold();
                for (int x = 0; x < 4; x++) {
                    Equipped.get(x).addExp(exp/4);
                }
                inventory.save(Equipped);
                once = true;
                level++;
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("level", level);
                editor.commit();
            }
            int expGainRate = exp/60;
            int goldGainRate = gold/60;
            canvas.drawColor(Color.BLACK);
            if (expGain < exp-expGainRate) {
                if (expGainRate == 0) {
                    expGainRate = 1;
                }
                expGain += expGainRate;
            }
            else if (expGain < exp) {
                expGain++;
            }

            if (goldGain < gold-goldGainRate) {
                if (goldGainRate == 0) {
                    goldGainRate = 1;
                }
                goldGain += goldGainRate;
            } else if (goldGain < gold) {
                goldGain++;
            }
            drawText(canvas, "Cleared! " + zone + " - " + stage, (int) (WIDTH * scaleFactorX / 2) - (int) (30 * scaleFactorY) * 4, (int) (30 * scaleFactorX + 20), (int) (30 * scaleFactorX), Color.WHITE);
            drawText(canvas, "Exp Gain: " + expGain, (int) (WIDTH * scaleFactorX / 8), (int) (HEIGHT * scaleFactorY / 2), (int) (20 * scaleFactorX), Color.WHITE);
            drawText(canvas, "Gold Gain: " + goldGain, (int) (WIDTH * scaleFactorX / 8), (int) (HEIGHT * scaleFactorY / 2) + (int) (20 * scaleFactorX) + 20, (int) (20 * scaleFactorX), Color.WHITE);
        }
        else if (lose) {
            if (!once) {
                exp = kills * 50 + manaSpent;
                gold = kills * 10 + manaSpent/2;
                inventory.addGold(gold);
                inventory.saveGold();
                for (int x = 0; x < 4; x++) {
                    Equipped.get(x).addExp(exp/4);
                }
                inventory.save(Equipped);
                once = true;
            }
            int expGainRate = exp/60;
            int goldGainRate = gold/60;
            canvas.drawColor(Color.BLACK);
            if (expGain < exp-expGainRate) {
                if (expGainRate == 0) {
                    expGainRate = 1;
                }
                expGain += expGainRate;
            }
            else if (expGain < exp) {
                expGain++;
            }

            if (goldGain < gold-goldGainRate) {
                if (goldGainRate == 0) {
                    goldGainRate = 1;
                }
                goldGain += goldGainRate;
            } else if (goldGain < gold) {
                goldGain++;
            }
            drawText(canvas, "Game Over " + zone + " - " + stage, (int) (WIDTH * scaleFactorX / 2) - (int) (30 * scaleFactorY) * 4, (int) (30 * scaleFactorX + 20), (int) (30 * scaleFactorX), Color.WHITE);
            drawText(canvas, "Exp Gain: " + expGain, (int) (WIDTH * scaleFactorX / 8), (int) (HEIGHT * scaleFactorY / 2), (int) (20 * scaleFactorX), Color.WHITE);
            drawText(canvas, "Gold Gain: " + goldGain, (int) (WIDTH * scaleFactorX / 8), (int) (HEIGHT * scaleFactorY / 2) + (int) (20 * scaleFactorX) + 20, (int) (20 * scaleFactorX), Color.WHITE);
        }
        else if (canvas != null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.background), 0, 0, null);
            EnemyWalk = sortforDraw(EnemyWalk);
            EnemyFight = sortforDraw(EnemyFight);

            AllyWalk = sortforDraw(AllyWalk);
            AllyFight = sortforDraw(AllyFight);

            for (Projectile p: rocks) {
                p.draw(canvas);
            }
            for (Projectile a: arrows) {
                a.drawEnemy(canvas);
            }

            for (Object m: deathAnim) {
                if (!m.playedOnce()) {
                    m.draw(canvas);
                }
            }

            for (Spell s: fireSpell) {
                s.draw(canvas);
            }

            for (Spell s: explosionSpell) {
                if (!s.playedOnce()) {
                    s.draw(canvas);
                }
            }

            for (Spell s: waterSpell) {
                s.draw(canvas);
            }

            for (Spell s: earthSpell) {
                s.draw(canvas);
            }

            for (Object e: EnemyWalk) {
                e.getUnit().addMoveInterval();
                if (e.getUnit().getMoveSpeed() == e.getUnit().getMoveInterval()) {
                    e.drawEnemyMoving(canvas);
                    e.getUnit().setMoveInterval(0);
                }
                else {
                    e.draw(canvas);
                }
                if (e.getUnit().getHP() != e.getUnit().getOriginalhp()) {
                    drawText(canvas, e.getUnit().getHP() + " / " + e.getUnit().getOriginalhp(), e.getX() + 40, e.getY() + 90, 5, Color.BLACK);
                }
            }
            for (Object e: EnemyFight) {
                e.draw(canvas);
                drawText(canvas, e.getUnit().getHP() + " / " + e.getUnit().getOriginalhp(), e.getX() + 40, e.getY() + 90, 5, Color.BLACK);
            }
            for (Object a: AllyWalk) {
                a.getUnit().addMoveInterval();
                if (a.getUnit().getMoveSpeed() == a.getUnit().getMoveInterval()) {
                    a.drawMoving(canvas);
                    a.getUnit().setMoveInterval(0);
                }
                else {
                    a.draw(canvas);
                }
                if (a.getUnit().getHP() != a.getUnit().getOriginalhp()) {
                    if (a.getUnit().getName().matches("megagolem")) {
                        drawText(canvas, a.getUnit().getHP() + " / " + a.getUnit().getOriginalhp(), a.getX()-20, a.getY() + 140+20, 5, Color.BLACK);
                    }
                    else {
                        drawText(canvas, a.getUnit().getHP() + " / " + a.getUnit().getOriginalhp(), a.getX()-20, a.getY() + 70+20, 5, Color.BLACK);
                    }
                }
            }
            for (Object a: AllyFight) {
                a.draw(canvas);
                drawText(canvas, a.getUnit().getHP() + " / " + a.getUnit().getOriginalhp(), a.getX() - 20, a.getY() + 90, 5, Color.BLACK);
                if (a.getUnit().getName().matches("megagolem")) {
                    drawText(canvas, a.getUnit().getHP() + " / " + a.getUnit().getOriginalhp(), a.getX()-20, a.getY() + 140+20, 5, Color.BLACK);
                }
                else {
                    drawText(canvas, a.getUnit().getHP() + " / " + a.getUnit().getOriginalhp(), a.getX()-20, a.getY() + 70+20, 5, Color.BLACK);
                }
            }
            drawText(canvas, currentMana + " / " + player.getMana(), 2, 20, 15, Color.BLACK);
            for (int x = 0; x < Equipped.size(); x++) {
                int res = Equipped.get(x).getSmallImage();
                canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), res), (WIDTH/4) + (x * 50), HEIGHT - 50, null);
            }
            if (costume == R.drawable.bluemage) {
                canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.wavebutton), (WIDTH/4) + 200, HEIGHT - 50, null);
            }
            else if (costume == R.drawable.redmage) {
                canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.meteorbutton), (WIDTH/4) + 200, HEIGHT - 50, null);
            }
            else if (costume == R.drawable.greenmage) {
                canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.spikebutton), (WIDTH/4) + 200, HEIGHT - 50, null);
            }
            drawText(canvas, AllyTower.getUnit().getHP() + " / " + AllyTower.getUnit().getOriginalhp(), WIDTH/4, 30, 20, Color.BLACK);
            drawText(canvas, EnemyTower.getUnit().getHP() + " / " + EnemyTower.getUnit().getOriginalhp(), WIDTH*3/4, 30, 20, Color.BLACK);
            canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), tower), -40, HEIGHT - 150 - 70, null);
            EnemyTower.draw(canvas);
            pcostume.draw(canvas);
            canvas.restoreToCount(savedState);
        }
    }

    public ArrayList<Object> sortforDraw(ArrayList<Object> e) {
        for (int x = 0; x < e.size(); x++) {
            for (int y = 1; y < e.size(); y++) {
                if (e.get(y-1).getY() > e.get(y).getY()) {
                    Collections.swap(e, y-1, y);
                }
            }
        }
        return e;
    }

    @Override
    public boolean onTouchEvent (MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                float x = event.getX() / scaleFactorX;
                float y = event.getY() / scaleFactorY;
                if (!player.getPlaying() && !lose) {
                    startTime = System.nanoTime() / 1000;
                    player.setPlaying(true);
                    initialize = false;
                }
                else if (unit1.contains((int)x, (int)y)) {
                    spawnUnit(0);
                }
                else if (unit2.contains((int)x, (int)y)) {
                    spawnUnit(1);
                }
                else if (unit3.contains((int)x, (int)y)) {
                    spawnUnit(2);
                }
                else if (unit4.contains((int)x, (int)y)) {
                   spawnUnit(3);
                }
                else if (unit5.contains((int)x, (int)y)) {
                    if (costume == R.drawable.bluemage) {
                        castWater();
                    }
                    else if (costume == R.drawable.redmage) {
                        castFire();
                    }
                    else if (costume == R.drawable.greenmage) {
                        castEarth();
                    }
                }
            }
        }
        return true;
    }

    public void castFire() {
        if (currentMana > 5) {
            int spellPower = currentMana;
            currentMana = 0;
            for (int x = 0; x < spellPower/2; x++) {
                int spawnLoc = random.nextInt(10) * 25 + 100;
                fireSpell.add(new Spell(BitmapFactory.decodeResource(context.getResources(), R.drawable.fireblast), spawnLoc, -70, 70, 70, 1, 1000, 4, spellPower*8));
            }
        }
    }

    public void castWater() {
        if (currentMana > 20) {
            int spellPower = currentMana;
            currentMana = 0;
            int offsety = random.nextInt(3) * 10;
            waterSpell.add(new Spell(BitmapFactory.decodeResource(context.getResources(), R.drawable.wavemoving), 0, HEIGHT-130 - offsety, 70, 70, 2, 500, 1, spellPower/2));
        }
    }

    public void castEarth() {
        if (currentMana > 1) {
            int spellPower = currentMana;
            currentMana = 0;
            earthSpell.add(new Spell(BitmapFactory.decodeResource(context.getResources(), R.drawable.earthspike), 0, HEIGHT-150, 40, 60, 7, 1000/7, 3, spellPower));
            earthSpell.add(new Spell(BitmapFactory.decodeResource(context.getResources(), R.drawable.earthspike), -5, HEIGHT-140, 40, 60, 7, 1000/7, 3, spellPower));
            earthSpell.add(new Spell(BitmapFactory.decodeResource(context.getResources(), R.drawable.earthspike), -10, HEIGHT-130, 40, 60, 7, 1000/7, 3, spellPower));
        }
    }

    public void spawnUnit(int num) {
        int offsetx = random.nextInt(6); // 0 - 5
        int offsety = random.nextInt(4) * 5; // 0 - 4
        if (Equipped.get(num).getName().matches("peasant")) {
            unit = new Object(BitmapFactory.decodeResource(context.getResources(), R.drawable.peasantwalking), BitmapFactory.decodeResource(context.getResources(), R.drawable.peasantattacking), new Unit("peasant", Equipped.get(num).getLevel(), Equipped.get(num).getExp(), true),0 - offsetx, HEIGHT - 70 - 82 - offsety, 70, 70, 4, (1000/4), 1, false);
        }
        else if (Equipped.get(num).getName().matches("knight")) {
            unit = new Object(BitmapFactory.decodeResource(context.getResources(), R.drawable.knightwalking), BitmapFactory.decodeResource(context.getResources(), R.drawable.knightattacking), new Unit("knight", Equipped.get(num).getLevel(), Equipped.get(num).getExp(), true),0 - offsetx, HEIGHT - 70 - 82 - offsety, 70, 70, 4, (1000/4), 1, false);
        }
        else if (Equipped.get(num).getName().matches("slingshot")) {
            unit = new Object(BitmapFactory.decodeResource(context.getResources(), R.drawable.peasantslingwalking), BitmapFactory.decodeResource(context.getResources(), R.drawable.peasantslingattacking), new Unit("slingshot", Equipped.get(num).getLevel(), Equipped.get(num).getExp(), true),0 - offsetx, HEIGHT - 70 - 82 - offsety, 70, 70, 4, (1000/4), 1, true);
        }
        else if (Equipped.get(num).getName().matches("minigolem")) {
            unit = new Object(BitmapFactory.decodeResource(context.getResources(), R.drawable.minigolemwalk), BitmapFactory.decodeResource(context.getResources(), R.drawable.minigolemattack), new Unit("minigolem", Equipped.get(num).getLevel(), Equipped.get(num).getExp(), true),0 - offsetx, HEIGHT - 70 - 82 - offsety, 70, 70, 4, (1000/4), 1, false);
        }
        else if (Equipped.get(num).getName().matches("megagolem")) {
            unit = new Object(BitmapFactory.decodeResource(context.getResources(), R.drawable.megagolemwalk), BitmapFactory.decodeResource(context.getResources(), R.drawable.megagolemattack), new Unit("megagolem", Equipped.get(num).getLevel(), Equipped.get(num).getExp(), true),0 - offsetx, HEIGHT - 140 - 82 - offsety, 140, 140, 4, (1000/4), 1, false);
        }
        if (unit.getUnit().getManacost() <= currentMana) {
            Equipped.get(num).addSpawn();
            AllyWalk.add(unit);
            Ally.add(unit);
            currentMana -= unit.getUnit().getManacost();
            manaSpent -= unit.getUnit().getManacost();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("level", level);
        editor.commit();
        while (retry) {
            try{
                thread.setRunning(false);
                thread.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    public void clearALists() {
        toRemoveEnemyWalk.clear();
        toRemoveEnemyFight.clear();
        toRemoveEnemy.clear();
        toRemoveAllyFight.clear();
        toRemoveAllyWalk.clear();
        toRemoveAlly.clear();
        toRemoverocks.clear();
        toRemovearrows.clear();
    }

    public void drawText(Canvas canvas, String text, int x, int y, int size, int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(size);
        canvas.drawText(text, x, y, paint);
    }

    public ArrayList<Object> sortArray(ArrayList<Object> a, int type) {
        int[] distance = new int[a.size()];
        if (type == 0) { //ally
            for(int x = 0; x < a.size(); x++) {
                distance[x] = Math.abs(0 - a.get(x).getX());
            }
        }
        else if (type == 1) { //enemy
            for(int x = 0; x < a.size(); x++) {
                distance[x] = Math.abs(WIDTH - a.get(x).getX());
            }
        }

        for (int x = 0; x < distance.length; x++) {
            for (int y = 1; y < distance.length; y++) {
                if (distance[y-1] > distance[y]) {
                    int temp = distance[y-1];
                    distance[y-1] = distance[y];
                    distance[y] = temp;
                    Collections.swap(a, y-1, y);
                }
            }
        }
        return a;

    }

}
