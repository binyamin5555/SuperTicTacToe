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

    public int rowIndex , colIndex;

    public static int textSize = 50;
    public int numOfClicks;


    private boolean isMarked;
    private static int size;

    private Context givenContext;

    public TTTButton(Context context , int colIndex , int rowIndex) {
        super(context);
        this.givenContext = context;

        doCtorStuff(1 , rowIndex , colIndex);
    }

    public TTTButton(Context context , int numOfClicks , int rowIndex , int colIndex) {
        super(context);

        doCtorStuff(numOfClicks , rowIndex , colIndex);

    }

    //copy constructor
    public TTTButton(TTTButton b){
        super(b.givenContext);

        copyFromOther(b);
    }

    public void copyFromOther(TTTButton b) {
        this.givenContext = b.givenContext;
        doCtorStuff(b.numOfClicks , b.rowIndex , b.colIndex);
        this.setText(b.getText());
        this.setBackgroundColor(b.getDrawingCacheBackgroundColor());
    }

    private void doCtorStuff(int numOfClicks , int rowIndex , int colIndex) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.numOfClicks = numOfClicks;
        this.setTextSize(TypedValue.COMPLEX_UNIT_DIP , textSize);

        isMarked = false;
    }

    public void setWidthHeight(int w , int h) {
        setMinimumWidth(w);
        setMinimumHeight(h);
    }

    public void pressButton(String str , int color) {
        isMarked = true;

        numOfClicks--;

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
