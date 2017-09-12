/*
 * Copyright © Yan Zhenjie. All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.yf.creatorshirt.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.ui.adapter.AddressListAdapter;
import com.example.yf.creatorshirt.mvp.model.address.City;
import com.example.yf.creatorshirt.mvp.model.address.RequestCityListTask;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>选择地址的页面。</p>
 */
public class AddressCheckActivity extends AppCompatActivity {

    private static final String KEY_OUTPUT_PROVINCE_CITY_DISTRICT = "KEY_OUTPUT_PROVINCE_CITY_DISTRICT";

    public static ArrayList<City> parse(Intent data) {
        return data.getParcelableArrayListExtra(KEY_OUTPUT_PROVINCE_CITY_DISTRICT);
    }

    AddressListAdapter mOneListAdapter;
    List<City> mOneList = new ArrayList<>();
    int mCurrentOneSelect = -1;

    AddressListAdapter mTwoListAdapter;
    List<City> mTwoList = new ArrayList<>();
    int mCurrentTwoSelect = -1;

    AddressListAdapter mThreeListAdapter;
    List<City> mThreeList = new ArrayList<>();
    RecyclerView oneView;
    RecyclerView twoView;
    RecyclerView threeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_select);

        oneView = (RecyclerView) findViewById(R.id.one_recyclerView);
        twoView = (RecyclerView) findViewById(R.id.two_recyclerView);
        threeView = (RecyclerView) findViewById(R.id.three_recyclerView);
        List<RecyclerView> recyclerViewList = new ArrayList<>();
        recyclerViewList.add(oneView);
        recyclerViewList.add(twoView);
        recyclerViewList.add(threeView);

        oneView.setLayoutManager(new LinearLayoutManager(this));
        mOneListAdapter = new AddressListAdapter(getLayoutInflater(), mProvinceItemClickListener);
        oneView.setAdapter(mOneListAdapter);

        twoView.setLayoutManager(new LinearLayoutManager(this));
        mTwoListAdapter = new AddressListAdapter(getLayoutInflater(), mCityItemClickListener);
        twoView.setAdapter(mTwoListAdapter);

        threeView.setLayoutManager(new LinearLayoutManager(this));
        mThreeListAdapter = new AddressListAdapter(getLayoutInflater(), mDistrictItemClickListener);
        threeView.setAdapter(mThreeListAdapter);

        RequestCityListTask requestCityTask = new RequestCityListTask(this, callback);
        requestCityTask.execute();
    }


    /**
     * 请求服务器数据回来。(我这里是从asset中的json中读取的，模拟从服务器请求。)
     */
    private RequestCityListTask.Callback callback = new RequestCityListTask.Callback() {
        @Override
        public void callback(List<City> cities) {
            Log.e("TGA", "DDDD" + cities);
            mOneList = cities;
            mOneListAdapter.notifyDataSetChanged(mOneList);
        }
    };

    /**
     * 省的item被点击。
     */
    private ItemClickListener.OnItemClickListener mProvinceItemClickListener = new ItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (mCurrentOneSelect == position) {
                return;
            }
            if (mCurrentOneSelect != -1) {
                mOneList.get(mCurrentOneSelect).setSelect(false);
                mOneListAdapter.notifyItemChanged(mCurrentOneSelect);
            }

            mCurrentOneSelect = position;
            mOneList.get(mCurrentOneSelect).setSelect(true);
            mOneListAdapter.notifyItemChanged(mCurrentOneSelect);

            City one = mOneList.get(mCurrentOneSelect);
            Log.e("TAG", "dddd" + one);
            mTwoList = one.getCityList();
            Log.e("TAG", "" + mOneList.size() + ":" + mCurrentOneSelect + "：：");

            if (mTwoList == null || mTwoList.size() == 0) { // 选定一级。
                setResultFinish(one, null, null);
                Log.e("TSG", "DDDF CU K YOU ");
            } else {
                // 更新二级的content和title。
                mTwoListAdapter.notifyDataSetChanged(mTwoList);
                twoView.setVisibility(View.VISIBLE);
                oneView.setVisibility(View.GONE);
                Log.e("TAG", "DDDDD");
                // 三级置空。
                mThreeList = null;
                mCurrentTwoSelect = -1;
            }
        }
    };

    /**
     * 市的item被点击。
     */
    private ItemClickListener.OnItemClickListener mCityItemClickListener = new ItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            if (mCurrentTwoSelect == position) {
                return;
            }

            if (mCurrentTwoSelect != -1) {
                mTwoList.get(mCurrentTwoSelect).setSelect(false);
                mTwoListAdapter.notifyItemChanged(mCurrentTwoSelect);
            }

            mCurrentTwoSelect = position;
            mTwoList.get(mCurrentTwoSelect).setSelect(true);
            mTwoListAdapter.notifyItemChanged(mCurrentTwoSelect);

            City two = mTwoList.get(mCurrentTwoSelect);
            mThreeList = two.getCityList();
            if (mThreeList == null || mThreeList.size() == 0) { // 选定二级。
                setResultFinish(mOneList.get(mCurrentOneSelect), two, null);
            } else {
                twoView.setVisibility(View.GONE);
                threeView.setVisibility(View.VISIBLE);
                mThreeListAdapter.notifyDataSetChanged(mThreeList);
            }
        }
    };

    /**
     * 区的item被点击。
     */
    private ItemClickListener.OnItemClickListener mDistrictItemClickListener = new ItemClickListener.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            setResultFinish(mOneList.get(mCurrentOneSelect), mTwoList.get(mCurrentTwoSelect), mThreeList.get(position));
        }
    };

    /**
     * 选中。
     */
    private void setResultFinish(City province, City city, City district) {
        ArrayList<City> cityArrayList = new ArrayList<>();
        cityArrayList.add(province);
        if (city != null)
            cityArrayList.add(city);
        if (district != null)
            cityArrayList.add(district);

        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(KEY_OUTPUT_PROVINCE_CITY_DISTRICT, cityArrayList);
        setResult(RESULT_OK, intent);
        finish();
    }
}
