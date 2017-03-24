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



import android.app.Activity;

import com.hehao.library.base.BaseFragmentActivity;
import com.limi88.financialplanner.di.PerActivity;


import dagger.Module;
import dagger.Provides;

/**
 * Activity Module.
 *
 * @author zhaosc
 * @version 1.0.0
 * @since 2016-04-19
 */

@Module
public class ActivityModule {

    private final Activity activity;

    /**
     * ActivityModule的构造方法.
     *
     * @param baseActivity BaseActivity
     */
    public ActivityModule(Activity baseActivity) {
        this.activity = baseActivity;
    }

    /**
     * 提供BaseActivity.
     *
     * @return BaseActivity
     */
    @Provides
    @PerActivity
    Activity baseActivity() {
        return this.activity;
    }
}
