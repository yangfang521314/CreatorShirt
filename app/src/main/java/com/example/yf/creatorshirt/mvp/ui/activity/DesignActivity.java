//package com.example.yf.creatorshirt.mvp.ui.activity;
//
//import android.os.Bundle;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.widget.Button;
//import android.widget.RelativeLayout;
//
//import com.example.yf.creatorshirt.R;
//import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
//import com.example.yf.creatorshirt.mvp.model.basechoice.DesignBaseBean;
//import com.example.yf.creatorshirt.mvp.presenter.DesignPresenter;
//import com.example.yf.creatorshirt.mvp.presenter.contract.DesignBaseContract;
//import com.example.yf.creatorshirt.mvp.ui.activity.base.BaseActivity;
//import com.example.yf.creatorshirt.mvp.ui.adapter.DesignAdapter;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import butterknife.BindView;
//import butterknife.OnClick;
//
///**
// * 选择衣服总体样式页面
// */
//public class DesignActivity extends BaseActivity<DesignPresenter> implements DesignBaseContract.DesignBaseView
//        , ItemClickListener.OnItemClickListener {
//    @BindView(R.id.choice_man)
//    RelativeLayout mChoiceMan;
//    @BindView(R.id.choice_woman)
//    RelativeLayout mChoiceWoman;
//    @BindView(R.id.btn_start)
//    Button mStartDesign;
//    @BindView(R.id.clothes_style_rv)
//    RecyclerView mClotheRV;
//    @BindView(R.id.tool_bar)
//    Toolbar mToolbar;
//    private List<DesignBaseBean> listM = new ArrayList<>();
//    private List<DesignBaseBean> listW = new ArrayList<>();
//    private DesignAdapter adapter;
//    private View beforeView;
//    private View currentView;
//    private int currentPosition;
//
//    @Override
//    protected void inject() {
//        getActivityComponent().inject(this);
//    }
//
//    @Override
//    protected void initView() {
//        mAppBarTitle.setText(R.string.design);
//        mAppBarBack.setVisibility(View.VISIBLE);
//        mClotheRV.setVisibility(View.GONE);
//        mClotheRV.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false));
//        adapter = new DesignAdapter(this);
//        adapter.setItemClickListener(this);
//        mClotheRV.setAdapter(adapter);
//    }
//
//    @Override
//    public void initData() {
//        super.initData();
//        mPresenter.getBaseData();
//    }
//
//    @OnClick({R.id.btn_start, R.id.choice_man, R.id.choice_woman, R.id.back})
//    void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.btn_start:
//                if ((mChoiceWoman.isSelected() || mChoiceMan.isSelected()) && (currentView != null && (currentView.isSelected()))) {
//                    Bundle bundle =new Bundle();
//                    if(mChoiceMan.isSelected()){
//                        bundle.putString("gender",listM.get(currentPosition).getGender());
//                        bundle.putString("type",listM.get(currentPosition).getBaseId());
//                    }
//                    if(mChoiceWoman.isSelected()){
//                        bundle.putString("gender",listM.get(currentPosition).getGender());
//                        bundle.putString("type",listM.get(currentPosition).getBaseId());
//                    }
//                    startCommonActivity(this,bundle,DetailDesignActivity.class);
//                }
//                break;
//            case R.id.back:
//                finish();
//                break;
//            case R.id.choice_man:
//                mChoiceMan.setSelected(true);
//                mChoiceWoman.setSelected(false);
//                mClotheRV.setVisibility(View.VISIBLE);
//                adapter.setData(listM);
//                break;
//            case R.id.choice_woman:
//                mClotheRV.setVisibility(View.VISIBLE);
//                adapter.setData(listW);
//                mChoiceWoman.setSelected(true);
//                mChoiceMan.setSelected(false);
//                break;
//        }
//    }
//
//    @Override
//    protected int getView() {
//        return R.layout.activity_design;
//    }
//
//    @Override
//    public void showBaseDesignSuccess(Map<String, List<DesignBaseBean>> designBean) {
//        listM = designBean.get("m");
//        listW = designBean.get("w");
//    }
//
//    @Override
//    public void onItemClick(View currentView, int position) {
//        currentPosition = position;
//        if (beforeView != null) {
//            beforeView.setSelected(false);
//        }
//        this.currentView = currentView;
//        currentView.setSelected(true);
//        beforeView = currentView;
//    }
//
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//    }
//}
