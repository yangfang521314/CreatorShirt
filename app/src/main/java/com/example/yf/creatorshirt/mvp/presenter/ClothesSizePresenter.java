package com.example.yf.creatorshirt.mvp.presenter;

import com.alibaba.fastjson.JSONArray;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.model.orders.ClothesSize;
import com.example.yf.creatorshirt.mvp.presenter.base.BasePresenter;
import com.example.yf.creatorshirt.mvp.view.BaseView;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonObserver;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by yangfang on 2018/1/13.
 */

public class ClothesSizePresenter implements BasePresenter {
    private OrderSizeView mView;

    @Override
    public void attachView(BaseView view) {
        mView = (OrderSizeView) view;
    }

    @Override
    public void detachView(BaseView view) {
        if (mView != null) {
            mView = null;
        }
    }

    public interface OrderSizeView extends BaseView {
        void showSizeList(Map<String, List<ClothesSize>> list);
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
