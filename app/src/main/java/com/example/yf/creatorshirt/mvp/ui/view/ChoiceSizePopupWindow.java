package com.example.yf.creatorshirt.mvp.ui.view;

import android.view.View;
import android.widget.Button;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.CommonListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yang on 23/06/2017.
 */

public class ChoiceSizePopupWindow extends BasePopupWindow {
    private CommonListener.CommonClickListener onPopupClickListener;

    @BindView(R.id.btn_make_order)
    Button mBtnMakeOrder;

    @Override
    public View getView() {
        View view = layoutInflater.inflate(R.layout.choice_size_item, null);
        ButterKnife.bind(this, view);
        return view;
    }

    public void setOnPopupClickListener(CommonListener.CommonClickListener onPopupClickListener) {
        this.onPopupClickListener = onPopupClickListener;
    }

    @OnClick(R.id.btn_make_order)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_make_order:
                if (onPopupClickListener != null) {
                    onPopupClickListener.onClickListener();
                }
                break;
        }
    }

}
