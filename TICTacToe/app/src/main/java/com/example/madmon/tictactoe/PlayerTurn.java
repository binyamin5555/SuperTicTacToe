package com.example.madmon.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

import static com.example.madmon.tictactoe.GameSettings.SHARED_PREFS_PLAYERS_NUM_SELECTED;

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

    public static PlayerTurn getNext(PlayerTurn playerTurn , int numOfPlayers) {
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

            indexOfNextPlayer %= numOfPlayers;
//            indexOfNextPlayer %= values().length;
            return values()[indexOfNextPlayer];
        }

    }
}
