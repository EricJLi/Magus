package com.example.eric.magus;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Evolve extends Activity {

    private int clickCounter;
    private Inventory inventory;
    private ArrayList<Unit> Roster = new ArrayList<Unit>();
    private int[] clicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set to fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_evolve);

        clickCounter = 0;
        clicked = new int[2];
        inventory = new Inventory(this);
        Roster = inventory.getRoster();
        int counter = 0;
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

    public void doEvolve() {
        ImageView image = (ImageView) findViewById(R.id.evoslot1);
        ImageView image2 = (ImageView) findViewById(R.id.evoslot2);
        if (image != null && image2 != null) {

        }

    }

    public void Clicked (View view) {
        if (clickCounter < 2) {
            if (view.getId() == R.id.slot1 && clicked[0] != view.getId()) {
                if (!Roster.get(0).getEquipped()) {
                    //show clicked
                    ImageView image = (ImageView) findViewById(R.id.imageView);
                    image.setImageResource(R.drawable.selectedwhitebox);
                    TextView eimageresult = (TextView) findViewById(R.id.evoslot3);
                    int n = 0;
                    eimageresult.setText("Name: " + Roster.get(n).getName() + "\n" + "Level: " + Roster.get(n).getLevel() + "\n" + "HP: " + Roster.get(n).getHP() + "\n" + "Damage: " + Roster.get(n).getDMG() + "\n" + "Exp to Next Level: " + Roster.get(n).getExptoNextLevel() + "\n");
                    clicked[clickCounter] = R.id.slot1;
                    clickCounter++;
                    if (clickCounter == 1) {
                        ImageView eimage = (ImageView) findViewById(R.id.evoslot1);
                        eimage.setImageResource(Roster.get(0).getImage());
                    }
                    else if (clickCounter == 2) {
                        ImageView eimage = (ImageView) findViewById(R.id.evoslot2);
                        eimage.setImageResource(Roster.get(0).getImage());
                    }
                }
                else if (Roster.get(0).getEquipped()) {
                    clear();
                    TextView eimageresult = (TextView) findViewById(R.id.evoslot3);
                    int n = 0;
                    eimageresult.setText("Name: " + Roster.get(n).getName() + "\n" + "Level: " + Roster.get(n).getLevel() + "\n" + "HP: " + Roster.get(n).getHP() + "\n" + "Damage: " + Roster.get(n).getDMG() + "\n" + "Exp to Next Level: " + Roster.get(n).getExptoNextLevel() + "\n");
                }
            }
            else if (view.getId() == R.id.slot2 && clicked[0] != view.getId()) {
                if (!Roster.get(1).getEquipped()) {
                    //show clicked
                    ImageView image = (ImageView) findViewById(R.id.imageView2);
                    image.setImageResource(R.drawable.selectedwhitebox);
                    TextView eimageresult = (TextView) findViewById(R.id.evoslot3);
                    int n = 1;
                    eimageresult.setText("Name: " + Roster.get(n).getName() + "\n" + "Level: " + Roster.get(n).getLevel() + "\n" + "HP: " + Roster.get(n).getHP() + "\n" + "Damage: " + Roster.get(n).getDMG() + "\n" + "Exp to Next Level: " + Roster.get(n).getExptoNextLevel() + "\n");
                    clicked[clickCounter] = R.id.slot2;
                    clickCounter++;
                    if (clickCounter == 1) {
                        ImageView eimage = (ImageView) findViewById(R.id.evoslot1);
                        eimage.setImageResource(Roster.get(1).getImage());
                    }
                    else if (clickCounter == 2) {
                        ImageView eimage = (ImageView) findViewById(R.id.evoslot2);
                        eimage.setImageResource(Roster.get(1).getImage());
                    }
                }
                else if (Roster.get(1).getEquipped()) {
                    clear();
                    TextView eimageresult = (TextView) findViewById(R.id.evoslot3);
                    int n = 1;
                    eimageresult.setText("Name: " + Roster.get(n).getName() + "\n" + "Level: " + Roster.get(n).getLevel() + "\n" + "HP: " + Roster.get(n).getHP() + "\n" + "Damage: " + Roster.get(n).getDMG() + "\n" + "Exp to Next Level: " + Roster.get(n).getExptoNextLevel() + "\n");
                }
            }
            else if (view.getId() == R.id.slot3 && clicked[0] != view.getId()) {
                if (!Roster.get(2).getEquipped()) {
                    //show clicked
                    ImageView image = (ImageView) findViewById(R.id.imageView3);
                    image.setImageResource(R.drawable.selectedwhitebox);
                    TextView eimageresult = (TextView) findViewById(R.id.evoslot3);
                    int n = 2;
                    eimageresult.setText("Name: " + Roster.get(n).getName() + "\n" + "Level: " + Roster.get(n).getLevel() + "\n" + "HP: " + Roster.get(n).getHP() + "\n" + "Damage: " + Roster.get(n).getDMG() + "\n" + "Exp to Next Level: " + Roster.get(n).getExptoNextLevel() + "\n");
                    clicked[clickCounter] = R.id.slot3;
                    clickCounter++;
                    if (clickCounter == 1) {
                        ImageView eimage = (ImageView) findViewById(R.id.evoslot1);
                        eimage.setImageResource(Roster.get(2).getImage());
                    }
                    else if (clickCounter == 2) {
                        ImageView eimage = (ImageView) findViewById(R.id.evoslot2);
                        eimage.setImageResource(Roster.get(2).getImage());
                    }
                }
                else if (Roster.get(2).getEquipped()) {
                    clear();
                    TextView eimageresult = (TextView) findViewById(R.id.evoslot3);
                    int n = 2;
                    eimageresult.setText("Name: " + Roster.get(n).getName() + "\n" + "Level: " + Roster.get(n).getLevel() + "\n" + "HP: " + Roster.get(n).getHP() + "\n" + "Damage: " + Roster.get(n).getDMG() + "\n" + "Exp to Next Level: " + Roster.get(n).getExptoNextLevel() + "\n");
                }
            }
            else if (view.getId() == R.id.slot4 && clicked[0] != view.getId()) {
                if (!Roster.get(3).getEquipped()) {
                    //show clicked
                    ImageView image = (ImageView) findViewById(R.id.imageView6);
                    image.setImageResource(R.drawable.selectedwhitebox);
                    TextView eimageresult = (TextView) findViewById(R.id.evoslot3);
                    int n = 3;
                    eimageresult.setText("Name: " + Roster.get(n).getName() + "\n" + "Level: " + Roster.get(n).getLevel() + "\n" + "HP: " + Roster.get(n).getHP() + "\n" + "Damage: " + Roster.get(n).getDMG() + "\n" + "Exp to Next Level: " + Roster.get(n).getExptoNextLevel() + "\n");
                    clicked[clickCounter] = R.id.slot4;
                    clickCounter++;
                    if (clickCounter == 1) {
                        ImageView eimage = (ImageView) findViewById(R.id.evoslot1);
                        eimage.setImageResource(Roster.get(3).getImage());
                    }
                    else if (clickCounter == 2) {
                        ImageView eimage = (ImageView) findViewById(R.id.evoslot2);
                        eimage.setImageResource(Roster.get(3).getImage());
                    }
                }
                else if (Roster.get(3).getEquipped()) {
                    clear();
                    TextView eimageresult = (TextView) findViewById(R.id.evoslot3);
                    int n = 3;
                    eimageresult.setText("Name: " + Roster.get(n).getName() + "\n" + "Level: " + Roster.get(n).getLevel() + "\n" + "HP: " + Roster.get(n).getHP() + "\n" + "Damage: " + Roster.get(n).getDMG() + "\n" + "Exp to Next Level: " + Roster.get(n).getExptoNextLevel() + "\n");
                }
            }
            else if (view.getId() == R.id.slot5 && clicked[0] != view.getId()) {
                if (!Roster.get(4).getEquipped()) {
                    //show clicked
                    ImageView image = (ImageView) findViewById(R.id.imageView7);
                    image.setImageResource(R.drawable.selectedwhitebox);
                    TextView eimageresult = (TextView) findViewById(R.id.evoslot3);
                    int n = 4;
                    eimageresult.setText("Name: " + Roster.get(n).getName() + "\n" + "Level: " + Roster.get(n).getLevel() + "\n" + "HP: " + Roster.get(n).getHP() + "\n" + "Damage: " + Roster.get(n).getDMG() + "\n" + "Exp to Next Level: " + Roster.get(n).getExptoNextLevel() + "\n");
                    clicked[clickCounter] = R.id.slot5;
                    clickCounter++;
                    if (clickCounter == 1) {
                        ImageView eimage = (ImageView) findViewById(R.id.evoslot1);
                        eimage.setImageResource(Roster.get(4).getImage());
                    }
                    else if (clickCounter == 2) {
                        ImageView eimage = (ImageView) findViewById(R.id.evoslot2);
                        eimage.setImageResource(Roster.get(4).getImage());
                    }
                }
                else if (Roster.get(4).getEquipped()) {
                    clear();
                    TextView eimageresult = (TextView) findViewById(R.id.evoslot3);
                    int n = 4;
                    eimageresult.setText("Name: " + Roster.get(n).getName() + "\n" + "Level: " + Roster.get(n).getLevel() + "\n" + "HP: " + Roster.get(n).getHP() + "\n" + "Damage: " + Roster.get(n).getDMG() + "\n" + "Exp to Next Level: " + Roster.get(n).getExptoNextLevel() + "\n");
                }
            }
            else if (view.getId() == R.id.slot6 && clicked[0] != view.getId()) {
                if (!Roster.get(5).getEquipped()) {
                    //show clicked
                    ImageView image = (ImageView) findViewById(R.id.imageView8);
                    image.setImageResource(R.drawable.selectedwhitebox);
                    TextView eimageresult = (TextView) findViewById(R.id.evoslot3);
                    int n = 5;
                    eimageresult.setText("Name: " + Roster.get(n).getName() + "\n" + "Level: " + Roster.get(n).getLevel() + "\n" + "HP: " + Roster.get(n).getHP() + "\n" + "Damage: " + Roster.get(n).getDMG() + "\n" + "Exp to Next Level: " + Roster.get(n).getExptoNextLevel() + "\n");
                    clicked[clickCounter] = R.id.slot6;
                    clickCounter++;
                    if (clickCounter == 1) {
                        ImageView eimage = (ImageView) findViewById(R.id.evoslot1);
                        eimage.setImageResource(Roster.get(5).getImage());
                    }
                    else if (clickCounter == 2) {
                        ImageView eimage = (ImageView) findViewById(R.id.evoslot2);
                        eimage.setImageResource(Roster.get(5).getImage());
                    }
                }
                else if (Roster.get(5).getEquipped()) {
                    clear();
                    TextView eimageresult = (TextView) findViewById(R.id.evoslot3);
                    int n = 5;
                    eimageresult.setText("Name: " + Roster.get(n).getName() + "\n" + "Level: " + Roster.get(n).getLevel() + "\n" + "HP: " + Roster.get(n).getHP() + "\n" + "Damage: " + Roster.get(n).getDMG() + "\n" + "Exp to Next Level: " + Roster.get(n).getExptoNextLevel() + "\n");
                }
            }
            else if (view.getId() == R.id.slot7 && clicked[0] != view.getId()) {
                if (!Roster.get(6).getEquipped()) {
                    //show clicked
                    ImageView image = (ImageView) findViewById(R.id.imageView10);
                    image.setImageResource(R.drawable.selectedwhitebox);
                    TextView eimageresult = (TextView) findViewById(R.id.evoslot3);
                    int n = 6;
                    eimageresult.setText("Name: " + Roster.get(n).getName() + "\n" + "Level: " + Roster.get(n).getLevel() + "\n" + "HP: " + Roster.get(n).getHP() + "\n" + "Damage: " + Roster.get(n).getDMG() + "\n" + "Exp to Next Level: " + Roster.get(n).getExptoNextLevel() + "\n");
                    clicked[clickCounter] = R.id.slot7;
                    clickCounter++;
                    if (clickCounter == 1) {
                        ImageView eimage = (ImageView) findViewById(R.id.evoslot1);
                        eimage.setImageResource(Roster.get(6).getImage());
                    }
                    else if (clickCounter == 2) {
                        ImageView eimage = (ImageView) findViewById(R.id.evoslot2);
                        eimage.setImageResource(Roster.get(6).getImage());
                    }
                }
                else if (Roster.get(6).getEquipped()) {
                    clear();
                    TextView eimageresult = (TextView) findViewById(R.id.evoslot3);
                    int n = 6;
                    eimageresult.setText("Name: " + Roster.get(n).getName() + "\n" + "Level: " + Roster.get(n).getLevel() + "\n" + "HP: " + Roster.get(n).getHP() + "\n" + "Damage: " + Roster.get(n).getDMG() + "\n" + "Exp to Next Level: " + Roster.get(n).getExptoNextLevel() + "\n");
                }
            }
            else if (view.getId() == R.id.slot8 && clicked[0] != view.getId()) {
                if (!Roster.get(7).getEquipped()) {
                    //show clicked
                    ImageView image = (ImageView) findViewById(R.id.imageView5);
                    image.setImageResource(R.drawable.selectedwhitebox);
                    TextView eimageresult = (TextView) findViewById(R.id.evoslot3);
                    int n = 7;
                    eimageresult.setText("Name: " + Roster.get(n).getName() + "\n" + "Level: " + Roster.get(n).getLevel() + "\n" + "HP: " + Roster.get(n).getHP() + "\n" + "Damage: " + Roster.get(n).getDMG() + "\n" + "Exp to Next Level: " + Roster.get(n).getExptoNextLevel() + "\n");
                    clicked[clickCounter] = R.id.slot8;
                    clickCounter++;
                    if (clickCounter == 1) {
                        ImageView eimage = (ImageView) findViewById(R.id.evoslot1);
                        eimage.setImageResource(Roster.get(7).getImage());
                    }
                    else if (clickCounter == 2) {
                        ImageView eimage = (ImageView) findViewById(R.id.evoslot2);
                        eimage.setImageResource(Roster.get(7).getImage());
                    }
                }
                else if (Roster.get(7).getEquipped()) {
                    clear();
                    TextView eimageresult = (TextView) findViewById(R.id.evoslot3);
                    int n = 7;
                    eimageresult.setText("Name: " + Roster.get(n).getName() + "\n" + "Level: " + Roster.get(n).getLevel() + "\n" + "HP: " + Roster.get(n).getHP() + "\n" + "Damage: " + Roster.get(n).getDMG() + "\n" + "Exp to Next Level: " + Roster.get(n).getExptoNextLevel() + "\n");
                }
            }
            else if (view.getId() == R.id.slot9 && clicked[0] != view.getId()) {
                if (!Roster.get(8).getEquipped()) {
                    //show clicked
                    ImageView image = (ImageView) findViewById(R.id.imageView9);
                    image.setImageResource(R.drawable.selectedwhitebox);
                    TextView eimageresult = (TextView) findViewById(R.id.evoslot3);
                    int n = 8;
                    eimageresult.setText("Name: " + Roster.get(n).getName() + "\n" + "Level: " + Roster.get(n).getLevel() + "\n" + "HP: " + Roster.get(n).getHP() + "\n" + "Damage: " + Roster.get(n).getDMG() + "\n" + "Exp to Next Level: " + Roster.get(n).getExptoNextLevel() + "\n");
                    clicked[clickCounter] = R.id.slot9;
                    clickCounter++;
                    if (clickCounter == 1) {
                        ImageView eimage = (ImageView) findViewById(R.id.evoslot1);
                        eimage.setImageResource(Roster.get(8).getImage());
                    }
                    else if (clickCounter == 2) {
                        ImageView eimage = (ImageView) findViewById(R.id.evoslot2);
                        eimage.setImageResource(Roster.get(8).getImage());
                    }
                }
                else if (Roster.get(8).getEquipped()) {
                    clear();
                    TextView eimageresult = (TextView) findViewById(R.id.evoslot3);
                    int n = 8;
                    eimageresult.setText("Name: " + Roster.get(n).getName() + "\n" + "Level: " + Roster.get(n).getLevel() + "\n" + "HP: " + Roster.get(n).getHP() + "\n" + "Damage: " + Roster.get(n).getDMG() + "\n" + "Exp to Next Level: " + Roster.get(n).getExptoNextLevel() + "\n");
                }
            }
        }
        else {
            clear();
        }


    }

    public void clear() {
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
        ImageView eimage = (ImageView) findViewById(R.id.evoslot1);
        eimage.setImageResource(R.drawable.whitebox);
        ImageView eimage2 = (ImageView) findViewById(R.id.evoslot2);
        eimage2.setImageResource(R.drawable.whitebox);
        TextView charinfo = (TextView) findViewById(R.id.evoslot3);
        charinfo.setText("");
        clicked[0] = 0;
        clicked[1] = 0;
        clickCounter = 0;
    }
}
