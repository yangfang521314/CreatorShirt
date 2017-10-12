package com.example.yf.creatorshirt.http;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.yf.creatorshirt.mvp.model.BombStyleBean;

import java.util.List;


/**
 * Created by yang on 31/07/2017.
 * 服务器统一返回的参数 * 所有的接口返回的形式一样
 */

public class HttpResultResponse implements Parcelable{
    private int status;
    private List<BombStyleBean> result;

    protected HttpResultResponse(Parcel in) {
        status = in.readInt();
        result = in.createTypedArrayList(BombStyleBean.CREATOR);
    }

    public static final Creator<HttpResultResponse> CREATOR = new Creator<HttpResultResponse>() {
        @Override
        public HttpResultResponse createFromParcel(Parcel in) {
            return new HttpResultResponse(in);
        }

        @Override
        public HttpResultResponse[] newArray(int size) {
            return new HttpResultResponse[size];
        }
    };

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<BombStyleBean> getResult() {
        return result;
    }

    public void setResult(List<BombStyleBean> result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "HttpResultResponse{" +
                "status=" + status +
                ", result=" + result +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(status);
        dest.writeTypedList(result);
    }
}
