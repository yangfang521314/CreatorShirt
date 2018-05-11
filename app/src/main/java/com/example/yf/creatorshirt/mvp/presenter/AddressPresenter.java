package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.common.manager.UserInfoManager;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.http.TestRequestServer;
import com.example.yf.creatorshirt.mvp.model.AddressBean;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.AddressContract;
import com.example.yf.creatorshirt.utils.GsonUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //获取地址
    @Override
    public void getAddressData() {
        addSubscribe(mDataManager.getAddressData(SharedPreferencesUtil.getUserToken())
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new CommonSubscriber<List<AddressBean>>(mView) {
                    @Override
                    public void onNext(List<AddressBean> addressBeen) {
                        if (addressBeen != null) {
                            if (addressBeen.size() != 0) {
                                mView.showSuccess(addressBeen);
                            } else {
                                mView.showErrorMsg("没有添加地址");
                            }
                        }
                    }
                })
        );

    }

    /**
     * 设置默认地址
     *
     * @param id
     */
    public void setDefaultAddress(String id) {
        Map<String, String> map = new HashMap<>();
        map.put("id", id);
//        addSubscribe(mDataManager.setDefaultAddress(UserInfoManager.getInstance().getToken(), GsonUtils.getGson(map))
//                .compose(RxUtils.<HttpResponse>rxSchedulerHelper())
//                .map(new Function<HttpResponse, Integer>() {
//                    @Override
//                    public Integer apply(@NonNull HttpResponse httpResponse) throws Exception {
//                        return httpResponse.getStatus();
//                    }
//                })
//                .subscribeWith(new CommonSubscriber<Integer>(mView, "设置默认地址失败") {
//                    @Override
//                    public void onNext(Integer integer) {
//                        Log.e("Tag","fuck you "+"....");
//                       if (integer.equals(1)) {
//                            mView.successDefaultAddress("设置默认地址成功");
//                        }
//                    }
//                })
//        );

        TestRequestServer.getInstance().setDefaultAddress(UserInfoManager.getInstance().getToken(), GsonUtils.getGson(map))
                .enqueue(new Callback<HttpResponse>() {
                    @Override
                    public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                        if (response.body().getStatus() == 1) {
                            mView.successDefaultAddress("设置默认地址成功");
                        } else {
                            mView.successDefaultAddress("设置默认地址失败");
                        }
                    }

                    @Override
                    public void onFailure(Call<HttpResponse> call, Throwable t) {
                        mView.successDefaultAddress("网络连接失败");
                    }
                });
    }
}
