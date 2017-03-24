package com.limi88.financialplanner.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.di.component.FragmentComponent;
import com.limi88.financialplanner.pojo.home.HomeData;
import com.limi88.financialplanner.ui.MainActivity;
import com.limi88.financialplanner.ui.adapters.BasePageAdapter;
import com.limi88.financialplanner.ui.adapters.HomePageAdapterV3;
import com.limi88.financialplanner.ui.base.BaseSafeFragment;
import com.limi88.financialplanner.ui.base.BaseWebViewActivity;
import com.limi88.financialplanner.ui.login.LoginActivity;
import com.limi88.financialplanner.ui.widget.LimiHeaderLayout;
import com.limi88.financialplanner.ui.widget.LoadingDialog;
import com.limi88.financialplanner.ui.widget.MyLinearLayoutManager;
import com.limi88.financialplanner.util.Constants;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by hehao on 16/9/5.
 */
public class HomeFragment extends BaseSafeFragment implements HomeContract.HomeView {

    @Inject
    HomePresenter mPresenter;
    @Bind(R.id.common_title)
    TextView titleview;
    @Bind(R.id.rl_titleView)
    RelativeLayout mTitleBar;
    @Bind(R.id.ll_errorview)
    LinearLayout mLlErrorView;
    @Bind(R.id.iv_error_reload)
    ImageView mReloadView;
    @Bind(R.id.prv_home)
    PullToRefreshRecyclerView mRefreshRecyclerView;
    @Bind(R.id.homepager_float_tab)
    SmartTabLayout mHomeSubTab;
    @Bind(R.id.ll_float_tab)
    RelativeLayout mFloatTab;
    @Bind(R.id.home_top_container)
    RelativeLayout mTopView;
    @Bind(R.id.iv_home_showTop)
    ImageView mShowTopView;
    @Bind(R.id.iv_home_shadow)
    ImageView mShadowView;

