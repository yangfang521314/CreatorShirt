package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.http.HttpResultResponse;
import com.example.yf.creatorshirt.http.TestRequestServer;
import com.example.yf.creatorshirt.mvp.model.BombStyleBean;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.DesignerOrdersContract;
import com.example.yf.creatorshirt.utils.GsonUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yangfang on 2017/9/12.
 */

public class DesignerOrdersPresenter extends RxPresenter<DesignerOrdersContract.DesignerDesignView> implements
        DesignerOrdersContract.Presenter {
    private DataManager mDataManager;
    private int userID;
    private int pageIndex = 0;

    @Inject
    public DesignerOrdersPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;

    }

    @Override
    public void getTotalDesigner() {
        pageIndex = 0;
        Map<String, Integer> map = new HashMap<>();
        map.put("desginUserId", userID);
        map.put("pageIndex", pageIndex);
        addSubscribe(mDataManager.getDesignOrders(GsonUtils.getGson(map))
                .compose(RxUtils.<HttpResponse<List<BombStyleBean>>>rxSchedulerHelper())
                .compose(RxUtils.<List<BombStyleBean>>handleResult())
                .subscribeWith(new CommonSubscriber<List<BombStyleBean>>(mView, false) {
                    @Override
                    public void onNext(List<BombStyleBean> orderStyleBeen) {
                        if (orderStyleBeen != null) {
                            if (orderStyleBeen.size() == 0) {
                                mView.showErrorMsg("没有原创设计");
                            } else {
                                mView.showSuccessData(orderStyleBeen);
                            }
                        } else {
                            mView.showErrorMsg(App.getInstance().getString(R.string.load_failure));
                        }
                    }

                })

        );
//        TestRequestServer.getInstance().getDesignOrders(GsonUtils.getGson(map)).enqueue(new Callback<HttpResultResponse>() {
//            @Override
//            public void onResponse(Call<HttpResultResponse> call, Response<HttpResultResponse> response) {
//                if (response.body().getStatus() == 1) {
//                    if (response.body().getResult() != null ) {
//                        if(response.body().getResult().size() != 0) {
//                            mView.showSuccessData(response.body().getResult());
//                        }else {
//                            mView.showErrorMsg("没有原创设计");
//                        }
//                    } else {
//                        mView.showErrorMsg(App.getInstance().getString(R.string.load_failure));
//                    }
//
//                } else {
//                    mView.showErrorMsg(App.getInstance().getString(R.string.load_failure));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<HttpResultResponse> call, Throwable t) {
//                mView.showErrorMsg(App.getInstance().getString(R.string.no_net));
//            }
//        });
    }

    public void setUserId(int userId) {
        this.userID = userId;
    }

    public void getMoreDesignOrders() {
        Map<String, Integer> map = new HashMap<>();
        map.put("desginUserId", userID);
        map.put("pageIndex", ++pageIndex);
//        addSubscribe(mDataManager.getDesignOrders(GsonUtils.getGson(map))
//                .compose(RxUtils.<HttpResponse<List<BombStyleBean>>>rxSchedulerHelper())
//                .compose(RxUtils.<List<BombStyleBean>>handleResult())
//                .subscribeWith(new CommonSubscriber<List<BombStyleBean>>(mView, "请求失败，请检查网络", false) {
//                    @Override
//                    public void onNext(List<BombStyleBean> orderStyleBeen) {
//                        if (orderStyleBeen != null) {
//                            if (orderStyleBeen.size() == 0) {
//                                mView.showErrorMsg("没有更多原创设计");
//                            } else {
//                                mView.showSuccessData(orderStyleBeen);
//                            }
//                        } else {
//                            mView.showErrorMsg(App.getInstance().getString(R.string.load_failure));
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        mView.showErrorMsg(App.getInstance().getString(R.string.no_net));
//                    }
//                })
//        );
        TestRequestServer.getInstance().getDesignOrders(GsonUtils.getGson(map)).enqueue(new Callback<HttpResultResponse>() {
            @Override
            public void onResponse(Call<HttpResultResponse> call, Response<HttpResultResponse> response) {
                if (response.body().getStatus() == 1) {
                    if (response.body().getResult() != null) {
                        if (response.body().getResult().size() != 0) {
                            mView.showMoreData(response.body().getResult());
                        } else {
                            mView.showErrorMsg("没有更多数据");
                        }
                    } else {
                        mView.showErrorMsg(App.getInstance().getString(R.string.load_more_failure));
                    }
                } else {
                    mView.showErrorMsg(App.getInstance().getString(R.string.load_more_failure));
                }
            }

            @Override
            public void onFailure(Call<HttpResultResponse> call, Throwable t) {
                mView.showErrorMsg(App.getInstance().getString(R.string.no_net));
            }
        });
    }

    public void getRefreshDesigner() {
        pageIndex = 0;
        Map<String, Integer> map = new HashMap<>();
        map.put("desginUserId", userID);
        map.put("pageIndex", pageIndex);
        addSubscribe(mDataManager.getDesignOrders(GsonUtils.getGson(map))
                .compose(RxUtils.<HttpResponse<List<BombStyleBean>>>rxSchedulerHelper())
                .compose(RxUtils.<List<BombStyleBean>>handleResult())
                .subscribeWith(new CommonSubscriber<List<BombStyleBean>>(mView, false) {
                    @Override
                    public void onNext(List<BombStyleBean> orderStyleBeen) {
                        if (orderStyleBeen != null) {
                            if (orderStyleBeen.size() == 0) {
                                mView.showUpdateZero(0);
                            } else {
                                mView.showRefreshData(orderStyleBeen);
                            }
                        } else {
                            mView.showErrorMsg(App.getInstance().getString(R.string.refresh_data_failure));
                        }
                    }
                })

        );

//        TestRequestServer.getInstance().getDesignOrders(GsonUtils.getGson(map)).enqueue(new Callback<HttpResultResponse>() {
//            @Override
//            public void onResponse(Call<HttpResultResponse> call, Response<HttpResultResponse> response) {
//                if (response.body().getStatus() == 1) {
//                    if (response.body().getResult() != null && response.body().getResult().size() != 0) {
//                        mView.showRefreshData(response.body().getResult());
//                    } else {
//                        mView.showErrorMsg(App.getInstance().getString(R.string.refresh_data_failure));
//                    }
//
//                } else {
//                    mView.showUpdateZero(0);
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<HttpResultResponse> call, Throwable t) {
//                mView.showErrorMsg(App.getInstance().getString(R.string.no_net));
//            }
//        });
    }
}
