package com.example.madmon.tictactoe;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;


public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner boardSizeSpinner;
    Spinner numOfPlayersSpinner;
    Spinner computerPlayersSpinner;
    Spinner magicButtonChanceSpinner;
    Spinner maxPressLimitSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        boardSizeSpinner = (Spinner) findViewById(R.id.spinnerBoardSize);
        numOfPlayersSpinner = (Spinner) findViewById(R.id.spinnerAllPlayers);
        computerPlayersSpinner = (Spinner) findViewById(R.id.spinnerComputerPlayers);
        magicButtonChanceSpinner = (Spinner) findViewById(R.id.spinnerSpecialButtons);
        maxPressLimitSpinner = (Spinner) findViewById(R.id.spinnerPressLimit);

        setBoardSize();
        setNumOfPlayers();
        setComputerPlayers();
        setMagicButtonChance();
        setMaxPressLimit();



//        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences sharedPref = GameSettings.getSharedPreferences(this);

        if(sharedPref.contains(GameSettings.SHARED_PREFS_BOARD_SIZE)) {
            int indexOfItem = sharedPref.getInt(GameSettings.SHARED_PREFS_BOARD_SIZE , 0) - GameSettings.MIN_BOARD_SIZE;
            boardSizeSpinner.setSelection(indexOfItem);
        }
        if(sharedPref.contains(GameSettings.SHARED_PREFS_PLAYERS_NUM_SELECTED)) {
            int indexOfItem = sharedPref.getInt(GameSettings.SHARED_PREFS_PLAYERS_NUM_SELECTED , 0) - GameSettings.MIN_NUMBER_OF_PLAYERS;
            numOfPlayersSpinner.setSelection(indexOfItem);
        }
        if(sharedPref.contains(GameSettings.SHARED_PREFS_COMP_PLAYERS_NUM)) {
            int indexOfItem = sharedPref.getInt(GameSettings.SHARED_PREFS_COMP_PLAYERS_NUM , 0) - GameSettings.MIN_COMP_PLAYERS;
            computerPlayersSpinner.setSelection(indexOfItem);
        }
        if(sharedPref.contains(GameSettings.SHARED_PREFS_MAGIC_BUTTON_CHANCE)) {
            int indexOfItem = (sharedPref.getInt(GameSettings.SHARED_PREFS_MAGIC_BUTTON_CHANCE , 0) - GameSettings.MIN_MAGIC_BUTTON_CHANCE) / GameSettings.INTERVAL_MAGIC_BUTTON_CHANCE;
            magicButtonChanceSpinner.setSelection(indexOfItem);
        }
        if(sharedPref.contains(GameSettings.SHARED_PREFS_MAGIC_BUTTON_PRESS_LIMIT)) {
            int indexOfItem = sharedPref.getInt(GameSettings.SHARED_PREFS_MAGIC_BUTTON_PRESS_LIMIT , 0) - GameSettings.MIN_MAGIC_BUTTON_PRESS_LIMIT;
            maxPressLimitSpinner.setSelection(indexOfItem);
        }

    }

    private void setBoardSize() {
        List<String> list = new LinkedList<>();
        for(int i = GameSettings.MIN_BOARD_SIZE; i <= GameSettings.MAX_GAME_SIZE ; i++) {
            list.add("" + i + "x" + i + " board size");
        }
        placeListInSpinner(list , boardSizeSpinner);

    }
    private void setNumOfPlayers() {
        List<String> list = new LinkedList<>();
        for(int i = 1 ; i < PlayerTurn.values().length ; i++) { //2 players and above
            list.add("" + (i+1) + " players");
        }
        placeListInSpinner(list , numOfPlayersSpinner);
    }
    private void setComputerPlayers() {
        List<String> list = new LinkedList<>();
        int currentNumOfPlayers = GameSettings.getNumOfPlayer(this);
        for(int i = GameSettings.MIN_COMP_PLAYERS ; i <= currentNumOfPlayers ; i++) {
            list.add("" + i + " computer players");
        }
        placeListInSpinner(list , computerPlayersSpinner);

    }
    private void setMagicButtonChance() {
        List<String> list = new LinkedList<>();
        for(int i = GameSettings.MIN_MAGIC_BUTTON_CHANCE ; i <= GameSettings.MAX_MAGIC_BUTTON_CHANCE ; i += GameSettings.INTERVAL_MAGIC_BUTTON_CHANCE) {
            list.add("" + i + "% chance button special");
        }
        placeListInSpinner(list , magicButtonChanceSpinner);
    }
    private void setMaxPressLimit() {
        List<String> list = new LinkedList<>();
        for(int i = GameSettings.MIN_MAGIC_BUTTON_PRESS_LIMIT ; i <= GameSettings.MAX_MAGIC_BUTTON_PRESS_LIMIT ; i++) {
            list.add("" + i + " max regular button presses");
        }
        placeListInSpinner(list , maxPressLimitSpinner);
    }


    private void placeListInSpinner(List list , Spinner spinner) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences sharedPref = GameSettings.getSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();

        if(parent.equals(boardSizeSpinner)) {
            editor.putInt(GameSettings.SHARED_PREFS_BOARD_SIZE , position + GameSettings.MIN_BOARD_SIZE);
        }
        if(parent.equals(numOfPlayersSpinner)) {
            editor.putInt(GameSettings.SHARED_PREFS_PLAYERS_NUM_SELECTED , position + GameSettings.MIN_NUMBER_OF_PLAYERS);
        }
        if(parent.equals(computerPlayersSpinner)) {
            editor.putInt(GameSettings.SHARED_PREFS_COMP_PLAYERS_NUM , position + GameSettings.MIN_COMP_PLAYERS);
        }
        if(parent.equals(magicButtonChanceSpinner)) {
            editor.putInt(GameSettings.SHARED_PREFS_MAGIC_BUTTON_CHANCE , (position + GameSettings.MIN_MAGIC_BUTTON_CHANCE) * GameSettings.INTERVAL_MAGIC_BUTTON_CHANCE);
        }
        if(parent.equals(maxPressLimitSpinner)) {
            editor.putInt(GameSettings.SHARED_PREFS_MAGIC_BUTTON_PRESS_LIMIT , position + GameSettings.MIN_MAGIC_BUTTON_PRESS_LIMIT);
        }

        editor.commit();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




    public class MagicRandomalitySettings implements SeekBar.OnSeekBarChangeListener {

        private TextView progressShow;
        private int persistance;

        public MagicRandomalitySettings(SeekBar seekBar , TextView progressShow) {
            seekBar.setOnSeekBarChangeListener(this);
            this.progressShow = progressShow;

            persistance = GameSettings.DEFAULT_PERSISTANT_PERCENTAGE;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            final int maxSeekBarProgress = 100;

            persistance = i;

            progressShow.setText("" + i + "% chance for persistant tile");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }

        public int getPersistance(){return  persistance;}
    }

}
