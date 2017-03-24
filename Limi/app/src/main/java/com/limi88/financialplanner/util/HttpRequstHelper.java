package com.limi88.financialplanner.util;

import com.limi88.financialplanner.BuildConfig;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.api.LoggingInterceptor;
import com.limi88.financialplanner.api.UserAgentInterceptor;
import com.limi88.financialplanner.di.component.ApplicationComponent;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hehao
 * Date on 16/9/26.
 * Email: hao.he@limi88.com
 */
public class HttpRequstHelper  {

    private static OkHttpClient okHttpClient;
    public static final MediaType MediaTypeJSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final MediaType MEDIA_TYPE_JPEG = MediaType.parse("image/jpeg");
    private static final MediaType MEDIA_TYPE_ZIP = MediaType.parse("zip");
    public static int REQUEST_TIME_OUT = 30;
    public static int WRITE_TIME_OUT = 30;
    public static int READ_TIME_OUT = 30;
    public static String USER_AGENT = "Android OkHttp With ZMAsyncHttp 0.0.3";
    private String code;
    private String tonce;


    @Inject
    RequestHelper requestHelper;
    @Inject
    ApplicationComponent mApplicationComponent;
    private static OkHttpClient getInstance(String signature){
       String tonce=String.valueOf(System.currentTimeMillis() / 1000L);
        String code=  SecurityUtils.getHMACSHA1(tonce);
        if (okHttpClient == null)

            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(REQUEST_TIME_OUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                    .addInterceptor(new UserAgentInterceptor("limi88 -v"+ BuildConfig.VERSION_NAME
                            ,tonce,code,signature))
                    .addInterceptor(new LoggingInterceptor())
                    .build();
        return okHttpClient;
    }

    public static String get(String url,String signature) {
        Request request = new Request.Builder().url(url)
                .addHeader("Limikey",Constants.CURRENT_TOKEN)

                .build();
        return getResponseJSON(request,signature);
    }

    public static String post(String url, String params,String signature) {
        return sendRequestBody(url, HTTPMethod.POST, params,signature);
    }

    public static String put(String url, String params,String signature) {
        return sendRequestBody(url, HTTPMethod.PUT, params,signature);
    }

    public static String delete(String url,String signature) {
        return sendRequestBody(url, HTTPMethod.DELETE,"",signature);
    }

    private static String sendRequestBody(String url, String methodName, String params,String signature){
        RequestBody body = RequestBody.create(MediaTypeJSON, params);
        return sendRequest(url, methodName, body,signature);
    }

    public static String sendMultiRequestBody(String url, String methodName,
                                              Map<String, String> textParams,
                                              Map<String, String> fileParams
    ,String signature){
        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        requestBodyBuilder.addFormDataPart("ZMRequestMethod", methodName);

        if (textParams != null) {
            for (String key : textParams.keySet()) {
                String value = textParams.get(key);
                if (null != value && "".equals(value)){
                    if (key.contains("[]")) {
                        String[] values = value.split(",");
                        for (int i = 0; i < values.length; i++)
                            requestBodyBuilder.addFormDataPart(key, values[i]);
                    } else
                        requestBodyBuilder.addFormDataPart(key, value);
                }
            }
        }
        if (fileParams != null){
            for (String key:fileParams.keySet()){
                String fileName = fileParams.get(key);
                requestBodyBuilder.addFormDataPart(key, fileName, RequestBody.create(getMineTypeFromFileName(fileName), new File(fileName)));
            }
        }
        RequestBody body = requestBodyBuilder.build();
        return sendRequest(url, methodName, body,signature);
    }

    private static MediaType getMineTypeFromFileName(String fileName){
        if (fileName.endsWith(".png")) {
            return MEDIA_TYPE_PNG;
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return MEDIA_TYPE_JPEG;
        } else {
            return MEDIA_TYPE_ZIP;
        }
    }

    private static String sendRequest(String url, String methodName, RequestBody body,String signature){
        Request request;
        Request.Builder builder = new Request.Builder().url(url)
                .header("User-Agent", USER_AGENT);
        if (methodName.equals(HTTPMethod.DELETE)){
            request = builder.delete(body).build();
        } else if (methodName.equals(HTTPMethod.PUT)){
            request = builder.put(body).build();
        } else {
            request = builder.post(body).build();
        }
        return getResponseJSON(request,signature);
    }

    // 所有请求的返回处理函数
    private static String getResponseJSON(Request request,String signature){
        String res = "";
        try {
            Response response = getInstance(signature).newCall(request).execute();
            res = response.body().string();
        } catch (IOException e){
            e.printStackTrace();
        }
        return res;
    }


    public static class HTTPMethod {
       public final static  String DELETE="DELETE";
       public final static  String PUT="PUT";
       public final static String POST="POST";
       public final static String GET="GET";

    }
}
