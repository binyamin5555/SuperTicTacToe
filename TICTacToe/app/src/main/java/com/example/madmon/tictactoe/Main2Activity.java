package com.example.madmon.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    public static int GAME_DIM = 3;

    TTTButton[][] buttons; //[num-of-row][num-of-col]

    boolean isXNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        isXNow = true;

        GridLayout gl = (GridLayout) findViewById(R.id.gridLayout);

        android.widget.AbsListView.LayoutParams params = getParamsForScreenSize();
        gl.setColumnCount(GAME_DIM);

        TTTButton.setButtonsSizes(this , getButtonOptimalSize());
//
        buttons = new TTTButton[GAME_DIM][GAME_DIM];

        for(int i = 0 ; i < GAME_DIM ; i++) {
            for(int j = 0 ; j < GAME_DIM ; j++) {
                if(Math.random() < 0.5)
                   buttons[i][j] = new TTTButton(this);
                else
                    buttons[i][j] = new TTTButtonEndless(this);

                buttons[i][j].setOnClickListener(this);
//                buttons[i][j].setWidthHeight(gl.getWidth()/GAME_DIM , gl.getHeight()/GAME_DIM);
                gl.addView(buttons[i][j] , params);
            }
        }
    }

    @Override
    public void onClick(View view) {

        setButtonState((TTTButton)view);
        checkGameEnd();

        isXNow = !isXNow;
    }

    public void setButtonState(TTTButton b) {

        if(isXNow) {
            b.pressButton("X" , Color.CYAN);
        }
        else {
            b.pressButton("O", Color.YELLOW);
        }

    }

    public void checkGameEnd() {
        //check rows
        for(int i = 0 ; i < GAME_DIM ; i++) {
            TTTButton[] row = buttons[i];
            if(isArrayWinning(row)) {
                finishGame();
            }


        }

        //check columns
        for(int j = 0 ; j < GAME_DIM ; j++) {
            TTTButton[] col = new TTTButton[GAME_DIM];

            for(int i = 0 ; i < GAME_DIM ; i++) {
                col[i] = buttons[i][j];
            }

            if(isArrayWinning(col)) {
                finishGame();
            }

        }

        //check diagonal 1
        TTTButton[] diag = new TTTButton[GAME_DIM];
        for(int i = 0 ; i < GAME_DIM ; i++) {
            diag[i] = buttons[i][i];
        }
        if(isArrayWinning(diag)) {
            finishGame();
        }

        //check diagonal 2
        TTTButton[] diag2 = new TTTButton[GAME_DIM];
        for(int i = 0 ; i < GAME_DIM ; i++) {
            diag2[i] = buttons[i][GAME_DIM-i-1];
        }
        if(isArrayWinning(diag2)) {
            finishGame();
        }

    }

    public boolean isArrayWinning(TTTButton[] array){
        String s = "";
        if(array[0].isMarked()) {
            s = array[0].getText().toString();
        }
        else {
            return false;
        }

        for(int j = 0 ; j < array.length ; j++) {
            if(array[j].isMarked()) {
                if(!(array[j].getText().toString().equals(s))) {
                    return false;
                }
            }
            else {
                return false;
            }
        }

        return true;
    }

    public void finishGame() {

        String s;
        if(isXNow)
            s = "X";
        else
            s = "O";

        //dump all buttons
        GridLayout gl = (GridLayout) findViewById(R.id.gridLayout);
        gl.removeAllViews();

        Toast.makeText(this, "Winner Player " + s + "!", Toast.LENGTH_LONG).show();

        Intent in = new Intent(this , EndGame.class);
        startActivity(in);
    }



    private android.widget.AbsListView.LayoutParams getParamsForScreenSize() {
        int minimal = getButtonOptimalSize();

        return new android.widget.AbsListView.LayoutParams(minimal, minimal);
    }

    private int getButtonOptimalSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_main2);

        //take into account the activity's layout padding
        int buttonMaxWidth = (size.x - rl.getPaddingLeft() - rl.getPaddingRight()) / GAME_DIM;
        int buttonMaxHeight = (size.y - rl.getPaddingTop() - rl.getPaddingBottom()) / GAME_DIM;

        return Math.min(buttonMaxWidth, buttonMaxHeight);
    }
}
