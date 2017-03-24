package com.limi88.financialplanner.ui.base;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.github.piasy.safelyandroid.activity.StartActivityDelegate;
import com.github.piasy.safelyandroid.fragment.FragmentTransactionDelegate;
import com.github.piasy.safelyandroid.fragment.TransactionCommitter;
import com.hehao.library.base.BaseLazyFragment;

/**
 * Created by hehao
 * Date on 16/10/31.
 * Email: hao.he@sunanzq.com
 */
public  abstract class BaseSafeFragment extends BaseLazyFragment implements TransactionCommitter {

    private final FragmentTransactionDelegate mFragmentTransactionDelegate =
            new FragmentTransactionDelegate();

    protected boolean startActivitySafely(final Intent intent) {
        return StartActivityDelegate.startActivitySafely(this, intent);
    }

    protected boolean safeCommit(@NonNull FragmentTransaction transaction) {
        return mFragmentTransactionDelegate.safeCommit(this, transaction);
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentTransactionDelegate.onResumed();
    }

    @Override
    public boolean isCommitterResumed() {
        return isResumed();
    }


    @Override
    protected View getLoadingTargetView() {
        return null;
    }



    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }


}
