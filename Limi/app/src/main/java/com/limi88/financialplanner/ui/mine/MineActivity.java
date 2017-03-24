
package com.limi88.financialplanner.ui.mine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hehao.library.base.BaseFragmentActivity;
import com.hehao.library.netstatus.NetUtils;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.di.component.ActivityComponent;
import com.limi88.financialplanner.greendao.bean.AccountBean;
import com.limi88.financialplanner.pojo.user.User;
import com.limi88.financialplanner.ui.adapters.MineAdapter;
import com.limi88.financialplanner.ui.base.BaseWebViewActivity;
import com.limi88.financialplanner.ui.customers.CustomersActivity;
import com.limi88.financialplanner.ui.login.LoginActivity;
import com.limi88.financialplanner.ui.widget.BaseBottomTab;
import com.limi88.financialplanner.ui.widget.LoadingDialog;
import com.limi88.financialplanner.util.Constants;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import butterknife.Bind;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MineActivity extends BaseFragmentActivity implements MineContract.MineView {

    @Bind(R.id.common_title)
    TextView titleview;
    @Bind(R.id.iv_me_icon)
    ImageView mIconView;
    @Bind(R.id.iv_me_2Dcode)
    ImageView m2DcodeView;
    @Bind(R.id.tv_me_name)
    TextView mNameTextView;
    @Bind(R.id.rv_me_selections)
    RecyclerView mRecyclerView;
    @Bind(R.id.ll_logout)
    LinearLayout mLogOutView;
    @Bind(R.id.bottomtab)
    BaseBottomTab mBottomTab;
    private MineAdapter adapter;
    Bundle bundle;
    Intent mIntent;
    private LoadingDialog mDialog;
    private Integer[] bottomIcon = {R.drawable.ic_home_tab_home,
            R.drawable.ic_home_tab_products, R.drawable.ic_home_tab_clients,
            R.drawable.ic_home_tab_mine};
    private User user;
    @Inject
    MinePresenter mPresenter;
    SharedPreferences mSharedPreferences;

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mine;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.attachView(this);
        mPresenter.getUserInfo();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.detachView();
    }

    @Override
    protected void initViewsAndEvents() {

        initializeInjector();
        initTab();
        mDialog = new LoadingDialog(this);

        titleview.setText(R.string.home_title_mine);
        mLogOutView.setOnClickListener(v1 -> logout());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new MineAdapter(this, user);
        adapter.setOnItemClickListener((v, position, o) -> {
            if (bundle == null) {
                bundle = new Bundle();
            } else {
                bundle.clear();
            }
            if (mIntent == null) {
                mIntent = new Intent(this, BaseWebViewActivity.class);
            }
            bundle.putInt(Constants.WEB_PAGE_TAG, Constants.WEB_MINE_TAG);
            if (position == 3 || position == 4)
                bundle.putString(Constants.WEB_MINE_NAME, o.toString());
            switch (position) {
                case 0:
                    bundle.putString(Constants.WEB_MINE_LINK, Constants.MINE_AUTHENTICATION_URL);
                    mIntent.putExtras(bundle);
                    this.startActivityForResult(mIntent, Constants.MINE_FLAG);
                    break;
                case 1:
                    bundle.putString(Constants.WEB_MINE_LINK, Constants.USER_PROFILE_URL);
                    mIntent.putExtras(bundle);
                    this.startActivityForResult(mIntent, Constants.MINE_FLAG);
                    break;
                case 2:
                    bundle.putString(Constants.WEB_MINE_LINK, Constants.PRODUCTS_FAVOR_URL);
                    mIntent.putExtras(bundle);
                    this.startActivityForResult(mIntent, Constants.MINE_FLAG);
                    break;
                case 3:
//                     getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new LawFragment()).commit();
                    startActivity(new Intent(MineActivity.this, LawActivity.class));
                    break;
                case 4:
                    startActivityForResult(new Intent(MineActivity.this, FeedbackActivity.class), Constants.FEEDBACK_CODE);
//                     getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new FeedbackFragment()).commit();
                    break;
            }


        });
        mRecyclerView.setAdapter(adapter);
        mIconView.setOnClickListener(v -> {
            if (user != null && user.getData() != null) {
                if (mIntent != null) {
                    mIntent = null;
                }
                mIntent = new Intent(this, MeProfileActivity.class);
                if (bundle == null) {
                    bundle = new Bundle();
                } else {
                    bundle.clear();
                }
                bundle.putParcelable(Constants.USER_FLAG, user);
                mIntent.putExtras(bundle);
                startActivityForResult(mIntent, Constants.UPDATE_USER_INFO_CODE);

            }

//             meProfileFragment = new MeProfileFragment();
//             meProfileFragment.setUser(user);
//             meProfileFragment.setMineView(this);
//             getSupportFragmentManager().beginTransaction().replace(R.id.main_container
//                     , meProfileFragment, Constants.MINE_PROFILE_FRAGMENT_TAG).commit();

        });
        m2DcodeView.setOnClickListener(v -> {
            if (bundle == null) {
                bundle = new Bundle();
            } else {
                bundle.clear();
            }
            if (mIntent != null) {
                mIntent = null;
            }
            mIntent = new Intent(this, BaseWebViewActivity.class);
            bundle.putInt(Constants.WEB_PAGE_TAG, Constants.WEB_MINE_TAG);
            bundle.putString(Constants.WEB_MINE_NAME, getString(R.string.home_title_mine));
            bundle.putString(Constants.WEB_MINE_LINK, Constants.MINE_2DCODE_URL);
            mIntent.putExtras(bundle);
            this.startActivityForResult(mIntent, Constants.MINE_FLAG);

        });
    }

    private void initTab() {

        mBottomTab.initTab();
        mBottomTab.setRes(bottomIcon);
        mBottomTab.itemSelected(Constants.MINE_FLAG);
        mBottomTab.setOnItemClickListener((view, obj, position) -> {
            mBottomTab.itemSelected(position);
            if (position == 2) {
                this.startActivityForResult(new Intent(MineActivity.this, CustomersActivity.class), Constants.CLIENTS_FLAG);
                this.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);

            } else if (position == 1) {
                setResult(Constants.PRODUCTS_FLAG);
            } else if (position == 0) {
                setResult(Constants.HOME_FLAG);
            }
            this.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
            this.finish();
        });
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


    @Override
    public void showDataLoading() {
        mDialog.show();
    }

    @Override
    public void hideDataLoading() {
        mDialog.dismiss();
    }

    @Override
    public void loadAccount(AccountBean accountBean) {

        Glide.with(this)
                .load(accountBean.getUserAvatar())
                .placeholder(R.mipmap.ic_default_logo)
                .crossFade()
                .bitmapTransform(new CropCircleTransformation(this))
                .into(mIconView);
    }

    @Override
    public void loadUserInfoToUI(User user) {

        if (user != null && user.getData() != null) {
            this.user = user;
            adapter.setUser(user);
            adapter.notifyDataSetChanged();
            if (user.getData().getName() != null && user.getData().getName() != "") {
                mNameTextView.setText(user.getData().getName());
            } else
                mNameTextView.setText(user.getData().getPhone().substring(user.getData().getPhone().length() - 4));
            Glide.with(this)
                    .load(user.getData().getAvatarUrl())
                    .placeholder(R.mipmap.ic_default_logo)
                    .crossFade()
                    .bitmapTransform(new CropCircleTransformation(this))
                    .into(mIconView);
        }

    }

    private void logout() {
        DataCenter.clearToken();
        mSharedPreferences = this.getSharedPreferences(Constants.USER_FLAG, mContext.MODE_PRIVATE);
        mSharedPreferences.edit().putString(Constants.TOKEN_FLAG, "").commit();
        DataCenter.logout();
        CookieSyncManager.createInstance(mContext);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
        setResult(Constants.LOG_OUT_CODE);
        this.finish();
    }


    private void initializeInjector() {
        ActivityComponent component = ActivityComponent.Initializer.init(this);
        component.inject(this);
    }

    @Override
    public void loadUserInfoToUI() {

        mPresenter.getUserInfo();
    }

    @Override
    public void setDataLoaded(boolean isDataLoaded) {

    }

    @Override
    public void dismissUpDateUserUI() {
//         getSupportFragmentManager().beginTransaction().remove(meProfileFragment).commit();
    }

    @Override
    public void upLoadUerInfo(User user) {
        mPresenter.uploadUserInfo(user);
    }

    @Override
    public void gotoLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        this.finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MainApplication.getRefWatcher(this);
        refWatcher.watch(this);
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
