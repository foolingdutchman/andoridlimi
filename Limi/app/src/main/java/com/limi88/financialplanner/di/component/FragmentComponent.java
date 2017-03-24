package com.limi88.financialplanner.di.component;


import com.hehao.library.base.BaseLazyFragment;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.di.PerFragment;
import com.limi88.financialplanner.di.module.ActivityModule;
import com.limi88.financialplanner.di.module.FragmentModule;
import com.limi88.financialplanner.ui.MainActivity;

import com.limi88.financialplanner.ui.customers.CustomersFragment;
import com.limi88.financialplanner.ui.home.HomeFragment;
import com.limi88.financialplanner.ui.home.HomeSubPageFragment;
import com.limi88.financialplanner.ui.home.ServiceToolsFragment;

import com.limi88.financialplanner.ui.mine.MineFragment;
import com.limi88.financialplanner.ui.products.ProductFragment;
import com.limi88.financialplanner.ui.products.SearchFragment;


import dagger.Component;

/**
 * Created by Administrator on 2016/7/13.
 */
@PerFragment

@Component(
        dependencies = ApplicationComponent.class,
        modules = {FragmentModule.class
                , ActivityModule.class}
)
public interface FragmentComponent {

    /**
     * 注入Fragment.
     *
     * @param newsPageFragment NewsPageFragment
     */
    void inject(HomeFragment newsPageFragment);

    void inject(ProductFragment newsPageFragment);

    void inject(SearchFragment newsPageFragment);

    void inject(MineFragment newsPageFragment);

    void inject(CustomersFragment newsPageFragment);

    void inject(ServiceToolsFragment newsPageFragment);
    void inject(HomeSubPageFragment homeSubPageFragment);


    //    void inject(LogInFragment newsPageFragment);
//    void inject(OverAllVideosFragment newsPageFragment);
    void inject(BaseLazyFragment newsPageFragment);

    /**
     * 获得Fragment
     *
     * @return Fragment.
     */
    BaseLazyFragment newsPageFragment();

    /**
     * ActivityComponent的Initializer.
     */
    public static final class Initializer {
        /**
         * 初始化NewsPageFragment.
         *
         * @param newsPageFragment NewsPageFragment
         * @return fragmentComponent FragmentComponent
         */
        public static FragmentComponent init(BaseLazyFragment newsPageFragment) {
            return DaggerFragmentComponent.builder()
                    .applicationComponent(((MainApplication) newsPageFragment.getActivity().getApplication()).getComponent())
                    .activityModule(new ActivityModule((MainActivity) newsPageFragment.getActivity()))
                    .fragmentModule(new FragmentModule(newsPageFragment))
                    .build();
        }
    }
}
