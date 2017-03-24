package com.limi88.financialplanner.ui.splash;

import android.support.annotation.NonNull;
import android.util.Log;

import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.api.account.LoginService;
import com.limi88.financialplanner.api.home.HomeService;
import com.limi88.financialplanner.di.component.ApplicationComponent;
import com.limi88.financialplanner.pojo.ad.Ad;
import com.limi88.financialplanner.ui.base.BaseView;
import com.limi88.financialplanner.util.JsonUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehao
 * Date on 16/12/15.
 * Email: hao.he@sunanzq.com
 */
public class SplashPresenter implements SplashContract.Presenter {
    private SplashContract.SplashView mView;
    private LoginService mLoginService;
    private ApplicationComponent mApplicationComponent;

    @Singleton
    @Inject
    public SplashPresenter(LoginService mLoginService, ApplicationComponent applicationComponent) {
        this.mLoginService = mLoginService;
        this.mApplicationComponent = applicationComponent;
    }

    @Override
    public void getAds() {
        String dir=((MainApplication)mApplicationComponent.getContext()).getLogCacheDir();
        mLoginService.getAds().observeOn(AndroidSchedulers.mainThread())
                .subscribe(ad -> {
            if (ad.isSuccess()) {
                if (mView!=null) {
                    mView.loadDataToUI(ad);
                }

                JsonUtils.saveAdsToJson(ad, dir);
            }
        }, throwable -> {
            Ad ad = JsonUtils.getAdsFromJson(dir);
            if (ad == null || ad.getData() == null) {
                Log.i("slapsh", throwable.getMessage() + "");
            } else {
                if (mView!=null) {
                    mView.loadDataToUI(ad);
                }
            }

        });

//        Observable.just(JsonUtils.getAdsFromJson(dir))
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread()).subscribe( mAd -> {
//            if (mView!=null) {
//                mView.loadDataToUI(mAd);
//            }
//        });
    }

    @Override
    public void attachView(@NonNull BaseView view) {
        mView = (SplashContract.SplashView) view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
