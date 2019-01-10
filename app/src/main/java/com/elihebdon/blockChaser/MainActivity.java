package com.elihebdon.blockChaser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


   private static Button quit;
   private static Button hint;
   private static Button scramble;
   private static ArrayList<LightButton> buttons;
   private int clicks;
   private TextView clickView;


   private TableLayout buttonLayout;
   private LinearLayout bottomButtons;
   private TableRow row1;
   private TableRow row2;
   private TableRow row3;
   private TableRow row4;
   private TableRow row5;


    private static final int[] BUTTON_IDS = {
            R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9,
            R.id.button10,
            R.id.button11,
            R.id.button12,
            R.id.button13,
            R.id.button14,
            R.id.button15,
            R.id.button16,
            R.id.button17,
            R.id.button18,
            R.id.button19,
            R.id.button20,
            R.id.button21,
            R.id.button22,
            R.id.button23,
            R.id.button24,
            R.id.button25
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quit = (Button) findViewById(R.id.quitButton);
        scramble = (Button) findViewById(R.id.scrambleButton);
        hint = (Button) findViewById(R.id.hintButton);


        buttonLayout = (TableLayout) findViewById(R.id.buttonLayout);
        bottomButtons = (LinearLayout) findViewById(R.id.bottomLayout);
        row1 = (TableRow) findViewById(R.id.row1);
        row2 = (TableRow) findViewById(R.id.row2);
        row3 = (TableRow) findViewById(R.id.row3);
        row4 = (TableRow) findViewById(R.id.row4);
        row5 = (TableRow) findViewById(R.id.row5);
        clickView = (TextView) findViewById(R.id.clicks);


        buttons = new ArrayList<LightButton>();

        quit.setOnClickListener(this);
        scramble.setOnClickListener(this);
        hint.setOnClickListener(this);

        for (int id : BUTTON_IDS){
            LightButton button = (LightButton)findViewById(id);
            button.setOnClickListener(this);
            buttons.add(button);
        }

        scramble();


    }

    public void scramble(){
        Random rand = new Random();

        for (int i = 0; i < 20; i++){
            int randomButton = rand.nextInt(24);

            buttons.get(randomButton).toggleLight();

            if (randomButton < 20){
                buttons.get(randomButton +5).toggleLight();
            }
            if (randomButton > 4){
                buttons.get(randomButton -5).toggleLight();
            }
            if(!Arrays.asList(0,5,10,15, 20).contains(randomButton)){
                buttons.get(randomButton -1).toggleLight();
            }
            if(!Arrays.asList(4,9,14,19,24).contains(randomButton)){
                buttons.get(randomButton + 1).toggleLight();
            }

        }


    }

    @Override
    public void onClick(View v) {

         int id = v.getId();

         if (id == R.id.scrambleButton) {
             scramble();
             clicks = 0;
             clickView.setText("CLICKS: " + clicks);

         }

         else if (id == R.id.quitButton) {
             finish();
         }

         else if( id == R.id.hintButton){
             boolean bottomLights = true;
             for (int i = 0; i < 20; i++){
                 if(buttons.get(i).isOn() == false){
                     bottomLights = false;
                 }
             }
             if(bottomLights == true){
                 gethint();
             } else {
                 Toast.makeText(this.getApplicationContext(), "Lights must only be on in the bottom row in order to receive a hint", Toast.LENGTH_LONG).show();
             }


        }

        else {

             LightButton chosenButton = (LightButton) findViewById(v.getId());
             chosenButton.toggleLight();
             int index = buttons.indexOf(chosenButton);

             if (index < 20){
                  buttons.get(index+5).toggleLight();
             }
             if (index > 4){
                 buttons.get(index -5).toggleLight();
             }
             if(!Arrays.asList(0,5,10,15, 20).contains(index)){
                 buttons.get(index -1).toggleLight();
             }
             if (!Arrays.asList(4,9,14,19,24).contains(index)){
                 buttons.get(index + 1).toggleLight();
             }

             clicks++;
             clickView.setText("CLICKS: " + clicks);

             if(allDark()){
//                 Toast.makeText(getApplicationContext(), R.string.game_won, Toast.LENGTH_SHORT).show();
                 AlertDialog.Builder alert = new AlertDialog.Builder(this);
                 alert.setTitle("Congrats!");
                 alert.setCancelable(false);
                 alert.setMessage("You won with " + clicks + " clicks");
                 alert.setNegativeButton("Close Application", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         finish();
                     }
                 });
                 alert.setPositiveButton("Play again", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         scramble();
                         clicks = 0;
                         clickView.setText("CLICKS: " + clicks);
                     }
                 });
                 alert.show();

             }

         }

    }

    public boolean allDark() {
        for (int i = 0; i < 25; i++) {
            if (!buttons.get(i).isOn()){
                return false;
            }
        }
        return true;
    }

    public void gethint(){

        boolean first = true;
        boolean second = true;
        boolean third = true;
        boolean fourth = true;
        boolean fifth = true;
        String message = "";
        if(buttons.get(20).isOn() == false){
            first = false;
        }
        if(buttons.get(21).isOn() == false){
            second = false;
        }
        if(buttons.get(22).isOn() == false){
            third = false;
        }
        if(buttons.get(23).isOn() == false){
            fourth = false;
        }
        if(buttons.get(24).isOn() == false){
            fifth = false;
        }

        if ((first && second) && (third == false && fourth == false && fifth == false)) {
            message = "Top Row Clicks: ...+.";
        }
        if ((first && third && fifth) && (second == false && fourth == false)) {
            message = "Top Row Clicks: .+..+";
        }
        if ((first && fourth) && (second == false && third == false && fifth == false)) {
            message = "Top Row Clicks: +....";
        }
        if ((second && third && fourth) && (first == false && fifth == false)) {
            message = "Top Row Clicks: ...++";
        }
        if ((second && fifth) && (first == false && third == false && fourth == false)) {
            message = "Top Row Clicks: ....+";
        }
        if ((third) && (first == false && second == false && fourth == false && fifth == false)) {
            message = "Top Row Clicks: ..+..";
        }
        if ((fourth && fifth) && (first == false && second == false && third == false)) {
            message = "Top Row Clicks: .+...";
        }

        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_LONG).show();

    }
}
