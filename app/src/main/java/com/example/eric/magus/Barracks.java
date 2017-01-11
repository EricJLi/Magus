package com.example.eric.magus;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Barracks extends Activity {

    private Inventory inventory;
    private ArrayList<Unit> Roster = new ArrayList<Unit>();
    private int slot;
    private int gold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set to fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_barracks);
        inventory = new Inventory(this);
        Roster = inventory.getRoster();
        gold = inventory.getGold();
        int counter = 0;
        TextView txtGold = (TextView) findViewById(R.id.textViewGold);
        txtGold.setText("Gold: " + gold);
        for (Unit x: Roster) {
            if (counter == 0) {
                ImageView image = (ImageView) findViewById(R.id.slot1);
                int res = x.getImage();
                image.setImageResource(res);
            }
            else if (counter == 1) {
                ImageView image = (ImageView) findViewById(R.id.slot2);
                int res = x.getImage();
                image.setImageResource(res);
            }
            else if (counter == 2) {
                ImageView image = (ImageView) findViewById(R.id.slot3);
                int res = x.getImage();
                image.setImageResource(res);
            }
            else if (counter == 3) {
                ImageView image = (ImageView) findViewById(R.id.slot4);
                int res = x.getImage();
                image.setImageResource(res);
            }
            else if (counter == 4) {
                ImageView image = (ImageView) findViewById(R.id.slot5);
                int res = x.getImage();
                image.setImageResource(res);
            }
            else if (counter == 5) {
                ImageView image = (ImageView) findViewById(R.id.slot6);
                int res = x.getImage();
                image.setImageResource(res);
            }
            else if (counter == 6) {
                ImageView image = (ImageView) findViewById(R.id.slot7);
                int res = x.getImage();
                image.setImageResource(res);
            }
            else if (counter == 7) {
                ImageView image = (ImageView) findViewById(R.id.slot8);
                int res = x.getImage();
                image.setImageResource(res);
            }
            else if (counter == 8) {
                ImageView image = (ImageView) findViewById(R.id.slot9);
                int res = x.getImage();
                image.setImageResource(res);
            }
            counter++;
        }
    }

    public void Clicked(View view) {
        reset();
        if (view.getId() == R.id.slot1) {
            slot = 0;
            ImageView imageContainer = (ImageView) findViewById(R.id.imageView);
            imageContainer.setImageResource(R.drawable.selectedwhitebox);
        }
        else if (view.getId() == R.id.slot2) {
            slot = 1;
            ImageView imageContainer = (ImageView) findViewById(R.id.imageView2);
            imageContainer.setImageResource(R.drawable.selectedwhitebox);
        }
        else if (view.getId() == R.id.slot3) {
            slot = 2;
            ImageView imageContainer = (ImageView) findViewById(R.id.imageView3);
            imageContainer.setImageResource(R.drawable.selectedwhitebox);
        }
        else if (view.getId() == R.id.slot4) {
            slot = 3;
            ImageView imageContainer = (ImageView) findViewById(R.id.imageView6);
            imageContainer.setImageResource(R.drawable.selectedwhitebox);
        }
        else if (view.getId() == R.id.slot5) {
            slot = 4;
            ImageView imageContainer = (ImageView) findViewById(R.id.imageView7);
            imageContainer.setImageResource(R.drawable.selectedwhitebox);
        }
        else if (view.getId() == R.id.slot6) {
            slot = 5;
            ImageView imageContainer = (ImageView) findViewById(R.id.imageView8);
            imageContainer.setImageResource(R.drawable.selectedwhitebox);
        }
        else if (view.getId() == R.id.slot7) {
            slot = 6;
            ImageView imageContainer = (ImageView) findViewById(R.id.imageView10);
            imageContainer.setImageResource(R.drawable.selectedwhitebox);
        }
        else if (view.getId() == R.id.slot8) {
            slot = 7;
            ImageView imageContainer = (ImageView) findViewById(R.id.imageView5);
            imageContainer.setImageResource(R.drawable.selectedwhitebox);
        }
        else if (view.getId() == R.id.slot9) {
            slot = 8;
            ImageView imageContainer = (ImageView) findViewById(R.id.imageView9);
            imageContainer.setImageResource(R.drawable.selectedwhitebox);
        }
        Intent baseIntent = new Intent(Barracks.this, BarrackUnit.class);
        baseIntent.putExtra("Unit", slot);
        startActivity(baseIntent);
    }

    public void reset() {
        ImageView image = (ImageView) findViewById(R.id.imageView);
        image.setImageResource(R.drawable.whitebox);
        ImageView image2 = (ImageView) findViewById(R.id.imageView2);
        image2.setImageResource(R.drawable.whitebox);
        ImageView image3 = (ImageView) findViewById(R.id.imageView3);
        image3.setImageResource(R.drawable.whitebox);
        ImageView image4 = (ImageView) findViewById(R.id.imageView6);
        image4.setImageResource(R.drawable.whitebox);
        ImageView image5 = (ImageView) findViewById(R.id.imageView7);
        image5.setImageResource(R.drawable.whitebox);
        ImageView image6 = (ImageView) findViewById(R.id.imageView8);
        image6.setImageResource(R.drawable.whitebox);
        ImageView image7 = (ImageView) findViewById(R.id.imageView10);
        image7.setImageResource(R.drawable.whitebox);
        ImageView image8 = (ImageView) findViewById(R.id.imageView5);
        image8.setImageResource(R.drawable.whitebox);
        ImageView image9 = (ImageView) findViewById(R.id.imageView9);
        image9.setImageResource(R.drawable.whitebox);
    }
}
