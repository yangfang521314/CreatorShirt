package com.example.yf.creatorshirt.mvp.ui.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.common.cache.FontCache;
import com.example.yf.creatorshirt.mvp.listener.CommonListener;
import com.example.yf.creatorshirt.mvp.model.detaildesign.TextEntity;
import com.example.yf.creatorshirt.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yangfang on 2018/1/28.
 */

public class TextItemView extends RelativeLayout {
    @BindView(R.id.rl)
    public RelativeLayout mCommonStyle;
    @BindView(R.id.color_linearLayout)
    public LinearLayout mLinearTextColor;
    @BindView(R.id.tv_style_size)
    public LinearLayout mLinearTextStyle;
    @BindView(R.id.tv_max)
    TextView mScaleMax;
    @BindView(R.id.tv_min)
    TextView mScaleMin;
    private CommonListener.TextSizeClickListener sizeClickListener;
    private int textColor;
    private Typeface typeface;
    private int sizeText = 36;
    private int[] colors = {R.color.red, R.color.black,
            R.color.white, R.color.blue, R.color.green, R.color.orange,
            R.color.purple, R.color.cyan, R.color.grey, R.color.yellow};
    private Context mContext;
    private List<TextEntity> fontName = new ArrayList<>();

    public TextItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_choice_text, this);
        ButterKnife.bind(this, view);
        DisplayUtil.calculateItemWidth(App.getInstance(), mCommonStyle);
        initData();
        initView();
    }

    private void initData() {
        List<Typeface> listType = new ArrayList<>();
        listType.add(FontCache.getTypeface("font_style_5.ttf", mContext));
        listType.add(FontCache.getTypeface("font_style_4.ttf", mContext));
        listType.add(FontCache.getTypeface("font_style_3.ttf", mContext));
        listType.add(FontCache.getTypeface("font_style_2.ttf", mContext));
        listType.add(FontCache.getTypeface("font_style_1.ttf", mContext));
        for (int i = 0; i < 5; i++) {
            TextEntity text = new TextEntity();
            text.setTypeface(listType.get(i));
            fontName.add(text);
        }
    }

    private void initView() {
        TextView textView;
        for (int i = 0; i < colors.length; i++) {
            textView = new TextView(App.getInstance());
            LayoutParams params = new LayoutParams(DisplayUtil.Dp2Px(mContext, 20), DisplayUtil.Dp2Px(mContext, 30));
            textView.setLayoutParams(params);
            textView.setBackgroundColor(App.getInstance().getResources().getColor(colors[i]));
            textView.setGravity(Gravity.CENTER);
            final int finalI = i;
            textView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    textColor = colors[finalI];
                    if (sizeClickListener != null) {
                        sizeClickListener.onClickListener(textColor, null, 0);
                    }
                }
            });
            mLinearTextColor.addView(textView);
        }
        for (int j = 0; j < fontName.size(); j++) {
            TextView textView1 = new TextView(mContext);
            LayoutParams params = new LayoutParams(DisplayUtil.Dp2Px(mContext, 50), DisplayUtil.Dp2Px(mContext, 30));
            params.rightMargin = DisplayUtil.Dp2Px(mContext, 1);
            textView1.setLayoutParams(params);
            textView1.setBackgroundResource(R.drawable.choice_size_background);
            textView1.setGravity(Gravity.CENTER);
            textView1.setTextSize(16);
            textView1.setTypeface(fontName.get(j).getTypeface());
            textView1.setText("字体");
            final int finalJ = j;
            textView1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    typeface = fontName.get(finalJ).getTypeface();
                    if (sizeClickListener != null && typeface != null) {
                        sizeClickListener.onClickListener(0, typeface, 0);
                    }
                }
            });
            mLinearTextStyle.addView(textView1);
        }
        mScaleMax.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sizeClickListener != null) {
                    sizeText++;
                    if (sizeText > 70) {
                        sizeText = 70;
                    }
                    sizeClickListener.onClickListener(0, null, sizeText);
                }
            }
        });
        mScaleMin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sizeClickListener != null) {
                    sizeText--;
                    if (sizeText <= 12) {
                        sizeText = 12;
                    }
                    sizeClickListener.onClickListener(0, null, sizeText);
                }
            }
        });
    }

    public void setSizeClickListener(CommonListener.TextSizeClickListener mSizeClickListener) {
        sizeClickListener = mSizeClickListener;
    }
}
