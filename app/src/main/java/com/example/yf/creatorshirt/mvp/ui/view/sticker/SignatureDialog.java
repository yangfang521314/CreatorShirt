package com.example.yf.creatorshirt.mvp.ui.view.sticker;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.common.cache.FontCache;
import com.example.yf.creatorshirt.mvp.model.detaildesign.TextEntity;
import com.example.yf.creatorshirt.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.INPUT_METHOD_SERVICE;

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


    public SignatureDialog(@NonNull Context context) {
        super(context, R.style.shareDialog_style);
        mContext = context;
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
        mEditSignature = findViewById(R.id.edit_signature);
        choiceBack = findViewById(R.id.choice_back);
        choiceDone = findViewById(R.id.choice_done);
        choiceBack.setOnClickListener(this);
        choiceDone.setOnClickListener(this);

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
                str = Constants.ADD_TEXT;
            } else {
                str = mEditSignature.getText().toString();
            }
            completeCallBack.onClickChoiceOrBack(v, str);
        }
    }

    public void setMessage(String message) {
        mEditSignature.setText(message);
    }


    public interface CompleteCallBack {
        void onClickChoiceOrBack(View view, String s);
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
