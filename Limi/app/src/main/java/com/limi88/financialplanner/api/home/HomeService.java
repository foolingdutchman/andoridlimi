package com.limi88.financialplanner.api.home;

import android.content.Context;
import android.util.SparseArray;

import com.limi88.financialplanner.api.ServiceFactory;
import com.limi88.financialplanner.pojo.BaseData;
import com.limi88.financialplanner.pojo.home.HomeData;
import com.limi88.financialplanner.pojo.home.servicetools.ServiceTools;
import com.limi88.financialplanner.pojo.hotnews.HotNews;
import com.limi88.financialplanner.pojo.newyearopenning.NewYearOpenning;
import com.limi88.financialplanner.pojo.oldpersonensurance.OldPersonEnsurance;
import com.limi88.financialplanner.pojo.topics.Topic;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.PakeageInfoHelper;
import com.limi88.financialplanner.util.RequestHelper;
import com.limi88.financialplanner.util.SecurityUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehao on 16/9/5.
 */
public class HomeService {

    private Context mContext;
    private RequestHelper mHelper;
    private String code;
    private String tonce;
    private String signature;
    private SparseArray<Integer> mPositionsAndStates = new SparseArray<>();
    @Inject
    public HomeService(Context mContext, RequestHelper mHelper) {
        this.mHelper = mHelper;
        this.mContext = mContext;

    }

    public Observable<HomeData> getHomeInfo(String mToken) {
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        signature = PakeageInfoHelper.getSignature(mContext);
        HomeApi homeApi = ServiceFactory.creatService(HomeApi.class, Constants.HOME_URL, code, tonce, signature);
        return homeApi.getHomeInfo(mToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Observable<ServiceTools> getServiceTool() {
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        signature = PakeageInfoHelper.getSignature(mContext);
        HomeApi homeApi = ServiceFactory.creatService(HomeApi.class, Constants.HOME_URL, code, tonce, signature);
        return homeApi.getServiceTools().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<HotNews> getSubPage(String path,int page) {
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        signature = PakeageInfoHelper.getSignature(mContext);
        HomeApi homeApi = ServiceFactory.creatService(HomeApi.class, Constants.HOME_URL, code, tonce, signature);
        return homeApi.getSubPage(path,page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<Topic> getTopics(String path,int page) {
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        signature = PakeageInfoHelper.getSignature(mContext);
        HomeApi homeApi = ServiceFactory.creatService(HomeApi.class, Constants.HOME_URL, code, tonce, signature);
        return homeApi.getTopics(path,page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<BaseData> postLikes(int id) {
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        signature = PakeageInfoHelper.getSignature(mContext);
        HomeApi homeApi = ServiceFactory.creatService(HomeApi.class, Constants.HOME_URL, code, tonce, signature);
        return homeApi.postLikes("Post",id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }


}
