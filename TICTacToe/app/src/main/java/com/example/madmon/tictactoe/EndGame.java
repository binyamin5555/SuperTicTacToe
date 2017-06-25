package com.example.madmon.tictactoe;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class EndGame extends AppCompatActivity {
    public static String winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        Log.i("starting endgame" , "started");
//        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
//        for(StackTraceElement e : elements) {
//            String s = "\tat " + e.getClassName() + "." + e.getMethodName()
//                    + "(" + e.getFileName() + ":" + e.getLineNumber() + ")";
//            Log.i("stacktrace" , s);
//        }

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

//        TextView t = (TextView) findViewById(R.id.textView);
//        t.setText(winner);

//        ((Button)findViewById(R.id.button2)).setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(EndGame.this, GameActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    public void GoActivity(View v) {


        switch(v.getId()) {
//            case R.id.b_NewGame:
//                if (MainMenuActivity.GameNode)
//                startActivity(new Intent(this , GameActivity.class));
//                else startActivity(new Intent(this , SinglePlayerActivity.class));
//                break;
            case R.id.b_MainMenu:
                startActivity(new Intent(this , MainMenuActivity.class));
                break;
            case R.id.b_Exit:
                Log.i("navigating" , " goActivity - b_Exit");

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
//                                EndGame.this.finish();
                                Log.i("exiting" , "system exit");
//                                android.os.Process.killProcess(android.os.Process.myPid());
//                                System.exit(1);
//                                android.os.Process.killProcess(android.os.Process.myPid());

                                //TODO - terminate the app
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
                break;
            default:
                Log.e("switch problem" , "should not have reached deafualt");
        }

    }


    @Override
    public void onBackPressed() {
    }
}