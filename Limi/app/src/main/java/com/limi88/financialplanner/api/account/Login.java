package com.limi88.financialplanner.api.account;


import com.limi88.financialplanner.pojo.account.Acount;
import com.limi88.financialplanner.pojo.ad.Ad;
import com.limi88.financialplanner.pojo.user.User;
import com.limi88.financialplanner.pojo.version.VersionInfo;
import com.limi88.financialplanner.util.Constants;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by liushuai on 16/9/3.
 */
public interface Login {
    @POST("login")
    @FormUrlEncoded
    Observable<Acount> login(@Field("phone") String phone, @Field("password") String password);

    @POST("send_sms")
    @FormUrlEncoded
    Observable<Acount> sendCode(@Field("phone") String phone, @Field("from") String password);

    @POST("sign")
    @FormUrlEncoded
    Observable<Acount> sign(@Field("phone") String phone, @Field("auth_code") String password, @Field("token") String token);

    @PUT("")
    Observable<Acount> reset(@Field("phone") String phone, @Field("password") String password);

    @GET("t")
    Observable<User> checkValidate(@Query("t") String mToken);

    @GET("last_version_info")
    Observable<VersionInfo> checkVersion(@Query("cat") String mCat);

    @FormUrlEncoded
    @POST("wx_user")
    Observable<Acount> loginByWX(@FieldMap Map<String, String> map);

    @GET("advertisements/ad")
    Observable<Ad> getAds(@Query("from")String path);
}
