package com.example.madmon.tictactoe;

import android.content.Context;
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

import static com.example.madmon.tictactoe.GameSettings.SHARED_PREFS_PLAYERS_NUM_SELECTED;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    MagicRandomalitySettings magicRandomalitySettings;
    Spinner numOfPlayersSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SeekBar sb = (SeekBar)findViewById(R.id.magicRandomalitySeek);
        TextView tv = (TextView) findViewById(R.id.randomalityDisplayText);
        magicRandomalitySettings = new MagicRandomalitySettings(sb , tv);
        numOfPlayersSpinner = (Spinner) findViewById(R.id.spinnerPlayers);

        List<String> list = new LinkedList<>();
        for(int i = 1 ; i < PlayerTurn.values().length ; i++) { //2 players and above
            list.add("" + (i+1) + " players");
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numOfPlayersSpinner.setAdapter(dataAdapter);
        numOfPlayersSpinner.setOnItemSelectedListener(this);



        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        if(sharedPref.contains(SHARED_PREFS_PLAYERS_NUM_SELECTED)) {
            int indexOfItem = sharedPref.getInt(SHARED_PREFS_PLAYERS_NUM_SELECTED , 0) - GameSettings.MIN_NUMBER_OF_PLAYERS;
            numOfPlayersSpinner.setSelection(indexOfItem);
        }

    }

    public void confirmSetting(View v){
        switch(v.getId()) {
            case R.id.bordSizeConfirm:
                EditText et = (EditText)findViewById(R.id.gameSizeEditText);
                String infoToUpdate  = et.getText().toString();
                int val = GameSettings.DEFAULT_FAME_DIM;    //if illegal value, put default

                if(infoToUpdate != null && infoToUpdate != "" && val != 0)
                    val = Integer.valueOf(infoToUpdate);

                GameSettings.gameDim = val;
                et.setText("");

                break;
            case R.id.persistanceConfirm:
                GameSettings.persistantTileAppearancePercentage = magicRandomalitySettings.getPersistance();
                break;


            default:
                Log.e("switch problem" , "should not have reached deafualt");
        }


        Toast.makeText(this , "updated information" , Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if(parent.equals(numOfPlayersSpinner)) {
            editor.putInt(GameSettings.SHARED_PREFS_PLAYERS_NUM_SELECTED , position + GameSettings.MIN_NUMBER_OF_PLAYERS);
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
