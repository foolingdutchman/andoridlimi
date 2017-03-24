
package com.limi88.financialplanner.pojo.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Acount {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("errmsg")
    @Expose
    private String errmsg;
    @SerializedName("errcode")
    @Expose
    private int errcode;
    @SerializedName("user_authenticated")
    @Expose
    private boolean userAuthenticated;
    @SerializedName("data")
    @Expose
    private AccountData data;

    /**
     * 
     * @return
     *     The success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * 
     * @param success
     *     The success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * 
     * @return
     *     The code
     */
    public int getCode() {
        return code;
    }

    /**
     * 
     * @param code
     *     The code
     */
    public void setCode(int code) {
        this.code = code;
    }


    /**
     * 
     * @param userAuthenticated
     *     The user_authenticated
     */
    public void setUserAuthenticated(boolean userAuthenticated) {
        this.userAuthenticated = userAuthenticated;
    }

    /**
     * 
     * @return
     *     The data
     */
    public AccountData getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(AccountData data) {
        this.data = data;
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

    public boolean isUserAuthenticated() {
        return userAuthenticated;
    }

}
