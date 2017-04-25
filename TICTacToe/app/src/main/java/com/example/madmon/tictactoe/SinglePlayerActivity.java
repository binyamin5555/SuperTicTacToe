package com.example.madmon.tictactoe;

import android.os.Handler;
import android.os.Bundle;

import java.util.LinkedList;
import java.util.List;

public class SinglePlayerActivity extends GameActivity {

    private static final long lockTime = 2000;  //time until button is chosen

    boolean isComputerX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isComputerX = false;

        cumputeTurnStart();
    }

    @Override
    public void doTurn(TTTButton choice) {
        super.doTurn(choice);

        cumputeTurnStart();
    }

    public void cumputeTurnStart() {
        //check if computer turn
        if (isXNow != isComputerX) {
            return;
        }

        //lock all buttons
        lockAllButtons();

        //choose an avilable TTTButton
        List<TTTButton> freeButtons = getAllPlayableButtons();
        final TTTButton chosenButton = freeButtons.get(
                (int)(freeButtons.size() * Math.random())
        );

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
                if(isButtonPlayable(b)) {
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

}
