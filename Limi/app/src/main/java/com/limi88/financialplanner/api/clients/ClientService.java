package com.limi88.financialplanner.api.clients;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.limi88.financialplanner.api.ProgressRequestListener;
import com.limi88.financialplanner.pojo.costumer.AddCustomerFeedback;
import com.limi88.financialplanner.pojo.costumer.Province;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.api.ServiceFactory;
import com.limi88.financialplanner.pojo.clients.Clients;
import com.limi88.financialplanner.pojo.costumer.Customer;
import com.limi88.financialplanner.pojo.costumer.Customers;
import com.limi88.financialplanner.util.PakeageInfoHelper;
import com.limi88.financialplanner.util.RequestHelper;
import com.limi88.financialplanner.util.SecurityUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehao
 * Date on 16/9/21.
 * Email: hao.he@limi88.com
 */
public class ClientService {
    public static final MediaType MediaTypeJSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final MediaType MEDIA_TYPE_JPEG = MediaType.parse("image/jpeg");
    private static final MediaType MEDIA_TYPE_ZIP = MediaType.parse("zip");
    private Context mContext;
    private RequestHelper mHelper;
    private String code;
    private String tonce;
    private String signature;
    @Inject
    public ClientService(Context mContext, RequestHelper mHelper) {
        this.mHelper=mHelper;
        this.mContext = mContext;
    }

    public Observable<Clients> getClientInfo(String mToken) {
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        signature= PakeageInfoHelper.getSignature(mContext);
        ClientsApi clientsApi = ServiceFactory.creatService(ClientsApi.class, Constants.LOGIN_URL,code,tonce,signature);
        return clientsApi.getClientInfo(mToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<AddCustomerFeedback> creatNewClient(String name,
                                              @Nullable String gender,
                                              @Nullable String birthday,
                                              @Nullable String phone,
                                              @Nullable int provinceId,
                                              @Nullable String status,
                                              @Nullable String level,
                                              @Nullable String tagNames,
                                              @Nullable String avatarUri,
    @Nullable String id) {
        Map<String, RequestBody> params = new HashMap<>();
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        signature= PakeageInfoHelper.getSignature(mContext);

        ClientsApi clientsApi = ServiceFactory.creatService(ClientsApi.class, Constants.LOGIN_URL,code,tonce,signature);
        Customers customers = new Customers();
        Customer customer = new Customer();
        customer.setName(name);
        customer.setBirthday(birthday);
        customer.setGender(gender);
        customer.setPhone(phone);
        customer.setLevel(level);
        customer.setTagNames(tagNames);
        customer.setStatus(status);
        customer.setProvinceId(provinceId);
        if (id!=null&&!id.equals("")) {
            customer.setId(id);
        }
//        RequestBody imagebody = RequestBody.create(okhttp3.MediaType.parse("image/png"), avatarUri);
        customers.setCustomer(customer);
        Gson gson = new Gson();
        String route = gson.toJson(customers);//通过Gson将Bean转化为Json字符串形式
        RequestBody body = RequestBody.create(MediaTypeJSON, route);
        return clientsApi.creatNewClient(body)
//        return clientsApi.creatNewClient(name,gender,birthday,phone,provinceId+"",status,level,tagNames)
                .subscribeOn(Schedulers.io());

    }


    public Observable<AddCustomerFeedback> creatNewClientForFile(String id, @Nullable String avatarUri) {
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        signature= PakeageInfoHelper.getSignature(mContext);

        ClientsApi clientsApi = ServiceFactory.creatService(ClientsApi.class, Constants.LOGIN_URL,code,tonce,signature);
        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        requestBodyBuilder.addFormDataPart("customer[avatar]", avatarUri, RequestBody.create(getMineTypeFromFileName(avatarUri), new File(avatarUri)));

        RequestBody body = requestBodyBuilder.build();
        return clientsApi.creatNewClientForFile(id, body)
                .subscribeOn(Schedulers.io());

    }
    public Observable<Province[]> getProvincesInfo() {
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        signature= PakeageInfoHelper.getSignature(mContext);

        ClientsApi clientsApi = ServiceFactory.creatService(ClientsApi.class, Constants.LOGIN_URL,code,tonce,signature);
        return clientsApi.getProvincesInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
    public Observable<String> getProvincesString() {
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        ClientsApi clientsApi = ServiceFactory.creatService(ClientsApi.class, Constants.LOGIN_URL,code,tonce,signature);
        return clientsApi.getProvincesString()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static MediaType getMineTypeFromFileName(String fileName){
        if (fileName.endsWith(".png")) {
            return MEDIA_TYPE_PNG;
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return MEDIA_TYPE_JPEG;
        } else {
            return MEDIA_TYPE_ZIP;
        }
    }

    public  Observable<AddCustomerFeedback> deleteClient(String id){
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        signature= PakeageInfoHelper.getSignature(mContext);

        ClientsApi clientsApi = ServiceFactory.creatService(ClientsApi.class, Constants.LOGIN_URL,code,tonce,signature);
        return clientsApi.deleteClient(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
