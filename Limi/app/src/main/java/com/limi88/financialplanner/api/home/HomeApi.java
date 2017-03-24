package com.limi88.financialplanner.api.home;

import com.limi88.financialplanner.pojo.BaseData;
import com.limi88.financialplanner.pojo.home.servicetools.ServiceTools;
import com.limi88.financialplanner.pojo.hotnews.HotNews;
import com.limi88.financialplanner.pojo.newyearopenning.NewYearOpenning;
import com.limi88.financialplanner.pojo.topics.Topic;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.pojo.home.HomeData;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hehao on 16/9/5.
 */
public interface HomeApi {

//    @POST("login") @FormUrlEncoded
//    Observable<Acount> login(@Field("phone") String phone, @Field("password") String password);
    @GET(Constants.PRE+"/home")
    Observable<HomeData> getHomeInfo(@Query("access_token") String mToken);

    @GET(Constants.PRE+"/infos/list/"+"{path}")
    Observable<HotNews> getSubPage(@Path("path")String path,@Query("page")int page );

    @GET(Constants.PRE+"/infos/list/"+"{path}")
    Observable<Topic> getTopics(@Path("path")String path ,@Query("page")int page);

     @GET(Constants.PRE+"/service_tools")
    Observable<ServiceTools> getServiceTools();
    @POST(Constants.PRE+"/likes")
    @FormUrlEncoded
    Observable <BaseData> postLikes(@Field("typeable_name") String name,@Field("typeable_id") int id);
}
