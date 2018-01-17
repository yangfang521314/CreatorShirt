package com.example.yf.creatorshirt.mvp.presenter;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.common.manager.UserInfoManager;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.http.TestRequestServer;
import com.example.yf.creatorshirt.mvp.model.AddressBean;
import com.example.yf.creatorshirt.mvp.model.PayOrderEntity;
import com.example.yf.creatorshirt.mvp.model.orders.SaveOrderInfo;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.MyOrderContract;
import com.example.yf.creatorshirt.pay.alipay.Alipay;
import com.example.yf.creatorshirt.pay.weixin.WXPay;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.GsonUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.utils.SharedPreferencesUtil;
import com.example.yf.creatorshirt.utils.ToastUtil;
import com.example.yf.creatorshirt.utils.Utils;
import com.example.yf.creatorshirt.widget.CommonSubscriber;
import com.google.gson.Gson;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yangfang on 2017/91.
 */

public class MyOrderPresenter extends RxPresenter<MyOrderContract.MyOrderView> implements MyOrderContract.Presenter {

    private DataManager manager;
    private String orderId;
    private UploadManager uploadManager = new UploadManager();
    private String userToken;
    private String QiniuToken;
    private AsyncTask<String, Integer, Void> asyncTask;
    private static final int WHAT_SUCCESS = 1;
    private static final int WHAT_SUCCESS2 = 2;
    private String mBack;
    private String UserId;

    private Map<String, String> map = new HashMap<>();
    private List<String> pictureList = new ArrayList<>();
    private List<String> totalNum = new ArrayList<>();
    private SaveOrderInfo saveOrderInfo;

