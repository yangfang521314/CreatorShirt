package com.example.yf.creatorshirt.mvp.presenter;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.dd.plist.NSArray;
import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListFormatException;
import com.dd.plist.PropertyListParser;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.mvp.model.VersionStyle;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.ClothesContract;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.xml.parsers.ParserConfigurationException;

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
        try {
            NSDictionary ary = (NSDictionary) PropertyListParser.parse(App.getInstance().getAssets().open("version_1.plist"));
            VerName = ary.allKeys();//衣服类型
//            NSDictionary dictionary = (NSDictionary) ary.objectForKey(VerName[0]);
//            String pp  = dictionary.objectForKey("name").toJavaObject().toString();
            for (int i = 0; i < VerName.length; i++) {

                NSDictionary dictionary = (NSDictionary) ary.objectForKey(VerName[i]);
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
                        style.setType(VerName[i]);
                        style.setColorName(colorName);
                        Log.e("atg","d"+type);
                        style.setGender(type);
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
                        style.setType(VerName[i]);
                        style.setColorName(colorName);
                        Log.e("atg","d"+type);
                        style.setGender(type);
                        mVersionStyle.add(style);

                    }
                    totalWomanMap.put(name, mVersionStyle);
                }
            }
            mView.showTotalClothes(totalManMap, totalWomanMap, mListVerName);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (PropertyListFormatException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

    }

}
