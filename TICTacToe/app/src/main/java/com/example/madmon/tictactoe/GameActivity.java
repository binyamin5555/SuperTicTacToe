package com.example.madmon.tictactoe;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sharedPreferences;
    protected int gameDim;
    protected int numOfPlayers;
    protected int numOfCompPlayers;
    protected int chanceOfMagicButton;
    protected int magicButtonPressLimit;
    protected boolean notWinner = false;

    protected TTTButton[][] buttons; //[num-of-row][num-of-col]
    GridLayout gl;
    LinearLayout playersIndicator;

    protected boolean isGameOver;
    boolean isSomeonePlayingNow;

    PlayerTurn currentPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gameDim = GameSettings.getBoardSize(this);
        numOfPlayers = GameSettings.getNumOfPlayer(this);
        numOfCompPlayers = GameSettings.getNumOfCompPlayers(this);
        chanceOfMagicButton = GameSettings.getChanceForMagic(this);
        magicButtonPressLimit = GameSettings.getMagicPressLimit(this);

        isGameOver = false;
        isSomeonePlayingNow = false;
        currentPlayer = PlayerTurn.getFirst();

        gl = (GridLayout) findViewById(R.id.gridLayout);

        android.widget.AbsListView.LayoutParams params = getParamsForScreenSize();
        gl.setColumnCount(gameDim);

        TTTButton.setButtonsSizes(this, getButtonOptimalSize());
        //
        buttons = new TTTButton[gameDim][gameDim];

        for (int i = 0; i < gameDim; i++) {
            for (int j = 0; j < gameDim; j++) {
                if (Math.random() < (double) chanceOfMagicButton / 100) {   //special button
                    int buttonPressesLimit = 1+ (int) (magicButtonPressLimit * Math.random());  //going to [1 , magicButtonPressLimit]
                    buttons[i][j] = new TTTButton(this, buttonPressesLimit, i, j);

                }
                else {  //regular button
                    buttons[i][j] = new TTTButton(this, i, j);
                }

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

    public void setPlayerIndicators(boolean[] isComputer) {
        playersIndicator = (LinearLayout) findViewById(R.id.playersIndicator);
        playersIndicator.removeAllViews();
        for(int i = 0 ; i < numOfPlayers ; i++) {
            playersIndicator.addView(new PlayerVisualView(this , PlayerTurn.values()[i] , isComputer[i]));
        }

        PlayerVisualView.setPlaying(currentPlayer); //set indicator on first
    }

    public void doTurn(TTTButton choice) {
        isSomeonePlayingNow = true;
        Log.i("doing turn" , "on button " + choice);

        setButtonState(choice);
        if(isGameEnd()) {
            finishGame();
        }

        currentPlayer = PlayerTurn.getNext(currentPlayer , numOfPlayers);   //player-turn indicator
        PlayerVisualView.setPlaying(currentPlayer);
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
        for (int i = 0; i < gameDim; i++) {
            TTTButton[] row = buttons[i];
            if (isArrayWinning(row)) {
                return true;
            }
        }

        //check columns
        for (int j = 0; j < gameDim; j++) {
            TTTButton[] col = new TTTButton[gameDim];

            for (int i = 0; i < gameDim; i++) {
                col[i] = buttons[i][j];
            }

            if (isArrayWinning(col)) {
                return true;
            }

        }

        //check diagonal 1
        TTTButton[] diag = new TTTButton[gameDim];
        for (int i = 0; i < gameDim; i++) {
            diag[i] = buttons[i][i];
        }
        if (isArrayWinning(diag)) {
            return true;
        }

        //check diagonal 2
        TTTButton[] diag2 = new TTTButton[gameDim];
        for (int i = 0; i < gameDim; i++) {
            diag2[i] = buttons[i][gameDim - i - 1];
        }
        if (isArrayWinning(diag2)) {
            return true;
        }

        //check is not winners
        TTTButton[] allButtons = new TTTButton[gameDim*gameDim];
        for (int i = 0; i < gameDim; i++)
            for (int j = 0; j < gameDim; j++) {
            if (!buttons[i][j].isMarked()) {
                return false;
            }
        }

        notWinner = true;
        return true;

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

        if (notWinner) {
            Intent in = new Intent(this, EndGame.class);
            startActivity(in);
        }
        else {
            Toast.makeText(this, "Winner PlayerTurn " + currentPlayer.displayInButton + "!", Toast.LENGTH_LONG).show();
            MusicPlayer.playRaw(this , R.raw.cheer);
            Intent in = new Intent(this, EndGame.class);
            startActivity(in);
        }
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
        int buttonMaxWidth = (size.x - rl.getPaddingLeft() - rl.getPaddingRight()) / gameDim;
        int buttonMaxHeight = (size.y - rl.getPaddingTop() - rl.getPaddingBottom()) / gameDim;

        return Math.min(buttonMaxWidth, buttonMaxHeight);
    }

}

