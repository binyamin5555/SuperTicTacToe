package com.example.madmon.tictactoe;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

/**
 * Created by user on 10/01/2017.
 */

public class TTTButton extends Button {
    public static int textSize = 50;


    private boolean isMarked;
    private static int size;

    public TTTButton(Context context) {
        super(context);

//        this.setMaxWidth(size);
//        this.setMaxHeight(size);

//        this.setMinimumWidth(size);
//        this.setMinimumHeight(size);

        this.setTextSize(TypedValue.COMPLEX_UNIT_DIP , textSize);

        isMarked = false;
    }

    public void setWidthHeight(int w , int h) {
        setMinimumWidth(w);
        setMinimumHeight(h);
    }

    public void pressButton(String str , int color) {
        isMarked = true;

        this.setText(str);
        this.setBackgroundColor(color);

        this.setEnabled(false);
    }

    public boolean isMarked() {return isMarked;}

    public static void setButtonsSizes(Context ctx, int buttonOptimalSize) {

        size = buttonOptimalSize;

    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int smallest = Math.min(widthMeasureSpec , heightMeasureSpec);
        super.onMeasure(smallest , smallest); // This is the key that will make the height equivalent to its width
    }
}
