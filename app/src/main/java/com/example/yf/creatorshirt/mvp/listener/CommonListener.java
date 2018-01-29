package com.example.yf.creatorshirt.mvp.listener;

import android.graphics.Typeface;

/**
 * Created by yang on 23/06/2017.
 */

public class CommonListener {

    public interface CommonClickListener{
        void onClickListener();
    }

    public interface TextSizeClickListener{
        void onClickListener(int color, Typeface typeface,int textSize);
    }


}
