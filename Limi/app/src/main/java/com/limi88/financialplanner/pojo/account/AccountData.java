
package com.limi88.financialplanner.pojo.account;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class AccountData {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("access_token")
    @Expose
    private String accessToken;

    @SerializedName("avatar_url")
    @Expose
    private String avatar;

    @SerializedName("sign_first")
    @Expose
    private String signFirst;

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("bind_weixin")
    @Expose
    private boolean bindWeixin;

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return The phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone The phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return The accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken The access_token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSignFirst() {
        return signFirst;
    }

    public void setSignFirst(String signFirst) {
        this.signFirst = signFirst;
    }

    public boolean isBindWeixin() {
        return bindWeixin;
    }

    public void setBindWeixin(boolean bindWeixin) {
        this.bindWeixin = bindWeixin;
    }
}
