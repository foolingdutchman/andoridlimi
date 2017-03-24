package com.limi88.financialplanner.ui.customers;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hehao.library.base.BaseLazyFragment;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.pojo.clients.Data;
import com.limi88.financialplanner.ui.MainActivity;
import com.limi88.financialplanner.ui.customers.ClientContract;
import com.limi88.financialplanner.ui.customers.ClientPresenter;
import com.limi88.financialplanner.ui.customers.CustomerMineFragment;
import com.limi88.financialplanner.ui.customers.CustomerRemindFragment;
import com.limi88.financialplanner.ui.widget.LoadingDialog;
import com.limi88.financialplanner.util.PermissionUtils;
import com.limi88.financialplanner.util.ToastUtils;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.di.component.FragmentComponent;
import com.limi88.financialplanner.pojo.clients.Clients;
import com.limi88.financialplanner.pojo.costumer.Province;
import com.limi88.financialplanner.ui.adapters.ClientPageAdapter;
import com.limi88.financialplanner.util.Constants;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import cn.bingoogolapple.badgeview.BGABadgeFrameLayout;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by hehao
 * Date on 16/9/20.
 * Email: hao.he@limi88.com
 */
public class CustomersFragment extends BaseLazyFragment implements View.OnClickListener, ClientContract.ClientView {

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
    private LoadingDialog mDialog;
    private CustomerMineFragment customerMineFragment;
    private CustomerRemindFragment customerRemindFragment;
    private Clients clients;
    private List<Province> provinces;
    private  boolean isDataLoaded=false;
    private BGABadgeFrameLayout mBadgeLayout;
    @Inject
    ClientPresenter mPresenter;

    @Override
    protected void onFirstUserVisible() {
        if (!isDataLoaded) {
            loadDataToUI();
        }
    }

    @Override
    protected void onUserVisible() {
//        if (!isDataLoaded) {
        loadDataToUI();
//        }
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainScreen");
        mPresenter.attachView(this);

        mLinearLayout.setVisibility(View.VISIBLE);
        ToastUtils.showToastForTest("isSigned:" + DataCenter.isSigned());

    }


    @Override
    public void onPause() {
        super.onPause();
        mPresenter.detachView();
        MobclickAgent.onPageEnd("MainScreen");
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        initializeInjector();

        titles = getActivity().getResources().getTextArray(R.array.customers_page_name_array);
        titleText.setText(getActivity().getString(R.string.home_title_clients));
        mDialog =((MainActivity)getActivity()).getLoadingDialog();
        initTab();
        initPager();
        mSmartTabLayout.setViewPager(mViewPager);
        mBadgeLayout= (BGABadgeFrameLayout) mSmartTabLayout.getTabAt(1).findViewById(R.id.icon_badgeView);
    }

    private void initTab() {

        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        mSmartTabLayout.setCustomTabView((container, position, adapter) -> {
            View view = inflater.inflate(R.layout.customer_tab, container, false);
            TextView titleView = (TextView) view.findViewById(R.id.tv_tab);
            titleView.setText(titles[position]);
            AutoUtils.auto(view);
            return view;
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
        mPageAdapter = new ClientPageAdapter(getChildFragmentManager());
        mPageAdapter.setFragmentList(mFragmentList);
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
    }

    private void initializeInjector() {
        FragmentComponent component = FragmentComponent.Initializer.init(this);
        component.inject(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_customers;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void showDataLoading() {
        if (mDialog != null) {
            mDialog.show();
        }

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
        hideDataLoading();
        if (clients!=null&&clients.getData()!=null) {
            this.clients = clients;
            customerMineFragment.setmReals(clients.getData().getReals());
            customerMineFragment.setSearchConditions(clients.getData().getSearchConditions());
            customerMineFragment.finishRefresh();
            customerRemindFragment.setData(clients.getData().getReminds());
            if (clients.getData().getReminds()!=null&&
                    clients.getData().getReminds().getToday().size()
                            +clients.getData().getReminds().getTomorrow().size()
                            +clients.getData().getReminds().getNextWeek().size()>0) {
                mBadgeLayout.setPadding(0,0,24,0);
                mBadgeLayout.showCirclePointBadge();
            }else  {
                mBadgeLayout.hiddenBadge();
            }

            setDataLoaded(true);
        }
    }

    @Override
    public void loadDataToUI() {
        showDataLoading();
        mPresenter.getClientsInfo();
    }

    @Override
    public void loadProvince(List<Province> provinces) {
        this.provinces = provinces;

    }

    @Override
    public void setDataLoaded(boolean isDataLoaded) {
        this.isDataLoaded=isDataLoaded;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MainApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
