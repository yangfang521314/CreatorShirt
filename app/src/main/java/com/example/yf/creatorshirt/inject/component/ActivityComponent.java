package com.example.yf.creatorshirt.inject.component;

import android.app.Activity;

import com.example.yf.creatorshirt.inject.module.ActivityModule;
import com.example.yf.creatorshirt.inject.scope.AcitivityScope;
import com.example.yf.creatorshirt.mvp.ui.activity.AddressEditActivity;
import com.example.yf.creatorshirt.mvp.ui.activity.AddressShowActivity;
import com.example.yf.creatorshirt.mvp.ui.activity.ChoiceSizeActivity;
import com.example.yf.creatorshirt.mvp.ui.activity.DesignerNewOrdersActivity;
import com.example.yf.creatorshirt.mvp.ui.activity.DetailClothesActivity;
import com.example.yf.creatorshirt.mvp.ui.activity.EditUserActivity;
import com.example.yf.creatorshirt.mvp.ui.activity.LoginActivity;
import com.example.yf.creatorshirt.mvp.ui.activity.MyOrderActivity;
import com.example.yf.creatorshirt.mvp.ui.activity.NewDesignActivity;

import dagger.Component;

/**
 * Created by yang on 16/05/2017.
 */

@AcitivityScope
@Component(dependencies = {AppComponent.class}, modules = {ActivityModule.class})
public interface ActivityComponent {
    Activity getActivity();

//    void inject(MainActivity mainActivity);

    void inject(LoginActivity loginActivity);

    void inject(AddressShowActivity addressActivity);

    void inject(ChoiceSizeActivity choiceSizeActivity);

    void inject(MyOrderActivity myOrderActivity);

    void inject(EditUserActivity editUserActivity);

    void inject(AddressEditActivity editAddressActivity);

    void inject(DetailClothesActivity detailClothesActivity);

    void inject(DesignerNewOrdersActivity detailOrders22Activity);


    void inject(NewDesignActivity newsDesignActivity);

}
