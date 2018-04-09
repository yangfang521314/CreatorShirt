package com.example.yf.creatorshirt.mvp.presenter;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.util.Log;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.common.manager.UserInfoManager;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.HttpResponse;
import com.example.yf.creatorshirt.mvp.model.orders.OrderType;
import com.example.yf.creatorshirt.mvp.model.orders.SaveOrderInfo;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.OrderInfoContract;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.GsonUtils;
import com.example.yf.creatorshirt.utils.LogUtil;
import com.example.yf.creatorshirt.utils.NetworkUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.utils.ToastUtil;
import com.example.yf.creatorshirt.utils.Utils;
import com.example.yf.creatorshirt.widget.CommonSubscriber;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import static com.umeng.socialize.utils.Log.TAG;

/**
 * Created by yangfang on 2017/8/28.
 * 保存信息到服务器
 */

public class OrderInfoPresenter extends RxPresenter<OrderInfoContract.OrderInfoView> implements
        OrderInfoContract.Presenter {

    private static final int WHAT_SUCCESS = 1;
    private DataManager manager;
    private String QiniuToken;
    private UploadManager uploadManager = new UploadManager();
    private String userToken;
    private SimpleArrayMap<String, String> mapList = new SimpleArrayMap<>();//上传服务器的形成的url集合
    private SimpleArrayMap<String, String> mapTotal = new SimpleArrayMap<>();//本地图片的url集合
    private SaveOrderInfo saveOrderInfo;
    private ExecutorService executor = Executors.newCachedThreadPool();


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == WHAT_SUCCESS) {
                ToastUtil.cancel();
                saveOrderInfo(mapList);
            }
        }
    };

    @Inject
    OrderInfoPresenter(DataManager manager) {
        this.manager = manager;
    }

    private void getList(final String kewWord, final String imageUrl) {

        if (TextUtils.isEmpty(QiniuToken)) {
            LogUtil.e("TAG", "没有取到token");
            return;
        }
        if (imageUrl == null) {
            return;
        }
        String userId = UserInfoManager.getInstance().getLoginResponse().getUserInfo().getUserid() + "_";
        String key = userId + Utils.getTime() + kewWord;
        uploadManager.put(imageUrl, key, QiniuToken, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                String url;
                if (info.isOK()) {

                    try {
                        url = Constants.ImageUrl + response.get("key");
                        LogUtil.e("qiniu_clothes", "UPLOAD SUCCESS:" + url);
                        mapList.put(kewWord, url);
                        if (mapTotal.size() == 2) {
                            if (mapList != null && mapList.size() == 2) {
                                handler.sendEmptyMessage(WHAT_SUCCESS);
                            }
                        }
                        if (mapTotal.size() == 3) {
                            if (mapList != null && mapList.size() == 3) {
                                handler.sendEmptyMessage(WHAT_SUCCESS);
                            }
                        }
                        if (mapTotal.size() == 4) {
                            if (mapList != null && mapList.size() == 4) {
                                handler.sendEmptyMessage(WHAT_SUCCESS);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    LogUtil.e("qiniu_clothes", "UPLOAD fail");
                    mView.showErrorMsg("保存图片失败");
                    ToastUtil.cancel();
                }

            }
        }, null);
    }


    /**
     * 获取七牛token
     */
    public void getToken() {
        userToken = UserInfoManager.getInstance().getToken();
        LogUtil.e("UserToken", "UserToken" + userToken);
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
                    }
                })
        );
    }

    /**
     * 保存信息上传到服务器
     *
     * @param map
     */
    private void saveOrderInfo(SimpleArrayMap<String, String> map) {
        if (map != null && map.size() != 0) {
            saveOrderInfo.setFinishBimage(map.get("B"));
            saveOrderInfo.setFinishAimage(map.get("A"));
            if (map.containsKey("0")) {
                saveOrderInfo.setPicture1(map.get("0"));
            } else {
                saveOrderInfo.setPicture1("");
            }
            if (map.containsKey("1")) {
                saveOrderInfo.setPicture2(map.get("1"));
            } else {
                saveOrderInfo.setPicture2("");
            }
        }
        addSubscribe(manager.saveOrderData(userToken, GsonUtils.getGson(saveOrderInfo))
                .compose(RxUtils.<HttpResponse<OrderType>>rxSchedulerHelper())
                .compose(RxUtils.<OrderType>handleResult())
                .subscribeWith(new CommonSubscriber<OrderType>(mView, "保存失败，请检查网络") {
                    @Override
                    public void onNext(OrderType orderType) {
                        if (orderType != null) {
                            LogUtil.e("OrderInfo", "orderId" + orderType.getOrderId());
                            mView.showOrderId(orderType, saveOrderInfo);
                        }
                    }

                }));

    }

    /**
     * 保存图片
     *
     * @param frontUrl
     * @param backUrl
     * @param arrayList
     */
    @SuppressLint("StaticFieldLeak")
    public void requestSave(final String frontUrl, final String backUrl, final SimpleArrayMap<String, String> arrayList) {
        if (!NetworkUtils.isNetWorkConnected()) {
            mView.showErrorMsg(App.getInstance().getString(R.string.no_upload_net));
            return;
        }
        mapTotal.put("A", frontUrl);
        mapTotal.put("B", backUrl);
        SaveWork work1 = null;
        SaveWork work2 = null;
        if (arrayList != null) {
            if (arrayList.size() != 0) {
                if (arrayList.containsKey("0")) {
                    mapTotal.put("0", arrayList.get("0"));
                    if (arrayList.get("0").contains("pattern")) {
                        Log.e(TAG, "requestSave: " + Constants.ImageUrl + arrayList.get(0) + ".png");
                        mapList.put("0", Constants.ImageUrl + arrayList.get(0) + ".png");
                    } else {
                        work1 = new SaveWork("0", arrayList.get("0"));
                    }
                }
                if (arrayList.containsKey("1")) {
                    mapTotal.put("1", arrayList.get("1"));
                    if (arrayList.get("1").contains("pattern")) {
                        mapList.put("1", Constants.ImageUrl + arrayList.get("1") + ".png");
                    } else {
                        work2 = new SaveWork("1", arrayList.get("1"));
                    }


                }
            }
        }

        SaveWork work3 = new SaveWork("A", frontUrl);
        SaveWork work4 = new SaveWork("B", backUrl);
        ToastUtil.showProgressToast(App.getInstance(), "正在生成订单", R.drawable.progress_icon);
        executor.execute(work3);
        executor.execute(work4);
        if (work1 != null) {
            executor.execute(work1);
        }
        if (work2 != null) {
            executor.execute(work2);
        }
        executor.shutdown();
        if (executor.isTerminated()) {
            ToastUtil.cancel();
        }
    }


    public void setSaveEntity(SaveOrderInfo saveStyleEntity) {
        saveOrderInfo = saveStyleEntity;
    }

    private class SaveWork implements Runnable {
        private String key;

        private String avatar;

        SaveWork(String s, String s1) {
            key = s;
            avatar = s1;
        }

        @Override
        public void run() {
            getList(key, avatar);
        }
    }

}
