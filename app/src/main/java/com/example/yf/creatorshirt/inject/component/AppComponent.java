package com.example.yf.creatorshirt.inject.component;

import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.inject.module.AppModule;
import com.example.yf.creatorshirt.inject.module.HttpModule;
import com.example.yf.creatorshirt.mvp.model.db.DataManager;
import com.example.yf.creatorshirt.mvp.model.db.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yang on 16/05/2017.
 * 连接module的桥梁
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {
    App getContext();//提供App的Context

    RetrofitHelper retrofitHelper();  //提供http的帮助类

    DataManager getDataManager(); //数据查找
}
