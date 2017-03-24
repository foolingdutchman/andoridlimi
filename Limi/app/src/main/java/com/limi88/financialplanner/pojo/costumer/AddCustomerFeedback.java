package com.limi88.financialplanner.pojo.costumer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.limi88.financialplanner.pojo.clients.Real;

/**
 * Created by hehao
 * Date on 16/9/26.
 * Email: hao.he@limi88.com
 */
public class AddCustomerFeedback {

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("data")
    @Expose
    private Real data;
    @SerializedName("user_authenticated")
    @Expose
    private boolean userAuthenticated;

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
     * @return
     *     The data
     */
    public Real getData() {
        return data;
    }

    /**
     *
     * @param data
     *     The data
     */
    public void setData(Real data) {
        this.data = data;
    }

    /**
     *
     * @return
     *     The userAuthenticated
     */
    public boolean isUserAuthenticated() {
        return userAuthenticated;
    }

    /**
     *
     * @param userAuthenticated
     *     The user_authenticated
     */
    public void setUserAuthenticated(boolean userAuthenticated) {
        this.userAuthenticated = userAuthenticated;
    }

}
