package com.limi88.financialplanner.pojo.version;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by hehao
 * Date on 16/11/9.
 * Email: hao.he@sunanzq.com
 */
public class VersionInfo {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("errmsg")
    @Expose
    private String errmsg;
    @SerializedName("errcode")
    @Expose
    private int errcode;

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

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
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
