package com.limi88.financialplanner.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2016/7/13.
 */

@Scope
@Retention(RUNTIME)
public @interface PerFragment {
}