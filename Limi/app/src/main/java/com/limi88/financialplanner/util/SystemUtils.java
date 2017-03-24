package com.limi88.financialplanner.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.limi88.financialplanner.ui.base.BaseWebViewActivity;

import java.util.List;

/**
 * Created by liangzili on 15/8/3.
 */
public class SystemUtils {
    /**
     * 判断应用是否已经启动
     * @param context 一个context
     * @param packageName 要判断应用的包名
     * @return boolean
     */
    public static boolean isAppAlive(Context context, String packageName){
        ActivityManager activityManager =
                (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();
        for(int i = 0; i < processInfos.size(); i++){
            if(processInfos.get(i).processName.equals(packageName)){
                Log.i("NotificationLaunch",
                        String.format("the %s is running, isAppAlive return true", packageName));
                return true;
            }
        }
        Log.i("NotificationLaunch",
                String.format("the %s is not running, isAppAlive return false", packageName));
        return false;
    }

    public static void startDetailActivity(Context context, String name,
                                           String url){
        Intent intent = new Intent(context, BaseWebViewActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString(Constants.WEB_PAGE_DETAIL_NAME, name);
        bundle.putString(Constants.WEB_PAGE_DETAIL_LINK, url);
        bundle.putInt(Constants.WEB_PAGE_TAG, Constants.GO_DETAIL_TAG);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
