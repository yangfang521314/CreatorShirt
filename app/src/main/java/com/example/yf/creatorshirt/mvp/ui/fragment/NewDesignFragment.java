package com.example.yf.creatorshirt.mvp.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.ClothesStyleBean;
import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.presenter.ClothesPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.ClothesContract;
import com.example.yf.creatorshirt.mvp.ui.activity.NewDesignActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.NewDesignAdapter;
import com.example.yf.creatorshirt.mvp.ui.fragment.base.BaseFragment;
import com.example.yf.creatorshirt.mvp.ui.view.popupwindow.RecyclerViewPopupWindow;
import com.example.yf.creatorshirt.mvp.ui.view.scalelayoutmanager.ScaleLayoutManager;
import com.example.yf.creatorshirt.mvp.ui.view.scalelayoutmanager.ViewPagerLayoutManager;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Inject
    Activity mActivity;
    private ScaleLayoutManager scaleLayoutManager;
    private NewDesignAdapter adapter;
    private List<String> mClothesName;
    private ArrayMap<String, List<VersionStyle>> mManData;
    private List<VersionStyle> mBaseClothes = new ArrayList<>();
    private ArrayMap<String, List<VersionStyle>> mWomanData;
    private List<VersionStyle> firstList = new ArrayList<>();//显示第一张图片
    private Map<String, List<VersionStyle>> sendMap = new HashMap<>();//到达第二个页面数据
    private RecyclerViewPopupWindow mPopupWindow;

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
    }

    private void initClothesName() {
        scaleLayoutManager = new ScaleLayoutManager(DisplayUtil.Dp2Px(mContext, 10));
        mStyleRecyclerView.setLayoutManager(scaleLayoutManager);
        scaleLayoutManager.setItemSpace(DisplayUtil.Dp2Px(mContext, 15));
        scaleLayoutManager.setCenterScale(1.4f);
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
                    sendMap.put(mManData.get(o).get(0).getSex(), mManData.get(o));
                }
                if (mWomanData.containsKey(o)) {
                    firstList.add(mWomanData.get(o).get(0));
                    sendMap.put(mWomanData.get(o).get(0).getSex(), mWomanData.get(o));
                }
                choiceDiff(firstList, o);
            }

        });
    }

    private void choiceDiff(List<VersionStyle> firstList, Object o) {
        if (firstList.size() == 2) {
            initPopupWindow(firstList).showAtLocation(mBaseNewFragment, Gravity.CENTER, 0, 0);
            setParams(Constants.CHANGE_ALPHA);
        } else {
            if (o != null) {
                Bundle bundle = new Bundle();
                if (mManData.containsKey(o)) {
                    bundle.putParcelable("choice", (Parcelable) mManData.get(o).get(0));
                    bundle.putParcelableArrayList("clothes", (ArrayList<? extends Parcelable>) mManData.get(o));
                }
                if (mWomanData.containsKey(o)) {
                    bundle.putParcelable("choice", (Parcelable) mWomanData.get(o).get(0));
                    bundle.putParcelableArrayList("clothes", (ArrayList<? extends Parcelable>) mWomanData.get(o));
                }
                firstList.clear();
                startCommonActivity(getActivity(), bundle, NewDesignActivity.class);
            }

        }
    }

    private void setParams(float f) {
        WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
        params.alpha = f;
        params.dimAmount = f;
        mActivity.getWindow().setAttributes(params);
        mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    @SuppressLint("WrongConstant")
    private RecyclerViewPopupWindow initPopupWindow(final List<VersionStyle> firstList) {
        mPopupWindow = new RecyclerViewPopupWindow();
        mPopupWindow.setData(firstList);
        mPopupWindow.setOnClickListener(new OnObjectClickListener());
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                firstList.clear();
                setParams(Constants.NORMAL_ALPHA);
            }
        });
        mPopupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return mPopupWindow;
    }


    @Override
    public void showTotalClothes(ClothesStyleBean clothesStyleBean) {
        mClothesName = clothesStyleBean.getmListVerName();
        mManData = clothesStyleBean.getTotalManMap();
        mWomanData = clothesStyleBean.getTotalWomanMap();
        initClothesName();
    }


    public class OnObjectClickListener implements DesignOnClickListener {
        @Override
        public void onItemClickListener(Object o, Object o1) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("choice", (Parcelable) o);
            if (o1 != null) {
                if (sendMap.containsKey(o1)) {
                    bundle.putParcelableArrayList("clothes", (ArrayList<? extends Parcelable>) sendMap.get(o1));
                }
                if (sendMap.containsKey(o1)) {
                    bundle.putParcelableArrayList("clothes", (ArrayList<? extends Parcelable>) sendMap.get(o1));
                }
            }

            startCommonActivity(getActivity(), bundle, NewDesignActivity.class);
        }
    }

    interface DesignOnClickListener {
        void onItemClickListener(Object o, Object o1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
