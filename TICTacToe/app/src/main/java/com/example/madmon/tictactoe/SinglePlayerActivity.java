package com.example.madmon.tictactoe;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class SinglePlayerActivity extends GameActivity {

    private static final long lockTime = 2000;  //time until button is chosen

    boolean isComputerX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isComputerX = false;

        (new CheckPlayThread()).execute(this);
    }

    @Override
    public void doTurn(TTTButton choice) {
        super.doTurn(choice);

        //lock all buttons
        lockAllButtons();
    }

    public void cumputeTurnStart() {
        //check if computer turn

        Log.i("computer turn" , "checking if my turn");

        if (isXNow != isComputerX) {
            Log.i("computer turn" , "not my turn.......");
            return;
        }
        isSomeonePlayingNow = true;

        //choose an avilable TTTButton
        final TTTButton chosenButton = chooseButtonToPlay();


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                endComputeTurn(chosenButton);
            }
        }, lockTime);


    }

    public void endComputeTurn(TTTButton chosenButtonToLock) {
        doTurn(chosenButtonToLock);

        //unlock buttons
        unlockAllButtons();
    }

    public void lockAllButtons() {
        for (TTTButton[] buttonArr : buttons) {
            for (TTTButton b : buttonArr) {
                b.setEnabled(false);
            }
        }
    }

    public void unlockAllButtons() {
        for (TTTButton[] buttonArr : buttons) {
            for (TTTButton b : buttonArr) {
                if (isButtonPlayable(b)) { //TODO - check through a function if exhausted all clicks
                    b.setEnabled(true);
                }
            }

        }
    }

    public List<TTTButton> getAllPlayableButtons() {
        List<TTTButton> freeButtons = new LinkedList<>();
        for (TTTButton[] buttonArr : buttons) {
            for (TTTButton b : buttonArr) {
                if (isButtonPlayable(b)) {
                    freeButtons.add(b);
                }
            }
        }

        return freeButtons;
    }


    public boolean isButtonPlayable(TTTButton b) {
        if (!b.isMarked() || (b instanceof TTTButtonEndless))
            return true;
        else
            return false;
    }


    private class CheckPlayThread extends AsyncTask<GameActivity , GameActivity, String> {
        private static final long scanTime = 1000;  //time until button is chosen

        @Override
        protected String doInBackground(GameActivity... params) {
            while(!isGameOver) {
                try {
                    Thread.sleep(scanTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(params);
            }
            return null;
        }

        protected void onProgressUpdate(GameActivity... progress) {
            if(progress[0] == null) return;

//            GameActivity activityCalled = progress[0];
            if(!isSomeonePlayingNow)
                cumputeTurnStart();
//            activityCalled.pushButton(null);
        }

        protected void onPostExecute(String... strs) {

        }

    }

    /**
     * check for win, then check for not-loss, then check for good position
     * @return
     */
    private TTTButton chooseButtonToPlay() {
        List<TTTButton> freeButtons = getAllPlayableButtons();

        //check for win
        for(TTTButton b : freeButtons) {
            //save the button stats
            TTTButton tempHolder = new TTTButton(b);

            //change the button and simulate
            setButtonState(b);
            if(isGameEnd()) {
                b.copyFromOther(tempHolder);    //return to prev state
                return b;
            }

            //check if it's a good option

            //load the saved stats back into button
            b.copyFromOther(tempHolder);
        }


        //check for loss (win for opponent)
//        for(TTTButton b : freeButtons) {
//            //save the button stats
//            TTTButton tempHolder = new TTTButton(b);
//
//            //change the button and simulate
//            simulateButtonPress(b , !isXNow);
//            if(isGameEnd()) {
//                b.copyFromOther(tempHolder);    //return to prev state
//                return b;
//            }
//
//            //check if it's a good option
//
//            //load the saved stats back into button
//            b.copyFromOther(tempHolder);
//        }

        //find a generally-good button
        TTTButton bestSoFar = freeButtons.get(0);
        int bestAdvantageSoFar = 0;   //number of directions open for win - up to 4

        //TODO - place back once function is done
//        for(TTTButton b : freeButtons) {
//            int currentAdvantage = getButtonDirections(b);
//            if(currentAdvantage > bestAdvantageSoFar) {
//                bestSoFar = b;
//                bestAdvantageSoFar = currentAdvantage;
//            }
//        }

        return bestSoFar;
    }


    public void simulateButtonPress(TTTButton b , boolean forcedTurn) {
        setButtonState(b , forcedTurn);
    }


    public int getButtonDirections(TTTButton checkMyStatus) {

        //TODO - finish using this,
        //utilize "isArrayClearForCurrentPlayer"


        int advantageCounter = 0;
        //check if row is winnable
        int rowIndex = checkMyStatus.rowIndex;
        for (int i = 0; i < GameSettings.gameDim; i++) {
            TTTButton[] row = buttons[i];
            if (isArrayWinning(row)) {
                advantageCounter++;
            }


        }

        //check columns
        for (int j = 0; j < GameSettings.gameDim; j++) {
            TTTButton[] col = new TTTButton[GameSettings.gameDim];

            for (int i = 0; i < GameSettings.gameDim; i++) {
                col[i] = buttons[i][j];
            }

            if (isArrayWinning(col)) {
                advantageCounter++;
            }

        }

        //check diagonal 1
        TTTButton[] diag = new TTTButton[GameSettings.gameDim];
        for (int i = 0; i < GameSettings.gameDim; i++) {
            diag[i] = buttons[i][i];
        }
        if (isArrayWinning(diag)) {
            advantageCounter++;
        }

        //check diagonal 2
        TTTButton[] diag2 = new TTTButton[GameSettings.gameDim];
        for (int i = 0; i < GameSettings.gameDim; i++) {
            diag2[i] = buttons[i][GameSettings.gameDim - i - 1];
        }
        if (isArrayWinning(diag2)) {
            advantageCounter++;
        }

        return advantageCounter;
    }
}
