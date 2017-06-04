package com.example.madmon.tictactoe;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

/**
 * Created by madmon on 28/05/2017.
 */

public enum PlayerTurn {
    EX ("X" , Color.CYAN),
    CIRCLE ("O" , Color.YELLOW),
    TRIANGLE ("Δ" , Color.RED),
    SQUARE ("□" , Color.GREEN);

    public final String displayInButton;
    public final Drawable bgDrawable;

    PlayerTurn(String displayInButton , int bgColor) {
        this.displayInButton = displayInButton;

//        Drawable color = new TTTButton(null , -1 , -1).getBackground();   //TODO - fix!!! Don't place null
//        color.setColorFilter( bgColor, PorterDuff.Mode.MULTIPLY);
//        bgDrawable = color;
        bgDrawable = null;

    }

    public static PlayerTurn getFirst() {
        return values()[0];
    }

    public static PlayerTurn getNext(PlayerTurn playerTurn) {
        if(playerTurn == null) {
            return getFirst();
        }
        else {
            int indexOfNextPlayer = 0;
            for(int i = 0 ; i < values().length ; i++) {
                if(values()[i] == playerTurn) {
                    indexOfNextPlayer = i+1;
                    break;
                }
            }
            indexOfNextPlayer %= values().length;   //TODO - limit to num of players
            return values()[indexOfNextPlayer];
        }

    }
}
