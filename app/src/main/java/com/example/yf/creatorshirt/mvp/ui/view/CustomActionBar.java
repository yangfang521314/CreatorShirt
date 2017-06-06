package com.example.yf.creatorshirt.mvp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;

/**
 * Created by yang on 17/05/2017.
 */

public class CustomActionBar extends LinearLayout {

    private ImageButton leftButton;
    private TextView titleTextView;
    private ImageButton rightButton;
    private TextView rightTV;

    private Button openNewsButton;

    {
        Context context = getContext();
        View view = LayoutInflater.from(context).inflate(
                R.layout.custom_actionbar_layout, this);
        leftButton = (ImageButton) view.findViewById(R.id.leftBtn);
        titleTextView = (TextView) view.findViewById(R.id.titleTV);
        rightButton = (ImageButton) view.findViewById(R.id.rightBtn);
        rightTV = (TextView) view.findViewById(R.id.rightTV);
        openNewsButton = (Button) view.findViewById(R.id.openNewsBtn);
        setBackgroundColor(getResources().getColor(R.color.background_red));
    }

    public CustomActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomActionBar(Context context) {
        super(context);
    }

    public void setLeftViewImg(int resid) {
        leftButton.setVisibility(View.VISIBLE);
        leftButton.setImageDrawable(getResources().getDrawable(resid));
    }

    public void setTitleView(String title) {
        titleTextView.setVisibility(View.VISIBLE);
        titleTextView.setText(title);
    }

    public void setRightViewImg(int resid) {
        rightButton.setVisibility(View.VISIBLE);
        rightButton.setImageDrawable(getResources().getDrawable(resid));
    }

    public ImageButton getRightImgButton() {
        return rightButton;
    }

    public void setRightTV(String rightContext) {
        rightTV.setVisibility(View.VISIBLE);
        rightTV.setText(rightContext);
    }

    public void setOpenNews() {
        openNewsButton.setVisibility(View.GONE);//openNewsButton.setVisibility(View.VISIBLE);
    }

    public void setOnClickListener(final ActionBarInterface actionBarInterface) {
        leftButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                actionBarInterface.leftViewClick();
            }
        });
        rightButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                actionBarInterface.rightImgClick();
            }
        });
        rightTV.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                actionBarInterface.rightTVClick();
            }
        });
        openNewsButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                actionBarInterface.openNewsClick();

            }
        });
    }

    public interface ActionBarInterface {

        void leftViewClick();

        void rightImgClick();

        void rightTVClick();

        void openNewsClick();
    }

    public interface ActionBarLeftClick {
        void leftViewClick();
    }

    public void setOnClickListener(final ActionBarLeftClick actionBarLeftClick) {
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionBarLeftClick.leftViewClick();
            }
        });
    }
}