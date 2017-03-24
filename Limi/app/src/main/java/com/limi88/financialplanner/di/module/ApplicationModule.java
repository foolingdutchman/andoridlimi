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

package com.limi88.financialplanner.di.module;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.limi88.financialplanner.util.RequestHelper;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Application Module.
 *
 * @author zhaosc
 * @version 1.0.0
 * @since 2016-04-19
 */

@Module
public class ApplicationModule {

    private final Application app;

    /**
     * 构造ApplicationModule.
     *
     * @param application app
     */
    public ApplicationModule(Application application) {
        this.app = application;
    }

    /**
     * 获得Application.
     *
     * @return app
     */
    @Provides
    @Singleton
    Application application() {
        return app;
    }

    /**
     * 获得Resources.
     *
     * @return resources
     */
    @Provides
    @Singleton
    Resources resources() {
        return app.getResources();
    }


    @Provides
    @Singleton
    Context provideApplicationContext(){
        return app.getApplicationContext();
    }

    @Provides
    @Singleton
    RequestHelper provideRequestHelper(Context mContext) {
        return new RequestHelper(mContext);
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttipClient(){
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        /**
         *
         * token 拦截器
         */
//        TokenInterceptor tokenInterceptor = new TokenInterceptor();

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);
        builder.addInterceptor(logInterceptor);
//        builder.addInterceptor(tokenInterceptor);
        return builder.build();
    }


}