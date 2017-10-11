package com.example.yf.creatorshirt.widget.stickerview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.detaildesign.TextEntity;
import com.example.yf.creatorshirt.utils.DisplayUtil;
import com.example.yf.creatorshirt.utils.FontCache;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.example.yf.creatorshirt.utils.FontCache.getTypeface;

/**
 * Created by yang on 22/06/2017.
 */

public class SignatureDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private EditText mEditSignature;
    private ImageView choiceBack;
    private ImageView choiceDone;
    private CompleteCallBack completeCallBack;
    private String str;
    private List<TextEntity> fontName = new ArrayList<>();
    private Typeface typeface;
    private Typeface typeUpdate;
    private LinearLayout mLinearLayout;

    public SignatureDialog(@NonNull Context context, Typeface typeFace) {
        super(context, R.style.shareDialog_style);
        mContext = context;
        this.typeface = typeFace;
        initView();
    }

    public SignatureDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.shareDialog_style);
        mContext = context;
        initView();
    }


    private void initView() {
        setContentView(R.layout.view_input_dialog);
        initData();
        mEditSignature = (EditText) findViewById(R.id.edit_signature);
        choiceBack = (ImageView) findViewById(R.id.choice_back);
        choiceDone = (ImageView) findViewById(R.id.choice_done);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_choice_font);
        choiceBack.setOnClickListener(this);
        choiceDone.setOnClickListener(this);

        if (typeface != null) {
            mEditSignature.setTypeface(typeface);
        }
        for (int i = 0; i < fontName.size(); i++) {
            TextView textView = new TextView(mContext);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(DisplayUtil.Dp2Px(mContext, 50), DisplayUtil.Dp2Px(mContext, 30));
            params.rightMargin = DisplayUtil.Dp2Px(mContext, 1);
            textView.setLayoutParams(params);
            textView.setBackgroundResource(R.drawable.choice_size_background);
            textView.setGravity(Gravity.CENTER);
            textView.setTypeface(fontName.get(i).getTypeface());
            textView.setText(fontName.get(i).getText());
            final int finalI = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    typeUpdate = fontName.get(finalI).getTypeface();
                    mEditSignature.setTypeface(typeUpdate);

                }
            });
            mLinearLayout.addView(textView);
        }
    }

    private void initData() {
        List<Typeface> listType = new ArrayList<>();
        listType.add(getTypeface("mellony_dry_brush.ttf", mContext));
        listType.add(FontCache.getTypeface("Mala's_Handwriting.otf", mContext));
        listType.add(FontCache.getTypeface("orange_juice_2.0.ttf", mContext));
        listType.add(FontCache.getTypeface("HeartExplosion.otf", mContext));
        listType.add(FontCache.getTypeface("Font_untuk_Ibu.ttf", mContext));
        for (int i = 0; i < 5; i++) {
            TextEntity text = new TextEntity();
            text.setText("style" + (i + 1));
            text.setTypeface(listType.get(i));
            fontName.add(text);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choice_back:
                done(v);
                break;
            case R.id.choice_done:
                done(v);
                break;
        }

    }

    private void done(View v) {
        dismiss();

        if (completeCallBack != null) {
            if (TextUtils.isEmpty(mEditSignature.getText())) {
                str = "";
            } else {
                str = mEditSignature.getText().toString();
            }
            completeCallBack.onClickChoiceOrBack(v, str, typeUpdate);
        }
    }

    public void setMessage(String message) {
        mEditSignature.setText(message);
    }


    public interface CompleteCallBack {
        void onClickChoiceOrBack(View view, String s, Typeface o);
    }

    public void setCompleteCallBack(CompleteCallBack completeCallBack) {
        this.completeCallBack = completeCallBack;
    }

    @Override
    public void show() {
        super.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager m = (InputMethodManager) mEditSignature.getContext().getSystemService(INPUT_METHOD_SERVICE);
                m.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        }, 500);

    }

    @Override
    public void dismiss() {
        super.dismiss();
        InputMethodManager m = (InputMethodManager) mEditSignature.getContext().getSystemService(INPUT_METHOD_SERVICE);
        m.hideSoftInputFromWindow(mEditSignature.getWindowToken(), 0);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != this.getCurrentFocus()) {
            /**
             * 点击空白位置 隐藏软键盘
             */
            InputMethodManager mInputMethodManager = (InputMethodManager) mContext.getSystemService(INPUT_METHOD_SERVICE);
            return mInputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
        return super.onTouchEvent(event);
    }

}
