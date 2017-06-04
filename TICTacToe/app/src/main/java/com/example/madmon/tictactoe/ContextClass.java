package com.example.madmon.tictactoe;

import android.app.Application;
import android.content.Context;

/**
 * Created by madmon on 04/06/2017.
 */


public class ContextClass extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}

