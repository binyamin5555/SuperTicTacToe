package com.example.madmon.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }


    public void moveToAnotherActivity(View v) {
        switch(v.getId()) {
            case R.id.settingsB:
                startActivity(new Intent(this , SettingsActivity.class));
                break;
            case R.id.startSingle:
                startActivity(new Intent(this , SinglePlayerActivity.class));
                break;
            case R.id.startMulti:
                startActivity(new Intent(this , GameActivity.class));
                break;
            default:
                Log.e("switch problem" , "should not have reached deafualt");
        }

    }
}
