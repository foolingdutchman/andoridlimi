package com.limi88.financialplanner.api;

import com.limi88.financialplanner.BuildConfig;
import com.limi88.financialplanner.util.RequestHelper;
import com.limi88.financialplanner.util.SecurityUtils;
import com.limi88.financialplanner.util.ValidatorUtils;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/7/6.
 */
public class ServiceFactory {

    static OkHttpClient client;
//    static Retrofit retrofit;
    @Inject
    SecurityInterceptor mInterceptor;


    public static <T> T creatService(Class<T> serviceClass, String ENDPOINT,String code,String tonce,String signature) {
        if (client == null){

            client = new OkHttpClient.Builder()
                    .addInterceptor(new UserAgentInterceptor("limi88 -v"+ BuildConfig.VERSION_NAME,tonce,code,signature))
                    .addInterceptor(new LoggingInterceptor())
                    .build();
        }

       Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(serviceClass);
    }
    public static <T> T creatProgresService(Class<T> serviceClass, String ENDPOINT,
                                            String code,String tonce,String signature
            ,ProgressRequestListener progressRequestListener) {
        if (client == null){
            client = new OkHttpClient.Builder()
                    .addInterceptor(new UserAgentInterceptor("limi88 v-1.0.1",code,tonce,signature))
                    .addInterceptor(new UpLoadProgressInterceptor(progressRequestListener))
                    .addInterceptor(new LoggingInterceptor())
                    .build();
        }

       Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(serviceClass);
    }
}
