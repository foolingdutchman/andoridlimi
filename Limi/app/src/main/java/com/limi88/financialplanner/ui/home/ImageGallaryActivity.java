package com.limi88.financialplanner.ui.home;

import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hehao.library.base.BaseFragmentActivity;
import com.hehao.library.netstatus.NetUtils;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.ui.adapters.ImagePageAdapter;

import java.util.List;

import butterknife.Bind;

public class ImageGallaryActivity extends BaseFragmentActivity {
    @Bind(R.id.vp_image_gallary)
    ViewPager mViewPager;
    @Bind(R.id.tv_indicator)
    TextView mIndicatorText;
    @Bind(R.id.tv_header_back)
    TextView mLeftText;
    @Bind(R.id.ll_left_btn)
    LinearLayout mLeftAction;
    private List<String>mImageList;
    private int position;
    private ImagePageAdapter mAdapter;

    @Override
    protected void getBundleExtras(Bundle extras) {
       mImageList= extras.getStringArrayList("images");
        position=extras.getInt("position");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_image_gallary;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        mLeftAction.setVisibility(View.VISIBLE);
        mLeftText.setVisibility(View.VISIBLE);
        mLeftText.setText("返回");
        mIndicatorText.setText((position+1)+"/"+mImageList.size());

        mAdapter=new ImagePageAdapter(this,mImageList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mIndicatorText.setText((position+1)+"/"+mImageList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setCurrentItem(position);

    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

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

    public void backPre(View view) {
        this.finish();
    }
}
