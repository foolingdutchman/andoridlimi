package com.limi88.financialplanner.pojo.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by hehao on 16/9/9.
 */
public class Avatar {
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("thumb")
    @Expose
    public Thumb thumb;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Thumb getThumb() {
        return thumb;
    }

    public void setThumb(Thumb thumb) {
        this.thumb = thumb;
    }

    @Override
    public String toString() {
        return "Avatar{" +
                "url='" + url + '\'' +
                ", thumb=" + thumb +
                '}';
    }
}
