package com.example.yf.creatorshirt.mvp.ui.view;

import android.view.View;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yangfang on 2017/9/4.
 * 编辑照片popupwindow
 */

public class EditUserPopupWindow extends BasePopupWindow {
    private View.OnClickListener clickListener;

    @BindView(R.id.take_gallery)
    TextView mTakeGallery;
    @BindView(R.id.take_photo)
    TextView mTakePhoto;
    @BindView(R.id.take_cancel)
    TextView mTakeCancel;

    @Override
    public View getView() {
        View view = layoutInflater.inflate(R.layout.item_edit_user, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.take_cancel,R.id.take_photo,R.id.take_gallery})
    void Onclick(View view){
        switch (view.getId()){
            case R.id.take_gallery:
                mTakeGallery.setOnClickListener(clickListener);
                break;
            case R.id.take_cancel:
                mTakeCancel.setOnClickListener(clickListener);
                break;
            case R.id.take_photo:
                mTakePhoto.setOnClickListener(clickListener);
                break;
        }
    }

    public void setOnclickListener(View.OnClickListener itemsOnClickListener) {
        clickListener = itemsOnClickListener;
    }
}
