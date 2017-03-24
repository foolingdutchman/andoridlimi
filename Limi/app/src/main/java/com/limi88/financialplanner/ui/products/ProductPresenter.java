package com.limi88.financialplanner.ui.products;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.api.products.ProductService;
import com.limi88.financialplanner.di.component.ApplicationComponent;
import com.limi88.financialplanner.greendao.dao.SearchDao;
import com.limi88.financialplanner.pojo.home.Product;
import com.limi88.financialplanner.pojo.products.ProductData;
import com.limi88.financialplanner.ui.base.BaseView;
import com.limi88.financialplanner.util.JsonUtils;
import com.limi88.financialplanner.util.ToastUtils;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehao on 16/9/12.
 */
public class ProductPresenter implements ProductContract.Presenter  {
    private ProductService mProductService;
    private ProductContract.ProductView mProductView;
    private ProductContract.SearchView mSearchView;
    private Subscription mSubscription;
    private RecyclerView filterView;
    private SearchDao mSearchDao;
    private ApplicationComponent applicationComponent;



    @Inject
    public ProductPresenter(ProductService mProductService, SearchDao mSearchDao, ApplicationComponent applicationComponent) {
        this.mProductService = mProductService;
        this.mSearchDao = mSearchDao;
        this.applicationComponent = applicationComponent;
    }


    public void getProductData(String path, String mToken,int page) {
//        mProductView.showDataLoading();
        mProductService.getProductInfo(path, mToken,page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productData -> {
                    if (productData != null && productData.getData().size() != 0) {
                        Log.i("ProductApi", productData.getData().get(0).getName());
                        saveDataToCache(productData, path);
                    }
                    mProductView.hideErrorView();
                    mProductView.loadDataToUI(productData.getData());
                }, throwable -> getProductsFromCache(path));
    }
    public void attachData(List<Product> products,String path, String mToken,int page) {
//        mProductView.showDataLoading();
        mProductService.getProductInfo(path, mToken,page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productData -> {
                    if (productData != null && productData.getData().size() != 0) {
                        Log.i("ProductApi", productData.getData().get(0).getName());
//                        saveDataToCache(productData, path);

                    }else {
                        ToastUtils.showToast("没有更多了...");
                    }
                    mProductView.hideErrorView();
//                    products.addAll(productData.getData());
                    mProductView.attachDataToUI(productData.getData());
                }, throwable -> getProductsFromCache(path));
    }
    public void getProductsFromCache(String path) {
        Observable.just(path).flatMap(s -> Observable.just(JsonUtils.
                        getProductsFromJson(s,
                                ((MainApplication) (applicationComponent.getContext())).getLogCacheDir())
        )).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productData -> {
                    if (mProductView!=null) {
                        if (productData.getData() != null && productData.getData().size() != 0) {
                            mProductView.loadDataToUI(productData.getData());
                            mProductView.hideErrorView();
                        } else {
                            mProductView.showErrorView();
                            mProductView.hideDataLoading();
                        }
                    }

                }, throwable -> {
                    if (mProductView!=null) {
                        mProductView.hideDataLoading();
                        mProductView.showErrorView();
                        mProductView.finishSweap();
                    }

                    Log.d("ProductApi", "Error:-----------" + throwable.getMessage());
                });
    }

    public void saveDataToCache(ProductData productData, String path) {
        Observable.just(path).flatMap(s -> Observable.just(JsonUtils.
                        saveProductsToJson(productData, s,
                                ((MainApplication) (applicationComponent.getContext())).getLogCacheDir())
        )).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> {
                    Log.d("ProductApi", "Error:-----------");
                }, throwable -> {
                    Log.d("ProductApi", "Error:-----------" + throwable.getMessage());
                });
    }



    public void seachDataBySort(String path, String mToken, String condition) {
        Log.i("SearchApi", "seachDataBySort----------------");
        mProductView.showDataLoading();
        HashMap<String, String> options = new HashMap<>();
        options.put("access_token", mToken);
        options.put("sort_by", condition);
        mProductService.searchProduct(path, options).flatMap(productData -> {
            if (productData.getData().size() != 0) {
                Log.i("SearchApi", productData.getData().get(0).getName());

            }

            return Observable.just(productData.getData());
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Product>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("SearchApi", "Error:-----------" + e.getMessage());
                        mProductView.hideDataLoading();
                    }

                    @Override
                    public void onNext(List<Product> products) {
//                        DataBundle<TodoListAction.Key> bundle = new DataBundle<>();
//                        bundle.put(TodoListAction.Key.LIST, products);
                        mProductView.loadDataToUI(products);
                        mProductView.hideDataLoading();
                    }
                });

    }

    public void seachDataWithCondition(String path, HashMap<String,String> options) {
        Log.i("SearchApi", "seachDataWithCondition----------------");
        mProductView.showDataLoading();
        mProductService.searchProduct(path, options).flatMap(productData -> {
            if ( productData.getData().size()!=0) {
                Log.i("seachDataWithCondition", productData.getData().get(0).getName());
            }

            return Observable.just(productData.getData());
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Product>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("SearchApi", "Error:-----------"+e.getMessage());
                        mProductView.hideDataLoading();
                    }

                    @Override
                    public void onNext(List<Product> products) {
//
                        mProductView.loadDataToUI(products);

                    }
                });

    }



    public void searchCondition(String path, String mToken) {
        mSearchView.showDataLoading();
        mProductService.searchProductCondition(path, mToken).observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchCondition -> {
                    Log.i("searchApi", "---------" + searchCondition.getData().getKeys().get(0));
                    mSearchView.loadSearchCondition(searchCondition);
                    mSearchView.hideDataLoading();
                }, throwable -> {
                    Log.i("searchApi", "---------" + throwable.getMessage());

                });

    }



    @Override
    public void attachView(@NonNull BaseView view) {
        mProductView = (ProductContract.ProductView) view;
    }

    @Override
    public void detachView() {
        mProductView = null;
    }


    public void attachSearchView(@NonNull BaseView view) {
        mSearchView = (ProductContract.SearchView) view;
    }


    public void detachSearchView() {
        mSearchView = null;
    }


    public void getProductDataWithoutCache(String s, String token,int page) {
        mProductView.showDataLoading();
        mProductService.getProductInfo(s, token,page)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(productData -> {
                    mProductView.loadDataToUI(productData.getData());
                }, throwable -> Log.i("productApi",throwable.getMessage()));
    }
}
