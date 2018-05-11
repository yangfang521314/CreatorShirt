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

public class DialogAlert extends AlertDialog implements View.OnClickListener {
    private PositiveClickListener positiveClickListener;

    private DialogAlert(Builder builder, @NonNull Context context) {
        super(context);
        View view = LayoutInflater.from(builder.mContext).inflate(R.layout.fragment_fragment_dialog, null, false);
        initView(view, builder);
        setView(view);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    private void initView(View view, Builder builder) {
        View mLine = view.findViewById(R.id.view_line1);
        TextView mDialogName = view.findViewById(R.id.tv_dialog_name);
        Button mConfirm = view.findViewById(R.id.positive);
        Button mCancle = view.findViewById(R.id.negative);
        mDialogName.setText(builder.title);
        mConfirm.setText(builder.nei);
        mCancle.setText(builder.pos);
        mLine.setVisibility(View.GONE);
        mConfirm.setOnClickListener(this);
        mCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.negative:
                dismiss();
                break;
            case R.id.positive:
                dismiss();
                positiveClickListener.onClickListener();
                break;
            default:
                break;
        }

    }

    public void setOnPositionClickListener(PositiveClickListener positiveClickListener) {
        this.positiveClickListener = positiveClickListener;
    }


    public interface PositiveClickListener {
        void onClickListener();
    }

    public static class Builder {
        private Context mContext;
        private String title;
        private String pos;
        private String nei;

        public Builder setContext(Context mContext) {
            this.mContext = mContext;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setCancel(String pos) {
            this.pos = pos;
            return this;
        }

        public Builder setConfirm(String nei) {
            this.nei = nei;
            return this;
        }

        public DialogAlert builder() {
            return new DialogAlert(this, mContext);
        }

    }


}
