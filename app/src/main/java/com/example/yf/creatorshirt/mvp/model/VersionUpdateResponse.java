package com.example.yf.creatorshirt.mvp.model;

/**
 * Created by yangfang on 2018/1/12.
 */

public class VersionUpdateResponse {
    private Boolean update;              //友盟为String型
    private String new_version;
    private String apk_url;
    private String update_log;

    public Boolean getUpdate() {
        return update;
    }

    public void setUpdate(Boolean update) {
        this.update = update;
    }

    public String getNew_version() {
        return new_version;
    }

    public void setNew_version(String new_version) {
        this.new_version = new_version;
    }

    public String getApk_url() {
        return apk_url;
    }

    public void setApk_url(String apk_url) {
        this.apk_url = apk_url;
    }

    public String getUpdate_log() {
        return update_log;
    }

    public void setUpdate_log(String update_log) {
        this.update_log = update_log;
    }



}
