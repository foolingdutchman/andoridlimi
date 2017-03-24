package com.limi88.financialplanner.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.RelativeLayout;

import com.limi88.financialplanner.R;
import com.limi88.financialplanner.pojo.version.VersionInfo;
import com.limi88.financialplanner.ui.adapters.BasePageAdapter;
import com.limi88.financialplanner.ui.base.BaseLimiActivity;
import com.limi88.financialplanner.util.Constants;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WelcomeActivity extends BaseLimiActivity {
    @Bind(R.id.vp_guide)
    ViewPager mVpGuide;
    @Bind(R.id.guide_tab)
    SmartTabLayout mTabLayout;

    @Bind(R.id.ll_welcome_bg)
    RelativeLayout mLayout;
    private BasePageAdapter mAdapter;
    private int pagesNum =4;
    private List<Fragment> mFragmentList;
    private int[] pagesRes = {R.mipmap.bg_guide_1v2,
            R.mipmap.bg_guide_2v2,
            R.mipmap.bg_guide_3v2,
            R.mipmap.bg_guide_4v2
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        ButterKnife.bind(this);
        initPage();

    }

    @Override
    public void showUpdateUI(VersionInfo info) {

    }

    @Override
    public void showNewInfoHint(boolean isShow) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_welcome;
    }


    private void initPage() {
        mFragmentList = new ArrayList<>();
        for (int i = 0; i < pagesNum; i++) {
            GuidePageFragment pageFragment = new GuidePageFragment();
            pageFragment.setPageNum(i);
            pageFragment.setTotalPage(pagesNum);
            pageFragment.setImgRes(pagesRes[i]);
            mFragmentList.add(pageFragment);
        }
        mAdapter=new BasePageAdapter(getSupportFragmentManager());
        mAdapter.setFragmentList(mFragmentList);
        mVpGuide.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


}
