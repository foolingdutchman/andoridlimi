package com.limi88.financialplanner.api.products;

import com.limi88.financialplanner.pojo.products.ProductData;
import com.limi88.financialplanner.pojo.searchcondition.SearchCondition;
import com.limi88.financialplanner.util.Constants;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by hehao on 16/9/12.
 */
public interface ProductApi {
    @GET(Constants.PRE+"{path}"+"/search")
    Observable<ProductData> getProductInfo( @Path("path")String mPath,@Query("access_token")String mToken,@Query("page")int page);

    @GET(Constants.PRE+"{path}"+"/search")
    Observable<ProductData> searchProduct( @Path("path")String mPath,@QueryMap Map<String, String> options);

    @GET(Constants.PRE+"exchange/search")
    Observable<ProductData> searchAllProductsWithCondition( @QueryMap Map<String, String> options);

    @GET(Constants.PRE+"{path}"+"/search_conditions")
    Observable<SearchCondition> searchProductCondition( @Path("path")String mPath,@Query("access_token")String mToken);


}
