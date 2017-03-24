package com.limi88.financialplanner.api;

import android.os.Build;

import com.limi88.financialplanner.BuildConfig;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.PakeageInfoHelper;
import com.limi88.financialplanner.util.RequestHelper;
import com.limi88.financialplanner.util.SecurityUtils;
import com.limi88.financialplanner.util.ToastUtils;
import com.limi88.financialplanner.util.ValidatorUtils;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hehao on 16/9/8.
 */
public final class UserAgentInterceptor implements Interceptor {

    private static final String USER_AGENT_HEADER_NAME = "User-Agent";
    private static final String USER_KEY="limikey";
    private static final String SIGNATURE="signature";
    private static final String TONCE="tonce";
    private static final String SHX1_CODE="code";
    private String tonce;
    private String code;
    private final String userAgentHeaderValue;
    private String signature;

    public UserAgentInterceptor(String userAgentHeaderValue,String tonce,String code,String signature) {
        this.code=code;
        this.tonce=tonce;
        this.userAgentHeaderValue = userAgentHeaderValue;
        this.signature=signature;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request originalRequest = chain.request();

        signature=SecurityUtils.getSHA1(signature);
        final Request requestWithUserAgent = originalRequest.newBuilder()
                .removeHeader(USER_AGENT_HEADER_NAME)
                .addHeader(USER_AGENT_HEADER_NAME, getUserAgentHeaderValue() + userAgentHeaderValue)
                .addHeader(USER_KEY, Constants.CURRENT_TOKEN)
                .addHeader(SIGNATURE, signature)
                .addHeader(TONCE, tonce)
                .addHeader(SHX1_CODE, code)
                .build();
        return chain.proceed(requestWithUserAgent);
    }

    /*
   * 获取SDK版本号
   * */
    public String getUserAgentHeaderValue() {
        return "(" +android.os.Build.MODEL+";"
                + Build.BRAND+";"
                +"Android"+Build.VERSION.SDK+";"
                + BuildConfig.APPLICATION_ID+";"
                +BuildConfig.VERSION_NAME+";"
                +BuildConfig.VERSION_CODE+
                ")";
    }
}
