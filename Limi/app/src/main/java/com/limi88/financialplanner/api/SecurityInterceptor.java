package com.limi88.financialplanner.api;

import android.os.Build;

import com.limi88.financialplanner.BuildConfig;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.RequestHelper;
import com.limi88.financialplanner.util.SecurityUtils;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hehao
 * Date on 16/11/4.
 * Email: hao.he@sunanzq.com
 */
public class SecurityInterceptor implements Interceptor {

    private static final String USER_AGENT_HEADER_NAME = "User-Agent";
    private static final String USER_KEY = "limikey";
    private static final String TONCE = "tonce";
    private static final String SHX1_CODE = "code";
    private String tonce;
    private String code;

    private RequestHelper mHelper;

    @Inject
    public SecurityInterceptor(RequestHelper mHelper) {
        this.mHelper = mHelper;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request originalRequest = chain.request();
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        final Request requestWithUserAgent = originalRequest.newBuilder()
                .addHeader(TONCE, tonce)
                .addHeader(SHX1_CODE, code)
                .build();
        return chain.proceed(requestWithUserAgent);
    }

    /*
   * 获取SDK版本号
   * */
    public String getUserAgentHeaderValue() {
        return "(" + android.os.Build.MODEL + ";"
                + Build.BRAND + ";"
                + "Android" + Build.VERSION.SDK + ";"
                + BuildConfig.APPLICATION_ID + ";"
                + BuildConfig.VERSION_NAME + ";"
                + BuildConfig.VERSION_CODE +
                ")";
    }
}
