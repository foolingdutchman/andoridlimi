package com.limi88.financialplanner.ui.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hehao.library.base.BaseFragmentActivity;
import com.hehao.library.netstatus.NetUtils;
import com.limi88.financialplanner.R;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;

public class LawActivity extends BaseFragmentActivity {


    @Bind(R.id.ll_left_btn)
    LinearLayout mLlLeftBtn;
    @Bind(R.id.common_title)
    TextView mCommonTitle;
    @Bind(R.id.tv_header_back)
    TextView mLeftTitle;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_law;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        mLlLeftBtn.setVisibility(View.VISIBLE);
        mCommonTitle.setText("用户使用协议");
        mCommonTitle.setVisibility(View.GONE);
        mLeftTitle.setVisibility(View.VISIBLE);
        mLeftTitle.setText("用户使用协议");
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
        finish();
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
