package com.limi88.financialplanner.di.module;

import android.content.Context;


import com.limi88.financialplanner.greendao.dao.AccountBeanDao;
import com.limi88.financialplanner.greendao.dao.SearchDao;
import com.limi88.financialplanner.greendao.dao.DaoMaster;
import com.limi88.financialplanner.greendao.dao.DaoSession;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by xu on 16/5/31.
 */

@Module
public class DBModule {

    @Provides
    @Singleton
    DaoMaster.DevOpenHelper provideDevOpenHelper(Context context){
        return new DaoMaster.DevOpenHelper(context,"com.limi88.com.limi88.financialplanner.db",null);
    }

    @Provides
    @Singleton
    DaoMaster provideDaoMaster(DaoMaster.DevOpenHelper helper){
        return new DaoMaster(helper.getWritableDatabase());
    }

    @Provides
    @Singleton
    DaoSession provideDaoSession(DaoMaster daoMaster){
        return daoMaster.newSession();
    }

    @Provides
    @Singleton
    AccountBeanDao getAccountDao(DaoSession session){
        return session.getAccountBeanDao();
    }

    @Provides
    @Singleton
    SearchDao getSearchDao(DaoSession session){
        return session.getSearchDao();
    }
}
