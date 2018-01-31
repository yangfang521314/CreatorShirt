package com.example.yf.creatorshirt.mvp.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;

/**
 * Created by yangfang on 2018/1/23.
 */

public class DialogAlert extends AlertDialog {
    private View mLine;
    private View mLine1;
    private TextView mDialogName;
    private Button mCancle;
    private Button mConfirm;

    private Context mContext;

    private String title;

    public DialogAlert(@NonNull Context context, String title) {
        super(context);
        mContext = context;
        this.title = title;
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_fragment_dialog, null,false);
        initView(view);
        setView(view);
    }


    public void setOnDialogClickListener(View.OnClickListener onclickListener) {
        mCancle.setOnClickListener(onclickListener);
        mConfirm.setOnClickListener(onclickListener);
    }


    private void initView(View view) {
        mLine = view.findViewById(R.id.view_line1);
        mDialogName = view.findViewById(R.id.tv_dialog_name);
        mConfirm = view.findViewById(R.id.positive);
        mCancle = view.findViewById(R.id.negative);

        mDialogName.setText(title);
        mLine.setVisibility(View.GONE);

    }

}
