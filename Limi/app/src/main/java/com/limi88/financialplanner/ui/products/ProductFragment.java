package com.limi88.financialplanner.ui.products;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hehao.library.base.BaseLazyFragment;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.ui.MainActivity;
import com.limi88.financialplanner.ui.adapters.ProductPageAdapter;
import com.limi88.financialplanner.ui.adapters.ProductSortAdapter;
import com.limi88.financialplanner.ui.base.FragmentBackHandler;
import com.limi88.financialplanner.ui.login.LoginActivity;
import com.limi88.financialplanner.ui.widget.LoadingDialog;
import com.limi88.financialplanner.util.PermissionUtils;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.di.component.FragmentComponent;
import com.limi88.financialplanner.pojo.home.Product;
import com.limi88.financialplanner.ui.base.BaseWebViewActivity;
import com.limi88.financialplanner.util.ConditionUtils;
import com.limi88.financialplanner.util.Constants;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by liushuai on 16/9/11.
 */
public class ProductFragment extends BaseLazyFragment implements FragmentBackHandler, ProductContract.ProductView, View.OnClickListener, ProductSortAdapter.OnItemClickListener {

    @Bind(R.id.viewpager)
    ViewPager mViewPager;
    @Bind(R.id.viewpagertab)
    SmartTabLayout mSmartTabLayout;
    @Bind(R.id.common_title)
    TextView titleText;
    @Bind(R.id.tv_product_sort)
    TextView mSortView;
    @Bind(R.id.tv_product_filter)
    TextView mFilterView;
    @Bind(R.id.ic_searcher)
    ImageView ivSearcher;
    @Bind(R.id.fab)
    FloatingActionButton mFavorButton;

    @Bind(R.id.ll_errorview)
    LinearLayout mLlErrorView;
    @Bind(R.id.iv_error_reload)
    ImageView mReloadView;
    @Bind(R.id.ll_searchbar)
    LinearLayout mSearchBarLayout;
    private List<ProductSubPageFragment> mFragmentList;
    private ProductPageAdapter mPageAdapter;
    private HashMap<String,String>options=new HashMap<>();
    private int currentPage = 0;
    private int lastPage = 0;
    CharSequence[] titles;
    CharSequence[] mSortSelections;
    private List<Product> mProductList = new ArrayList<Product>();
    private PopupWindow popupWindow;
    private View contentView;
    private CharSequence[] searchPath;
    private RecyclerView myDropDownMenu;
    private SearchConditionFragment fragment;
    private ProductSortAdapter mSortAdapter;
    private LoadingDialog mDialog;
    private Intent mIntent;
    private Bundle bundle;
    private boolean isFilter=false;
    private boolean isSort=false;
    @Inject
    ProductPresenter mPreasenter;


    @Override
    protected void onFirstUserVisible() {
        loadDataToUI();
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
        mPreasenter.attachView(this);
        titles = getActivity().getResources().getTextArray(R.array.product_type_array);
        searchPath = getActivity().getResources().getTextArray(R.array.product_path_array);
        titleText.setText(getActivity().getString(R.string.home_title_products));
        mReloadView.setOnClickListener(v -> reLoadData());
        mDialog = ((MainActivity)getActivity()).getLoadingDialog();
        mFavorButton.setOnClickListener(view -> {
            if (DataCenter.isSigned()) {
                mIntent = new Intent(getActivity(), BaseWebViewActivity.class);
                bundle = new Bundle();
                bundle.putInt(Constants.WEB_PAGE_TAG, Constants.WEB_FAVOR_TAG);
                bundle.putString(Constants.WEB_FAVOR_LINK, Constants.PRODUCTS_FAVOR_URL);
                bundle.putString(Constants.WEB_FAVOR_NAME, mContext.getString(R.string.home_title_my_favor));
                mIntent.putExtras(bundle);
                getActivity().startActivityForResult(mIntent, Constants.PRODUCTS_FLAG);
            } else {
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        initPager();
        initTab();
        mSmartTabLayout.setViewPager(mViewPager);
        mViewPager.setCurrentItem(currentPage);
        mFragmentList.get(currentPage).setProductView(this);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mFragmentList.get(position).setProductView(ProductFragment.this);
                switch (position) {
                    case 0:
                        if (!mFragmentList.get(position).isLoaded()) {
                            if (PermissionUtils.getInstance(ProductFragment.this).checkStoragePermission()) {
                                mPreasenter.getProductData(Constants.INSURANCE_PATH, Constants.CURRENT_TOKEN,1);

                            }else  mPreasenter.getProductDataWithoutCache(Constants.INSURANCE_PATH, Constants.CURRENT_TOKEN,1);

                            mFragmentList.get(position).setIsLoaded(true);
                        }
                        break;
                }
                lastPage = currentPage;
                currentPage = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mSortView.setOnClickListener(this);
        ivSearcher.setOnClickListener(this);
        mFilterView.setOnClickListener(this);
    }

    private void initTab() {

        final LayoutInflater inflater = LayoutInflater.from(getActivity());
        mSmartTabLayout.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                View view = inflater.inflate(R.layout.product_tab, container, false);
                TextView titleView = (TextView) view.findViewById(R.id.tv_product_tab);
                titleView.setText(titles[position]);
                AutoUtils.auto(view);
                return view;
            }
        });
    }

