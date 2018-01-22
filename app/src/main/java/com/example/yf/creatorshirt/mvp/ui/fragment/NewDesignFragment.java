package com.example.yf.creatorshirt.mvp.ui.fragment;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.ClothesStyleBean;
import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.presenter.ClothesPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.ClothesContract;
import com.example.yf.creatorshirt.mvp.ui.activity.MainActivity;
import com.example.yf.creatorshirt.mvp.ui.activity.NewDesignActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.NewClothesAdapter;
import com.example.yf.creatorshirt.mvp.ui.adapter.NewDesignAdapter;
import com.example.yf.creatorshirt.mvp.ui.fragment.base.BaseFragment;
import com.example.yf.creatorshirt.mvp.ui.view.popupwindow.RecyclerPopupWindow;
import com.example.yf.creatorshirt.mvp.ui.view.scalelayoutmanager.ScaleLayoutManager;
import com.example.yf.creatorshirt.mvp.ui.view.scalelayoutmanager.ViewPagerLayoutManager;
import com.example.yf.creatorshirt.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by yangfang on 2017/11/2.
 */

public class NewDesignFragment extends BaseFragment<ClothesPresenter> implements ClothesContract.ClothesView {
    @BindView(R.id.base_recyclerview)
    RecyclerView mStyleRecyclerView;
    @BindView(R.id.rl_new_fragment)
    RelativeLayout mBaseNewFragment;
    @BindView(R.id.rl_detail_style_m)
    RelativeLayout mManRelative;
    @BindView(R.id.detail_recyclerview)
    RecyclerView mDetailRCY;
    @BindView(R.id.view_background)
    View mViewBackGround;
    @Inject
    Activity mActivity;
    private ScaleLayoutManager scaleLayoutManager;
    private ScaleLayoutManager scaleMLayoutManager;

    private NewDesignAdapter adapter;
    private NewClothesAdapter designAdapter;
    //    private NewMClothesAdapter mMClothesAdapter;
    private List<String> mClothesName;
    private ArrayMap<String, List<VersionStyle>> mManData;
    private List<VersionStyle> mBaseClothes = new ArrayList<>();
    private ArrayMap<String, List<VersionStyle>> mWomanData;
    private List<VersionStyle> firstList = new ArrayList<>();//显示第一张图片
    private RecyclerPopupWindow popupWindow;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getClothesVersion();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_design;
    }

    @Override
    protected void initViews(View mView) {
        popupWindow = new RecyclerPopupWindow();
        if (((MainActivity) getActivity()) != null) {
            ((MainActivity) getActivity()).registerMyTouchListener(myOnTouchListener);
        }
    }

    /**
     * 初始化衣服
     */
    private void initClothesImage() {
        scaleMLayoutManager = new ScaleLayoutManager(DisplayUtil.Dp2Px(mContext, 10));
        scaleMLayoutManager.setItemSpace(DisplayUtil.Dp2Px(mContext, 10));
        scaleMLayoutManager.setCenterScale(1.3f);
        scaleMLayoutManager.setOrientation(ViewPagerLayoutManager.HORIZONTAL);
        mDetailRCY.setLayoutManager(scaleMLayoutManager);
    }

    private void initClothesName() {
        scaleLayoutManager = new ScaleLayoutManager(DisplayUtil.Dp2Px(mContext, 10));
        mStyleRecyclerView.setLayoutManager(scaleLayoutManager);
        scaleLayoutManager.setItemSpace(DisplayUtil.Dp2Px(mContext, 15));
        scaleLayoutManager.setCenterScale(1.3f);
        scaleLayoutManager.setOrientation(ViewPagerLayoutManager.VERTICAL);
        adapter = new NewDesignAdapter(mActivity);
        VersionStyle versionStyle;
        for (int i = 0; i < mClothesName.size(); i++) {
            String type = mManData.get(mClothesName.get(i)).get(0).getType();
            String colorName = mManData.get(mClothesName.get(i)).get(0).getColorName();
            final String current = "m" + type + "_" + colorName + "_" + "a";
            versionStyle = new VersionStyle();
            versionStyle.setColorName(current);
            versionStyle.setType(mClothesName.get(i));
            mBaseClothes.add(versionStyle);
        }
        adapter.setData(mBaseClothes);
        mStyleRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new ItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object o) {
                if (mManData.containsKey(o)) {
                    firstList.add(mManData.get(o).get(0));
                }
                if (mWomanData.containsKey(o)) {
                    firstList.add(mWomanData.get(o).get(0));
                }
                designAdapter = new NewClothesAdapter(mActivity);
                designAdapter.setData(firstList);
                designAdapter.setOnItemClickListener(new OnObjectClickListener());
                mDetailRCY.setAdapter(designAdapter);
                designAdapter.notifyDataSetChanged();
                mManRelative.setVisibility(View.VISIBLE);
                mViewBackGround.setVisibility(View.VISIBLE);

            }


        });
    }


    @Override
    public void showTotalClothes(ClothesStyleBean clothesStyleBean) {
        mClothesName = clothesStyleBean.getmListVerName();
        mManData = clothesStyleBean.getTotalManMap();
        mWomanData = clothesStyleBean.getTotalWomanMap();
        initClothesName();
        initClothesImage();
    }

    public MainActivity.MyTouchListener myOnTouchListener = new MainActivity.MyTouchListener() {
        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            //让GestureDetector先响应事件
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    int x = (int) ev.getX();
                    int y = (int) ev.getY();
                    Rect rect = new Rect(0,
                            (DisplayUtil.getScreenH(mActivity) - mManRelative.getHeight()) / 2,
                            mManRelative.getWidth(), (DisplayUtil.getScreenH(mActivity) + mManRelative.getHeight()) / 2);
                    if (rect.contains(x, y)) {
                        return true;
                    }
                    if (mViewBackGround.getVisibility() == View.VISIBLE && mManRelative.getVisibility() == View.VISIBLE) {
                        mViewBackGround.setVisibility(View.GONE);
                        mManRelative.setVisibility(View.GONE);
                        firstList.clear();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                   return true;
                default:
                    break;
            }
            return false;
        }


    };

    public class OnObjectClickListener implements DesignOnClickListener {
        @Override
        public void onItemClickListener(Object o, Object o1) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("choice", (Parcelable) o);
            bundle.putParcelableArrayList("clothes", (ArrayList<? extends Parcelable>) o1);
            startCommonActivity(getActivity(), bundle, NewDesignActivity.class);
        }
    }

    interface DesignOnClickListener {
        void onItemClickListener(Object o, Object o1);
    }

}
