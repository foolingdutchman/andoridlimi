package com.limi88.financialplanner.di.module;



import com.hehao.library.base.BaseLazyFragment;
import com.limi88.financialplanner.di.PerFragment;


import dagger.Module;
import dagger.Provides;

/**
 * Created by Administrator on 2016/7/13.
 */
@Module
public class FragmentModule {
    private final BaseLazyFragment baseLazyFragment;

    /**
     * FragmentModule的构造方法.
     *
     * @param baseLazyFragment BaseLazyFragment
     */
    public FragmentModule(BaseLazyFragment baseLazyFragment) {
        this.baseLazyFragment = baseLazyFragment;
    }

    /**
     * 提供BaseActivity.
     *
     * @return BaseActivity
     */
    @Provides
    @PerFragment
    BaseLazyFragment baseLazyFragment() {
        return this.baseLazyFragment;
    }
}
