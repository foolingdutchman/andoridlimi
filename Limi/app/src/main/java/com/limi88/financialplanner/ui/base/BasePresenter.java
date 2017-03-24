package com.limi88.financialplanner.ui.base;

import android.support.annotation.NonNull;

/**
 * Created by xu on 16/5/31.
 */
public interface BasePresenter<T extends BaseView> {

    void attachView(@NonNull T view);
    void detachView();
}
