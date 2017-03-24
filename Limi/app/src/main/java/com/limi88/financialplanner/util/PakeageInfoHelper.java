package com.limi88.financialplanner.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

/**
 * Created by hehao
 * Date on 16/11/21.
 * Email: hao.he@sunanzq.com
 */
public class PakeageInfoHelper {
    private static PackageManager manager;
    private static PackageInfo packageInfo;
    private static Signature[] signatures;
    private static StringBuilder builder;
    private static String signature;
    private static String pkgname = Constants.PAG_NAME;
    public static String getSignature(Context context) {

            try {
                builder = new StringBuilder();
                manager=context.getPackageManager();
                /** 通过包管理器获得指定包名包含签名的包信息 **/
                packageInfo = manager.getPackageInfo(pkgname, PackageManager.GET_SIGNATURES);
                /******* 通过返回的包信息获得签名数组 *******/
                signatures = packageInfo.signatures;
                /******* 循环遍历签名数组拼接应用签名 *******/
                for (Signature signature : signatures) {
                    builder.append(signature.toCharsString());
                }
                /************** 得到应用签名 **************/
                signature = builder.toString();

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        return signature;
        }

}
