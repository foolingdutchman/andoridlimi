/*
 *
 *    Copyright 2016 zhaosc
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.limi88.financialplanner;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.hehao.library.action.creator.ActionCreator;
import com.hehao.library.base.BaseAppManager;
import com.hehao.library.dispatcher.Dispatcher;
import com.hehao.library.store.Store;

import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.tool.ProcessTool;
import com.limi88.financialplanner.ui.MainActivity;
import com.limi88.financialplanner.ui.widget.LoadingDialog;
import com.limi88.financialplanner.util.UmengAdTrackTest;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.limi88.financialplanner.di.component.ApplicationComponent;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.ToastUtils;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhy.autolayout.config.AutoLayoutConifg;

import javax.inject.Inject;
import javax.inject.Named;


/**
 * Flux Application
 *
 * @author hehao
 * @version 1.0.0
 * @since 2016-9-20
 */
public class MainApplication extends Application {

    private RefWatcher refWatcher;
    public static String logCacheDir;
    private ApplicationComponent component;
    private Bundle bundle;
    private Intent mIntent;

    public static RefWatcher getRefWatcher(Context context) {
        MainApplication application = (MainApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    @Override
    public void onTerminate() {
        exitApp();
        super.onTerminate();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AutoLayoutConifg.getInstance().useDeviceSize();
        String processName = ProcessTool.getProcessName(getApplicationContext());
        if (!BuildConfig.APPLICATION_ID.equals(processName)) {
            return;
        }
        initApplication();
        getLogCacheDir();
        ToastUtils.register(this);

        //设置LeakCanary
        refWatcher = LeakCanary.install(this);
        setQQX5();
        try {
            setUmeng();
        } catch (Exception e) {
            ToastUtils.showToastForTest(e.getMessage());
        }
    }

    private void setUmeng() {
        //设置友盟
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
//        MobclickAgent.UMAnalyticsConfig config = new MobclickAgent.UMAnalyticsConfig(this,
//                Constants.MOB_APP_KEY, Constants.MOB_CHANNEL_ID,
//                MobclickAgent.EScenarioType.E_UM_NORMAL);
//        MobclickAgent.startWithConfigure(config);
        MobclickAgent.setDebugMode(true);
//        Log.i("App", MobUtils.getDeviceInfo(this));

        //设置友盟推送
        PushAgent mPushAgent = PushAgent.getInstance(this);
//注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.i("PushApi", "success:-------" + deviceToken);
                DataCenter.setDeviceToken(deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.i("PushApi", "failed:-------" + s + "----" + s1);
            }
        });
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {


            @Override
            public void handleMessage(Context context, UMessage message) {
                Log.i("pushMessage", "massage:-----------------\n\t" + message.getRaw() + "\n\t-------------------------------");
                if (message.extra.get("msg_type").equals("default")) {
                    String url = message.url;
                    mIntent = new Intent(MainApplication.this, MainActivity.class);
                    mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    bundle = new Bundle();
                    bundle.putInt(Constants.WEB_PAGE_TAG, Constants.GO_DETAIL_TAG);
                    bundle.putString(Constants.WEB_PAGE_DETAIL_LINK, url);
                    mIntent.putExtras(bundle);
                    startActivity(mIntent);
                }
            }

            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                Log.i("pushMessage", "massage:-----------------\n\t" + msg.custom + "\n\t-------------------------------");

            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
//注册友盟第三方分享
        PlatformConfig.setWeixin("wxba62629c3e25666d", "b887fe403c3a150780352080b747c8bf");
        UMShareAPI.get(this);
        //配置x5浏览器信息
//搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        //TbsDownloader.needDownload(getApplicationContext(), false);

    //U-ADplus广效监测
        UmengAdTrackTest umengAdTrackTest =new UmengAdTrackTest();
        umengAdTrackTest.sendMessage(this,getResources().getString(R.string.umeng_key));
    }

    private void setQQX5() {
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                Log.e("0912", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub

            }
        };
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                Log.d("0912", "onDownloadFinish");
            }

            @Override
            public void onInstallFinish(int i) {
                Log.d("0912", "onInstallFinish");
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.d("0912", "onDownloadProgress:" + i);
            }
        });
        QbSdk.allowThirdPartyAppDownload(true);
        QbSdk.initX5Environment(getApplicationContext(), QbSdk.WebviewInitType.FIRSTUSE_AND_PRELOAD, cb);
    }

    /**
     * 如果存在SD卡则将缓存写入SD卡,否则写入手机内存
     */
    public String getLogCacheDir() {

        if (getApplicationContext().getExternalCacheDir() != null && ExistSDCard()) {
            logCacheDir = getApplicationContext().getExternalCacheDir().toString();
        } else {
            logCacheDir = getApplicationContext().getCacheDir().toString();
        }
        return logCacheDir;
    }

    private boolean ExistSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    //初始化Application
    private void initApplication() {
        initInjector();

    }

    private void initInjector() {
        component = ApplicationComponent.Initializer.init(this);
        component.inject(this);
    }

    /**
     * 获得ApplicationComponent.
     *
     * @return component
     */
    public ApplicationComponent getComponent() {
        return component;
    }

    public void exitApp() {

        BaseAppManager.getInstance().clear();
        System.gc();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}