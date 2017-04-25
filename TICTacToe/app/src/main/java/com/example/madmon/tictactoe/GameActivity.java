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

public class GameActivity extends AppCompatActivity implements View.OnClickListener {


    protected TTTButton[][] buttons; //[num-of-row][num-of-col]
    GridLayout gl;

    protected boolean isXNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        isXNow = true;

        gl = (GridLayout) findViewById(R.id.gridLayout);

        android.widget.AbsListView.LayoutParams params = getParamsForScreenSize();
        gl.setColumnCount(GameSettings.gameDim);

        TTTButton.setButtonsSizes(this , getButtonOptimalSize());
//
        buttons = new TTTButton[GameSettings.gameDim][GameSettings.gameDim];

        for(int i = 0; i < GameSettings.gameDim; i++) {
            for(int j = 0; j < GameSettings.gameDim; j++) {
                if(Math.random() < (double)GameSettings.persistantTileAppearancePercentage / 100)
                   buttons[i][j] = new TTTButtonEndless(this);
                else
                    buttons[i][j] = new TTTButton(this);

                buttons[i][j].setOnClickListener(this);
//                buttons[i][j].setWidthHeight(gl.getWidth()/gameDim , gl.getHeight()/gameDim);
                gl.addView(buttons[i][j] , params);
            }
        }
    }

    @Override
    public void onClick(View view) {

        doTurn((TTTButton)view);

    }
    public void doTurn(TTTButton choice) {
        setButtonState(choice);
        checkGameEnd();

        isXNow = !isXNow;   //player-turn indicator
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
        for(int i = 0; i < GameSettings.gameDim; i++) {
            TTTButton[] row = buttons[i];
            if(isArrayWinning(row)) {
                finishGame();
            }


        }

        //check columns
        for(int j = 0; j < GameSettings.gameDim; j++) {
            TTTButton[] col = new TTTButton[GameSettings.gameDim];

            for(int i = 0; i < GameSettings.gameDim; i++) {
                col[i] = buttons[i][j];
            }

            if(isArrayWinning(col)) {
                finishGame();
            }

        }

        //check diagonal 1
        TTTButton[] diag = new TTTButton[GameSettings.gameDim];
        for(int i = 0; i < GameSettings.gameDim; i++) {
            diag[i] = buttons[i][i];
        }
        if(isArrayWinning(diag)) {
            finishGame();
        }

        //check diagonal 2
        TTTButton[] diag2 = new TTTButton[GameSettings.gameDim];
        for(int i = 0; i < GameSettings.gameDim; i++) {
            diag2[i] = buttons[i][GameSettings.gameDim -i-1];
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
        int buttonMaxWidth = (size.x - rl.getPaddingLeft() - rl.getPaddingRight()) / GameSettings.gameDim;
        int buttonMaxHeight = (size.y - rl.getPaddingTop() - rl.getPaddingBottom()) / GameSettings.gameDim;

        return Math.min(buttonMaxWidth, buttonMaxHeight);
    }
}
