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
package com.example.yf.creatorshirt.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yf.creatorshirt.R;
import com.example.yf.creatorshirt.mvp.listener.ItemClickListener;
import com.example.yf.creatorshirt.mvp.model.address.City;

import java.util.List;

/**
 * <p>地址列表的适配器。</p>
 */
public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.AddressViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<City> mCityList;
    private ItemClickListener.OnItemClickListener mItemClickListener;

    public AddressListAdapter(LayoutInflater layoutInflater, ItemClickListener.OnItemClickListener itemClickListener) {
        this.mLayoutInflater = layoutInflater;
        this.mItemClickListener = itemClickListener;
    }

    public void notifyDataSetChanged(List<City> mCityList) {
        this.mCityList = mCityList;
        super.notifyDataSetChanged();
    }

    @Override
    public AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AddressViewHolder viewHolder = new AddressViewHolder(mLayoutInflater.inflate(R.layout.item_address_select, parent, false));
        viewHolder.mItemClickListener = mItemClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AddressViewHolder holder, int position) {
        holder.setData(mCityList.get(position));
    }

    @Override
    public int getItemCount() {
        return mCityList == null ? 0 : mCityList.size();
    }

    static class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTvAddress;
        ImageView mRadioButton;

        ItemClickListener.OnItemClickListener mItemClickListener;

        AddressViewHolder(View itemView) {
            super(itemView);
            mTvAddress = (TextView) itemView.findViewById(R.id.tv_area_name);
            mRadioButton = (ImageView) itemView.findViewById(R.id.radio_btn);
            itemView.setOnClickListener(this);
        }

        public void setData(City mData) {
            mTvAddress.setText(mData.getName());
            mRadioButton.setSelected(mData.isSelect());
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null)
                mItemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

}
