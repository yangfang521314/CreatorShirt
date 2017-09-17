package com.example.yf.creatorshirt.mvp.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.basechoice.DesignBaseBean;
import com.example.yf.creatorshirt.mvp.presenter.DesignPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.DesignBaseContract;
import com.example.yf.creatorshirt.mvp.ui.activity.DetailDesignActivity;
import com.example.yf.creatorshirt.mvp.ui.adapter.DesignAdapter;
import com.example.yf.creatorshirt.mvp.ui.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yangfang on 2017/9/16.
 */

public class DesignFragment extends BaseFragment<DesignPresenter> implements DesignBaseContract.DesignBaseView
        , ItemClickListener.OnItemClickListener {
    @BindView(R.id.choice_man)
    RelativeLayout mChoiceMan;
    @BindView(R.id.choice_woman)
    RelativeLayout mChoiceWoman;
    @BindView(R.id.btn_start)
    Button mStartDesign;
    @BindView(R.id.clothes_style_rv)
    RecyclerView mClotheRV;
    private List<DesignBaseBean> listM = new ArrayList<>();
    private List<DesignBaseBean> listW = new ArrayList<>();
    private DesignAdapter adapter;
    private View beforeView;
    private View currentView;
    private int currentPosition;

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getBaseData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_design;
    }

    @Override
    protected void initViews(View mView) {
        mClotheRV.setVisibility(View.GONE);
        mClotheRV.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new DesignAdapter(getActivity());
        adapter.setItemClickListener(this);
        mClotheRV.setAdapter(adapter);
    }


    @OnClick({R.id.btn_start, R.id.choice_man, R.id.choice_woman})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                if ((mChoiceWoman.isSelected() || mChoiceMan.isSelected()) && (currentView != null && (currentView.isSelected()))) {
                    Bundle bundle = new Bundle();
                    if (mChoiceMan.isSelected()) {
                        bundle.putString("gender", listM.get(currentPosition).getGender());
                        bundle.putString("type", listM.get(currentPosition).getBaseId());
                    }
                    if (mChoiceWoman.isSelected()) {
                        bundle.putString("gender", listW.get(currentPosition).getGender());
                        bundle.putString("type", listW.get(currentPosition).getBaseId());
                    }
                    startCommonActivity(getActivity(), bundle, DetailDesignActivity.class);
                }
                break;
            case R.id.choice_man:
                mChoiceMan.setSelected(true);
                mChoiceWoman.setSelected(false);
                mClotheRV.setVisibility(View.VISIBLE);
                adapter.setData(listM);
                break;
            case R.id.choice_woman:
                mClotheRV.setVisibility(View.VISIBLE);
                adapter.setData(listW);
                mChoiceWoman.setSelected(true);
                mChoiceMan.setSelected(false);
                break;
        }
    }

    @Override
    public void showBaseDesignSuccess(Map<String, List<DesignBaseBean>> designBean) {
        listM = designBean.get("m");
        listW = designBean.get("w");
    }

    @Override
    public void onItemClick(View currentView, int position) {
        currentPosition = position;
        if (beforeView != null) {
            beforeView.setSelected(false);
        }
        this.currentView = currentView;
        currentView.setSelected(true);
        beforeView = currentView;
    }

}
