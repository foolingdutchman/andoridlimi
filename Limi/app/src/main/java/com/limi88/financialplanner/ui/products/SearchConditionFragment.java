package com.limi88.financialplanner.ui.products;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hehao.library.base.BaseLazyFragment;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.pojo.searchcondition.SearchCondition;
import com.limi88.financialplanner.ui.adapters.SeachConditionAdapter;
import com.limi88.financialplanner.ui.base.FragmentBackHandler;
import com.limi88.financialplanner.util.Constants;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;

/**
 * Created by hehao on 16/9/13.
 */
public class SearchConditionFragment extends BaseLazyFragment implements FragmentBackHandler, ProductContract.SearchView {


    private RecyclerView mRecycleView;

    private TextView mLeftTitle;
    private TextView titleView;

    private LinearLayout backView;
    private ProgressBar mProgressBar;

    ImageView leftAction;
    Button mConfirmBtn;
    Button mResetBtn;
    private ProductContract.ProductView productView;
    private SeachConditionAdapter mAdapter;
    private SearchCondition searchCondition;
    private ProductPresenter mPresenter;
    private HashMap<String,String>mSelections;
    public HashMap<String, String> getSelections() {
        return mSelections;
    }

    public void setSelections(HashMap<String, String> selections) {
        mSelections = selections;
    }



    public void setPresenter(ProductPresenter mPresenter) {
        this.mPresenter = mPresenter;

        mPresenter.attachSearchView(this);
    }


    public void loadData(String path, String token) {
        if (mPresenter != null) {
            mPresenter.searchCondition(path, token);
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressBar=new ProgressBar(getActivity());
        titleView = (TextView) view.findViewById(R.id.common_title);
        mLeftTitle = (TextView) view.findViewById(R.id.tv_header_back);
        backView = (LinearLayout) view.findViewById(R.id.ll_left_btn);
        leftAction = (ImageView) view.findViewById(R.id.common_left_action);
        mRecycleView = (RecyclerView) view.findViewById(R.id.rv_product_filter);
        mConfirmBtn = (Button) view.findViewById(R.id.btn_search_confirm);
        mResetBtn = (Button) view.findViewById(R.id.btn_search_reset);
        initView();
    }

    private void initView() {
        titleView.setText(getActivity().getString(R.string.home_title_products));
        backView.setVisibility(View.VISIBLE);
        titleView.setVisibility(View.GONE);
        mLeftTitle.setVisibility(View.VISIBLE);
        mLeftTitle.setText("筛选");

        backView.setClickable(true);
        mConfirmBtn.setSelected(true);
        mResetBtn.setSelected(false);

        //搜索键完成搜索并返回
        mConfirmBtn.setOnClickListener(v -> {
            productView.loadSearchResultToUI(mSelections);
            mPresenter.detachSearchView();
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        });
        //重置键重新加载搜索条件
        mResetBtn.setOnClickListener(v -> {
            mSelections.clear();
            mAdapter.notifyDataSetChanged();
        });

        //返回
        backView.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        });

        initAdapter();
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecycleView.setAdapter(mAdapter);
        //选择搜索条件
        mAdapter.setItemClickListener(new SeachConditionAdapter.ItemClickListener() {
            @Override
            public void OnItemClick(View v, Object obj, int position) {
                Bundle bundle = (Bundle) obj;
                if (bundle.getBoolean(Constants.SEARCH_ISSELECTED_FLAG)) {
                    if (mSelections.get(bundle.getString(Constants.SEARCH_KEY_FLAG)) == null) {
                        mSelections.put(bundle.getString(Constants.SEARCH_KEY_FLAG), bundle.getString(Constants.SEARCH_CONDITION_FLAG));
                    } else {
                        String selection = mSelections.get(bundle.getString(Constants.SEARCH_KEY_FLAG))
                                + "," + bundle.getString(Constants.SEARCH_CONDITION_FLAG);
                        mSelections.remove(bundle.getString(Constants.SEARCH_KEY_FLAG));
                        mSelections.put(bundle.getString(Constants.SEARCH_KEY_FLAG), selection);
                    }
                } else if (!bundle.getBoolean(Constants.SEARCH_ISSELECTED_FLAG)) {
                    String key = bundle.getString(Constants.SEARCH_KEY_FLAG);
                    String selection = mSelections.get(bundle.getString(Constants.SEARCH_CONDITION_FLAG));
                    if (selection != null && selection.contains(",")) {
                        selection.substring(0, selection.lastIndexOf(",") - 1);
                        Log.i("selection", key + ": ---------" + selection);
                        mSelections.remove(key);
                        mSelections.put(key, selection);
                    }
                }
            }
        });
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

    private void initAdapter() {

        mAdapter = new SeachConditionAdapter(getActivity(), searchCondition,mSelections);
    }


    @Override
    public boolean onBackPressed() {

        this.getSupportFragmentManager().beginTransaction().remove(this).commit();

        return true;
    }


    @Override
    public void showDataLoading() {
//        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDataLoading() {
//        mProgressBar.setVisibility(View.INVISIBLE);
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_serach_condition;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void loadSearchCondition(SearchCondition searchCondition) {
        mAdapter.setSearchCondition(searchCondition);
        mAdapter.notifyDataSetChanged();
    }


    public void setProductView(ProductContract.ProductView productView) {
        this.productView = productView;
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

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MainApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
