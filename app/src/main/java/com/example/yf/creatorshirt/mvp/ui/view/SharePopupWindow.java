package com.example.yf.creatorshirt.mvp.ui.view;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.CommonListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yangfang on 2017/9/24.
 */

public class SharePopupWindow extends BasePopupWindow {
    @BindView(R.id.share_button)
    TextView mTextView;

    private Context mContext;
    private CommonListener.CommonClickListener onPopupClickListener;

    public SharePopupWindow(Context context) {
        mContext = context;
    }

    @OnClick(R.id.share_button)
    void onClick(View view) {
        if (view.getId() == R.id.share_button) {
            if (onPopupClickListener != null) {
                onPopupClickListener.onClickListener();
            }
        }
    }


    @Override
    public View getView() {
        View view = layoutInflater.inflate(R.layout.share_item, null);
        ButterKnife.bind(this, view);
        return view;
    }


    public void setOnPopupClickListener(CommonListener.CommonClickListener clickListener) {
        onPopupClickListener = clickListener;
    }
}
