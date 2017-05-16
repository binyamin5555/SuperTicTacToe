package com.example.madmon.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainMenuActivity extends AppCompatActivity {
 public static boolean GameNode=false;

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
                GameNode=true;
                startActivity(new Intent(this , GameActivity.class));
                break;
            default:
                Log.e("switch problem" , "should not have reached deafualt");
        }

    }


}
