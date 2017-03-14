package com.example.madmon.tictactoe;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

/**
 * Created by user on 14/03/2017.
 */

public class SettingsOption extends FrameLayout {

    EditText inputField;
    Button buttonToConfirm;

    public SettingsOption(Context context) {
        super(context);
        ctorStuff(context);
    }

    public SettingsOption(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctorStuff(context);
    }

    public SettingsOption(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ctorStuff(context);
    }

//    public SettingsOption(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        ctorStuff();
//    }

    private void ctorStuff(Context context) {
        inputField = new EditText(context);
        buttonToConfirm = new Button(context);

        this.addView(inputField);
        this.addView(buttonToConfirm);
    }

}
