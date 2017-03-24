package com.limi88.financialplanner.api.clients;

import com.limi88.financialplanner.pojo.clients.Clients;
import com.limi88.financialplanner.pojo.costumer.AddCustomerFeedback;
import com.limi88.financialplanner.pojo.costumer.Province;
import com.limi88.financialplanner.util.Constants;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by hehao
 * Date on 16/9/21.
 * Email: hao.he@limi88.com
 */
public interface ClientsApi {
    @GET(Constants.PRE + "/customers")
    Observable<Clients> getClientInfo(@Query("access_token") String mToken);

//    @Multipart
//    @POST(Constants.PRE+"/customers")
//    Observable <AddCustomerFeedback> creatNewClient(@Part("name") String name,
//                                        @Part("gender") @Nullable String gender,
//                                        @Part("birthday") @Nullable String birthday,
//                                        @Part("phone") @Nullable String phone,
//                                        @Part("province_id") @Nullable String provinceId,
//                                        @Part("status") @Nullable String status,
//                                        @Part("level") @Nullable String level,
//                                        @Part("tag_names") @Nullable String tagNames);

    @POST(Constants.PRE + "/customers")
    Observable<AddCustomerFeedback> creatNewClient(@Body RequestBody client);

//    @Multipart
//    @POST(Constants.PRE )
//    Observable<User> creatNewClient(@Nullable @Part("customer[name]") RequestBody name,
//                                    @Nullable @Part("customer[gender]") RequestBody phone,
//                                    @Nullable @Part("customer[birthday]") RequestBody birthday,
//                                    @Nullable @Part("customer[phone]") RequestBody provinceId,
//                                    @Nullable @Part("customer[province_id]") RequestBody gender,
//                                    @Nullable @Part("customer[status]") RequestBody organization,
//                                    @Nullable @Part("customer[level]") RequestBody level,
//                                    @Nullable @Part("customer[tag_names]") RequestBody tag_names);
//

    @Multipart
    @PUT(Constants.PRE + "/customers/" + "{id}")
    Observable<AddCustomerFeedback> creatNewClientForFile(@Path("id") String id, @Part("customer[avatar]") RequestBody photo);

    @GET(Constants.PRE + "tags/provinces")
    Observable<Province[]> getProvincesInfo();

    @GET(Constants.PRE + "tags/provinces")
    Observable<String> getProvincesString();

    @DELETE(Constants.PRE + "/customers/" + "{id}")
    Observable<AddCustomerFeedback>deleteClient(@Path("id") String id);

    
}
