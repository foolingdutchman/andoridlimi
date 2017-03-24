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

package com.limi88.financialplanner.di.component;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.hehao.library.action.creator.ActionCreator;
import com.hehao.library.dispatcher.Dispatcher;
import com.hehao.library.store.Store;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.api.SecurityInterceptor;
import com.limi88.financialplanner.api.clients.ClientService;
import com.limi88.financialplanner.api.mine.MineService;
import com.limi88.financialplanner.di.module.ApplicationModule;
import com.limi88.financialplanner.greendao.dao.AccountBeanDao;
import com.limi88.financialplanner.greendao.dao.SearchDao;
import com.limi88.financialplanner.util.RequestHelper;
import com.limi88.financialplanner.api.account.LoginService;
import com.limi88.financialplanner.api.products.ProductService;
import com.limi88.financialplanner.di.ForApplication;
import com.limi88.financialplanner.di.module.DBModule;


import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Component;

/**
 * ApplicationComponent
 *
 * @author zhaosc
 * @version 1.0.0
 * @since 2016-04-19
 */
@ForApplication
@Singleton
@Component(

        modules = {ApplicationModule.class, DBModule.class}
)
public interface ApplicationComponent {

    void inject(MainApplication application);

    //暴露给对象图

    /**
     * 获得Application.
     *
     * @return Application
     */
    Application application();

    /**
     * 获得Resources.
     *
     * @return Resources
     */
    Resources resources();



    /**
     * 获得actionCreator.
     *
     * @return ActionCreator
     */

    Context getContext();

    RequestHelper getRequestHelper();

    LoginService getLoginService();

    ClientService getClientService();

    MineService getMineService();

    ProductService getProductService();

    AccountBeanDao getAccountDao();

    SearchDao getSearchDao();


    /**
     * ApplicationComponent的Initializer.
     */
    public static final class Initializer {
        /**
         * 初始化ApplicationComponent.
         *
         * @param application application
         * @return ApplicationComponent
         */
        public static ApplicationComponent init(Application application) {
            return DaggerApplicationComponent.builder().dBModule(new DBModule())
                    .applicationModule(new ApplicationModule(application))
                    .build();
        }
    }
}