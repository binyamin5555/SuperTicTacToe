package com.example.madmon.tictactoe;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by user on 22/01/2017.
 */

public class TTTButtonEndless extends TTTButton {
    public TTTButtonEndless(Context context , int rowIndex , int colIndex) {
        super(context , rowIndex , colIndex);

//        setBackgroundResource(R.drawable.inf);
    }

    @Override
    public void pressButton(PlayerTurn playerTurn) {
        super.pressButton(playerTurn);

            numOfClicks = -1;

//        setEnabled(true);
    }
}
