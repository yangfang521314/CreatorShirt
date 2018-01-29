package com.example.yf.creatorshirt.mvp.presenter;

import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.http.TestRequestServer;
import com.example.yf.creatorshirt.mvp.model.orders.ClothesSize;
import com.example.yf.creatorshirt.mvp.model.orders.SaveOrderInfo;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.CalculatePricesContract;
import com.example.yf.creatorshirt.utils.GsonUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonObserver;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yangfang on 2018/1/21.
 * 计算衣服价格
 */

public class CalculatePricesPresenter extends RxPresenter<CalculatePricesContract.CalculatePricesView>
        implements CalculatePricesContract.Presenter {

    private DataManager manager;
    private SaveOrderInfo saveOrderInfo;

    @Inject
    CalculatePricesPresenter(DataManager manager) {
        this.manager = manager;
    }

    /**
     * 衣服和尺寸
     *
     * @param mOrderClothesInfo
     * @param discount
     */
    public void setSaveEntity(SaveOrderInfo mOrderClothesInfo, ArrayList<ClothesSize> clothesSizeList, String discount) {
        Log.e("CalculatePrices",mOrderClothesInfo.toString()+"::"+clothesSizeList.toString()+"::"+discount);
        saveOrderInfo = new SaveOrderInfo();
        if (discount == null) {
            saveOrderInfo.setDiscount("");
        }
        saveOrderInfo.setDiscount(discount);
        saveOrderInfo.setText("");
        saveOrderInfo.setPicture1(mOrderClothesInfo.getPicture1());
        saveOrderInfo.setPicture2(mOrderClothesInfo.getPicture2());
        saveOrderInfo.setDetailList(clothesSizeList);
        computerOrderPrice();
    }

    /**
     * 计算价格
     */
    public void computerOrderPrice() {
//        addSubscribe(manager.getCalculateOrderPrice(GsonUtils.getGson(saveOrderInfo))
//                .compose(RxUtils.<HttpResponse<ClothesPrice>>rxSchedulerHelper())
//                .compose(RxUtils.<ClothesPrice>handleResult())
//                .subscribeWith(new CommonSubscriber<ClothesPrice>(mView) {
//                    @Override
//                    public void onNext(ClothesPrice s) {
//                        if (s != null) {
//                            mView.showPrices(s);
//                            Log.e("OrderInfo", "sss" + s.getOrderPrice());
//                        }
//                    }
//                }));
        TestRequestServer.getInstance().getCalculateOrderPrice(GsonUtils.getGson(saveOrderInfo))
                .enqueue(new Callback<HttpResponse>() {
                    @Override
                    public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                        Log.e(":ta","cfuclck "+response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<HttpResponse> call, Throwable t) {
                        Log.e(":ta","cccc"+t.getMessage());
                    }
                });

    }

    /**
     * 造衣服的尺寸
     */
    public void getClothesSize() {
        Observable.create(new ObservableOnSubscribe<Map<String, List<ClothesSize>>>() {
            @Override
            public void subscribe(ObservableEmitter<Map<String, List<ClothesSize>>> e) throws Exception {
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
            }
        }).compose(RxUtils.<Map<String, List<ClothesSize>>>rxObScheduleHelper())
                .subscribe(new CommonObserver<Map<String, List<ClothesSize>>>(mView) {
                    @Override
                    public void onNext(Map<String, List<ClothesSize>> list) {
                        if (list != null)
                            mView.showSizeList(list);
                    }
                });
    }

    private List<ClothesSize> mClothesSizesList;
    private Map<String, List<ClothesSize>> mSizeMap = new HashMap<>();

    private Map<String, List<ClothesSize>> getArrayList(String json) {
        JSONArray jsonArray = JSONArray.parseArray(json);
        for (int i = 0; i < jsonArray.size(); i++) {
            com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) jsonArray.get(i);
            String name = (String) jsonObject.get("name");
            JSONArray jsonArray1 = jsonObject.getJSONArray("type");
            ClothesSize clothesSize;
            mClothesSizesList = new ArrayList<>();
            for (int j = 0; j < jsonArray1.size(); j++) {
                com.alibaba.fastjson.JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                clothesSize = new ClothesSize();
                clothesSize.setSize(jsonObject1.getString("value"));
                clothesSize.setLetter(jsonObject1.getString("size"));
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
}
