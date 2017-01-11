package com.example.eric.magus;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainMenu extends Activity {
    ImageView battle;
    ImageView evolve;
    ImageView upgrade;
    ImageView quest;
    Inventory inventory;
    SharedPreferences prefs;
    ArrayList<Unit> Roster = new ArrayList<Unit>();
    ArrayList<Unit> equipped = new ArrayList<Unit>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set to fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_mainmenu);
        prefs = this.getSharedPreferences("myPrefsKey", this.MODE_PRIVATE);
        boolean firstTime = prefs.getBoolean("firstTime", true);
        if (firstTime) {
            inventory = new Inventory(this);
            Roster.add(new Unit("peasant", 1, 0, true));
            Roster.add(new Unit("knight", 1, 0, true));
            Roster.add(new Unit("minigolem", 1, 0, true));
            Roster.add(new Unit("slingshot", 1, 0, true));
            inventory.save(Roster);
            inventory.setGold(0);
            inventory.saveGold();
            firstTime = false;
            SharedPreferences.Editor editor = getSharedPreferences("myPrefsKey", MODE_PRIVATE).edit();
            editor.putBoolean("firstTime", firstTime);
            editor.commit();
        }
        else if (!firstTime){
            inventory = new Inventory(this);
        }

        final Dialog dialog = new Dialog(MainMenu.this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Welcome!");
        dialog.setCancelable(false);

        TextView text = (TextView) dialog.findViewById(R.id.TextView01);
        text.setText("Choose your element!");

        Button button = (Button) dialog.findViewById(R.id.Button01);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("myPrefsKey", MODE_PRIVATE).edit();
                editor.putInt("costume", R.drawable.bluemage);
                editor.putInt("tower", R.drawable.bluetower);
                editor.commit();
                dialog.cancel();
            }
        });
        Button button2 = (Button) dialog.findViewById(R.id.Button02);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("myPrefsKey", MODE_PRIVATE).edit();
                editor.putInt("costume", R.drawable.redmage);
                editor.putInt("tower", R.drawable.redtower);
                editor.commit();
                dialog.cancel();
            }
        });
        Button button3 = (Button) dialog.findViewById(R.id.Button03);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("myPrefsKey", MODE_PRIVATE).edit();
                editor.putInt("costume", R.drawable.greenmage);
                editor.putInt("tower", R.drawable.greentower);
                editor.commit();
                dialog.cancel();
            }
        });
        dialog.show();



        battle = (ImageView)findViewById(R.id.imageView14);
        battle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equipped.clear();
                inventory.loadRoster();
                Roster = inventory.getRoster();
                for (int x = 0; x < Roster.size(); x++) {
                    if (Roster.get(x).getEquipped()) {
                        equipped.add(Roster.get(x));
                    }
                }
                if (equipped.size() == 4) {
                    startActivity(new Intent(MainMenu.this, Game.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Make sure you have sufficient units equipped!", Toast.LENGTH_LONG).show();
                }
            }
        });

        evolve = (ImageView)findViewById(R.id.imageView15);
        evolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, Evolve.class));
            }
        });

        upgrade = (ImageView)findViewById(R.id.imageView16);
        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, Upgrade.class));
            }
        });

        quest = (ImageView)findViewById(R.id.imageView17);
        quest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, Barracks.class));
            }
        });

    }



}
