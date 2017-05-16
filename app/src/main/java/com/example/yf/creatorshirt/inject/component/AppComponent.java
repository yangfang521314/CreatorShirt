package com.example.yf.creatorshirt.inject.component;

import com.example.yf.creatorshirt.app.App;
import com.example.yf.creatorshirt.inject.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by yang on 16/05/2017.
 * 连接module的桥梁
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    App getContext();
}
