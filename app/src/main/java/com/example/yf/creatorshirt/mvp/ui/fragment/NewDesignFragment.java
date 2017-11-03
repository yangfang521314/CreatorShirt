package com.example.yf.creatorshirt.mvp.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.ui.adapter.NewClothesAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.NewsDesignAdapter;
import com.example.yf.creatorshirt.mvp.ui.fragment.base.BaseFragment;
import com.example.yf.creatorshirt.mvp.ui.view.scalelayoutmanager.ScaleLayoutManager;
import com.example.yf.creatorshirt.mvp.ui.view.scalelayoutmanager.ViewPagerLayoutManager;
import com.example.yf.creatorshirt.utils.DisplayUtil;
import com.example.yf.creatorshirt.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yangfang on 2017/11/2.
 */

public class NewDesignFragment extends BaseFragment {
    @BindView(R.id.base_recyclerview)
    RecyclerView mStyleRecyclerView;
    @BindView(R.id.rl_detail_style)
    RelativeLayout mRelative;
    @BindView(R.id.detail_recyclerview)
    RecyclerView mDetailRecyclerView;
    @BindView(R.id.delete_rcy)
    ImageView mDeleteCY;
    private ScaleLayoutManager scaleLayoutManager;
    private ScaleLayoutManager scale2LayoutManager;
    private NewsDesignAdapter adapter;
    private NewClothesAdapter designAdapter;
    private List<String> styleTitle;
    private List<Integer> styleImage;
    private Map<String, List<Integer>> map = new ArrayMap<>();

    private String title[] = {"短袖T恤", "长袖T恤", "卫衣", "Polo杉", "童装"};
    private int image[] = {R.mipmap.shirt0, R.mipmap.shirt1, R.mipmap.shirt2, R.mipmap.shirt2, R.mipmap.shirt1};

    @Override
    protected void initInject() {

    }

    @Override
    protected void initData() {
        super.initData();
        styleTitle = new ArrayList<>();
        styleImage = new ArrayList<>();
        styleTitle.addAll(Arrays.asList(title));
        for (int i = 0; i < image.length; i++) {
            styleImage.add(image[i]);
        }
        for (int i = 0; i < styleTitle.size(); i++) {
            putImage(styleTitle.get(i));
        }
    }

    private void putImage(String s) {
        Log.e("TAG", "ssss" + s);
        map.put(s, styleImage);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_design;
    }

    @Override
    protected void initViews(View mView) {
        mRelative.setVisibility(View.GONE);
        initClothesName();
        initClothesImage();
    }

    @OnClick({R.id.delete_rcy})
    void onClick(View view) {
        if (view.getId() == R.id.delete_rcy) {
            mRelative.setVisibility(View.GONE);
        }
    }


    /**
     * 初始化衣服
     */
    private void initClothesImage() {
        scale2LayoutManager = new ScaleLayoutManager(DisplayUtil.Dp2Px(mContext, 10));
        mDetailRecyclerView.setLayoutManager(scale2LayoutManager);
        scale2LayoutManager.setItemSpace(DisplayUtil.Dp2Px(mContext, 10));
        scale2LayoutManager.setCenterScale(1.4f);
        scale2LayoutManager.setOrientation(ViewPagerLayoutManager.HORIZONTAL);
        scale2LayoutManager.setMoveSpeed(1);


    }

    private void initClothesName() {
        scaleLayoutManager = new ScaleLayoutManager(DisplayUtil.Dp2Px(mContext, 10));
        mStyleRecyclerView.setLayoutManager(scaleLayoutManager);
        scaleLayoutManager.setItemSpace(DisplayUtil.Dp2Px(mContext, 15));
        scaleLayoutManager.setCenterScale(1.4f);
        scaleLayoutManager.setOrientation(ViewPagerLayoutManager.VERTICAL);
        scaleLayoutManager.setMoveSpeed(1);
        adapter = new NewsDesignAdapter(mContext);
        adapter.setData(styleTitle);
        adapter.setOnComClickListener(new ItemClickListener.OnItemComClickListener() {
            @Override
            public void onItemClick(Object o, View view) {
                ToastUtil.showToast(mContext, "" + o, 0);

                if(map.containsKey((String) o)) {
                    mRelative.setVisibility(View.VISIBLE);
                    designAdapter = new NewClothesAdapter(mContext);
                    designAdapter.setData(map.get(o));
                    mDetailRecyclerView.setAdapter(designAdapter);
                    designAdapter.notifyDataSetChanged();
                }

            }
        });
        mStyleRecyclerView.setAdapter(adapter);
    }
}
