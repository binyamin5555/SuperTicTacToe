package com.example.madmon.tictactoe;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by user on 14/03/2017.
 */

public class GameSettings {

    //save names
    public static final String SHARED_PREFS_BOARD_SIZE = "SHARED_PREFS_BOARD_SIZE";
    public static final String SHARED_PREFS_PLAYERS_NUM_SELECTED = "SHARED_PREFS_PLAYERS_NUM_SELECTED";
    public static final String SHARED_PREFS_COMP_PLAYERS_NUM = "SHARED_PREFS_COMP_PLAYERS_NUM";
    public static final String SHARED_PREFS_MAGIC_BUTTON_CHANCE = "SHARED_PREFS_MAGIC_BUTTON_CHANCE";
    public static final String SHARED_PREFS_MAGIC_BUTTON_PRESS_LIMIT = "SHARED_PREFS_MAGIC_BUTTON_PRESS_LIMIT";



    //defaults
    public static final int DEFAULT_GAME_DIM = 3;
    public static final int DEFAULT_PERSISTANT_PERCENTAGE = 50;

    public static final int MIN_NUMBER_OF_PLAYERS = 2;

    public static final int MIN_BOARD_SIZE = 2;
    public static final int MAX_GAME_SIZE = 6;

    public static final int MIN_COMP_PLAYERS = 0;

    public static final int MIN_MAGIC_BUTTON_CHANCE = 0;
    public static final int MAX_MAGIC_BUTTON_CHANCE = 100;
    public static final int INTERVAL_MAGIC_BUTTON_CHANCE = 10;


    public static final int MIN_MAGIC_BUTTON_PRESS_LIMIT = 1;
    public static final int MAX_MAGIC_BUTTON_PRESS_LIMIT = 10;


    public static SharedPreferences getSharedPreferences(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
    }

    public static int getBoardSize(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getInt(GameSettings.SHARED_PREFS_BOARD_SIZE , GameSettings.MIN_BOARD_SIZE);
    }
    public static int getNumOfPlayer(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getInt(GameSettings.SHARED_PREFS_PLAYERS_NUM_SELECTED , GameSettings.DEFAULT_GAME_DIM);
    }
    public static int getNumOfCompPlayers(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getInt(GameSettings.SHARED_PREFS_COMP_PLAYERS_NUM , GameSettings.MIN_COMP_PLAYERS);
    }
    public static int getChanceForMagic(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getInt(GameSettings.SHARED_PREFS_MAGIC_BUTTON_CHANCE , GameSettings.MIN_MAGIC_BUTTON_CHANCE);
    }
    public static int getMagicPressLimit(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getInt(GameSettings.SHARED_PREFS_MAGIC_BUTTON_PRESS_LIMIT , GameSettings.MIN_MAGIC_BUTTON_PRESS_LIMIT);
    }


}