    private void initPager() {


        mFragmentList = new ArrayList<ProductSubPageFragment>();
        for (int i = 0; i < titles.length; i++) {
            ProductSubPageFragment productSubPageFragment = new ProductSubPageFragment();
            mFragmentList.add(productSubPageFragment);
        }
        mPageAdapter = new ProductPageAdapter(getChildFragmentManager());
        mPageAdapter.setFragmentList(mFragmentList);
        mViewPager.setAdapter(mPageAdapter);
        mViewPager.setOffscreenPageLimit(mFragmentList.size());
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_fin_product;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    private void initializeInjector() {
        FragmentComponent component = FragmentComponent.Initializer.init(this);
        component.inject(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mPreasenter.detachView();
        MobclickAgent.onPageEnd("MainScreen");
    }

    @Override
    public void onResume() {
        super.onResume();
        mPreasenter.attachView(this);
        MobclickAgent.onPageStart("MainScreen");
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
    public void loadDataToUI(List<Product> products) {
        this.mProductList = products;
        if (products.size() != 0) {
            Log.i("Productfragment", mProductList.get(0).getName());
        }
        mFragmentList.get(currentPage).setProductList(mProductList);
        mFragmentList.get(currentPage).finishRefresh();
        hideDataLoading();
    }

    @Override
    public void loadDataToUI() {
        showDataLoading();
        mPreasenter.getProductData(Constants.INSURANCE_PATH, Constants.CURRENT_TOKEN,1);
}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_product_sort:
                Log.i("product", "sort-----------");

                showPopUpWindow(v);
                break;
            case R.id.tv_product_filter:
                Log.i("product", "search-----------");
                fragment = new SearchConditionFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment).commit();
                fragment.setProductView(this);
                fragment.setSelections(options);
                fragment.setPresenter(mPreasenter);
                loadSearchFragmentData();
                break;
//            case R.id.ic_searcher:
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new SearchFragment()).commit();
//                break;
        }
    }

    @Override
    public void loadSearchFragmentData() {

        fragment.loadData(searchPath[currentPage] + "", Constants.CURRENT_TOKEN);
    }

    @Override
    public void reLoadData() {
        showDataLoading();
        mPreasenter.getProductData(searchPath[currentPage] + "", Constants.CURRENT_TOKEN, 1);

    }

    @Override
    public void reLoadDataWithoutCache() {
        showDataLoading();
        mPreasenter.getProductDataWithoutCache(searchPath[currentPage] + "", Constants.CURRENT_TOKEN,1);

    }

    @Override
    public void finishSweap() {
        if ((mFragmentList.get(0)!=null)) {
            mFragmentList.get(0).finishRefresh();
        }
    }

    @Override
    public void showSearchBar() {
        mSearchBarLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSearchBar() {
        mSearchBarLayout.setVisibility(View.GONE);
    }

    @Override
    public void attachData(int page) {
        mPreasenter.attachData(mProductList, searchPath[currentPage] + "", Constants.CURRENT_TOKEN, page);
    }

    @Override
    public void attachDataToUI(List<Product> products) {
        this.mProductList = products;
        if (products.size() != 0) {
            Log.i("Productfragment", mProductList.get(0).getName());
        }
        mFragmentList.get(currentPage).attachData(mProductList);
        mFragmentList.get(currentPage).finishRefresh();
        hideDataLoading();

    }


    @Override
    public void loadSearchResultToUI(HashMap<String, String> options) {
        options=this.options;
        if (options.size()!=0) {
            mFilterView.setSelected(true);
            isFilter=true;
            if (isFilter||isSort) {
                mFragmentList.get(currentPage).setRefreshAble(false);
            }
        }else {mFilterView.setSelected(false);
            isFilter=false;
            if (!isSort&&!isFilter) {
                mFragmentList.get(currentPage).setRefreshAble(true);
            }
        }
        mPreasenter.seachDataWithCondition(searchPath[currentPage] + "", options);

    }


    private void showPopUpWindow(View v) {
        if (popupWindow==null) {
            initPopUpWindow();
        }
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("mengdd", "onTouch : ");
                return false;
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.argb(88, 0, 0, 0)));
        backgroundAlpha(0.5f);
        // 设置好参数之后再show
        popupWindow.showAsDropDown(v);

    }

    private void initPopUpWindow() {

        // 一个自定义的布局，作为显示的内容
        contentView = LayoutInflater.from(getActivity()).inflate(
                R.layout.popwindow_product_filter, null);
        myDropDownMenu = (RecyclerView) contentView.findViewById(R.id.rv_popmenu);
        myDropDownMenu.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setOnDismissListener(new poponDismissListener());
        mSortSelections = getActivity().getResources().getTextArray(R.array.produt_sort_insurance);
        mSortAdapter = new ProductSortAdapter(getActivity(), mSortSelections);
        myDropDownMenu.setAdapter(mSortAdapter);
        mSortAdapter.setOnItemClickListener(this);
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public void OnItemClick(View v, Object obj, int position) {
        mSortAdapter.setSelectedFlag(position);
        mPreasenter.seachDataBySort(searchPath[currentPage] + "", Constants.CURRENT_TOKEN, ConditionUtils.getSortPathForInsurance((String) v.getTag()));
        popupWindow.dismiss();
        if ( mSortSelections[position].equals("默认排序")) {
            isSort=false;
           mSortView.setSelected(false);
            if (!isFilter&&!isFilter) {
                mFragmentList.get(currentPage).setRefreshAble(true);
            }
        }else {mSortView.setSelected(true);
            isSort=true;
            if (isSort||isFilter) {
                mFragmentList.get(currentPage).setRefreshAble(false);
            }
        }
        lastPage = currentPage;
    }

    class poponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

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
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MainApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
