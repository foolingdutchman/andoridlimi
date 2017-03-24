package com.limi88.financialplanner.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.hehao.library.netstatus.NetUtils;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.pojo.version.VersionInfo;
import com.limi88.financialplanner.ui.adapters.MainPagerAdapter;
import com.limi88.financialplanner.ui.adapters.ViewpagerScroller;
import com.limi88.financialplanner.ui.base.BackHandlerHelper;
import com.limi88.financialplanner.ui.base.BaseLimiActivity;
import com.limi88.financialplanner.ui.base.BaseWebViewActivity;
import com.limi88.financialplanner.ui.customers.ClientContract;
import com.limi88.financialplanner.ui.customers.CustomersFragment;
import com.limi88.financialplanner.ui.home.HomeContract;
import com.limi88.financialplanner.ui.home.HomeFragment;
import com.limi88.financialplanner.ui.home.HomeWebFragment;
import com.limi88.financialplanner.ui.home.UpDateFragment;
import com.limi88.financialplanner.ui.mine.MineFragment;
import com.limi88.financialplanner.ui.products.ProductContract;
import com.limi88.financialplanner.ui.products.ProductFragment;
import com.limi88.financialplanner.ui.login.LoginActivity;
import com.limi88.financialplanner.ui.mine.MineContract;
import com.limi88.financialplanner.ui.widget.BaseBottomTab;
import com.limi88.financialplanner.ui.widget.LoadingDialog;
import com.limi88.financialplanner.ui.widget.NoScrollPager;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.ToastUtils;
import com.limi88.financialplanner.util.UmengUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import java.util.ArrayList;
import java.util.List;

import anet.channel.util.StringUtils;
import butterknife.Bind;


public class MainActivity extends BaseLimiActivity {

    private Integer[] bottomIcon = {R.drawable.ic_home_tab_home,
            R.drawable.ic_home_tab_products, R.drawable.ic_home_tab_clients,
            R.drawable.ic_home_tab_mine};

    @Bind(R.id.main_container)
    FrameLayout mainContainer;
    @Bind(R.id.bottomtab)
    BaseBottomTab mBottomTab;
    @Bind(R.id.viewpager)
    NoScrollPager viewPager;
    @Bind(R.id.ll_hint_no_network)
    LinearLayout mLlNoNetwork;
    private ViewpagerScroller scroller;
    private List<Fragment> mFragmentList;
    private MainPagerAdapter mAdapter;
    private Intent mIntent;
    private boolean isQuitApp=false;
    private LoadingDialog mLoadingDialog;
    private Intent onHomeIntent;
    public LoadingDialog getLoadingDialog() {
        return mLoadingDialog;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();

    }


    @Override
    protected void getBundleExtras(Bundle extras) {

    }


    @Override
    protected void onResume() {

        if(onHomeIntent != null){ // home键退出后通过intent启动程序
// dosomething···
            onHomeIntent = null;
        }
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        super.initViewsAndEvents();
        mIntent=getIntent();
        Bundle bundle=mIntent.getExtras();
        mLoadingDialog=new LoadingDialog(this);
        if (bundle!=null) {
            String url=bundle.getString(Constants.WEB_PAGE_DETAIL_LINK);
            startActivity(mIntent);
        }
        initCurrentUser(Constants.CURRENT_TOKEN);
        initPager();
        initPagerScroll();
        initTab();
        checkVersion();
//        ToastUtils.showToast("UMENG_CHANNEL:-" + UmengUtil.getChannelName(this));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        onHomeIntent = intent;
        Bundle bundle=intent.getExtras();
        mIntent=new Intent(this,BaseWebViewActivity.class);
        if (bundle!=null&&!bundle.getString(Constants.WEB_PAGE_DETAIL_LINK).equals("")) {
            String url=bundle.getString(Constants.WEB_PAGE_DETAIL_LINK);
            mIntent.putExtras(bundle);
            startActivity(mIntent);
        }
    }



    @Override
    public void showUpdateUI(VersionInfo info) {
        UpDateFragment upDateFragment = new UpDateFragment();
        upDateFragment.setVersionInfo(info);
        this.getSupportFragmentManager().beginTransaction().replace(R.id.main_container, upDateFragment, "update").commit();

    }

