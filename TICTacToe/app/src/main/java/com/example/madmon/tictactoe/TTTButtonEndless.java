package com.example.madmon.tictactoe;

import android.content.Context;

/**
 * Created by user on 22/01/2017.
 */

public class TTTButtonEndless extends TTTButton {
    public TTTButtonEndless(Context context , int rowIndex , int colIndex) {
        super(context , rowIndex , colIndex);

//        setBackgroundResource(R.drawable.inf);
    }

    @Override
    public void pressButton(String str, int color) {
        super.pressButton(str, color);

        numOfClicks = -1;

        setEnabled(true);
    }
}
