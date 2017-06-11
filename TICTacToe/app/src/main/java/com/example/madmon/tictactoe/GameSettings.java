package com.example.madmon.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by user on 14/03/2017.
 */

public class GameSettings {

    public static final String SHARED_PREFS_PLAYERS_NUM_SELECTED = "SHARED_PREFS_PLAYERS_NUM_SELECTED";



    public static final int DEFAULT_FAME_DIM = 3;
    public static final int DEFAULT_PERSISTANT_PERCENTAGE = 50;
    public static final int MIN_NUMBER_OF_PLAYERS = 2;

    public static int gameDim = DEFAULT_FAME_DIM;
    public static int persistantTileAppearancePercentage = DEFAULT_PERSISTANT_PERCENTAGE;

    public static SharedPreferences getSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }
}
