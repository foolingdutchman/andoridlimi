package com.limi88.financialplanner.api.account;

import android.content.Context;


import com.limi88.financialplanner.pojo.account.Acount;
import com.limi88.financialplanner.pojo.ad.Ad;
import com.limi88.financialplanner.pojo.user.User;
import com.limi88.financialplanner.pojo.version.VersionInfo;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.api.ServiceFactory;
import com.limi88.financialplanner.util.PakeageInfoHelper;
import com.limi88.financialplanner.util.RequestHelper;
import com.limi88.financialplanner.util.SecurityUtils;

import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liushuai on 16/9/3.
 */
public class LoginService {

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    private Context mContext;
    private RequestHelper mHelper;
    private String code;
    private String tonce;
    private String signature;
    @Inject
    public LoginService(Context mContext, RequestHelper mHelper) {
        this.mHelper=mHelper;
        this.mContext = mContext;

    }

    public Observable<Acount> login(String phone,String password) {
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        signature= PakeageInfoHelper.getSignature(mContext);
        Login homeApi = ServiceFactory.creatService(Login.class, Constants.LOGIN_URL,code,tonce,signature);
        return homeApi.login(phone, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
    public Observable<Acount> sign(String phone,String password,String token) {
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        signature= PakeageInfoHelper.getSignature(mContext);
        Login homeApi = ServiceFactory.creatService(Login.class, Constants.LOGIN_URL,code,tonce,signature);
        return homeApi.sign(phone, password, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
    public Observable<Acount> reset(String phone,String password) {
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        signature= PakeageInfoHelper.getSignature(mContext);
        Login homeApi = ServiceFactory.creatService(Login.class, Constants.LOGIN_URL,code,tonce,signature);
        return homeApi.reset(phone, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
    public Observable<Acount> sendCode(String phone) {
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        signature= PakeageInfoHelper.getSignature(mContext);
        Login homeApi = ServiceFactory.creatService(Login.class, Constants.LOGIN_URL,code,tonce,signature);
        return homeApi.sendCode(phone, "register")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
    public Observable<User> checkValidation(String mToken) {
        tonce = mHelper.getTimestamp();
        signature= PakeageInfoHelper.getSignature(mContext);
        code = SecurityUtils.getHMACSHA1(tonce);
        Login homeApi = ServiceFactory.creatService(Login.class, Constants.LOGIN_URL,code,tonce,signature);
        return homeApi.checkValidate(mToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Observable<Acount> logInByWX(Map<String,String> data){
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        signature= PakeageInfoHelper.getSignature(mContext);
        Login homeApi = ServiceFactory.creatService(Login.class, Constants.LOGIN_URL,code,tonce,signature);
        return homeApi.loginByWX(data)
                .subscribeOn(Schedulers.io());


    }
    public Observable<VersionInfo> checkVersion(){
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        signature= PakeageInfoHelper.getSignature(mContext);
        Login homeApi = ServiceFactory.creatService(Login.class, Constants.VERSION_URL,code,tonce,signature);
        return homeApi.checkVersion("android")
                .subscribeOn(Schedulers.io());


    }
    public Observable<Ad> getAds(){
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        signature= PakeageInfoHelper.getSignature(mContext);
        Login homeApi = ServiceFactory.creatService(Login.class, Constants.HOME_URL,code,tonce,signature);
        return homeApi.getAds("android")
                .subscribeOn(Schedulers.io());
    }
}
