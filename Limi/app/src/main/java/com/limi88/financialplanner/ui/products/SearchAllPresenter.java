package com.limi88.financialplanner.ui.products;

import android.support.annotation.NonNull;
import android.util.Log;

import com.limi88.financialplanner.ui.base.BaseView;
import com.limi88.financialplanner.api.products.ProductService;
import com.limi88.financialplanner.greendao.bean.Search;
import com.limi88.financialplanner.greendao.dao.SearchDao;
import com.limi88.financialplanner.pojo.home.Product;
import com.limi88.financialplanner.pojo.products.ProductData;
import com.limi88.financialplanner.util.RequestHelper;
import com.limi88.financialplanner.greendao.dao.SearchDao.Properties;
import com.limi88.financialplanner.util.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liushuai on 16/9/16.
 */
public class SearchAllPresenter implements ProductContract.Presenter {
    private SearchDao mSearchDao;
    private ProductService mProductService;
    private ProductContract.SearchAllView mSearchAllView;
    private RequestHelper mRequestHelper;

    @Inject
    public SearchAllPresenter(ProductService mProductService, SearchDao mSearchDao, RequestHelper mRequestHelper) {
        this.mProductService = mProductService;
        this.mSearchDao = mSearchDao;
        this.mRequestHelper = mRequestHelper;
    }

    @Override
    public void attachView(@NonNull BaseView view) {
        mSearchAllView = (ProductContract.SearchAllView) view;
    }

    @Override
    public void detachView() {
        mSearchAllView = null;
    }

    public void searchAllProductWithCondition(String name, String mToken) {

        mSearchAllView.showDataLoading();
        if (!name.equals("")) {
            List<Search>searchList=mSearchDao.queryBuilder()
                    .where(Properties.SearchTitle.eq(name)).orderAsc(Properties.Id).list();
            if (searchList==null||searchList.size()==0) {
                mSearchDao.insertOrReplace(new Search(mRequestHelper.getTimestampLong(), name));
            }
        }

        mProductService.searchAllProductWithCondition(name, mToken)
                .flatMap(new Func1<ProductData, Observable<List<Product>>>() {
                    @Override
                    public Observable<List<Product>> call(ProductData productData) {
                        if (productData.isSuccess()) {
                            if (productData.getData().size() == 0 || productData.getData() == null) {
                                mSearchAllView.loadNoSearchResult();
                            }
                        }else  ToastUtils.showToast("请检查网络");

                        return Observable.just(productData.getData());
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Product>>() {
                    @Override
                    public void call(List<Product> products) {
                        if (products.size() == 0) {
                            mSearchAllView.loadNoSearchResult();
                        }
                        mSearchAllView.loadSearchAllWithCondition(products);
                        mSearchAllView.hideDataLoading();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mSearchAllView.loadNoSearchResult();

                        Log.i("SearchAll", throwable.getMessage());
                    }
                });
    }

    public void loadAllHistory() {
        Observable.just(mSearchDao)

                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<SearchDao, Observable<List<Search>>>() {
                    @Override
                    public Observable<List<Search>> call(SearchDao searchDao) {
                        return Observable.just(searchDao.loadAll());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Search>>() {
                    @Override
                    public void call(List<Search> searches) {
                        mSearchAllView.loadSearchHistory(searches);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.i("SearchAll", throwable.getMessage());
                    }
                });

    }
}