    @Override
    public void showNewInfoHint(boolean isShow) {
        mBottomTab.showBadge(Constants.MINE_FLAG, isShow);
    }


    public void gotoPage(int page) {
        viewPager.setCurrentItem(page);
    }

    private void initTab() {

        mBottomTab.initTab();
        mBottomTab.setRes(bottomIcon);
        showNewInfoHint(DataCenter.isNewVersionExist());
        mBottomTab.setOnItemClickListener((view, obj, position) -> {
            mBottomTab.itemSelected(position);
            if (position == 2 && !DataCenter.isSigned()) {

                MainActivity.this.startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), Constants.CLIENTS_FLAG);
                MainActivity.this.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);

//                else {
//                    MainActivity.this.startActivityForResult(new Intent(MainActivity.this, CustomersActivity.class), Constants.CLIENTS_FLAG);
//                    MainActivity.this.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
//                }
            } else if (position == 3 && !DataCenter.isSigned()) {
                MainActivity.this.startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), Constants.MINE_FLAG);
                MainActivity.this.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
//                else {
//                    MainActivity.this.startActivityForResult(new Intent(MainActivity.this, MineActivity.class), Constants.MINE_FLAG);
//                    MainActivity.this.overridePendingTransition(R.anim.in_from_right, R.anim.out_from_left);
//                }
            } else {
                viewPager.setCurrentItem(position);
            }
        });
    }


    private void initPagerScroll() {
        scroller = new ViewpagerScroller(this);
        scroller.setScrollDuration(0);
        scroller.initViewPagerScroll(viewPager);
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {
        mLlNoNetwork.setVisibility(View.GONE);
    }

    @Override
    protected void onNetworkDisConnected() {
        mLlNoNetwork.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //登录相关
        if (resultCode == Constants.LOGIN_SUCCESS_CODE) {
            if (data != null && data.getStringExtra(Constants.WEB_PAGE_DETAIL_LINK) != null) {
                String path = data.getStringExtra(Constants.WEB_PAGE_DETAIL_LINK);
                toWebView(path, Constants.WEB_PAGE_DETAIL_LINK, Constants.GO_DETAIL_TAG);
            } else {
                reload();
//                ((HomeContract.HomeView) mFragmentList.get(0)).loadDataToUI();
//                ((ProductContract.ProductView) mFragmentList.get(1)).loadDataToUI();
//                ((ClientContract.ClientView) mFragmentList.get(2)).loadDataToUI();
//                ((MineContract.MineView) mFragmentList.get(3)).loadUserInfoToUI();
            }
            if (requestCode == Constants.HOME_FLAG) {
                viewPager.setCurrentItem(Constants.HOME_FLAG);
                mBottomTab.itemSelected(Constants.HOME_FLAG);
            } else if (requestCode == Constants.PRODUCTS_FLAG) {
                viewPager.setCurrentItem(Constants.PRODUCTS_FLAG);
                mBottomTab.itemSelected(Constants.PRODUCTS_FLAG);
            }
            if (requestCode == Constants.CLIENTS_FLAG) {
                viewPager.setCurrentItem(Constants.CLIENTS_FLAG);
                mBottomTab.itemSelected(Constants.CLIENTS_FLAG);
            }
            if (requestCode == Constants.MINE_FLAG) {
                viewPager.setCurrentItem(Constants.MINE_FLAG);
                mBottomTab.itemSelected(Constants.MINE_FLAG);
            }


            //客户相关
        } else if (resultCode == Constants.CLIENTS_FLAG) {
            viewPager.setCurrentItem(Constants.CLIENTS_FLAG);
            mBottomTab.itemSelected(Constants.CLIENTS_FLAG);
        } else if (resultCode == Constants.MODIFY_CUSTOMER_CODE) {
            viewPager.setCurrentItem(Constants.CLIENTS_FLAG);
            mBottomTab.itemSelected(Constants.CLIENTS_FLAG);
            ((ClientContract.ClientView) mFragmentList.get(2)).loadDataToUI();
        } else if (resultCode == Constants.NO_MODIFY_CUSTOMER_CODE) {
            viewPager.setCurrentItem(Constants.CLIENTS_FLAG);
            mBottomTab.itemSelected(Constants.CLIENTS_FLAG);
        }
        //产品相关
        else if (resultCode == Constants.PRODUCTS_FLAG) {
            viewPager.setCurrentItem(Constants.PRODUCTS_FLAG);
            mBottomTab.itemSelected(Constants.PRODUCTS_FLAG);
        }
        //用户相关
        else if (resultCode == Constants.FEEDBACK_CODE) {
            viewPager.setCurrentItem(Constants.MINE_FLAG);
            mBottomTab.itemSelected(Constants.MINE_FLAG);
        } else if (resultCode == Constants.MINE_FLAG) {
            viewPager.setCurrentItem(Constants.MINE_FLAG);
            mBottomTab.itemSelected(Constants.MINE_FLAG);
        } else if (resultCode == Constants.UPDATE_USER_INFO_CODE) {
            viewPager.setCurrentItem(Constants.MINE_FLAG);
            mBottomTab.itemSelected(Constants.MINE_FLAG);
            ((MineContract.MineView) mFragmentList.get(3)).loadUserInfoToUI();
        } else if (resultCode== Constants.NEWS_TAG) {
            reload();
        } else {
            viewPager.setCurrentItem(Constants.HOME_FLAG);
            mBottomTab.itemSelected(Constants.HOME_FLAG);
        }
        if (requestCode == Constants.FILECHOOSER_RESULTCODE) {
            ((HomeWebFragment)mFragmentList.get(0)).uploadImgFromSysPhotos(resultCode, data);
        }
    }


    private void toWebView(String path, String linkKey, int webPageTagValue) {
        if (!StringUtils.isBlank(path)) {
            Bundle bu = new Bundle();
            String url = path.contains("http") ? path : Constants.HOST + path;
//            String url = path;
            bu.putInt(Constants.WEB_PAGE_TAG, webPageTagValue);
            bu.putString(linkKey, url);
            mIntent = new Intent(this, BaseWebViewActivity.class);
            mIntent.putExtras(bu);
            startActivityForResult(mIntent, Constants.HOME_FLAG);
        }
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

    private void initPager() {

        mFragmentList = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
//        HomeWebFragment homeWebFragment = new HomeWebFragment();
        ProductFragment productFragment = new ProductFragment();
        CustomersFragment customersFragment = new CustomersFragment();
        MineFragment mineFragment = new MineFragment();
        mFragmentList.add(homeFragment);
//        mFragmentList.add(homeWebFragment);
        mFragmentList.add(productFragment);
        mFragmentList.add(customersFragment);
        mFragmentList.add(mineFragment);
        mAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mAdapter.setFragmentList(mFragmentList);
        viewPager.setOffscreenPageLimit(mFragmentList.size());
        viewPager.setAdapter(mAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mBottomTab.itemSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
//            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
//                    .setTitleText("您确定退出此程序？")
//                    .setConfirmText("确定")
//                    .setConfirmClickListener(
//                            sweetAlertDialog -> {
//                                sweetAlertDialog.dismissWithAnimation();
//                                MainActivity.this.finish();
//                            }
//                    ).setCancelText("取消")
//                    .setCancelClickListener(null).show();
            if (mLoadingDialog!=null&&mLoadingDialog.isShow()) {
                mLoadingDialog.dismiss();
            }
            if (!isQuitApp) {
                ToastUtils.showToast(getResources().getString(R.string.notice_quit_app));
                isQuitApp = true;
            } else {
                MainActivity.this.finish();
            }

        }

    }

    public void reload() {
        showBottomTab();
//        ((HomeWebFragment)mFragmentList.get(0)).reloadHomeWeb();
        ((HomeContract.HomeView) mFragmentList.get(0)).loadDataToUI();
        ((ProductContract.ProductView) mFragmentList.get(1)).loadDataToUI();
        ((ClientContract.ClientView) mFragmentList.get(2)).setDataLoaded(false);
        ((MineContract.MineView) mFragmentList.get(3)).setDataLoaded(false);
        checkUserNewinfoHint();
    }

    public void reloadUserUI(){
        ((MineContract.MineView) mFragmentList.get(3)).setDataLoaded(false);
        checkUserNewinfoHint();
    }


    public void hideBottomTab() {
        mBottomTab.setVisibility(View.GONE);
    }
    public void showBottomTab() {
        mBottomTab.setVisibility(View.VISIBLE);
    }
}
