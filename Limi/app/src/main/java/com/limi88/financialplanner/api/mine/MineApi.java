package com.limi88.financialplanner.api.mine;

import android.support.annotation.Nullable;

import com.limi88.financialplanner.pojo.account.Acount;
import com.limi88.financialplanner.pojo.user.User;
import com.limi88.financialplanner.util.Constants;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hehao
 * Date on 16/9/27.
 * Email: hao.he@limi88.com
 */
public interface MineApi {

    @GET(Constants.PRE + "/users/info")
    Observable<User> getUserInfo(@Query("access_token") String mToken);

    @Multipart
    @POST(Constants.PRE + "/users")
    Observable<User> updateUserInfo(@Nullable @Part("user[name]") RequestBody phone,
                                      @Nullable @Part("user[province_id]") RequestBody provinceId,
                                      @Nullable @Part("user[gender]") RequestBody gender,
                                      @Nullable @Part("user[organization]") RequestBody organization,
                                      @Nullable @Part("user[desc]") RequestBody desc,
                                      @Nullable  @PartMap Map<String,RequestBody> params);
    @POST(Constants.PRE + "feedbacks") @FormUrlEncoded
    Observable<User> feedback(@Field("text") String content,@Field("phone") String phone, @Field("org") String org);
}
