package com.example.yf.creatorshirt.mvp.ui.view;

import android.view.View;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yangfang on 2017/9/4.
 * 编辑照片popupwindow
 */

public class EditUserPopupWindow extends BasePopupWindow {

    @BindView(R.id.take_gallery)
    TextView mTakeGallery;
    @BindView(R.id.take_photo)
    TextView mTakePhoto;
    @BindView(R.id.take_cancel)
    TextView mTakeCancel;

    public EditUserPopupWindow() {
        super();
    }

    @Override
    public View getView() {
        View view = layoutInflater.inflate(R.layout.item_edit_user, null);
        ButterKnife.bind(this, view);
        return view;
    }


    public void setOnclickListener(View.OnClickListener itemsOnClickListener) {
        mTakeGallery.setOnClickListener(itemsOnClickListener);
        mTakePhoto.setOnClickListener(itemsOnClickListener);
        mTakeCancel.setOnClickListener(itemsOnClickListener);
    }
}
