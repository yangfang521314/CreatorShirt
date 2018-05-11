package com.example.yf.creatorshirt.mvp.ui.view.popupwindow;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.ui.adapter.NewClothesAdapter;
import com.example.yf.creatorshirt.mvp.ui.fragment.NewDesignFragment;
import com.example.yf.creatorshirt.mvp.ui.view.BasePopupWindow;
import com.example.yf.creatorshirt.mvp.ui.view.scalelayoutmanager.ScaleLayoutManager;
import com.example.yf.creatorshirt.mvp.ui.view.scalelayoutmanager.ViewPagerLayoutManager;
import com.example.yf.creatorshirt.utils.DisplayUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yangfang on 2018/1/25.
 */

public class RecyclerViewPopupWindow extends BasePopupWindow {
    @BindView(R.id.detail_recyclerview)
    RecyclerView mDetailRCY;
    private NewClothesAdapter designAdapter;

    public RecyclerViewPopupWindow() {
        super();
    }

    @Override
    public View getView() {
        View view = layoutInflater.inflate(R.layout.item_choice_popup, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        ScaleLayoutManager scaleMLayoutManager = new ScaleLayoutManager(DisplayUtil.Dp2Px(App.getInstance(), 10));
        scaleMLayoutManager.setItemSpace(DisplayUtil.Dp2Px(App.getInstance(), 10));
        scaleMLayoutManager.setCenterScale(1.4f);
        scaleMLayoutManager.setOrientation(ViewPagerLayoutManager.HORIZONTAL);
        mDetailRCY.setLayoutManager(scaleMLayoutManager);
        designAdapter = new NewClothesAdapter(App.getInstance());

    }

    public void setData(List<VersionStyle> firstList) {
        designAdapter.setData(firstList);
        mDetailRCY.setAdapter(designAdapter);
        designAdapter.notifyDataSetChanged();
    }

    public void setOnClickListener(NewDesignFragment.OnObjectClickListener onObjectClickListener) {
        if (onObjectClickListener != null) {
            designAdapter.setOnItemClickListener(onObjectClickListener);
        }
    }
}
