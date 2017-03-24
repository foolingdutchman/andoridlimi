
package com.limi88.financialplanner.pojo.ad;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ad {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("user_authenticated")
    @Expose
    private String userAuthenticated;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getUserAuthenticated() {
        return userAuthenticated;
    }

    public void setUserAuthenticated(String userAuthenticated) {
        this.userAuthenticated = userAuthenticated;
    }

}
