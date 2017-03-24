package com.limi88.financialplanner.ui.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hehao.library.utils.LogUtils;

import com.limi88.financialplanner.R;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.api.account.LoginService;
import com.limi88.financialplanner.di.PerActivity;
import com.limi88.financialplanner.greendao.bean.AccountBean;
import com.limi88.financialplanner.greendao.dao.AccountBeanDao;
import com.limi88.financialplanner.pojo.account.AccountData;
import com.limi88.financialplanner.pojo.account.Acount;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.ToastUtils;
import com.limi88.financialplanner.util.ValidatorUtils;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by xu on 16/5/31.
 */
@PerActivity
public class LoginPresenter implements LoginContract.Presenter {


    private LoginService mLoginService;
    private LoginContract.View mLoginView;
    private LoginContract.BindWXView mWXView;
    private Subscription mSubscription;
    private AccountBeanDao mAccountDao;
//    private Bus mBus;

    @Inject
    @Singleton
    public LoginPresenter(LoginService mLoginService
            , AccountBeanDao accountDao) {
        this.mLoginService = mLoginService;
        this.mAccountDao = accountDao;
    }

    public void setWXView(LoginContract.BindWXView WXView) {
        mWXView = WXView;
    }

    @Override
    public void login(String mobile, String code) {

        LogUtils.debug("----code ", code);
        mLoginService.sign(mobile, code, DataCenter.getDeviceToken()).subscribe(new Subscriber<Acount>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.i("LimiApi", e.getMessage());
            }

            @Override
            public void onNext(Acount limiAcount) {
                if (limiAcount != null) {
                    if (limiAcount.isSuccess()) {
                        AccountData limiData = limiAcount.getData();

                        Constants.CURRENT_USER_ID = limiData.getId();

                        AccountBean accountBean = new AccountBean();
                        accountBean.setAuthenticated(limiAcount.isUserAuthenticated());
                        accountBean.setUserMobile(limiData.getPhone());
                        accountBean.setName(limiData.getName());
                        accountBean.setUserAvatar(limiData.getAvatar());
                        accountBean.setUserToken(limiData.getAccessToken());
                        mAccountDao.getSession().insertOrReplace(accountBean);
                        mLoginView.saveAccountInfo(accountBean);
                        mLoginView.finishWithLogin(limiAcount);
                    } else {
                        ToastUtils.showToast(R.string.code_text_error);
                    }
                } else {
                    ToastUtils.showToast(R.string.code_text_error);
                }
            }
        });


    }

    @Override
    public void register(String mobile, String code) {

        mLoginService.sign(mobile, code, DataCenter.getDeviceToken()).subscribe(account -> {
            if (account.isSuccess()) {
                Log.i("registerApi", "Name:" + account.getData().getName());
                mLoginView.hideSendCodeLoading();
                login(mobile, code);
            } else {
                Log.i("registerApi", "Name:" + account.isSuccess());
            }
        }, throwable -> {
            Log.i("registerApi", throwable.getMessage()+"");
        });

    }

    @Override
    public void reset(String mobile, String code) {
        if (!ValidatorUtils.isMobile(mobile)) {
            ToastUtils.showToast(R.string.mobile_text_error);
            return;
        }
        mLoginService.reset(mobile, code).subscribe(acount -> {
            if (acount.isSuccess()) {
                Log.i("registerApi", "Name:" + acount.getData().getName());
                mLoginView.hideSendCodeLoading();
                login(mobile, code);
            }
        }, throwable -> {
            Log.i("registerApi", throwable.getMessage()+"");
        });

    }

    @Override
    public void sendCode(String mobile) {
        mLoginService.sendCode(mobile).subscribe(acount -> {
            if (acount.isSuccess()) {
                ToastUtils.showToast("验证码已发送");
                ToastUtils.showToastForTest("验证码:" + acount.getData().getMessage());

                Log.i("LoginApi", "message" + acount.getData().getMessage());
            } else ToastUtils.showToast("请输入正确的手机号码");
        }, throwable -> {
            Log.i("LoginApi", throwable.getMessage()+"");
        });
    }



    @Override
    public void attachView(@NonNull LoginContract.View view) {
        mLoginView = view;
    }

    @Override
    public void detachView() {
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mLoginView = null;
    }

    public void logInByWX(Map<String, String> data) {
        mLoginService.logInByWX(data)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(acount -> {
            if (acount.isSuccess()) {
                DataCenter.setSignedIn(acount.getData().getAccessToken());

                if (mLoginView!=null) {
                    mLoginView.saveAccountInfo(acount);
                    mLoginView.finishWithLogin(acount);
                }
                ToastUtils.showToast("登录成功");
            } else {
                ToastUtils.showToast("登录失败");
                if (mLoginView!=null) {
                    mLoginView.finishWithLogin(null);
                }
            }
        }, throwable -> {
            Log.i("LoginApi", throwable.getMessage()+"");
            if (mLoginView!=null) {
                mLoginView.finishWithLogin(null);
            }
        });
    }
    public void bindWithWX(Map<String, String> data) {
        mLoginService.logInByWX(data)
                .observeOn(AndroidSchedulers.mainThread()).subscribe(acount -> {
            if (acount.isSuccess()) {
//                DataCenter.setSignedIn(acount.getData().getAccessToken());

                if (mWXView!=null) {
                    mWXView.onWXbindSuccess();
                }
                ToastUtils.showToast("登录成功");
            } else {
                ToastUtils.showToast(acount.getErrmsg()+"");
                if (mWXView!=null) {
                    mWXView.onWXbindFailed();
                }
            }
        }, throwable -> {
            ToastUtils.showToastForTest(throwable.getMessage()+"");
            Log.i("LoginApi", throwable.getMessage()+"");
            if (mWXView!=null) {
                mWXView.onWXbindFailed();
            }
        });
    }


}
