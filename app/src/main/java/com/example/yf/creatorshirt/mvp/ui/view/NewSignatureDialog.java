package com.example.yf.creatorshirt.mvp.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.example.yf.creatorshirt.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yangfang on 2018/1/25.
 */

public class NewSignatureDialog extends AlertDialog {
    @BindView(R.id.choice_back)
    ImageView mCancle;
    @BindView(R.id.choice_done)
    ImageView mConfirm;

    private Context mContext;


    public NewSignatureDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.view_input_dialog, null, false);
        setView(view);
        ButterKnife.bind(this,view);
    }

    public void setOnDialogClickListener(View.OnClickListener onclickListener) {
        mCancle.setOnClickListener(onclickListener);
        mConfirm.setOnClickListener(onclickListener);
    }

}
