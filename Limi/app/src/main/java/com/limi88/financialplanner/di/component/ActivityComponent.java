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



import android.app.Activity;

import com.hehao.library.base.BaseFragmentActivity;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.di.PerActivity;
import com.limi88.financialplanner.di.module.ActivityModule;
import com.limi88.financialplanner.ui.base.BaseLimiActivity;
import com.limi88.financialplanner.ui.MainActivity;
import com.limi88.financialplanner.ui.customers.CustomerTagsActivity;
import com.limi88.financialplanner.ui.customers.CustomersActivity;
import com.limi88.financialplanner.ui.customers.NewCustomersActivity;
import com.limi88.financialplanner.ui.customers.ProvincePickActivity;
import com.limi88.financialplanner.ui.login.BindWXActivity;
import com.limi88.financialplanner.ui.login.LoginActivity;
import com.limi88.financialplanner.ui.mine.FeedbackActivity;
import com.limi88.financialplanner.ui.mine.MeProfileActivity;
import com.limi88.financialplanner.ui.mine.MineActivity;
import com.limi88.financialplanner.ui.splash.PromotionActivity;


import dagger.Component;

/**
 * ActivityComponent
 *
 * @author zhaosc
 * @version 1.0.0
 * @since 2016-04-19
 */
@PerActivity
@Component(
        dependencies = ApplicationComponent.class,
        modules = ActivityModule.class
)
public interface ActivityComponent {

    /**
     * 注入TodoListActivity.
     *
     * @param baseActivity TodoListActivity
     */

    void inject(MainActivity baseActivity);
    void inject(LoginActivity baseActivity);
    void inject(BaseLimiActivity baseActivity);
    void inject(MineActivity baseActivity);
    void inject(FeedbackActivity baseActivity);
    void inject(MeProfileActivity baseActivity);
    void inject(CustomersActivity baseActivity);
    void inject(NewCustomersActivity baseActivity);
    void inject(ProvincePickActivity baseActivity);
    void inject(CustomerTagsActivity baseActivity);
    void inject(PromotionActivity baseActivity);
    void inject(BindWXActivity baseActivity);



    /**
     * 获得Activity
     *
     * @return BaseActivity.
     */
    Activity baseActivity();

    /**
     * ActivityComponent的Initializer.
     */
    public static final class Initializer {
        /**
         * 初始化ActivityComponent.
         *
         * @param baseActivity baseActivity
         * @return ActivityComponent
         */
        public static ActivityComponent init(Activity baseActivity) {
            return DaggerActivityComponent.builder()
                    .applicationComponent(((MainApplication) baseActivity.getApplication()).getComponent())
                    .activityModule(new ActivityModule(baseActivity))
                    .build();
        }
    }
}
