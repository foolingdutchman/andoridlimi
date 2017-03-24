package com.limi88.financialplanner.ui.base;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.view.View;

import com.github.piasy.safelyandroid.activity.StartActivityDelegate;
import com.github.piasy.safelyandroid.fragment.FragmentTransactionDelegate;
import com.github.piasy.safelyandroid.fragment.TransactionCommitter;
import com.hehao.library.base.BaseFragmentActivity;
import com.hehao.library.netstatus.NetUtils;

/**
 * Created by hehao
 * Date on 16/10/31.
 * Email: hao.he@sunanzq.com
 */
public  abstract class BaseSafeActivity extends BaseFragmentActivity implements TransactionCommitter {
    private volatile boolean mIsResumed = false;
    private final FragmentTransactionDelegate mFragmentTransactionDelegate =
            new FragmentTransactionDelegate();

    protected boolean safeCommit(@NonNull FragmentTransaction transaction) {
        return mFragmentTransactionDelegate.safeCommit(this, transaction);
    }

    protected boolean startActivitySafely(final Intent intent) {
        return StartActivityDelegate.startActivitySafely(this, intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsResumed = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsResumed = true;
        mFragmentTransactionDelegate.onResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsResumed = false;
    }

    @Override
    public boolean isCommitterResumed() {
        return mIsResumed;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }


}
