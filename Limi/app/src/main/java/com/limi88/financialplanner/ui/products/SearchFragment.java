package com.limi88.financialplanner.ui.products;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hehao.library.base.BaseLazyFragment;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.di.component.FragmentComponent;
import com.limi88.financialplanner.greendao.bean.Search;
import com.limi88.financialplanner.greendao.dao.SearchDao;
import com.limi88.financialplanner.pojo.home.Product;
import com.limi88.financialplanner.ui.adapters.ProductSubPageAdapter;
import com.limi88.financialplanner.ui.adapters.SearchHintAdapter;
import com.limi88.financialplanner.ui.base.FragmentBackHandler;
import com.limi88.financialplanner.ui.widget.MySearchView;
import com.limi88.financialplanner.util.Constants;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by liushuai on 16/9/16.
 */
public class SearchFragment extends BaseLazyFragment implements FragmentBackHandler, ProductContract.SearchAllView, MySearchView.SearchViewListener {


    private RecyclerView mProductView;
    private TextView titleView;
    private TextView mLeftTitle;
    private MySearchView mySearchView;
    private LinearLayout backView;
    private LinearLayout noResultView;

    private ProductSubPageAdapter mAdapter;
    private SearchHintAdapter autoCompleteAdapter;
    private SearchHintAdapter historyAdapter;
    private ProgressBar mProgressBar;
    @Inject
    SearchAllPresenter mPresenter;
    @Inject
    SearchDao mSearchDao;

    private List<Search> mHistoryList = new ArrayList<>();
    private List<Search> mHintList = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleView = (TextView) view.findViewById(R.id.common_title);
        mLeftTitle = (TextView) view.findViewById(R.id.tv_header_back);
        mySearchView = (MySearchView) view.findViewById(R.id.searchView);
        mProductView = (RecyclerView) view.findViewById(R.id.rv_product_search_result);
        backView = (LinearLayout) view.findViewById(R.id.ll_left_btn);
        noResultView = (LinearLayout) view.findViewById(R.id.ll_search_no_result);
        mProgressBar= new ProgressBar(getActivity());

        mProductView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        initAdapter();
        initView();

    }

    @Override
    public void loadNoSearchResult() {
        noResultView.setVisibility(View.VISIBLE);
    }


    private void getAutoCompleteData(String string) {
        mHintList.clear();
        for (int i = 0; i < mHistoryList.size(); i++) {
            if (mHistoryList.get(i).getSearchTitle().contains(string.trim())) {
                mHintList.add(mHistoryList.get(i));

            }
        }
        autoCompleteAdapter.setStringList(mHintList);
        autoCompleteAdapter.notifyDataSetChanged();
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_search_product;
    }

    private void initView() {
        titleView.setText(getActivity().getString(R.string.home_title_products));
        titleView.setVisibility(View.GONE);
        mLeftTitle.setVisibility(View.VISIBLE);
        mLeftTitle.setText(getActivity().getString(R.string.home_title_products));

        mySearchView.setAutoCompleteAdapter(autoCompleteAdapter);
        mySearchView.setTipsHintAdapter(historyAdapter);
        mySearchView.setSearchViewListener(this);
        mProductView.setAdapter(mAdapter);
        backView.setVisibility(View.VISIBLE);
        backView.setClickable(true);
        //返回
        backView.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        });

    }


    private void initAdapter() {
        mAdapter = new ProductSubPageAdapter(getActivity(), productList);
        historyAdapter = new SearchHintAdapter(getActivity(), mHistoryList);
        autoCompleteAdapter = new SearchHintAdapter(getActivity(), mHintList);
        historyAdapter.setOnClickListener(view -> {
            int positon = (int) view.getTag();

            mSearchDao.delete(mHistoryList.get(positon));
            mHistoryList.remove(positon);
            historyAdapter.setStringList(mHintList);
            historyAdapter.notifyDataSetChanged();
        });
        autoCompleteAdapter.setOnClickListener(view -> {
            int positon = (int) view.getTag();
            mSearchDao.delete(mHistoryList.get(positon));
            mHistoryList.remove(mHintList.get(positon));
            mHintList.remove(positon);
            autoCompleteAdapter.setStringList(mHintList);
            autoCompleteAdapter.notifyDataSetChanged();

        });
    }

    private void initializeInjector() {
        FragmentComponent component = FragmentComponent.Initializer.init(this);
        component.inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.attachView(this);
        mPresenter.loadAllHistory();
        MobclickAgent.onPageStart("MainScreen");
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.detachView();
        MobclickAgent.onPageEnd("MainScreen");
    }

    @Override
    protected void initViewsAndEvents() {
        initializeInjector();
    }

    @Override
    public void onRefreshAutoComplete(String text) {
        getAutoCompleteData(text);
    }

    @Override
    public void onSearch(String text) {

        mPresenter.searchAllProductWithCondition(text, Constants.CURRENT_TOKEN);
    }

    @Override
    public void onClearHistory() {
        mHistoryList.clear();
        mHintList.clear();
        mSearchDao.deleteAll();
        historyAdapter.setStringList(mHistoryList);
        autoCompleteAdapter.setStringList(mHintList);
        historyAdapter.notifyDataSetChanged();
        autoCompleteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onReadyToSearch() {
        mPresenter.loadAllHistory();
        noResultView.setVisibility(View.INVISIBLE);
        mProductView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void loadSearchAllWithCondition(List<Product> products) {
        noResultView.setVisibility(View.INVISIBLE);
        mProductView.setVisibility(View.VISIBLE);
        mAdapter.setmHomeData(products);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void loadSearchHistory(List<Search> searches) {
        for (int i = 0; i < searches.size(); i++) {
            Log.i("SearchAll", searches.get(i).getSearchTitle() + "---------");
        }
        mHistoryList = searches;
        historyAdapter.setStringList(searches);
        mySearchView.setTipsHintAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    public boolean onBackPressed() {
        this.getSupportFragmentManager().beginTransaction().remove(this).commit();
        return true;
    }


    @Override
    public void showDataLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDataLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }


    @Override
    protected boolean isBindEventBusHere() {
        return false;
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
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MainApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

}

