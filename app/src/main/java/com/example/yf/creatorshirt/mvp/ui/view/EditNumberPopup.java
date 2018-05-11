package com.example.yf.creatorshirt.mvp.ui.view;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.utils.PhoneUtils;
import com.example.yf.creatorshirt.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yang on 22/06/2017.
 */

public class EditNumberPopup extends BasePopupWindow {
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.edit_number)
    EditText mEditNumber;
    private Context mContext;
    private int count;
    private ItemClickListener.OnItemClickListener onPopupClickListener;

    public EditNumberPopup(Context context) {
        super();
        mContext = context;
    }

    @Override
    public View getView() {
        View view = layoutInflater.inflate(R.layout.item_choice_avatar, null);
        ButterKnife.bind(this, view);
        return view;
    }


    public void setOnPopupClickListener(ItemClickListener.OnItemClickListener commonClickListener) {
        this.onPopupClickListener = commonClickListener;
    }

    @OnClick({R.id.btn_cancel, R.id.btn_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                this.dismiss();
                break;
            case R.id.btn_sure:
                if (onPopupClickListener != null) {
                    if (PhoneUtils.notEmptyText(mEditNumber) && Integer.valueOf(PhoneUtils.getTextString(mEditNumber)) > 0) {
                        onPopupClickListener.onItemClick(view, 0, mEditNumber.getText().toString());
                    } else {
                        ToastUtil.showToast(mContext, "请添加衣服数量", 0);
                    }
                }

                break;
        }
    }

    public void setNumber(int count) {
        mEditNumber.setText(String.valueOf(count));
    }
}
