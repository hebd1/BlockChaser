package com.elihebdon.blockChaser;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

public class LightButton extends android.support.v7.widget.AppCompatButton{
    private int row;
    private int col;
    private boolean isOn;


    public LightButton(Context context) {
        super(context);
    }


    public LightButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.row = row;
        this.col = col;
        this.isOn = isOn;

    }

    public void toggleLight(){
        if(isOn == false){
            this.setBackgroundColor(Color.BLACK);
            isOn = true;

        } else {
            this.setBackgroundColor(Color.WHITE);
            isOn = false;
        }
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isOn() {
        return isOn;
    }
}
