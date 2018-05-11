package com.example.yf.creatorshirt.mvp.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.common.manager.ClothesSizeManager;
import com.example.yf.creatorshirt.common.manager.UserInfoManager;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.mvp.model.ClothesPrice;
import com.example.yf.creatorshirt.mvp.model.orders.ClothesSize;
import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.model.orders.SaveOrderInfo;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.CalculatePricesContract;
import com.example.yf.creatorshirt.utils.GsonUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonObserver;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

import static org.greenrobot.eventbus.EventBus.TAG;

/**
 * Created by yangfang on 2018/1/21.
 * 计算衣服价格
 */

public class CalculatePricesPresenter extends RxPresenter<CalculatePricesContract.CalculatePricesView>
        implements CalculatePricesContract.Presenter {

    private DataManager manager;
    private SaveOrderInfo saveOrderInfo;
    private SaveOrderInfo myOrderInfo;
    private List<ClothesSize> sizeList;
    private String discount;
    private double prices;


    @Inject
    CalculatePricesPresenter(DataManager manager) {
        this.manager = manager;
    }

    /**
     * 衣服和尺寸
     *
     * @param mOrderClothesInfo
     * @param clothesSizeList
     * @param discount
     */
    public void setSaveEntity(SaveOrderInfo mOrderClothesInfo, List<ClothesSize> clothesSizeList, String discount) {
        saveOrderInfo = mOrderClothesInfo;
        if (discount == null) {
            saveOrderInfo.setDiscount("");
        }
        this.discount = discount;
        saveOrderInfo.setDiscount(discount);
        sizeList = clothesSizeList;
        saveOrderInfo.setDetailList(clothesSizeList);
        computerOrderPrice();
    }

    public SaveOrderInfo getSaveOrderInfo() {
        return saveOrderInfo;
    }

    /**
     * 计算价格
     */
    private void computerOrderPrice() {
        addSubscribe(manager.getCalculateOrderPrice(UserInfoManager.getInstance().getToken(), GsonUtils.getGson(saveOrderInfo))
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new CommonSubscriber<ClothesPrice>(mView, "访问出错") {
                    @Override
                    public void onNext(ClothesPrice s) {
                        if (s != null) {
                            if (s.getOrderPrice() > s.getDiscountPrice()) {
                                saveOrderInfo.setOrderPrice(s.getDiscountPrice());
                                saveOrderInfo.setDiscount(s.getDiscountcode());
                                prices = s.getDiscountPrice();
                                mView.showPrices(s.getDiscountPrice(), s.getOrderPrice());
                            } else {
                                prices = s.getOrderPrice();
                                saveOrderInfo.setOrderPrice(s.getOrderPrice());
                                saveOrderInfo.setDiscount("");
                                mView.showPrices(s.getDiscountPrice(), s.getOrderPrice());
                            }
                        }
                    }
                }));


//        TestRequestServer.getInstance().getCalculateOrderPrice(UserInfoManager.getInstance().getToken(), GsonUtils.getGson(saveOrderInfo))
//                .enqueue(new Callback<HttpResponse>() {
//                    @Override
//                    public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
//                        Log.e(TAG, "onResponse: "+response.body().toString());
//                    }
//
//                    @Override
//                    public void onFailure(Call<HttpResponse> call, Throwable t) {
//
//                    }
//                });


    }

    /**
     * 造衣服的尺寸
     */
    public void getClothesSize() {
        Observable.create((ObservableOnSubscribe<Map<String, List<ClothesSize>>>) e -> {
            InputStream inputStream = null;
            inputStream = App.getInstance().getAssets().open("clothes_size.json");

            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                arrayOutputStream.write(buffer, 0, len);
            }
            arrayOutputStream.flush();
            arrayOutputStream.close();
            inputStream.close();
            String json = new String(arrayOutputStream.toByteArray());
            e.onNext(getArrayList(json));
        }).compose(RxUtils.rxObScheduleHelper())
                .subscribe(new CommonObserver<Map<String, List<ClothesSize>>>(mView) {
                    @Override
                    public void onNext(Map<String, List<ClothesSize>> list) {
                        if (list != null)
                            ClothesSizeManager.getInstance().saveCache(list);
                        mView.showSizeList(list);
                    }
                });
    }

    private Map<String, List<ClothesSize>> mSizeMap = new HashMap<>();

    private Map<String, List<ClothesSize>> getArrayList(String json) {
        JSONArray jsonArray = JSONArray.parseArray(json);
        for (int i = 0; i < jsonArray.size(); i++) {
            com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) jsonArray.get(i);
            String name = (String) jsonObject.get("name");
            JSONArray jsonArray1 = jsonObject.getJSONArray("type");
            ClothesSize clothesSize;
            List<ClothesSize> mClothesSizesList = new ArrayList<>();
            for (int j = 0; j < jsonArray1.size(); j++) {
                com.alibaba.fastjson.JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                clothesSize = new ClothesSize();
                clothesSize.setValue(jsonObject1.getString("value"));
                clothesSize.setSize(jsonObject1.getString("size"));
                clothesSize.setWeight(jsonObject1.getString("weight"));
                Log.e(TAG, "getArrayList: "+jsonObject1.getString("weight") );
                mClothesSizesList.add(clothesSize);
            }
            putMap(name, mClothesSizesList);
        }
        return mSizeMap;
    }

    private void putMap(String name, List<ClothesSize> mClothesSizesList) {
        if (!mSizeMap.containsKey(name)) {
            mSizeMap.put(name, mClothesSizesList);
        }
    }

    public void updateOrders() {
        if (myOrderInfo == null) {
            return;
        }
        saveOrderInfo.setDetailList(sizeList);
        myOrderInfo.setDetailList(sizeList);
        myOrderInfo.setDiscount(discount == null ? "" : discount);
        myOrderInfo.setOrderPrice(prices);
        myOrderInfo.setPartner(UserInfoManager.getInstance().getLoginResponse().getUserInfo().getMobile());
        addSubscribe(manager.updateOrders(UserInfoManager.getInstance().getToken(), GsonUtils.getGson(myOrderInfo))
                .compose(RxUtils.rxSchedulerHelper())
                .compose(RxUtils.handleResult())
                .subscribeWith(new CommonSubscriber<OrderType>(mView) {
                    @Override
                    public void onNext(OrderType orderType) {
                        if (orderType != null)
                            mView.showPay(orderType);
                    }
                }));

    }

    public void setUpDateOrders(SaveOrderInfo myOrderInfo) {
        this.myOrderInfo = myOrderInfo;
    }
}