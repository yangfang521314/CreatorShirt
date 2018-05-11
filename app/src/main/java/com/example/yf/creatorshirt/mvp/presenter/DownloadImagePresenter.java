package com.example.yf.creatorshirt.mvp.presenter;

import android.annotation.SuppressLint;
import android.os.Environment;

import com.example.yf.creatorshirt.http.DataManager;
import com.example.yf.creatorshirt.http.TestRequestServer;
import com.example.yf.creatorshirt.mvp.presenter.base.RxPresenter;
import com.example.yf.creatorshirt.mvp.presenter.contract.CommonAvatarContract;
import com.example.yf.creatorshirt.utils.Constants;
import com.example.yf.creatorshirt.utils.RxUtils;
import com.example.yf.creatorshirt.widget.CommonSubscriber;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.inject.Inject;

import okhttp3.ResponseBody;

public class DownloadImagePresenter extends RxPresenter<CommonAvatarContract.CommonAvatarView> {
    private DataManager manager;

    @Inject
    DownloadImagePresenter(DataManager manager) {
        this.manager = manager;
    }

    @SuppressLint("CheckResult")
    public void downloadImage(String extra) {

        TestRequestServer.getDownInstance().downloadImage(extra)
                .map(this::saveFile)
                .compose(RxUtils.rxSchedulerHelper()).subscribeWith(new CommonSubscriber<String>(mView) {
            @Override
            public void onNext(String s) {
                if (s != null) {
                    mView.showSuccessAvatar(new File(s));
                } else {
                    mView.showErrorMsg("保存图片失败");
                }
            }
        });


    }

    private String saveFile(ResponseBody responseBody) {
        String result = "";
        File file = new File(Environment.getExternalStorageDirectory().toString() + Constants.DOWNIMAGES);
        if (!file.exists()) {
            file.mkdirs();
        }
        File imgfile = new File(file.getAbsolutePath(), new Date().getTime() + ".png");
        InputStream inputStream = responseBody.byteStream();
        byte[] buffer = new byte[4096];
        int len = 0;
        try {
            FileOutputStream outputStream = new FileOutputStream(imgfile);
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            inputStream.close();
            outputStream.close();
            result = imgfile.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
