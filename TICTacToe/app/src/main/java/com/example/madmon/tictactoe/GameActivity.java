package com.example.madmon.tictactoe;

import android.content.Intent;
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

    protected boolean isGameOver;
    boolean isSomeonePlayingNow;

    PlayerTurn currentPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        isGameOver = false;
        isSomeonePlayingNow = false;
        currentPlayer = PlayerTurn.getFirst();

        gl = (GridLayout) findViewById(R.id.gridLayout);

        android.widget.AbsListView.LayoutParams params = getParamsForScreenSize();
        gl.setColumnCount(GameSettings.gameDim);

        TTTButton.setButtonsSizes(this, getButtonOptimalSize());
        //
        buttons = new TTTButton[GameSettings.gameDim][GameSettings.gameDim];

        for (int i = 0; i < GameSettings.gameDim; i++) {
            for (int j = 0; j < GameSettings.gameDim; j++) {
                if (Math.random() < (double) GameSettings.persistantTileAppearancePercentage / 100)
                    buttons[i][j] = new TTTButtonEndless(this , i , j);
                else
                    buttons[i][j] = new TTTButton(this , i , j);

                buttons[i][j].setOnClickListener(this);
                //                buttons[i][j].setWidthHeight(gl.getWidth()/gameDim , gl.getHeight()/gameDim);
                gl.addView(buttons[i][j], params);
            }
        }
    }

    @Override
    public void onClick(View view) {

        doTurn((TTTButton) view);

    }

    public void doTurn(TTTButton choice) {
        isSomeonePlayingNow = true;
        Log.i("doing turn" , "on button " + choice);

        setButtonState(choice);
        if(isGameEnd()) {
            finishGame();
        }

        currentPlayer = PlayerTurn.getNext(currentPlayer);   //player-turn indicator
        isSomeonePlayingNow = false;
}

    public void setButtonState(TTTButton b) {
        setButtonState(b , currentPlayer);
    }
    public void setButtonState(TTTButton b , PlayerTurn playerTurn) {

        b.pressButton(playerTurn);

    }

    public boolean isGameEnd() {
        Log.i("gameActivity" , "checking game end for turn " + currentPlayer.displayInButton);
        //check rows
        for (int i = 0; i < GameSettings.gameDim; i++) {
            TTTButton[] row = buttons[i];
            if (isArrayWinning(row)) {
                return true;
            }
        }

        //check columns
        for (int j = 0; j < GameSettings.gameDim; j++) {
            TTTButton[] col = new TTTButton[GameSettings.gameDim];

            for (int i = 0; i < GameSettings.gameDim; i++) {
                col[i] = buttons[i][j];
            }

            if (isArrayWinning(col)) {
                return true;
            }

        }

        //check diagonal 1
        TTTButton[] diag = new TTTButton[GameSettings.gameDim];
        for (int i = 0; i < GameSettings.gameDim; i++) {
            diag[i] = buttons[i][i];
        }
        if (isArrayWinning(diag)) {
            return true;
        }

        //check diagonal 2
        TTTButton[] diag2 = new TTTButton[GameSettings.gameDim];
        for (int i = 0; i < GameSettings.gameDim; i++) {
            diag2[i] = buttons[i][GameSettings.gameDim - i - 1];
        }
        if (isArrayWinning(diag2)) {
            return true;
        }

        return false;

    }

    public boolean isArrayWinning(TTTButton[] array) {
        PlayerTurn firstOwner = null;
        if (array[0].isMarked()) {
            firstOwner = array[0].getOwner();
        } else {
            return false;
        }

        for (int j = 0; j < array.length; j++) {
            if (!array[j].isMarked() || !(array[j].getOwner().equals(firstOwner))) {
                return false;
            }
        }

        return true;
    }

    public boolean isArrayClearForCurrentPlayer(TTTButton[] array , PlayerTurn playerTurn) {


        for(int j = 0; j < array.length; j++) {

            if(playerTurn != array[j].getOwner() && array[j].numOfClicks == 0) {
                return false;
            }
        }

        return true;

    }

    public void finishGame() {
        Log.i("gameActivity" , "finishing game");
        if (isGameOver) {
            return;
        } else {
            isGameOver = true;
        }

        //dump all buttons
        GridLayout gl = (GridLayout) findViewById(R.id.gridLayout);
        gl.removeAllViews();

        Toast.makeText(this, "Winner PlayerTurn " + currentPlayer.displayInButton + "!", Toast.LENGTH_LONG).show();

        Intent in = new Intent(this, EndGame.class);
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

