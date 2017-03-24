package com.limi88.financialplanner.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by hehao
 * Date on 16/10/14.
 * Email: hao.he@sunanzq.com
 */
public class BasePageAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragmentList;

    public void setFragmentList(List<Fragment> mFragmentList) {
        this.mFragmentList = mFragmentList;
    }

    public BasePageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}


