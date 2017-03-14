package com.example.madmon.tictactoe;

import android.content.Context;

/**
 * Created by user on 22/01/2017.
 */

public class TTTButtonEndless extends TTTButton {
    public TTTButtonEndless(Context context) {
        super(context);

//        setBackgroundResource(R.drawable.inf);
    }

    @Override
    public void pressButton(String str, int color) {
        super.pressButton(str, color);

        setEnabled(true);
    }
}
