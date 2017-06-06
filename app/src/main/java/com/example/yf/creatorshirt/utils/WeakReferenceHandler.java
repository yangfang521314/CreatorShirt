package com.example.yf.creatorshirt.utils;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by ${yangfang} on 2016/11/30.
 * 创建Handler的公共抽象类
 */

public abstract class WeakReferenceHandler<T> extends Handler {
    private WeakReference<T> mWeakReference;

    public WeakReferenceHandler(T reference) {
        mWeakReference = new WeakReference<T>(reference);
    }

    @Override
    public void handleMessage(Message msg) {
        if (mWeakReference.get() == null) {
            return;
        }
        handleMessage(mWeakReference.get(), msg);
    }

    protected abstract void handleMessage(T reference, Message msg);
}
