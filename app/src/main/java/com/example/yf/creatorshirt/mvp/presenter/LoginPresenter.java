package com.example.yf.creatorshirt.mvp.presenter;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.ArrayMap;
import android.util.Log;

import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.model.LoginBean;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.LoginContract;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.example.yf.creatorshirt.widget.CommonSubscriber;
import com.google.gson.Gson;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import okhttp3.RequestBody;

/**
 * Created by yang on 12/06/2017.
 * 微信登录和手机登录方法类
 */

public class LoginPresenter extends RxPresenter<LoginContract.LoginView> implements LoginContract.Presenter {
    private DataManager mDataManager;
    private String phone;
    private String password;

    @Inject
    public LoginPresenter(DataManager manager) {
        this.mDataManager = manager;
    }

    @Override
    public void setPhoneNumber(String phoneNumber) {
        phone = phoneNumber;
    }

    @Override
    public void setPhoneCode(String code) {
        this.password = password;
    }

    /**
     * 手机号码登录
     *
     * @param phone
     * @param password
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void phoneLogin(String phone, String password) {
        if (password != null && phone != null) {
            ArrayMap<String, String> map = new ArrayMap<>();
            map.put("mobile", phone);
            map.put("password", password);
            Gson gson = new Gson();
            String requestEntity = gson.toJson(map);
            Log.e("TAG", "phone" + requestEntity);
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), requestEntity);
            addSubscribe(mDataManager.login(body)
                    .compose(RxUtils.<HttpResponse<LoginBean>>rxSchedulerHelper())
                    .compose(RxUtils.<LoginBean>handleResult())
                    .subscribeWith(new CommonSubscriber<LoginBean>(mView) {

                        @Override
                        public void onNext(LoginBean loginBean) {
                            SharedPreferencesUtil.saveUserId(loginBean.getUserInfo().getUserid());
                            SharedPreferencesUtil.saveUserToken(loginBean.getToken());
                            SharedPreferencesUtil.saveUserPhone(loginBean.getUserInfo().getMobile());
                            mView.LoginSuccess(loginBean);
                        }
                    }));
        }
    }

    @Override
    public void getVerifyCode() {
        if (phone == null)
            return;
        Gson gson = new Gson();
        HashMap<String, String> paramsMap = new HashMap<>();
        paramsMap.put("mobile", phone);
        String strEntity = gson.toJson(paramsMap);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), strEntity);
        addSubscribe(mDataManager.getVerifyCode(body)
                .compose(RxUtils.<HttpResponse<String>>rxSchedulerHelper())
                .compose(RxUtils.<String>handleResult())
                .subscribeWith(new CommonSubscriber<String>(mView) {
                    @Override
                    public void onNext(@NonNull String s) {
                        Log.e("TAG", "请求成功");
                    }
                }));

    }

    /**
     * 微信登录
     */
    @Override
    public void wenxinLogin() {

    }
}
