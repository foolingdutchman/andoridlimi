package com.limi88.financialplanner.ui.products;

import com.limi88.financialplanner.greendao.bean.Search;
import com.limi88.financialplanner.pojo.home.Product;
import com.limi88.financialplanner.pojo.searchcondition.SearchCondition;
import com.limi88.financialplanner.ui.base.BasePresenter;
import com.limi88.financialplanner.ui.base.BaseView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by hehao on 16/9/12.
 */
public interface ProductContract  {
    interface ProductView extends BaseView {

        void showDataLoading();
        void hideDataLoading();
        void loadDataToUI(List<Product> products);
        void loadDataToUI();
        void loadSearchResultToUI(HashMap<String,String>options);

        void loadSearchFragmentData();
        void reLoadData();
        void showErrorView() ;

        void hideErrorView();
        void reLoadDataWithoutCache();

        void finishSweap();
        void showSearchBar();
        void hideSearchBar();

        void attachData(int page);

        void attachDataToUI(List<Product> products);
    }
    interface SearchView extends BaseView{
        void showDataLoading();
        void hideDataLoading();
        void loadSearchCondition(SearchCondition searchCondition);

    }
    interface SearchAllView extends BaseView{
        void showDataLoading();
        void hideDataLoading();
        void loadSearchAllWithCondition(List<Product> products);
        void loadSearchHistory(List<Search> searches);
        void loadNoSearchResult();
    }

    interface Presenter extends BasePresenter<BaseView> {

    }
}
