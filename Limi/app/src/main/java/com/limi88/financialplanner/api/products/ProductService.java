package com.limi88.financialplanner.api.products;

import android.content.Context;

import com.limi88.financialplanner.api.ServiceFactory;
import com.limi88.financialplanner.pojo.products.ProductData;
import com.limi88.financialplanner.pojo.searchcondition.SearchCondition;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.PakeageInfoHelper;
import com.limi88.financialplanner.util.RequestHelper;
import com.limi88.financialplanner.util.SecurityUtils;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by hehao on 16/9/12.
 */
public class ProductService {
    private Context mContext;
    private RequestHelper mHelper;
    private String code;
    private String tonce;
    private String signature;
    @Inject
    public ProductService(Context mContext, RequestHelper mHelper) {
        this.mHelper=mHelper;
        this.mContext = mContext;

    }

    public Observable<ProductData> getProductInfo(String path,String mToken,int page) {
        getCode();

        ProductApi homeApi = ServiceFactory.creatService(ProductApi.class, Constants.HOME_URL,code,tonce,signature);
        return homeApi.getProductInfo(path,mToken,page)
                .subscribeOn(Schedulers.io());
    }

    public Observable<ProductData> searchProduct(String path,Map<String, String> options) {
        getCode();
        ProductApi homeApi = ServiceFactory.creatService(ProductApi.class, Constants.HOME_URL,code,tonce,signature);
        return homeApi.searchProduct(path, options)
                .subscribeOn(Schedulers.io());
    }

    public Observable<SearchCondition> searchProductCondition(String path,String mToken) {
       getCode();
        ProductApi homeApi = ServiceFactory.creatService(ProductApi.class, Constants.HOME_URL,code,tonce,signature);
        return homeApi.searchProductCondition(path, mToken)
                .subscribeOn(Schedulers.io());
    }

    private void getCode() {
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        signature= PakeageInfoHelper.getSignature(mContext);

    }

    public Observable<ProductData> searchAllProductWithCondition(String name, String mToken) {
        HashMap<String,String> opitons=new HashMap<>();
        opitons.put("access_token",mToken);
        opitons.put("key",name);
        getCode();
        ProductApi homeApi = ServiceFactory.creatService(ProductApi.class, Constants.HOME_URL,code,tonce,signature);
        return homeApi.searchAllProductsWithCondition(opitons)
                .subscribeOn(Schedulers.io());
    }



}
