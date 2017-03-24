package com.sunanzq.greendao.bean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "ACCOUNT_BEAN".
 */
public class AccountBean {

    private String userMobile;
    private String userToken;
    private String userAvatar;
    private String name;
    private Boolean authenticated;

    public AccountBean() {
    }

    public AccountBean(String userMobile) {
        this.userMobile = userMobile;
    }

    public AccountBean(String userMobile, String userToken, String userAvatar, String name, Boolean authenticated) {
        this.userMobile = userMobile;
        this.userToken = userToken;
        this.userAvatar = userAvatar;
        this.name = name;
        this.authenticated = authenticated;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }

}