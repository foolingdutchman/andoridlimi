package com.limi88.financialplanner.api.mine;

import android.content.Context;

import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.di.component.ApplicationComponent;
import com.limi88.financialplanner.pojo.user.User;
import com.limi88.financialplanner.util.BitmapUtils;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.api.ServiceFactory;
import com.limi88.financialplanner.util.PakeageInfoHelper;
import com.limi88.financialplanner.util.RequestHelper;
import com.limi88.financialplanner.util.SecurityUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehao
 * Date on 16/9/27.
 * Email: hao.he@limi88.com
 */
public class MineService {
    public static final MediaType MediaTypeJSON = MediaType.parse("application/json; charset=utf-8");
    private Context mContext;
    private RequestHelper mHelper;
    private String code;
    private String tonce;
    private String signature;
    @Inject
    public MineService(Context mContext, RequestHelper mHelper,ApplicationComponent mComponent) {
        this.mHelper=mHelper;
        this.mContext = mComponent.getContext();

    }

    public Observable<User> getUserInfo(String mToken) {
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        signature= PakeageInfoHelper.getSignature(mContext);
        MineApi mineApi = ServiceFactory.creatService(MineApi.class, Constants.LOGIN_URL,code,tonce,signature);
        return mineApi.getUserInfo(mToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
     public Observable<User> feedback(String phone,String content) {
         tonce = mHelper.getTimestamp();
         code = SecurityUtils.getHMACSHA1(tonce);
         signature= PakeageInfoHelper.getSignature(mContext);
        MineApi mineApi = ServiceFactory.creatService(MineApi.class, Constants.LOGIN_URL,code,tonce,signature);
        return mineApi.feedback(content,phone,"android")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    public Observable<User> updateUserInfo(User user) {
        String name = user.getData().getName();
        String provinceId = user.getData().getProvinceId() + "";
        String gender = user.getData().getGender();
        String organization = user.getData().getOrganization();
        String desc = user.getData().getDesc();
        Map<String, RequestBody> bodyMap = null;
        RequestBody param1 = null;
        RequestBody param2 = null;
        RequestBody param3 = null;
        RequestBody param4 = null;
        RequestBody param5 = null;
        RequestBody param6 = null;
        tonce = mHelper.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        signature= PakeageInfoHelper.getSignature(mContext);

        MineApi mineApi = ServiceFactory.creatService(MineApi.class, Constants.LOGIN_URL,code,tonce,signature);
        if (name != null) {
            param1 = RequestBody.create(MediaType.parse("text/plain"), name);
        }
        if (provinceId != null) {
            param3 = RequestBody.create(MediaType.parse("text/plain"), provinceId);
        }
        if (gender != null) {
            param4 = RequestBody.create(MediaType.parse("text/plain"), gender);
        }
        if (organization != null) {
            param5 = RequestBody.create(MediaType.parse("text/plain"), organization);
        }
        if (desc != null) {
            param6 = RequestBody.create(MediaType.parse("text/plain"), desc);
        }
        bodyMap = new HashMap<>();
        if (user.getData().getAvatarUrl() != null) {
            File file = new File(user.getData().getAvatarUrl());
            if (file.exists()) {
                File cacheFile=new File(((MainApplication)mContext).getLogCacheDir()+File.separator+"imgCaches"+File.separator+"avatar"+SecurityUtils.getTimestamp()+".png");
                if (!cacheFile.getParentFile().exists()) {
                    cacheFile.getParentFile().mkdir();
                }
                BitmapUtils.compressImage(file,cacheFile,null,false);
                bodyMap.put("user[avatar]" + "\"; filename=\"" + cacheFile.getName(), RequestBody.create(MediaType.parse("image/png"), cacheFile));
            }
        }
        if (user.getData().getPersonalImages()!=null) {
            for (int i = 0; i < user.getData().getPersonalImages().size() ; i++) {

                File photo = new File(user.getData().getPersonalImages().get(i));
                if (photo.exists()) {
                    File cacheFile=new File(((MainApplication)mContext).getLogCacheDir()+File.separator+"imgCaches"+File.separator+"photo"+i+"-"+SecurityUtils.getTimestamp()+".png");
                    if (!cacheFile.getParentFile().exists()) {
                        cacheFile.getParentFile().mkdir();
                    }
                    BitmapUtils.compressImage(photo,cacheFile,null,false);
                    int a = i + 1;
                    bodyMap.put("user[photo" + a + "]" + "\"; filename=\"" + cacheFile.getName(), RequestBody.create(MediaType.parse("image/png"), cacheFile));
                }

            }
        }

        return mineApi.updateUserInfo(param1, param3, param4, param5, param6, bodyMap)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io());

    }

}
