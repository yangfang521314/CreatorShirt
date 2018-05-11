package com.example.yf.creatorshirt.mvp.presenter;

import com.example.yf.creatorshirt.common.manager.UserInfoManager;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.model.address.AddressEntity;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.AddressEditContract;
import com.example.yf.creatorshirt.utils.GsonUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.RequestBody;

/**
 * Created by yang on 07/08/2017.
 */

public class AddressEditPresenter extends RxPresenter<AddressEditContract.AddressEditView> implements AddressEditContract.Presenter {

    private DataManager mDataManager;
    private AddressEntity saveEntity;

    @Inject
    public AddressEditPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
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
                .compose(RxUtils.rxSchedulerHelper())
                .map(new Function<HttpResponse, Integer>() {
                    @Override
                    public Integer apply(@NonNull HttpResponse httpResponse) {
                        return httpResponse.getStatus();
                    }
                })
                .subscribeWith(new CommonSubscriber<Integer>(mView, "地址保存失败") {
                    @Override
                    public void onNext(Integer integer) {
                        if (integer.equals(1)) {
                            mView.SuccessSaveAddress("地址保存成功");
                        }
                    }
                })
        );
//
    }

    /**
     * 更新地址
     */
    @Override
    public void setUpdateAddress() {
        addSubscribe(mDataManager.saveAddress(UserInfoManager.getInstance().getToken(), GsonUtils.getGson(saveEntity))
                .compose(RxUtils.rxSchedulerHelper())
                .map(new Function<HttpResponse, Integer>() {
                    @Override
                    public Integer apply(@NonNull HttpResponse httpResponse) {
                        return httpResponse.getStatus();
                    }
                })
                .subscribeWith(new CommonSubscriber<Integer>(mView, "地址修改失败") {
                    @Override
                    public void onNext(Integer integer) {
                        if (integer.equals(1)) {
                            mView.SuccessSaveAddress("地址修改成功");
                        }
                    }
                })
        );
    }

    public void setUpdate(String receiverName, String receiverPhone, String receiverEmail, String receiverCity, String receiverAddress, int isDefault, String id) {
        saveEntity = new AddressEntity();
        saveEntity.setAddress(receiverAddress);
        saveEntity.setCity(receiverCity);
        saveEntity.setMobile(receiverPhone);
        saveEntity.setZipcode(receiverEmail);
        saveEntity.setUserName(receiverName);
        saveEntity.setIsDefault(isDefault);
        saveEntity.setId(id);
    }
}
