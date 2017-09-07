package com.example.yf.creatorshirt.mvp.presenter;

import android.util.Log;

import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.http.TestRequestServer;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.AddressContract;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yang on 07/08/2017.
 */

public class AddressPresenter extends RxPresenter<AddressContract.AddressView> implements AddressContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public AddressPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getAddressData() {
        Log.e("tag", "fffffff");
//        addSubscribe(mDataManager.getAddressData()
//                .compose(RxUtils.<HttpResponse<List<AddressBean>>>rxSchedulerHelper())
//                .compose(RxUtils.<List<AddressBean>>handleResult())
//                .subscribeWith(new CommonSubscriber<List<AddressBean>>(mView, "数据请求错误") {
//                    @Override
//                    public void onNext(List<AddressBean> addressBeen) {
//                        Log.e("TAG", "FUCK YOU");
//                        mView.showSuccess(addressBeen);
//                    }
//                })
//        );

        TestRequestServer.getInstance().getAddressData(SharedPreferencesUtil.getUserToken()).enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                Log.e("Address","dddd"+response.body().getResult());
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                Log.e("ADDRESS","FAILURE");
            }
        });
    }
}
