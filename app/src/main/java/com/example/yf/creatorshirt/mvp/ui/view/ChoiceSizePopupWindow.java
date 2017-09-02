package com.example.yf.creatorshirt.mvp.ui.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.listener.CommonListener;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.orders.ClothesSize;
import com.example.yf.creatorshirt.mvp.ui.adapter.ChoiceSizeAdapter;
import com.example.yf.creatorshirt.utils.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yang on 23/06/2017.
 */

public class ChoiceSizePopupWindow extends BasePopupWindow implements ItemClickListener.OnItemClickListener{
    private CommonListener.CommonClickListener onPopupClickListener;
    private Context mContext;
    private ArrayList<ClothesSize> clothesList;

    @BindView(R.id.btn_make_order)
    Button mBtnMakeOrder;
    @BindView(R.id.recyclerView_size)
    RecyclerView mRecyclerViewSize;
    private View mBeforeView;
    private String size;



    public ChoiceSizePopupWindow(Context context) {
        mContext = context;
    }

    @Override
    public View getView() {
        View view = layoutInflater.inflate(R.layout.choice_size_item, null);
        ButterKnife.bind(this, view);
        initData();
        initView();
        return view;
    }

    private void initData() {
        ClothesSize clothesSize;
        clothesList = new ArrayList<>();
        for (int i = 0; i < Constants.size.length; i++) {
            clothesSize = new ClothesSize();
            clothesSize.setSize(Constants.size[i]);
            clothesSize.setLetter(Constants.letter[i]);
            clothesList.add(clothesSize);
        }
    }

    private void initView() {
        mRecyclerViewSize.setLayoutManager(new LinearLayoutManager(App.getInstance(), LinearLayoutManager.HORIZONTAL, false));
        ChoiceSizeAdapter choiceSizeAdapter = new ChoiceSizeAdapter(App.getInstance());
        choiceSizeAdapter.setData(clothesList);
        choiceSizeAdapter.setOnItemClickListener(this);
        mRecyclerViewSize.setAdapter(choiceSizeAdapter);
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

    @Override
    public void onItemClick(View view, int position) {
        if (mBeforeView != null) {
            mBeforeView.setSelected(false);
        }
        //点击选择大小
        view.setSelected(true);
        mBeforeView = view;
        setChoice(clothesList.get(position).getSize());
    }

    private void setChoice(String size) {
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public View getBeforeView() {
        return mBeforeView;
    }
}
