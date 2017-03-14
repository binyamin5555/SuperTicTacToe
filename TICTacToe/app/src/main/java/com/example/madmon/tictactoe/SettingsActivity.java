package com.example.madmon.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    MagicRandomalitySettings magicRandomalitySettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SeekBar sb = (SeekBar)findViewById(R.id.magicRandomalitySeek);
        TextView tv = (TextView) findViewById(R.id.randomalityDisplayText);
        magicRandomalitySettings = new MagicRandomalitySettings(sb , tv);
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
