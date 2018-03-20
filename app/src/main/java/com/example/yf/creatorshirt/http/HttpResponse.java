package com.example.yf.creatorshirt.http;

/**
 * Created by yang on 31/07/2017.
 * 服务器统一返回的参数
 * 所有的接口返回的形式一样
 */

public class HttpResponse<T> {
    private int status;
    private T result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "status=" + status +
                ", result=" + result +
                '}';
    }

}
