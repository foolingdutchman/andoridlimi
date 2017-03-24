package com.limi88.financialplanner.ui.customers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hehao.library.base.BaseFragmentActivity;
import com.hehao.library.netstatus.NetUtils;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.di.component.ActivityComponent;
import com.limi88.financialplanner.pojo.clients.Clients;
import com.limi88.financialplanner.pojo.costumer.Province;
import com.limi88.financialplanner.ui.adapters.ClientPageAdapter;
import com.limi88.financialplanner.ui.mine.MineActivity;
import com.limi88.financialplanner.ui.widget.BaseBottomTab;
import com.limi88.financialplanner.util.Constants;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class CustomersActivity extends BaseFragmentActivity implements ClientContract.ClientView {

    @Bind(R.id.common_title)
    TextView titleText;
    @Bind(R.id.viewpagertab)
    SmartTabLayout mSmartTabLayout;
    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.root_view)
    LinearLayout mLinearLayout;
    private List<Fragment> mFragmentList;
    private ClientPageAdapter mPageAdapter;
    private CharSequence[] titles;
    private SweetAlertDialog mDialog;
    private CustomerMineFragment customerMineFragment;
    private CustomerRemindFragment customerRemindFragment;
    private Clients clients;
    private List<Province> provinces;
    @Bind(R.id.bottomtab)
    BaseBottomTab mBottomTab;
    private Integer[] bottomIcon = {R.drawable.ic_home_tab_home,
            R.drawable.ic_home_tab_products, R.drawable.ic_home_tab_clients,
            R.drawable.ic_home_tab_mine};
    @Inject
    ClientPresenter mPresenter;

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.attachView(this);
        loadDataToUI();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.detachView();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_customers;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
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
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        initializeInjector();

        titles = this.getResources().getTextArray(R.array.customers_page_name_array);
        titleText.setText(this.getString(R.string.home_title_clients));
        mDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
                .setTitleText("正在加载");
        initTab();
        initBottomTab();
        initPager();
        mSmartTabLayout.setViewPager(mViewPager);
    }

    private void initTab() {

        final LayoutInflater inflater = LayoutInflater.from(this);
        mSmartTabLayout.setCustomTabView((container, position, adapter) -> {
            View view = inflater.inflate(R.layout.customer_tab, container, false);
            TextView titleView = (TextView) view.findViewById(R.id.tv_tab);
            titleView.setText(titles[position]);
            AutoUtils.auto(view);
            return view;
        });
    }

    private void initBottomTab() {

        mBottomTab.initTab();
        mBottomTab.setRes(bottomIcon);
        mBottomTab.itemSelected(Constants.CLIENTS_FLAG);
        mBottomTab.setOnItemClickListener((view, obj, position) -> {
            mBottomTab.itemSelected(position);
            if (position != 4) {

                if (position == 3) {
                    this.startActivityForResult(new Intent(CustomersActivity.this, MineActivity.class), Constants.CLIENTS_FLAG);
                    this.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);

                } else if (position == 1) {
                    setResult(Constants.PRODUCTS_FLAG);
                    this.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
                } else if (position == 0) {
                    setResult(Constants.HOME_FLAG);
                    this.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
                }
                this.finish();
            }
        });
    }

    private void initPager() {

        mFragmentList = new ArrayList<Fragment>();
        customerMineFragment = new CustomerMineFragment();
        customerRemindFragment = new CustomerRemindFragment();
        customerMineFragment.setmClientView(this);
        customerMineFragment.setmPresenter(mPresenter);
        mFragmentList.add(customerMineFragment);
        mFragmentList.add(customerRemindFragment);
//        for (int i = 0; i < 5; i++) {
//            mFragmentList.add(new CustomerRemindFragment());
//        }
        mPageAdapter = new ClientPageAdapter(getSupportFragmentManager());
        mPageAdapter.setFragmentList(mFragmentList);
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
    }

    private void initializeInjector() {
        ActivityComponent component = ActivityComponent.Initializer.init(this);
        component.inject(this);
    }


    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }


    @Override
    public void showDataLoading() {

    }

    @Override
    public void hideDataLoading() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        if (customerMineFragment != null) {
            customerMineFragment.finishRefresh();
        }
    }

    @Override
    public void loadClients(Clients clients) {
        if (clients != null && clients.getData() != null) {
            this.clients = clients;
            customerMineFragment.setmReals(clients.getData().getReals());
            customerMineFragment.setSearchConditions(clients.getData().getSearchConditions());
            customerMineFragment.finishRefresh();
            customerRemindFragment.setData(clients.getData().getReminds());
        }
    }

    @Override
    public void loadDataToUI() {

        mPresenter.getClientsInfo();

    }

    @Override
    public void loadProvince(List<Province> provinces) {
        this.provinces = provinces;

    }

    @Override
    public void setDataLoaded(boolean isDataLoaded) {

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
