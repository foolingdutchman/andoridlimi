package com.limi88.financialplanner.ui.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.LoadingLayoutBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.limi88.financialplanner.R;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by hehao
 * Date on 16/11/14.
 * Email: hao.he@sunanzq.com
 */
public class LimiFooterView extends LoadingLayoutBase {
    static final String LOG_TAG = "PullToRefresh-LimiHeaderLayout";

    private LinearLayout mInnerLayout;


    private final TextView mSubHeaderText;

    private CharSequence mPullLabel;
    private CharSequence mRefreshingLabel;
    private CharSequence mReleaseLabel;
    private CharSequence mSuccessLabel;

    private ProgressBar mRefreshBar;
    private ImageView mPreparedImage;
    private ImageView mSuccessImage;

    public LimiFooterView(Context context) {
        this(context, PullToRefreshBase.Mode.PULL_FROM_END);
    }

    public LimiFooterView(Context context, PullToRefreshBase.Mode mode) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.limi_footer_loadinglayout, this);

        mInnerLayout = (LinearLayout) findViewById(R.id.fl_inner);
        mSubHeaderText = (TextView) mInnerLayout.findViewById(R.id.pull_to_refresh_sub_text);
        mRefreshBar = (ProgressBar) mInnerLayout.findViewById(R.id.pull_to_refresh_refreshing);
        mPreparedImage = (ImageView) mInnerLayout.findViewById(R.id.pull_to_refresh_prepared);
        mSuccessImage = (ImageView) mInnerLayout.findViewById(R.id.pull_to_refresh_success);

        LayoutParams lp = (LayoutParams) mInnerLayout.getLayoutParams();
        lp.gravity = mode == PullToRefreshBase.Mode.PULL_FROM_END ? Gravity.TOP : Gravity.BOTTOM;

        // Load in labels
        mPullLabel = "";
        mRefreshingLabel = "加载中...";
        mReleaseLabel = "";
        mSuccessLabel = "";
        reset();
    }

    @Override
    public final int getContentSize() {
        return mInnerLayout.getHeight();
    }

    @Override
    public final void pullToRefresh() {
        mSubHeaderText.setText(mPullLabel);
        mSuccessImage.setVisibility(GONE);
        mRefreshBar.setVisibility(GONE);
        if (mPreparedImage.getVisibility() != View.VISIBLE) {
            mPreparedImage.setVisibility(View.VISIBLE);
        }
        mPreparedImage.setSelected(false);

    }

    @Override
    public final void onPull(float scaleOfLayout) {
        scaleOfLayout = scaleOfLayout > 1.0f ? 1.0f : scaleOfLayout;

        if (mPreparedImage.getVisibility() != View.VISIBLE) {
            mPreparedImage.setVisibility(View.VISIBLE);
        }
        mPreparedImage.setVisibility(View.GONE);
        mSuccessImage.setVisibility(GONE);
        mRefreshBar.setVisibility(GONE);
//        //透明度动画
//        ObjectAnimator animAlphaP = ObjectAnimator.ofFloat(mPreparedImage, "alpha", -1, 1).setDuration(300);
//        animAlphaP.setCurrentPlayTime((long) (scaleOfLayout * 300));
//        ObjectAnimator animAlphaG = ObjectAnimator.ofFloat(mRefreshBar, "alpha", -1, 1).setDuration(300);
//        animAlphaG.setCurrentPlayTime((long) (scaleOfLayout * 300));
//
//        //缩放动画
//        ViewHelper.setPivotX(mPreparedImage, 0);  // 设置中心点
//        ViewHelper.setPivotY(mPreparedImage, 0);
//        ObjectAnimator animPX = ObjectAnimator.ofFloat(mPreparedImage, "scaleX", 0, 1).setDuration(300);
//        animPX.setCurrentPlayTime((long) (scaleOfLayout * 300));
//        ObjectAnimator animPY = ObjectAnimator.ofFloat(mPreparedImage, "scaleY", 0, 1).setDuration(300);
//        animPY.setCurrentPlayTime((long) (scaleOfLayout * 300));
//
//        ViewHelper.setPivotX(mRefreshBar, mRefreshBar.getMeasuredWidth());
//        ObjectAnimator animGX = ObjectAnimator.ofFloat(mRefreshBar, "scaleX", 0, 1).setDuration(300);
//        animGX.setCurrentPlayTime((long) (scaleOfLayout * 300));
//        ObjectAnimator animGY = ObjectAnimator.ofFloat(mRefreshBar, "scaleY", 0, 1).setDuration(300);
//        animGY.setCurrentPlayTime((long) (scaleOfLayout * 300));
    }

    @Override
    public final void refreshing() {
        mSubHeaderText.setText(mRefreshingLabel);

        mRefreshBar.setVisibility(VISIBLE);
        mPreparedImage.setVisibility(GONE);
        mSuccessImage.setVisibility(GONE);
//        animP = (AnimationDrawable) mPreparedImage.getDrawable();

//        animP.start();
//        if (mRefreshBar.getVisibility() == View.VISIBLE) {
//            mRefreshBar.setVisibility(View.INVISIBLE);
//        }
    }

    @Override
    public final void releaseToRefresh() {

        mSubHeaderText.setText(mReleaseLabel);
        if (mPreparedImage.getVisibility() != View.VISIBLE) {
            mPreparedImage.setVisibility(View.VISIBLE);
        }
        mPreparedImage.setVisibility(GONE);
        mPreparedImage.setSelected(true);
        mSuccessImage.setVisibility(GONE);
        mRefreshBar.setVisibility(GONE);
    }

    @Override
    public final void reset() {
        mSubHeaderText.setText(mSuccessLabel);
        mSuccessImage.setVisibility(VISIBLE);
        mRefreshBar.setVisibility(GONE);
        if (mRefreshBar.getVisibility() == View.VISIBLE) {
            mRefreshBar.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void setLastUpdatedLabel(CharSequence label) {
        mSubHeaderText.setText(label);
    }

    @Override
    public void setPullLabel(CharSequence pullLabel) {
        mPullLabel = pullLabel;
    }

    @Override
    public void setRefreshingLabel(CharSequence refreshingLabel) {
        mRefreshingLabel = refreshingLabel;
    }

    @Override
    public void setReleaseLabel(CharSequence releaseLabel) {
        mReleaseLabel = releaseLabel;
    }

    @Override
    public void setTextTypeface(Typeface tf) {
//        mHeaderText.setTypeface(tf);
    }
}
