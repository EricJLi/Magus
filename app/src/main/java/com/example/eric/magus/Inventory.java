package com.example.eric.magus;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Created by Eric on 07/02/2016.
 */
public class Inventory {
    private Context context;
    private int gold;
    SharedPreferences prefs;
    private ArrayList<Unit> Roster = new ArrayList<Unit>();

    public Inventory (Context context) {
        this.context = context;
        prefs = context.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        gold = prefs.getInt("gold", 0);
        loadRoster();
    }


    public void save(ArrayList<Unit> party) {
        try {
            OutputStreamWriter out = new OutputStreamWriter(context.openFileOutput("roster.txt", 0));
            for (int x = 0; x < party.size(); x++) {
                out.write(party.get(x).getName() + " " + party.get(x).getLevel() + " " + party.get(x).getExp() + " " + party.get(x).getEquipped() + "\n");
            }
            out.close();
        }
        catch (Exception e) {
        }

    }

    public void loadRoster() {
        try {
            Roster.clear();
            InputStream in = context.openFileInput("roster.txt");
            if (in != null) {
                InputStreamReader tmp = new InputStreamReader(in);
                BufferedReader reader = new BufferedReader(tmp);
                String str;
                while ((str = reader.readLine()) != null) {
                    String[] values = str.split(" ");
                    String unitName = values[0];
                    int unitLevel = Integer.parseInt(values[1]);
                    int unitExp = Integer.parseInt(values[2]);
                    boolean unitEquipped = Boolean.parseBoolean(values[3]);
                    Roster.add(new Unit(unitName, unitLevel, unitExp, unitEquipped));
                }
                in.close();
            }
        }
        catch (Exception e) {
        }
    }

    public ArrayList<Unit> getRoster() {
        return Roster;
    }

    public void addUnit(Unit x) {
        Roster.add(x);
    }

    public void removeUnit(Unit x) {
        for (int i = 0; i < Roster.size(); i++) {
            if (Roster.get(i) == x) {
                Roster.remove(i);
                break;
            }
        }
    }

    public int getGold () {
        return gold;
    }

    public void setGold(int x) {
        gold = x;
    }

    public void addGold(int x) {
        gold += x;
    }

    public void spendGold (int x) {
        gold -= x;
    }

    public void saveGold() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("gold", gold);
        editor.commit();
    }
}
