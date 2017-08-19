package com.example.yf.creatorshirt.mvp.model.detaildesign;

/**
 * Created by yangfang on 2017/8/19.
 * 最小的style，example：
 * "name": "碎花",
 * "file": "pattern_2.png"
 *
 */

public class DetailStyle {
    private String name;
    private String file;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "DetailStyle{" +
                "name='" + name + '\'' +
                ", file='" + file + '\'' +
                '}';
    }
}
