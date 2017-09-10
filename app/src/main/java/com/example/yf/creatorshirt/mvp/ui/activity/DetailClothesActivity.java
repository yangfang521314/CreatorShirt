package com.example.yf.creatorshirt.mvp.ui.activity;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
import com.example.yf.creatorshirt.mvp.ui.view.CircleView;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailClothesActivity extends BaseActivity {
    @BindView(R.id.user_avatar)
    ImageView mUserAavtar;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.clothes_design_name)
    TextView mClothesName;
    @BindView(R.id.praise_num)
    TextView mClothesPraiseNum;
    @BindView(R.id.detail_clothes_time)
    TextView mDetailTime;
    @BindView(R.id.clothes_size)
    TextView mClothesSize;
    @BindView(R.id.clothes_id)
    TextView mClothesId;
    @BindView(R.id.clothes_iv_color)
    CircleView mClothesColor;
    @BindView(R.id.clothes_design_price)
    TextView mClothesPrice;
    @BindView(R.id.btn_start)
    Button mStartOrder;
    @BindView(R.id.view_pager_clothes)
    ViewPager mViewPager;


    private BombStyleBean mBombStyleBean;

    @Override
    protected void inject() {

    }

    @Override
    protected void initView() {
        mAppBarTitle.setText("衣服详情");
        mAppBarBack.setVisibility(View.VISIBLE);
        mUserName.setText(mBombStyleBean.getUserName());
        Glide.with(this).load(mBombStyleBean.getHeaderImage()).into(mUserAavtar);
        mClothesName.setText(mBombStyleBean.getBaseName());
        mDetailTime.setText(mBombStyleBean.getTitle());
        mClothesPraiseNum.setText(mBombStyleBean.getPraise() + "人赞");
        mClothesId.setText("商品ID :  " + mBombStyleBean.getBaseId());
        int colorN = Color.parseColor("#" + mBombStyleBean.getColor());
        mClothesColor.setOutColor(colorN);
        mClothesPrice.setText("¥ " + mBombStyleBean.getFee());
        mClothesSize.setText("尺寸 :  " + mBombStyleBean.getSize());

    }

    @OnClick({R.id.btn_start,R.id.user_avatar})
    void onClick(View view){

    }


    @Override
    protected int getView() {
        return R.layout.activity_detail_clothes;
    }

    @Override
    public void initData() {
        super.initData();
        if (getIntent().getExtras() != null) {
            mBombStyleBean = getIntent().getExtras().getParcelable("detail");
        }
    }
}