    private List<Fragment> mSubPageFragmentList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private HomePageAdapterV3 mHomePageAdapter;
    private BasePageAdapter mSubPageAdapter;
    private ViewPager mViewPager;
    private HomeData mHomeData;
    private CharSequence[] subTitles;
    private Intent mIntent;
    private String[] pathArray;
    private MyLinearLayoutManager mLayoutManager;
    private int topHeight;
    private boolean isShowFloatTab = false;
    private boolean isNeedReload = true;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        initializeInjector();
        initSubPageFragment();
        initAdapter();
        mLoadingDialog = ((MainActivity) getActivity()).getLoadingDialog();
        titleview.setText(R.string.home_title_home);
        mTitleBar.setVisibility(View.GONE);
        mReloadView.setOnClickListener(v -> mPresenter.loadHomeData(Constants.CURRENT_TOKEN));
        mRecyclerView = mRefreshRecyclerView.getRefreshableView();
        mRecyclerView.setId(R.id.rv_home);
        mLayoutManager = new MyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRefreshRecyclerView.setHeaderLayout(new LimiHeaderLayout(getActivity()));
//        mRefreshRecyclerView.setFooterLayout(new LimiHeaderLayout(getActivity()));
        mRefreshRecyclerView.setHasTransientState(false); // 设置没有上拉阻力
        mRecyclerView.setAdapter(mHomePageAdapter);
        mRefreshRecyclerView.setPullToRefreshEnabled(false);
        mViewPager = mHomePageAdapter.getViewPager();
        mRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                loadDataToUI();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                loadDataToUI();
            }
        });
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mLayoutManager.isgetTop(2) && !isShowFloatTab) {
                    showFloatTab();
                } else {
                    if (isShowFloatTab) {
                        mLayoutManager.setScrollEnabled(false);
                    }

                }


            }
        });

    }

    private void initSubPageFragment() {
        pathArray = getActivity().getResources().getStringArray(R.array.home_sub_path_array);
        for (int i = 0; i < 4; i++) {
            Fragment fragment = new HomeSubPageFragment();
            ((HomeSubPageFragment) fragment).setHomeView(this);
            ((HomeSubPageFragment) fragment).setPath(pathArray[i]);
            ((HomeSubPageFragment) fragment).setPresenter(mPresenter);
            ((HomeSubPageFragment) fragment).setIsLoaded(false);
            ((HomeSubPageFragment) fragment).setRetainInstance(true);
            mSubPageFragmentList.add(fragment);
        }
    }


    private void showFloatTab() {
//        mTitleBar.setVisibility(View.GONE);
        mFloatTab.setVisibility(View.VISIBLE);
        mShadowView.setVisibility(View.GONE);
        mLayoutManager.setScrollEnabled(false);
        mHomePageAdapter.getSubTab().setVisibility(View.INVISIBLE);
        isShowFloatTab = true;
        mRecyclerView.scrollToPosition(3);

        subTitles = getActivity().getResources().getTextArray(R.array.home_sub_tab_array);
        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        mHomeSubTab.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                View view = inflater.inflate(R.layout.home_sub_tab2, container, false);
                TextView titleView = (TextView) view.findViewById(R.id.custom_text);
                titleView.setText(subTitles[position]);
                ViewGroup.LayoutParams lp=view.getLayoutParams();
                int width=mScreenWidth;
                width=width/4;
                lp.width=width;
                view.setLayoutParams(lp);
//                AutoUtils.auto(view);
                return view;

            }
        });
        mHomeSubTab.setViewPager(mHomePageAdapter.getViewPager());
        mShowTopView.setOnClickListener(v -> {
//            mTitleBar.setVisibility(View.VISIBLE);
            mFloatTab.setVisibility(View.GONE);
            mShowTopView.setVisibility(View.VISIBLE);
            mShadowView.setVisibility(View.VISIBLE);
            mHomePageAdapter.getSubTab().setVisibility(View.VISIBLE);
            mRecyclerView.smoothScrollToPosition(0);
            isShowFloatTab = false;
            mLayoutManager.setScrollEnabled(true);

        });

    }


    private void initAdapter() {
        mHomePageAdapter = new HomePageAdapterV3(getActivity(), mHomeData, getChildFragmentManager(), this, mPresenter, mSubPageFragmentList);
        mHomePageAdapter.setOnClickListener(v -> {
            Log.i("homepage", "clicked--------");
            Bundle bundle = (Bundle) v.getTag();
            int tag = bundle.getInt(Constants.WEB_PAGE_TAG);
            Log.i("homepage", "clicked:--------" + tag);
            switch (tag) {
                case Constants.NO_LOGIN_TAG:
                    getActivity().startActivityForResult(new Intent(this.getActivity(), LoginActivity.class), Constants.HOME_FLAG);
                    break;
                case Constants.NO_AUTHENTICATION_TAG:
//                    getActivity().startActivityForResult(new Intent(this.getActivity(), AuthenticationActivity.class), Constants.HOME_FLAG);
                    break;
                case Constants.GO_DETAIL_TAG:
                    mIntent = new Intent(getActivity(), BaseWebViewActivity.class);
                    mIntent.putExtras(bundle);
                    startActivity(mIntent);
                    break;
            }
        });

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }


    private void initializeInjector() {

        FragmentComponent component = FragmentComponent.Initializer.init(this);
        component.inject(this);

    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainScreen");
//        loadDataToUI();
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.attachView(this);
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    Constants.WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        } else {
            mPresenter.loadHomeData(Constants.CURRENT_TOKEN);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults.length!=0&&grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                mPresenter.loadHomeData(Constants.CURRENT_TOKEN);
                Log.i("permission", "WRITE_EXTERNAL_STORAGE_REQUEST_CODE------------permited");
            } else {
                // Permission Denied
                Log.i("permission", "WRITE_EXTERNAL_STORAGE_REQUEST_CODE------------denied");
                mPresenter.loadHomeDataWithoutCache(Constants.CURRENT_TOKEN);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainScreen");
    }

    @Override
    public void onDestroyView() {
        mPresenter.detachView();
        super.onDestroyView();

    }

    @Override
    public void showLoadingHomeData() {
        mLoadingDialog.show();
    }

    @Override
    public void hideLoadingHomeData() {
        mLoadingDialog.dismiss();
    }

    @Override
    public void showErrorView() {
        mLlErrorView.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideErrorView() {
        mLlErrorView.setVisibility(View.GONE);
    }

    @Override
    public void loadDataToUI(HomeData mHomeData) {
        if (isNeedReload) {
            this.mHomeData = mHomeData;
            mHomePageAdapter.setmDataList(mHomeData);
            mHomePageAdapter.notifyDataSetChanged();
            mRefreshRecyclerView.onRefreshComplete();
            isNeedReload = false;
            hideLoadingHomeData();
        }

    }

    @Override
    public void loadDataToUI() {
        isNeedReload = true;
        showLoadingHomeData();
        for (int i = 0; i < mSubPageFragmentList.size(); i++) {
            ((HomeSubPageFragment) mSubPageFragmentList.get(i)).setIsLoaded(false);
        }
        mPresenter.loadHomeData(Constants.CURRENT_TOKEN);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MainApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }


}

