package com.example.yf.creatorshirt.mvp.presenter;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListParser;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.model.ClothesStyleBean;
import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.ClothesContract;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonObserver;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by yangfang on 2017/11/30.
 * 获取所有衣服的名称
 */

public class ClothesPresenter extends RxPresenter<ClothesContract.ClothesView> implements ClothesContract.Presenter {
    private String[] VerName;
    private List<VersionStyle> mVersionStyle;
    private List<String> mListVerName = new ArrayList<>();
    private ArrayMap<String, List<VersionStyle>> totalManMap = new ArrayMap<>();
    private ArrayMap<String, List<VersionStyle>> totalWomanMap = new ArrayMap<>();

    @Inject
    public ClothesPresenter() {

    }


    /**
     * 获取衣服的名称
     */
    @Override
    public void getClothesVersion() {
        Observable.create(new ObservableOnSubscribe<ClothesStyleBean>() {
            @Override
            public void subscribe(ObservableEmitter<ClothesStyleBean> e) throws Exception {
                NSDictionary ary = (NSDictionary) PropertyListParser.parse(App.getInstance().getAssets().open("version_1.plist"));
                VerName = ary.allKeys();//衣服类型
                for (String aVerName : VerName) {

                    NSDictionary dictionary = (NSDictionary) ary.objectForKey(aVerName);
                    String name = dictionary.objectForKey("name").toJavaObject().toString();
                    mListVerName.add(name);
                    if (dictionary.containsKey("M")) {
                        mVersionStyle = new ArrayList<>();
                        NSArray nsArray = (NSArray) dictionary.objectForKey("M");
                        for (int j = 0; j < nsArray.count(); j++) {
                            NSDictionary dictionaryDic = (NSDictionary) nsArray.objectAtIndex(j);
                            String type = dictionaryDic.objectForKey("type").toJavaObject().toString();
                            String color = dictionaryDic.objectForKey("colorhex").toJavaObject().toString();
                            String colorName = dictionaryDic.objectForKey("color").toJavaObject().toString();
                            VersionStyle style = new VersionStyle();
                            style.setColor(color);
                            style.setType(aVerName);
                            style.setColorName(colorName);
                            style.setGender(type);
                            style.setSex("m");
                            style.setClothesType(name);
                            mVersionStyle.add(style);
                        }
                        totalManMap.put(name, mVersionStyle);
                    }

                    if (dictionary.containsKey("W")) {
                        mVersionStyle = new ArrayList<>();
                        NSArray nsArray = (NSArray) dictionary.objectForKey("W");
                        for (int j = 0; j < nsArray.count(); j++) {
                            NSDictionary dictionaryDic = (NSDictionary) nsArray.objectAtIndex(j);
                            String type = dictionaryDic.objectForKey("type").toJavaObject().toString();
                            String color = dictionaryDic.objectForKey("colorhex").toJavaObject().toString();
                            String colorName = dictionaryDic.objectForKey("color").toJavaObject().toString();
                            VersionStyle style = new VersionStyle();
                            style.setColor(color);
                            style.setType(aVerName);
                            style.setColorName(colorName);
                            style.setGender(type);
                            style.setSex("w");
                            style.setClothesType(name);
                            mVersionStyle.add(style);

                        }
                        totalWomanMap.put(name, mVersionStyle);
                    }
                }
                ClothesStyleBean clothesStyleBean = new ClothesStyleBean();
                clothesStyleBean.setmListVerName(mListVerName);
                clothesStyleBean.setTotalManMap(totalManMap);
                clothesStyleBean.setTotalWomanMap(totalWomanMap);
                e.onNext(clothesStyleBean);

            }
        }).compose(RxUtils.<ClothesStyleBean>rxObScheduleHelper())
                .compose(RxUtils.<ClothesStyleBean>rxObScheduleHelper())
                .subscribeWith(new CommonObserver<ClothesStyleBean>(null) {
                    @Override
                    public void onNext(ClothesStyleBean clothesStyleBean) {
                        if (clothesStyleBean != null) {
                            mView.showTotalClothes(clothesStyleBean);
                            Log.e("R", "clo" + clothesStyleBean.toString());
                        }
                    }
                });

    }


}
