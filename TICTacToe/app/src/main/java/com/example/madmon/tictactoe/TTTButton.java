package com.example.madmon.tictactoe;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

/**
 * Created by user on 10/01/2017.
 */

public class TTTButton extends android.support.v7.widget.AppCompatButton {

    public int rowIndex , colIndex;

    public static int textSize = 40;
    public int numOfClicks;
    protected PlayerTurn owner;


    private boolean isMarked;
    private static int size;

    private Context givenContext;

    public TTTButton(Context context , int colIndex , int rowIndex) {
        super(context);
        this.givenContext = context;

        doCtorStuff(1 , rowIndex , colIndex);
    }
    public TTTButton(Context context , int numOfClicks , int colIndex , int rowIndex) {
        super(context);
        this.givenContext = context;

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
        this.owner = b.owner;
        this.setEnabled(b.isEnabled());
        this.isMarked = b.isMarked;

        Drawable buttonBackground = b.getBackground();
        this.setBackground(buttonBackground);
    }

    private void doCtorStuff(int numOfClicks , int rowIndex , int colIndex) {
        owner = null;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.numOfClicks = numOfClicks;
        this.setTextSize(TypedValue.COMPLEX_UNIT_DIP , textSize);
        this.setBackgroundResource(android.R.drawable.btn_default);

        isMarked = false;

    }

    public void setWidthHeight(int w , int h) {
        setMinimumWidth(w);
        setMinimumHeight(h);
    }

    public void pressButton(PlayerTurn playerTurn) {

        Log.i("button" , "pressed button " + this);
        isMarked = true;

        numOfClicks--;

        this.setText(playerTurn.displayInButton);
        this.owner = playerTurn;
        this.setBackground(playerTurn.getDefaultDrawable(getContext()));

        if(numOfClicks == 0)
           this.setEnabled(false);
    }

    public boolean isMarked() {return isMarked;}

    public static void setButtonsSizes(Context ctx, int buttonOptimalSize) {

        size = buttonOptimalSize;

    }

    public PlayerTurn getOwner() {
        return owner;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int smallest = Math.min(widthMeasureSpec , heightMeasureSpec);
        super.onMeasure(smallest , smallest); // This is the key that will make the height equivalent to its width
    }

    @Override
    public String toString() {
        return "TTTButton{" +
                "rowIndex=" + rowIndex +
                ", colIndex=" + colIndex +
                ", numOfClicks=" + numOfClicks +
                ", owner='" + owner + '\'' +
                ", isMarked=" + isMarked +
                ", givenContext=" + givenContext +
                '}';
    }
}
