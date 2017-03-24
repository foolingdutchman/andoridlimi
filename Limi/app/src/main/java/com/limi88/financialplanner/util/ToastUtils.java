package com.limi88.financialplanner.util;

import android.content.Context;
import android.content.Loader;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by xu on 16/5/31.
 */
public class ToastUtils {

    public static Context mContext;

    private ToastUtils() {
    }

    public static void register(Context context) {
        mContext = context.getApplicationContext();
    }

    public static void showToast(int resId) {
        Toast.makeText(mContext, mContext.getString(resId), Toast.LENGTH_SHORT).show();
    }

    public static void showToast(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToastForTest(String msg) {
        Log.i("DEBUGING: ", msg);
//        showToast("DEBUGING：" + msg);
    }
}

