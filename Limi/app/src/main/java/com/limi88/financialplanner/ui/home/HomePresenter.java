package com.limi88.financialplanner.ui.home;

import android.support.annotation.NonNull;
import android.util.Log;

import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.api.home.HomeService;
import com.limi88.financialplanner.di.component.ApplicationComponent;
import com.limi88.financialplanner.pojo.home.HomeData;
import com.limi88.financialplanner.pojo.hotnews.HotNews;
import com.limi88.financialplanner.ui.base.BaseView;
import com.limi88.financialplanner.util.JsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehao
 * Date on 16/10/8.
 * Email: hao.he@limi88.com
 */
public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.HomeView mHomeView;
    private Map<String,HomeContract.SubView> mSubViewList=new HashMap<>();
    private HomeContract.ServiceToolView mServiceToolView;
    private HomeService mHomeService;
    private ApplicationComponent mApplicationComponent;
    public void setServiceToolView(HomeContract.ServiceToolView serviceToolView) {
        mServiceToolView = serviceToolView;
    }

    @Inject
    @Singleton
    public HomePresenter(HomeService mHomeService, ApplicationComponent mApplicationComponent) {
        this.mHomeService = mHomeService;
        this.mApplicationComponent=mApplicationComponent;
    }


    public void addSubView(String path,HomeContract.SubView subView) {
        mSubViewList.put(path, subView);
    }
  public void removeSubView(String path) {
        mSubViewList.remove(path);
    }

    @Override
    public void attachView(@NonNull BaseView view) {
        mHomeView = (HomeContract.HomeView) view;
    }

    @Override
    public void detachView() {
        mHomeView = null;
    }

    @Override
    public void loadHomeData(String mToken) {
        mHomeService.getHomeInfo(mToken)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HomeData>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {

                        if (mHomeView != null){
                            String dir=((MainApplication)mApplicationComponent.getContext()).getLogCacheDir();
                            Observable.just(JsonUtils.getHomeFromJson(dir))
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(data -> {
                                        mHomeView.loadDataToUI(data);
                                        mHomeView.hideErrorView();
                                    },throwable -> {
                                        Log.d("HomeApi", e.getMessage()+"");
                                        mHomeView.showErrorView();
                                    });
                            mHomeView.hideLoadingHomeData();
                        }else   Log.d("HomeApi", e.getMessage()+"");

                    }

                    @Override
                    public void onNext(HomeData homeData) {
                        if (mHomeView != null) {
                            mHomeView.loadDataToUI(homeData);
                            String dir=((MainApplication)mApplicationComponent.getContext()).getLogCacheDir();
                            Observable.just(JsonUtils.saveHomeDataToJson(homeData,dir))
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(success ->{
                                        Log.d("HomeApi", "保存成功");
                                    },throwable ->    Log.d("HomeApi", "保存失败"));
                            mHomeView.hideLoadingHomeData();
                            mHomeView.hideErrorView();
                        }
                    }
                });

    }

    @Override
    public void loadServiceData() {
        if (mServiceToolView!=null) {
            mHomeService.getServiceTool().subscribe( serviceTools -> {
                if (serviceTools.isSuccess()) {
                    mServiceToolView.loadDataToUI(serviceTools);
                } else {
                    Log.i("HomeApi",serviceTools.getCode()+"");
                }
            },throwable -> {
                Log.i("HomeApi",throwable.getMessage()+"");
            });
        }
    }

    @Override
    public void loadSubPageData(String  path,int page) {
        HomeContract.SubView mSubView=mSubViewList.get(path);
        if (mSubView!=null) {
            if (path.equals("topics")) {
                mHomeService.getTopics(path,page).subscribe(baseData -> {
                    if (baseData.isSuccess()) {
                        mSubView.loadDataToUI(baseData);
                    } else {
                        Log.i("HomeApi", baseData.getCode() + "");
                    }
                }, throwable -> {
                    Log.i("HomeApi", throwable.getMessage()+"");
                });
            }else
            mHomeService.getSubPage(path,page).subscribe(baseData -> {
                if (baseData.isSuccess()) {
                    mSubView.loadDataToUI(baseData);
                } else {
                    Log.i("HomeApi", baseData.getCode() + "");
                }
            }, throwable -> {
                Log.i("HomeApi", throwable.getMessage()+"");
            });

        }
    }

    public void postTopicLikes(int id){
        HomeContract.SubView mSubView=mSubViewList.get("topics");
        mHomeService.postLikes(id).subscribe(baseData -> {
            Log.i("HomeApi","postLikeSuccess-------");
        },throwable -> {Log.i("HomeApi",throwable.getMessage()+"");});

    }
    @Override
    public void attachSubPageData(String  path,int page) {
        HomeContract.SubView mSubView=mSubViewList.get(path);
        if (mSubView!=null) {
            if (path.equals("topics")) {
                mHomeService.getTopics(path,page).subscribe(baseData -> {
                    if (baseData.isSuccess()) {
                        mSubView.attachData(baseData);
                    } else {
                        Log.i("HomeApi", baseData.getCode() + "");
                    }
                }, throwable -> {
                    Log.i("HomeApi", throwable.getMessage()+"");
                });
            }else
            mHomeService.getSubPage(path,page).subscribe(baseData -> {
                if (baseData.isSuccess()) {
                    mSubView.attachData(baseData);
                } else {
                    Log.i("HomeApi", baseData.getCode() + "");
                }
            }, throwable -> {
                Log.i("HomeApi", throwable.getMessage()+"");
            });

        }
    }


    public void loadHomeDataWithoutCache(String token) {
        mHomeService.getHomeInfo(token)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HomeData>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        if (mHomeView != null){
                            mHomeView.hideLoadingHomeData();
                        }else   Log.d("HomeApi", e.getMessage()+"");
                    }
                    @Override
                    public void onNext(HomeData homeData) {
                        if (mHomeView != null) {
                            mHomeView.loadDataToUI(homeData);
                            mHomeView.hideLoadingHomeData();
                        }
                    }
                });

    }
}
