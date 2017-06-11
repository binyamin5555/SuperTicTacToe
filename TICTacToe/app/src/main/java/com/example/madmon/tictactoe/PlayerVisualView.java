package com.example.madmon.tictactoe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import java.util.LinkedList;

/**
 * Created by madmon on 11/06/2017.
 */

public class PlayerVisualView extends LinearLayout {

    private static final int RESCALED_BITMAP_SIZE = 100;

    public static LinkedList<PlayerVisualView> allMadeViews = new LinkedList<>();
    public PlayerTurn ownTurn;
    public boolean isCompPlayer;

    public PlayerVisualView(Context context , PlayerTurn playerTurn , boolean isCompPlayer) {
        super(context);
        ctorStuff(playerTurn , isCompPlayer);
    }

    public PlayerVisualView(Context context, @Nullable AttributeSet attrs , PlayerTurn playerTurn , boolean isCompPlayer) {
        super(context, attrs);
        ctorStuff(playerTurn , isCompPlayer);
    }

    public PlayerVisualView(Context context, @Nullable AttributeSet attrs, int defStyleAttr , PlayerTurn playerTurn , boolean isCompPlayer) {
        super(context, attrs, defStyleAttr);
        ctorStuff(playerTurn , isCompPlayer);
    }

    private void ctorStuff(PlayerTurn playerTurn , boolean isCompPlayer) {
        this.ownTurn = playerTurn;

        this.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        this.setOrientation(LinearLayout.VERTICAL);

        //playing mark
        TTTButton tttb = new TTTButton(this.getContext() , -1 , -1);
        tttb.pressButton(playerTurn);
        tttb.setEnabled(false);
        this.addView(tttb);

        // human or computer
        ImageViewControlled iv = new ImageViewControlled(this.getContext());
        Drawable humanOrComp;
        if (isCompPlayer) {
            humanOrComp = getResources().getDrawable(R.drawable.comp_player);
        }
        else {
            humanOrComp = getResources().getDrawable(R.drawable.human_player);
        }

        humanOrComp = resize(humanOrComp , RESCALED_BITMAP_SIZE);

        iv.setImageDrawable(humanOrComp);
        this.addView(iv);



        allMadeViews.add(this);
    }
    private Drawable resize(Drawable image , int size) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, size, size, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }

    public static void setPlaying(PlayerTurn currentPlaying) {

        for(PlayerVisualView playerVisualView : allMadeViews) {
            playerVisualView.setBackgroundColor(Color.WHITE);
        }

        for(PlayerVisualView playerVisualView : allMadeViews) {
            if(playerVisualView.ownTurn == currentPlaying) {
                //mark currently playing
                playerVisualView.setBackgroundColor(Color.RED);
                return; //found it - we're done here!!!
            }
        }

//        //didn't find one
//        allMadeViews.get(0).setBackgroundColor(Color.RED);

    }


    protected class ImageViewControlled extends android.support.v7.widget.AppCompatImageView {

        public ImageViewControlled(Context context) {
            super(context);
        }

        @Override
        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            int smallest = Math.min(widthMeasureSpec , heightMeasureSpec);
            super.onMeasure(smallest , smallest); // This is the key that will make the height equivalent to its width
        }
    }
}
