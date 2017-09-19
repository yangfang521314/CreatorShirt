package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.common.UserInfoManager;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.http.TestRequestServer;
import com.example.yf.creatorshirt.mvp.model.AddressBean;
import com.example.yf.creatorshirt.mvp.model.address.AddressEntity;
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

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yang on 07/08/2017.
 */

public class AddressPresenter extends RxPresenter<AddressContract.AddressView> implements AddressContract.Presenter {

    private DataManager mDataManager;
    private AddressEntity saveEntity;

    @Inject
    public AddressPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    //获取地址
    @Override
    public void getAddressData() {
        addSubscribe(mDataManager.getAddressData(SharedPreferencesUtil.getUserToken())
                .compose(RxUtils.<HttpResponse<List<AddressBean>>>rxSchedulerHelper())
                .compose(RxUtils.<List<AddressBean>>handleResult())
                .subscribeWith(new CommonSubscriber<List<AddressBean>>(mView, "数据请求错误") {
                    @Override
                    public void onNext(List<AddressBean> addressBeen) {
                        mView.showSuccess(addressBeen);
                    }
                })
        );

    }

    public void setAddressInfo(String receiverName, String receiverPhone, String receiverEmail, String reciverCity, String receiverAddress) {
        saveEntity = new AddressEntity();
        saveEntity.setAddress(receiverAddress);
        saveEntity.setCity(reciverCity);
        saveEntity.setMobile(receiverPhone);
        saveEntity.setZipcode(receiverEmail);
        saveEntity.setUserName(receiverName);
    }

    /**
     * 保存地址
     */
    public void saveAddressData() {
        RequestBody body = GsonUtils.getGson(saveEntity);
        addSubscribe(mDataManager.saveAddress(SharedPreferencesUtil.getUserToken(), body)
                .compose(RxUtils.<HttpResponse>rxSchedulerHelper())
                .map(new Function<HttpResponse, Integer>() {
                    @Override
                    public Integer apply(@NonNull HttpResponse httpResponse) throws Exception {
                        return httpResponse.getStatus();
                    }
                })
                .subscribeWith(new CommonSubscriber<Integer>(mView, "地址保存失败") {
                    @Override
                    public void onNext(Integer integer) {
                        if (integer.equals(1)) {
                            mView.SuccessSaveAddress("保存成功");
                        }
                    }
                })
        );
//
    }

    public void setUpdateAddress(String receiverName, String receiverPhone, String receiverEmail, String receiverCity, String receiverAddress, int isDefault, String id) {
        saveEntity = new AddressEntity();
        saveEntity.setAddress(receiverAddress);
        saveEntity.setCity(receiverCity);
        saveEntity.setMobile(receiverPhone);
        saveEntity.setZipcode(receiverEmail);
        saveEntity.setUserName(receiverName);
        saveEntity.setIsDefault(isDefault);
        saveEntity.setId(id);
        addSubscribe(mDataManager.saveAddress(UserInfoManager.getInstance().getToken(), GsonUtils.getGson(saveEntity))
                .compose(RxUtils.<HttpResponse>rxSchedulerHelper())
                .map(new Function<HttpResponse, Integer>() {
                    @Override
                    public Integer apply(@NonNull HttpResponse httpResponse) throws Exception {
                        return httpResponse.getStatus();
                    }
                })
                .subscribeWith(new CommonSubscriber<Integer>(mView, "地址修改失败") {
                    @Override
                    public void onNext(Integer integer) {
                        if (integer.equals(1)) {
                            mView.SuccessSaveAddress("修改成功");
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
