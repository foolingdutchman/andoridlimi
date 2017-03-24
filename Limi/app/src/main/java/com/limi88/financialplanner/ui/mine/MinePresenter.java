package com.limi88.financialplanner.ui.mine;

import android.support.annotation.NonNull;
import android.util.Log;

import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.api.mine.MineService;
import com.limi88.financialplanner.di.component.ApplicationComponent;
import com.limi88.financialplanner.greendao.dao.AccountBeanDao;
import com.limi88.financialplanner.pojo.user.User;
import com.limi88.financialplanner.ui.base.BaseView;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.JsonUtils;
import com.limi88.financialplanner.util.ToastUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehao on 16/9/19.
 */
public class MinePresenter implements MineContract.Presenter {
    private AccountBeanDao mAccountDao;
    private MineService mineService;
    private MineContract.MineView mineView;
    private ApplicationComponent mApplicationComponent;
    private MineContract.FeedbackView mFeedbackView;
    private MineContract.MeProfileView mProfileView;

    public void setFeedbackView(MineContract.FeedbackView feedbackView) {
        mFeedbackView = feedbackView;
    }

    @Inject
    public MinePresenter(AccountBeanDao mAccountDao,MineService mineService,ApplicationComponent applicationComponent) {
        this.mAccountDao = mAccountDao;
        this.mineService=mineService;
        this.mApplicationComponent=applicationComponent;
    }


    @Override
    public void getAccountInfo() {
        Observable.just(mAccountDao).subscribeOn(Schedulers.io())
                .flatMap(accountBeanDao -> Observable.just(accountBeanDao.load(Constants.CURRENT_USER_MOBILE))).observeOn(AndroidSchedulers.mainThread())
                .subscribe(accountBean -> {
                    mineView.loadAccount(accountBean);
                }, throwable -> {
                    Log.i("user", throwable.getMessage());
                    ToastUtils.showToast("你还没有登录");
                });
    }

    @Override
    public void getUserInfo() {
        String cache=((MainApplication)
                (mApplicationComponent.getContext())).getLogCacheDir();

        mineService.getUserInfo(Constants.CURRENT_TOKEN).subscribe(user -> {
            if (user.isSuccess()) {
                if (mineView != null) {
                    mineView.loadUserInfoToUI(user);
                    DataCenter.setCurrentUser(user);
                    if (JsonUtils.saveUserToJson(user, cache)) {
                        Log.i("MineApi", "保存成功");
                    }
                }
            } else {
                if (mineView!=null) {
                    mineView.gotoLogin();
                }
            }

        }, throwable -> {

            Observable.just(JsonUtils.getUserFromJson(cache)).subscribeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread()).subscribe(user -> {
                if (mineView!=null&&user!=null) {
                    mineView.loadUserInfoToUI(user);
                    DataCenter.setCurrentUser(user);
                }

            }, throwable1 -> Log.i("MineApi", throwable.getMessage()));
            Log.i("MineApi", throwable.getMessage());

        });
    }

    public void feedback(String phone,String content){

        mineService.feedback(phone,content).subscribe(user -> {
            if (user.isSuccess()) {
                ToastUtils.showToast("提交成功");
                if (mFeedbackView!=null) {

                    mFeedbackView.dismissFeedbackByUpate();
                }
            } else {
                Log.i("Feedback", user.getCode()+"");

            }
        },throwable -> {Log.i("Feedback", throwable.getMessage());

 });


    }

    @Override
    public void uploadUserInfo(User user) {
        String cache=((MainApplication)
                (mApplicationComponent.getContext())).getLogCacheDir();
        mineService.updateUserInfo(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.i("mineApi", "success:-------" + s);
                    if (s.isSuccess()) {
                        ToastUtils.showToast("更新成功");
                        if (mProfileView!=null) {
                            mProfileView.dismiss();
                        }
                        if (JsonUtils.saveUserToJson(user, cache)) {
                            Log.i("MineApi", "保存成功");
                        }
//                        getUserInfo();
                    }

                }, throwable -> {
//                    Observable.just(JsonUtils.getUserFromJson(cache)).subscribeOn(Schedulers.io())
//                            .subscribeOn(AndroidSchedulers.mainThread()).subscribe(user1 -> {
//                        mineView.loadUserInfoToUI(user1);
//                        DataCenter.setCurrentUser(user1);
//                    }, throwable1 -> Log.i("MineApi", throwable.getMessage()));
                    Log.i("mineApi", "failed:-------" + throwable.getMessage());
                    ToastUtils.showToastForTest("更新失败");
//                    mineView.dismissUpDateUserUI();

                });
    }







    @Override
    public void attachView(@NonNull BaseView view) {
        mineView = (MineContract.MineView) view;
    }

    @Override
    public void detachView() {
        mineView = null;
    }

    public void getUserInfoWithoutCache() {

        mineService.getUserInfo(Constants.CURRENT_TOKEN).subscribe(user -> {
            mineView.loadUserInfoToUI(user);
            DataCenter.setCurrentUser(user);

        }, throwable -> {
            Log.i("MineApi", throwable.getMessage());
        });
    }

    public void setProfileView(MineContract.MeProfileView profileView) {
        mProfileView = profileView;
    }
}
