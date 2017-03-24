package com.limi88.financialplanner.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.limi88.financialplanner.ui.products.ProductSubPageFragment;

import java.util.List;

/**
 * Created by hehao on 16/9/12.
 */
public class ProductPageAdapter extends FragmentPagerAdapter {
    private List<ProductSubPageFragment> mFragmentList;

    public void setFragmentList(List<ProductSubPageFragment> mFragmentList) {
        this.mFragmentList = mFragmentList;
    }

    public ProductPageAdapter(FragmentManager fm) {
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
