package com.example.eric.magus;

/**
 * Created by Eric on 09/02/2016.
 */
public class Player {
    private int mana;
    private int manaregen;
    private int numofupgrades;
    private boolean playing;
    public Player (int numofupgrades) {
        // for now the numofupgrades increases each stat
        this.numofupgrades = numofupgrades;
        mana = 200 + 30 * numofupgrades;
        manaregen = 2 + numofupgrades;
    }

    public void setPlaying (Boolean b) {
        playing = b;
    }

    public boolean getPlaying () {
        return playing;
    }

    public int getMana() {
        return mana;
    }

    public int getManaregen() {
        return manaregen;
    }



}
