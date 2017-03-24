package com.limi88.financialplanner.ui.products;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.hehao.library.base.BaseLazyFragment;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.pojo.home.Product;
import com.limi88.financialplanner.ui.adapters.ProductSubPageAdapter;
import com.limi88.financialplanner.ui.base.BaseWebViewActivity;
import com.limi88.financialplanner.ui.base.FragmentBackHandler;
import com.limi88.financialplanner.ui.login.LoginActivity;
import com.limi88.financialplanner.ui.widget.LimiFooterView;
import com.limi88.financialplanner.ui.widget.LimiHeaderLayout;
import com.limi88.financialplanner.util.Constants;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;

/**
 * Created by liushuai on 16/9/11.
 */
public class ProductSubPageFragment extends BaseLazyFragment implements FragmentBackHandler {
    @Bind(R.id.prv_product_sub)
    PullToRefreshRecyclerView mRefreshRecyclerView;
    private RecyclerView mRecycleView;
    private List<Product> productList;
    private ProductSubPageAdapter mAdapter;
    private Intent mIntent;
    private int page = 1;
    private ProductContract.ProductView productView;
    private boolean isLoaded = false;
    private int itemNum;

    public boolean isLoaded() {
        return isLoaded;
    }

    public void setIsLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    public void setProductView(ProductContract.ProductView productView) {
        this.productView = productView;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        itemNum = productList.size();
        if (mAdapter != null) {
            mAdapter = null;
            mAdapter = new ProductSubPageAdapter(getActivity(), productList);
            mRecycleView.setAdapter(mAdapter);
            mAdapter.setOnClickListener(view -> {
                Log.i("homepage", "clicked--------");
                Bundle bundle = (Bundle) view.getTag();
                int tag = bundle.getInt(Constants.WEB_PAGE_TAG);
                Log.i("homepage", "clicked:--------" + tag);
                switch (tag) {
                    case Constants.NO_LOGIN_TAG:
                        getActivity().startActivityForResult(new Intent(ProductSubPageFragment.this.getActivity(), LoginActivity.class), Constants.HOME_FLAG);
                        break;
                    case Constants.NO_AUTHENTICATION_TAG:
//                            getActivity().startActivityForResult(new Intent(ProductSubPageFragment.this.getActivity(), AuthenticationActivity.class), Constants.HOME_FLAG);
                        break;
                    case Constants.GO_DETAIL_TAG:
                        mIntent = new Intent(getActivity(), BaseWebViewActivity.class);
                        mIntent.putExtras(bundle);
                        startActivity(mIntent);
                        break;
                }
            });
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainScreen");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainScreen");
    }


    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    public void setRefreshAble(boolean refreshAble) {
        if (refreshAble) {
            mRefreshRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        } else
            mRefreshRecyclerView.setMode(PullToRefreshBase.Mode.DISABLED);

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        mRecycleView = mRefreshRecyclerView.getRefreshableView();

        mRecycleView.setId(R.id.rv_products_sub);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new ProductSubPageAdapter(getActivity(), productList);
        mRecycleView.setAdapter(mAdapter);
        mRefreshRecyclerView.setHasTransientState(false); // 设置没有上拉阻力
        mRefreshRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        mRefreshRecyclerView.setHeaderLayout(new LimiHeaderLayout(getActivity()));
        mRefreshRecyclerView.setFooterLayout(new LimiFooterView(getActivity()));

        mRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                productView.reLoadData();
                page=1;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                page++;
                productView.attachData(page);
                mRecycleView.scrollToPosition(itemNum);
            }
        });


//        mRecycleView.setOnScrollListener(new RecyclerViewScrollDetector() {
//
//            @Override
//            public void onScrollUp() {
//                showSearchBar();
//            }
//
//            @Override
//            public void onScrollDown() {
//                hideSearchBar();
//            }
//        });
//        mRefreshLayout.setOnRefreshListener(() ->{
//            if (PermissionUtils.getInstance(this).checkStoragePermission())
//            productView.reLoadData();
//        else productView.reLoadDataWithoutCache();});

    }

    private void hideSearchBar() {
        if (productView!=null) {
            productView.hideSearchBar();
        }
    }

    private void showSearchBar() {
        if (productView!=null) {
            productView.showSearchBar();
        }
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_products_sub;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    public void finishRefresh() {
        if (mRefreshRecyclerView!=null) {
            mRefreshRecyclerView.onRefreshComplete();

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MainApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    public void attachData(List<Product> list) {
        productList.addAll(list);
        itemNum=itemNum+list.size();
        mAdapter.setmHomeData(productList);
        mAdapter.notifyDataSetChanged();
    }
}
