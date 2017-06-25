package com.example.madmon.tictactoe;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * Created by madmon on 25/06/2017.
 */

public class MusicPlayer {

    public static void playRaw(Context context , int rawId) {
        MediaPlayer mp = MediaPlayer.create(context, rawId);
        mp.start();
    }

}
