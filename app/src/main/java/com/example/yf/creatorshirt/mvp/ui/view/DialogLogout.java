package com.example.yf.creatorshirt.mvp.ui.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;


/**
 * Created by panguso on 2016/3/1.
 */
public class DialogLogout extends DialogFragment {
    private View mLine;
    private View mLine1;
    private TextView mDialogName;
    private Button mCancle;
    private Button mConfirm;

    private String title = "是否注销用户";

    private View.OnClickListener itemsOnclickListener;

    public DialogLogout() {
        super();
    }

    public void setOnDialogClickListener(View.OnClickListener onclickListener) {
        this.itemsOnclickListener = onclickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, 0);//自定义布局有title
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_dialog, container);
        initView(view);
        return view;
    }

    private void initView(View view) {;
        mLine = view.findViewById(R.id.view_line1);
        mDialogName = (TextView) view.findViewById(R.id.tv_dialog_name);
        mConfirm = (Button) view.findViewById(R.id.positive);
        mCancle = (Button) view.findViewById(R.id.negative);

        mDialogName.setText(title);
        mLine.setVisibility(View.GONE);
        mCancle.setOnClickListener(itemsOnclickListener);
        mConfirm.setOnClickListener(itemsOnclickListener);
    }
}
