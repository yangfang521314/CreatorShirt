package com.example.yf.creatorshirt.widget.stickerview;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.yf.creatorshirt.R;

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
        mEditSignature = (EditText) findViewById(R.id.edit_signature);
        choiceBack = (ImageView) findViewById(R.id.choice_back);
        choiceDone = (ImageView) findViewById(R.id.choice_done);
        choiceBack.setOnClickListener(this);
        choiceDone.setOnClickListener(this);
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
                InputMethodManager m = (InputMethodManager) mEditSignature.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                m.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
            }
        }, 500);
        //// TODO: 22/06/2017 点击空白处键盘消失 

    }

    @Override
    public void dismiss() {
        super.dismiss();
        InputMethodManager m = (InputMethodManager) mEditSignature.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        m.hideSoftInputFromWindow(mEditSignature.getWindowToken(), 0);
    }


}
