package com.limi88.financialplanner.ui.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hehao.library.base.BaseFragmentActivity;
import com.hehao.library.netstatus.NetUtils;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.api.account.LoginService;
import com.limi88.financialplanner.api.mine.MineService;
import com.limi88.financialplanner.di.component.ActivityComponent;
import com.limi88.financialplanner.pojo.version.VersionInfo;
import com.limi88.financialplanner.ui.login.LoginActivity;
import com.limi88.financialplanner.ui.widget.LoadingDialog;
import com.limi88.financialplanner.util.Constants;

import javax.inject.Inject;
import javax.inject.Singleton;

import anet.channel.util.StringUtils;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by sunzhimeng on 10/27/16.
 */

public abstract class BaseLimiActivity extends BaseFragmentActivity {
    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }
    @Override
    protected void onNewIntent(Intent intent) {
// 拦截Intent，做相应处理
    }
    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return 0;
    }

    @Override
    protected void initViewsAndEvents() {
        initializeInjector();
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    private SharedPreferences mSharedPreferences;

    @Inject
    @Singleton
    LoginService mLoginService;
    @Inject
    @Singleton
    MineService mMineService;

    public void initializeInjector() {
        ActivityComponent component = ActivityComponent.Initializer.init(this);
        component.inject(this);
    }

    public void initCurrentUser() {
        initCurrentUser(null);
    }

    public void initCurrentUser(String token) {
        mSharedPreferences = getSharedPreferences(Constants.USER_FLAG, MODE_PRIVATE);
        String mToken = StringUtils.isBlank(token) ? mSharedPreferences.getString(Constants.TOKEN_FLAG, "") : token;
        mLoginService.checkValidation(mToken).subscribe(
                user -> {
                    if (user.isSuccess()) {
                        DataCenter.setSignedIn(mToken, user);
                    } else {
                        DataCenter.clearToken();
                        mSharedPreferences.edit().putString(Constants.TOKEN_FLAG, "").commit();
                        startActivity(new Intent(this,LoginActivity.class));
                        this.finish();
                    }
                }, throwable -> {
                    Log.i("LogApi", "-----" + throwable.getMessage());
                }
        );
    }

    public void backPre(View v) {
        onBackPressed();
    }

    public void checkVersion(){
       mLoginService.checkVersion()
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(versionInfo -> {

                  checkVersion(versionInfo);
              }, throwable -> {
                  Log.i("checkVersion", throwable.getMessage()+"");
              });

    }
    public void checkUserNewinfoHint() {
        if (Constants.CURRENT_TOKEN!="") {
            mMineService.getUserInfo(Constants.CURRENT_TOKEN)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(mUser -> {
                        if (mUser != null) {
                            DataCenter.setCurrentUser(mUser);
                            checkNewInfoHint(mUser.getData().getNewInfo(), DataCenter.isNewVersionExist());
                        }
                    }, throwable -> {
                        Log.i("checkVersion", throwable.getMessage());
                    });
        }else showNewInfoHint(false);

    }
    private void checkNewInfoHint(boolean newUserInfo,boolean newVersionInfo) {
        if (newUserInfo||newVersionInfo) {
            showNewInfoHint(true);
        }else showNewInfoHint(false);

    }

    private void checkVersion(VersionInfo info) {
        if (info.getData() != null &&
                !DataCenter.getAppVersion().equals(info.getData().getVersion())) {
            showUpdateUI(info);
            DataCenter.setNewVersionExist(true);
        } else {
            DataCenter.setNewVersionExist(false);
        }
        if (DataCenter.getCurrentUser() != null && DataCenter.getCurrentUser().getData() != null) {
            checkNewInfoHint(DataCenter.getCurrentUser().getData().getNewInfo(), DataCenter.isNewVersionExist());
        } else {
            checkNewInfoHint(false, DataCenter.isNewVersionExist());
        }
    }

    public abstract void showUpdateUI(VersionInfo info);
    public abstract void showNewInfoHint(boolean isShow);


}