    @Inject
    public MyOrderPresenter(DataManager manager) {
        this.manager = manager;
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == WHAT_SUCCESS) {
                getList("B", mBack);
            }
            if (msg.what == WHAT_SUCCESS2) {
                ToastUtil.cancel();
                if (asyncTask != null) {
                    asyncTask.cancel(true);
                    asyncTask = null;
                }
                saveOrderInfo();
            }
        }
    };

    /**
     * 保存信息上传到服务器
     */
    private void saveOrderInfo() {
        saveOrderInfo.setFinishBimage(map.get("B"));
        saveOrderInfo.setFinishAimage(map.get("A"));
        saveOrderInfo.setZipcode("");
        saveOrderInfo.setAddress(mView.getReceiverAddress());
        saveOrderInfo.setMobile(mView.getReceiverMobile());
        saveOrderInfo.setText("aaaaa");
        saveOrderInfo.setPicture1(map.get("A"));
        saveOrderInfo.setPicture2(map.get("B"));
        saveOrderInfo.setPayorderid("");
        saveOrderInfo.setMaskAName("pattern_1");
        saveOrderInfo.setMaskBName("pattern_2");
        if (totalNum.size() > 1) {
            saveOrderInfo.setPicture1(totalNum.get(0));
            saveOrderInfo.setPicture2(totalNum.get(1));
        } else if (totalNum.size() == 1) {
            saveOrderInfo.setPicture1(totalNum.get(0));
        }


        Log.e("MyOrder", "dddd" + saveOrderInfo.toString());
//        addSubscribe(manager.saveOrderData(userToken, GsonUtils.getGson(saveOrderInfo))
//                .compose(RxUtils.<HttpResponse<OrderType>>rxSchedulerHelper())
//                .compose(RxUtils.<OrderType>handleResult())
//                .subscribeWith(new CommonSubscriber<OrderType>(mView) {
//                    @Override
//                    public void onNext(OrderType orderType) {
//
//                    }
//                }));
        TestRequestServer.getInstance().saveOrderData(userToken, GsonUtils.getGson(saveOrderInfo)).enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                Log.e("MyOrder", "response" + response.toString());
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {

            }
        });
    }

    /**
     * 获取七牛token
     */
    public void getToken() {
        userToken = UserInfoManager.getInstance().getToken();
        Log.e("UserToken", "UserToken" + userToken);
        if (userToken == null) {
            return;
        }
        addSubscribe(manager.getQiToken(userToken)
                        .compose(RxUtils.<HttpResponse<String>>rxSchedulerHelper())
                        .compose(RxUtils.<String>handleResult())
                        .subscribeWith(new CommonSubscriber<String>(mView) {
                            @Override
                            public void onNext(String s) {
                                QiniuToken = s;
//                        mView.showTokenSuccess(s);
                            }
                        })
        );
    }

    /**
     * 保存图片
     *
     * @param key
     * @param value
     */
    @SuppressLint("StaticFieldLeak")
    public void requestSave(final String key, final String value) {

        asyncTask = new AsyncTask<String, Integer, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                getList(key, value);
                return null;
            }

            @Override
            protected void onPreExecute() {
                ToastUtil.showProgressToast(App.getInstance(), "正在生成订单", R.drawable.progress_icon);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
            }
        };
        asyncTask.execute();

    }


    private void getList(final String kewWord, final String imageUrl) {
        if (TextUtils.isEmpty(QiniuToken)) {
            Log.e("TAG", "没有取到token");
            return;
        }
        UserId = UserInfoManager.getInstance().getLoginResponse().getUserInfo().getUserid() + "_";
        String key = UserId + Utils.getTime() + kewWord;
        uploadManager.put(imageUrl, key, QiniuToken, new UpCompletionHandler() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {

                if (info.isOK()) {
                    Log.e("qiniu_clothes", "UPLOAD SUCCESS" + key + ":" + info + ":" + response);
                    if (kewWord.equals("A")) {
                        try {
                            String imageFrontUrl = Constants.ImageUrl + response.get("key");
                            map.put("A", imageFrontUrl);
                            handler.sendEmptyMessage(WHAT_SUCCESS);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (kewWord.equals("B")) {
                        try {
                            String imageBackUrl = Constants.ImageUrl + response.get("key");
                            map.put("B", imageBackUrl);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (map.size() == 2) {
                        handler.sendEmptyMessage(WHAT_SUCCESS2);
                    }

                } else {
                    Log.e("qiniu_clothes", "UPLOAD fail");
                    mView.showErrorMsg("保存图片失败");
                    if (asyncTask != null) {
                        asyncTask.cancel(true);
                        asyncTask = null;
                    }
                }

            }
        }, null);
    }

    /**
     * 保存自定义图片
     *
     * @param avatarList
     */
    public void saveAvatar(List<String> avatarList) {
        pictureList = avatarList;
        getAvatarList(avatarList.get(0), 0);
    }

    private void getAvatarList(final String s, final int i) {
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull final FlowableEmitter<String> e) throws Exception {
                String kewWord = "pattern";
                if (TextUtils.isEmpty(QiniuToken)) {
                    Log.e("qiniu_avatar", "没有取到token");
                    return;
                }
                UserId = UserInfoManager.getInstance().getLoginResponse().getUserInfo().getUserid() + "_";
                String key = UserId + Utils.getTime() + kewWord + i;
                uploadManager.put(s, key, QiniuToken, new UpCompletionHandler() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {

                        if (info.isOK()) {
                            try {
                                String url = Constants.ImageUrl + response.get("key");
                                e.onNext(url);
                                Log.e("qiniu_avatar", "avatar_url:" + url);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e("qiniu_avatar", "UPLOAD fail" + key + ":" + info + ":" + response);
                            mView.showErrorMsg("保存图片失败");
                        }

                    }
                }, null);
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CommonSubscriber<String>(mView) {
                    @Override
                    public void onNext(String s) {
                        totalNum.add(s);
                        if (pictureList.size() >= 2) {
                            if (totalNum.size() < pictureList.size()) {
                                getAvatarList(pictureList.get(totalNum.size()), totalNum.size());
                            }

                        }
                    }
                });
    }

    //微信支付
    public void payForWeiChat(PayOrderEntity value) {
        Log.e("TAG", "VALUE" + value);
        String wx_appid = value.getAppId();
        WXPay.init(App.getInstance(), wx_appid);
        //要在支付前调用
        WXPay.getInstance().doPay(value, new WXPay.WXPayResultCallBack() {
            @Override
            public void onSuccess() {
                mView.showErrorMsg("支付成功");
            }

            @Override
            public void onError(int error_code) {
                switch (error_code) {
                    case WXPay.NO_OR_LOW_WX:
                        mView.showErrorMsg("未安装微信或微信版本过低");
                        break;

                    case WXPay.ERROR_PAY_PARAM:
                        mView.showErrorMsg("参数错误");
                        break;

                    case WXPay.ERROR_PAY:
                        mView.showErrorMsg("支付失败");
                        break;
                }
            }

            @Override
            public void onCancel() {
                mView.showErrorMsg("支付取消");
            }
        });
    }


    public void getAddressData() {
        addSubscribe(manager.getAddressData(SharedPreferencesUtil.getUserToken())
                .compose(RxUtils.<HttpResponse<List<AddressBean>>>rxSchedulerHelper())
                .compose(RxUtils.<List<AddressBean>>handleResult())
                .subscribeWith(new CommonSubscriber<List<AddressBean>>(mView, "请求地址错误") {
                    @Override
                    public void onNext(List<AddressBean> addressBeen) {
                        if (addressBeen == null) {
                            mView.showErrorMsg("地址为空");
                            return;
                        }
                        if (addressBeen.size() == 0)
                            return;

                        mView.showAddressSuccess(addressBeen);
                    }
                })
        );
    }

    /**
     * 支付宝支付
     *
     * @param value
     */
    public void aplipay(PayOrderEntity value) {
        String alipay_appid = value.toString();
        doAlipay(alipay_appid);
    }

    /**
     * 支付宝支付
     *
     * @param pay_param 支付服务生成的支付参数
     */
    private void doAlipay(String pay_param) {
        new Alipay(App.getInstance(), pay_param, new Alipay.AlipayResultCallBack() {
            @Override
            public void onSuccess() {
                ToastUtil.showToast(App.getInstance(), "支付成功", Toast.LENGTH_SHORT);
            }

            @Override
            public void onDealing() {
                ToastUtil.showToast(App.getInstance(), "支付处理中...", Toast.LENGTH_SHORT);
            }

            @Override
            public void onError(int error_code) {
                switch (error_code) {
                    case Alipay.ERROR_RESULT:
                        ToastUtil.showToast(App.getInstance(), "支付失败:支付结果解析错误", Toast.LENGTH_SHORT);
                        break;

                    case Alipay.ERROR_NETWORK:
                        ToastUtil.showToast(App.getInstance(), "支付失败:网络连接错误", Toast.LENGTH_SHORT);
                        break;

                    case Alipay.ERROR_PAY:
                        ToastUtil.showToast(App.getInstance(), "支付错误:支付码支付失败", Toast.LENGTH_SHORT);
                        break;

                    default:
                        ToastUtil.showToast(App.getInstance(), "支付错误", Toast.LENGTH_SHORT);
                        break;
                }

            }

            @Override
            public void onCancel() {
                ToastUtil.showToast(App.getInstance(), "支付取消", Toast.LENGTH_SHORT);
            }
        }).doPay();
    }


    /**
     * 选择支付获取到支付订单的详细信息
     *
     * @param id
     * @param address
     * @param zipcode
     * @param payType
     * @param fee
     */
    public void payMomentOrders(String id, String address, String zipcode, String payType, double fee) {
        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("address", address);
        map.put("zipCode", zipcode);
        map.put("payType", payType);
        map.put("fee", String.valueOf(fee));
        Gson gson = new Gson();
        String payEntity = gson.toJson(map);
        final RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"), payEntity);
//        addSubscribe(manager.payMentOrders(SharedPreferencesUtil.getUserToken(), requestBody)
//                .compose(RxUtils.<HttpResponse<PayOrderEntity>>rxSchedulerHelper())
//                .compose(RxUtils.<PayOrderEntity>handleResult())
//                .subscribeWith(new CommonSubscriber<PayOrderEntity>(mView) {
//                    @Override
//                    public void onNext(PayOrderEntity payOrderEntity) {
//                        if (payOrderEntity == null) {
//                            mView.showErrorMsg("数据为空");
//                            return;
//                        }
//                        mView.showPayOrder(payOrderEntity);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        super.onError(e);
//                        mView.showErrorMsg(e.getMessage());
//                    }
//                })
//
//        );
        TestRequestServer.getInstance().payMentOrders(UserInfoManager.getInstance().getToken(), requestBody)
                .enqueue(new Callback<HttpResponse>() {
                    @Override
                    public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                        if (response.isSuccessful()) {
                            mView.showErrorMsg(response.body().getResult().toString());
//                            mView.showPayOrder(null);
                        }
                        //// TODO: 2017/9/29 支付暂无
                    }

                    @Override
                    public void onFailure(Call<HttpResponse> call, Throwable t) {
                        mView.showErrorMsg(App.getInstance().getString(R.string.no_net));
                    }
                });
    }

    /**
     * 背面图片
     *
     * @param finishBimage
     */
    public void setBackUrl(String finishBimage) {
        mBack = finishBimage;
    }

    public void setSaveEntity(SaveOrderInfo mSaveStyleEntity) {
        saveOrderInfo = mSaveStyleEntity;
    }
}
