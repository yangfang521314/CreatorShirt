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
import com.example.yf.creatorshirt.mvp.model.orders.TextureEntity;
import com.example.yf.creatorshirt.mvp.ui.adapter.ChoiceSizeAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.TextureAdapter;
import com.example.yf.creatorshirt.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yang on 23/06/2017.
 */

public class ChoiceSizePopupWindow extends BasePopupWindow implements ItemClickListener.OnItemClickListener {
    private CommonListener.CommonClickListener onPopupClickListener;
    private Context mContext;
    private ArrayList<ClothesSize> clothesList;

    @BindView(R.id.btn_make_order)
    Button mBtnMakeOrder;
    @BindView(R.id.recyclerView_size)
    RecyclerView mRecyclerViewSize;
    @BindView(R.id.recyclerView_texture)
    RecyclerView mRecyclerViewTexture;
    private View mBeforeView;
    private View mTextureView;
    private String size;
    private List<TextureEntity> list;
    private String textUre;


    public ChoiceSizePopupWindow(Context context, List<TextureEntity> textureEntityList) {
        mContext = context;
        list = textureEntityList;
        initView();
    }

    @Override
    public View getView() {
        View view = layoutInflater.inflate(R.layout.choice_size_item, null);
        ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        ClothesSize clothesSize;
        clothesList = new ArrayList<>();
        for (int i = 0; i < Constants.size.length; i++) {
            clothesSize = new ClothesSize();
            clothesSize.setSize(Constants.size[i]);
            clothesList.add(clothesSize);
        }
    }

    private void initView() {
        mRecyclerViewSize.setLayoutManager(new LinearLayoutManager(App.getInstance(), LinearLayoutManager.HORIZONTAL, false));
        ChoiceSizeAdapter choiceSizeAdapter = new ChoiceSizeAdapter(App.getInstance());
        choiceSizeAdapter.setData(clothesList);
        choiceSizeAdapter.setOnItemClickListener(this);
        mRecyclerViewSize.setAdapter(choiceSizeAdapter);
        initTextUre();
    }

    private void setTexture(Object o) {
        textUre = (String) o;
    }

    private void initTextUre() {
        mRecyclerViewTexture.setLayoutManager(new LinearLayoutManager(App.getInstance(), LinearLayoutManager.HORIZONTAL, false));
        TextureAdapter adapter = new TextureAdapter(App.getInstance());
        adapter.setData(list);
        adapter.setOnComClickListener(new ItemClickListener.OnItemComClickListener() {

            @Override
            public void onItemClick(Object o, View view) {
                if (mTextureView != null) {
                    mTextureView.setSelected(false);
                }
                //点击选择大小
                view.setSelected(true);
                mTextureView = view;
                setTexture(o);
            }
        });
        mRecyclerViewTexture.setAdapter(adapter);
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
//                if(mBeforeView != null) {
//                    mBeforeView.setSelected(false);
//                }
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

    public String getTextUre() {
        return textUre;
    }

    public String getSize() {
        return size;
    }

    public View getBeforeView() {
        return mBeforeView;
    }
}
