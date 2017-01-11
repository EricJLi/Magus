package com.example.eric.magus;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BarrackUnit extends Activity {

    private int thisIndex;
    private Button btn;
    private Inventory inventory;
    private ArrayList<Unit> Roster = new ArrayList<Unit>();
    private ArrayList<Unit> Equipped = new ArrayList<Unit>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barrack_unit);
        Intent passedIntent = getIntent();
        inventory = new Inventory(this);
        Roster = inventory.getRoster();
        for (Unit o: Roster) {
            if (o.getEquipped()) {
                Equipped.add(o);
            }
        }

        thisIndex = passedIntent.getIntExtra("Unit", 0); //default index set to 0
        TextView hp = (TextView) findViewById(R.id.textView6);
        hp.setText(Roster.get(thisIndex).getOriginalhp() + "");
        TextView dmg = (TextView) findViewById(R.id.textView7);
        dmg.setText(Roster.get(thisIndex).getDMG() + "");
        TextView level = (TextView) findViewById(R.id.textView8);
        level.setText(Roster.get(thisIndex).getLevel() + "");
        TextView exp = (TextView) findViewById(R.id.textView9);
        exp.setText(Roster.get(thisIndex).getExptoNextLevel() + "");
        btn = (Button) findViewById(R.id.btnEquip);
        if (Roster.get(thisIndex).getEquipped()) {
            btn.setText("Unequip");
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Roster.get(thisIndex).getEquipped()) { //not equipped
                    if (Equipped.size() < 4) {
                        Roster.get(thisIndex).setEquipped(true);
                        inventory.save(Roster);
                        btn.setText("Unequip");
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "You already have four units equipped!", Toast.LENGTH_LONG).show();
                    }
                }
                else if (Roster.get(thisIndex).getEquipped()) { //equipped
                    Roster.get(thisIndex).setEquipped(false);
                    inventory.save(Roster);
                    btn.setText("Equip");
                }
            }
        });
        ImageView unitProfile = (ImageView) findViewById(R.id.imageViewunit);
        unitProfile.setImageResource(Roster.get(thisIndex).getImage());
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setGravity(Gravity.RIGHT);
        getWindow().setLayout((int) (width * 0.5), (int) (height * 0.6));


    }
}
