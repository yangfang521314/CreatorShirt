package com.example.yf.creatorshirt.mvp.presenter;

import android.util.Log;

import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.RequestApi;
import com.example.yf.creatorshirt.http.TestRequestApi;
import com.example.yf.creatorshirt.http.TestRequestServer;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.CommonAvatarContract;
import com.example.yf.creatorshirt.utils.NetworkUtils;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonObserver;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static org.greenrobot.eventbus.EventBus.TAG;

public class DownloadImagePresenter extends RxPresenter<CommonAvatarContract.CommonAvatarView> {
    private DataManager manager;


    public void downloadImage(String extra) {

    }
}
