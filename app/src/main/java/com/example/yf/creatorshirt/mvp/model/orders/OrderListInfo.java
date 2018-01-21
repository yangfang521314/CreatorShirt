package com.example.yf.creatorshirt.mvp.model.orders;

import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by yangfang on 2018/1/21.
 */

public class OrderListInfo extends SaveOrderInfo implements Parcelable {
    private double orderPrice;
    private String partner;
    private String discount;
    private String address;
    private String zipcode;
    private String paymode;
    private String payorderid;
    private ArrayList<ClothesSize> detailList;

}
