package com.example.yf.creatorshirt.mvp.model;

import android.text.TextUtils;

import com.example.yf.creatorshirt.mvp.model.detaildesign.CommonStyleData;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yangfang on 2017/8/28.
 */

public class OrderData {
    private JSONObject jsonObject = new JSONObject();
    private JSONObject styleContext = new JSONObject();
    private JSONObject backObject = new JSONObject();
    private JSONObject frontObject = new JSONObject();

    //处理背面数据，形成json上传
    public void setBackData(CommonStyleData mBackData) {
        try {
            if (!TextUtils.isEmpty(mBackData.getArmUrl())) {
                backObject.put("arm", mBackData.getArmUrl());
            }
            if (!TextUtils.isEmpty(mBackData.getNeckUrl())) {
                backObject.put("neck", mBackData.getNeckUrl());
            }
            if (!TextUtils.isEmpty(mBackData.getOrnametUrl())) {
                backObject.put("ornament", mBackData.getOrnametUrl());

            }
            if (mBackData.getColor() != null) {
                backObject.put("color", mBackData.getColor());
            }
            if (!TextUtils.isEmpty(mBackData.getPattern())) {
                backObject.put("pattern", mBackData.getPattern());
            }
            styleContext.put("A", backObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //处理正面数据，
    public void setFrontData(CommonStyleData mFrontData) {
        try {
            if (!TextUtils.isEmpty(mFrontData.getArmUrl())) {
                frontObject.put("arm", mFrontData.getArmUrl());
            }
            if (!TextUtils.isEmpty(mFrontData.getNeckUrl())) {
                frontObject.put("neck", mFrontData.getNeckUrl());
            }
            if (!TextUtils.isEmpty(mFrontData.getOrnametUrl())) {
                frontObject.put("ornament", mFrontData.getOrnametUrl());

            }
            if (mFrontData.getColor() != null) {
                frontObject.put("color", mFrontData.getColor());
            }
            if (!TextUtils.isEmpty(mFrontData.getPattern())) {
                frontObject.put("pattern", mFrontData.getPattern());
            }
            styleContext.put("B", frontObject);
            jsonObject.put("styleContext", styleContext);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getJsonObject() {
        String json = null;
        try {
            json = jsonObject.getJSONObject("styleContext").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }
}
