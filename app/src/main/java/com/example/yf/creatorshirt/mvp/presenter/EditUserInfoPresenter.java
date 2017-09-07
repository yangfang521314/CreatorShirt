package com.example.yf.creatorshirt.mvp.presenter;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.EditUserInfoContract;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.GsonUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.example.yf.creatorshirt.utils.Utils;
import com.example.yf.creatorshirt.utils.WeakReferenceHandler;
import com.example.yf.creatorshirt.widget.CommonSubscriber;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by yangfang on 2017/9/4.
 */

public class EditUserInfoPresenter extends RxPresenter<EditUserInfoContract.EditUserInfoView> implements EditUserInfoContract.Presenter {
    private static final int IMAGE_SUCCESS = 1;
    private DataManager manager;
    private File file;
    private String QiniuToken;
    private UploadManager uploadManager = new UploadManager();
    private String userAvatar;
    private String mUserName;

    private Handler mHandler;

    @Inject
    public EditUserInfoPresenter(DataManager manager) {
        this.manager = manager;
    }

    private class EditUserHandler extends WeakReferenceHandler<EditUserInfoPresenter> {

        public EditUserHandler(EditUserInfoPresenter reference) {
            super(reference);
        }

        @Override
        protected void handleMessage(EditUserInfoPresenter reference, Message msg) {
            if (msg.what == IMAGE_SUCCESS) {
                mView.showSuccessImage(userAvatar);
            }
        }
    }

    @Override
    public void attachView(EditUserInfoContract.EditUserInfoView view) {
        super.attachView(view);
        mHandler = new EditUserHandler(this);
    }

    @Override
    public void saveUserInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", SharedPreferencesUtil.getUserPhone());
        map.put("headimage", userAvatar);
        map.put("name", mUserName);
        addSubscribe(manager.saveUserInfo(SharedPreferencesUtil.getUserToken(), GsonUtils.getGson(map))
                .compose(RxUtils.<HttpResponse>rxSchedulerHelper())
                .map(new Function<HttpResponse, Integer>() {
                    @Override
                    public Integer apply(@NonNull HttpResponse httpResponse) throws Exception {
                        Log.e("TAG","RESPONSE"+httpResponse.getStatus());
                        return httpResponse.getStatus();
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer status) throws Exception {
                        mView.showSuccessSaveInfo(status);
                    }
                }));


    }

    /**
     * 保存头像到七牛
     */
    public void saveUserAvatar() {
        String key = "avatar_" + SharedPreferencesUtil.getUserId() + Utils.getTime();
        Log.e("TAG","EditUSER"+QiniuToken);
        uploadManager.put(file, key, QiniuToken, new UpCompletionHandler() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {

                if (info.isOK()) {
                    Log.e("qiniu_back", "UPLOAD SUCCESS" + key + ":" + info + ":" + response);
                    try {
                        userAvatar = Constants.ImageUrl + response.get("key");
                        mHandler.sendEmptyMessage(IMAGE_SUCCESS);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.e("qiniu_back", "UPLOAD fail");
                    mView.showErrorMsg("保存图片失败");
                }

            }
        }, null);
    }


    @Override
    public void setUserName(String userName) {
        mUserName = userName;
    }

    //image文件
    @Override
    public void setImageFile(File cover) {
        file = cover;
    }

    /**
     * 获取七牛token
     */
    public void getToken() {
        addSubscribe(manager.getQiToken(SharedPreferencesUtil.getUserToken())
                .compose(RxUtils.<HttpResponse<String>>rxSchedulerHelper())
                .compose(RxUtils.<String>handleResult())
                .subscribeWith(new CommonSubscriber<String>(mView,"没有TOKEN") {
                    @Override
                    public void onNext(String s) {
                        QiniuToken = s;
                    }
                })
        );
    }
}
