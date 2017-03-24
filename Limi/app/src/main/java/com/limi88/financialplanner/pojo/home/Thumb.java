package com.limi88.financialplanner.pojo.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hehao on 16/9/9.
 */
public class Thumb {
    @SerializedName("url")
    @Expose
    public String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Thumb{" +
                "url='" + url + '\'' +
                '}';
    }
}
