package com.limi88.financialplanner.ui.customers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.api.clients.ClientService;
import com.limi88.financialplanner.di.component.ApplicationComponent;
import com.limi88.financialplanner.pojo.clients.Clients;
import com.limi88.financialplanner.pojo.costumer.Province;
import com.limi88.financialplanner.ui.base.BaseView;

import com.limi88.financialplanner.ui.mine.MineContract;
import com.limi88.financialplanner.util.BitmapUtils;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.HttpRequstHelper;
import com.limi88.financialplanner.util.JsonUtils;
import com.limi88.financialplanner.util.PakeageInfoHelper;
import com.limi88.financialplanner.util.SecurityUtils;
import com.limi88.financialplanner.util.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hehao
 * Date on 16/9/21.
 * Email: hao.he@limi88.com
 */
public class ClientPresenter implements ClientContract.Presenter {
    private ClientContract.ClientView clientView;
    private ClientService mClientService;
    private Context mApplactionContext;

    private ClientContract.ProvinceView provinceView;
    private MineContract.MeProfileView meProfileView;
    private ClientContract.AddClientView addClinetView;
    private ClientContract.TagsView mTagsView;
    private List<Province> provinceList;
    private String signatue;

    public void setProvinceView(ClientContract.ProvinceView provinceView) {
        this.provinceView = provinceView;
    }

    public void setMeProfileView(MineContract.MeProfileView meProfileView) {
        this.meProfileView = meProfileView;
    }

    public void setAddClientView(ClientContract.AddClientView addClinetView) {
        this.addClinetView = addClinetView;
    }

    @Inject
    @Singleton
    public ClientPresenter(ClientService mClientService, ApplicationComponent applicationComponent) {
        this.mClientService = mClientService;
        mApplactionContext = applicationComponent.getContext();
    }

    @Override
    public void getClientsInfo() {
        mClientService.getClientInfo(Constants.CURRENT_TOKEN).observeOn(AndroidSchedulers.mainThread())
                .subscribe(clients -> {
                    if (clientView!=null) {
                        clientView.hideDataLoading();
                        clientView.loadClients(clients);
                    }
                    saveDataToCache(clients, ((MainApplication) mApplactionContext).getLogCacheDir());
                }, throwable -> {
                    Log.i("ClientApi", throwable.getMessage()+"");
                    if (clientView != null) {
                        clientView.hideDataLoading();
                    }
                    readDataFromCache(((MainApplication) mApplactionContext).getLogCacheDir());
                });
    }

    @Override
    public void creatNewClient(Map<String, String> uploadList) {
        String name = uploadList.get("name");
        String photo = uploadList.get("photo");
        String gender = uploadList.get("gender");
        String birthDate = uploadList.get("birthDate");
        String mobile = uploadList.get("phone");
        String province = uploadList.get("provinceId");
        int provinceId = Integer.parseInt(province);
        String status = uploadList.get("status");
        String catergory = uploadList.get("catergory");
        String tags = uploadList.get("tags");
        String id = uploadList.get("id");
        Map<String, String> textParams = new HashMap<>();
        textParams.put("customer[name]", name);
        Map<String, String> fileParams = new HashMap<>();
        if (photo!=null) {
            String cachePhoto=compressImage(photo);
            fileParams.put("customer[avatar]", cachePhoto);
        }
        signatue= PakeageInfoHelper.getSignature(mApplactionContext);

        mClientService.creatNewClient(name,
                gender, birthDate, mobile, provinceId, status, catergory, tags, photo, id)

                .flatMap(addCustomerFeedback -> {
                    if (addCustomerFeedback.isSuccess()) {
                        int id1 = addCustomerFeedback.getData().getId();
                        Log.i("NewCustomer", "id:----------" + id1);
                        if (photo != null && !photo.equals("")) {
                            return Observable.just(HttpRequstHelper.sendMultiRequestBody(Constants.HOST_WITH_PRE + "/customers/" + id1
                                    , HttpRequstHelper.HTTPMethod.PUT
                                    , textParams, fileParams,signatue));
                        } else {
                            return Observable.just("success");
                        }

                    } else return Observable.just("failed");
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.i("NewCustomer", s.toString());

                    if (s.equals("failed")) {
                        ToastUtils.showToast("添加失败！");
                    } else {
                        addClinetView.dismissAddClientUI();
                        ToastUtils.showToast("添加成功！");
                    }

                }, throwable -> {
                    Log.i("NewCustomer", throwable.toString()+"");
                    addClinetView.dismissAddClientUI();
                });


    }

    private String compressImage(String photo) {
        File photofile = new File(photo);
        String cacheFile =null;
        if (photofile.exists()) {

            File targetFile = new File(((MainApplication) mApplactionContext).getLogCacheDir()+File.separator+"imgCaches" + File.separator + SecurityUtils.getTimestamp() + ".png");
            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }
            BitmapUtils.compressImage(photofile, targetFile, null, false);
            cacheFile = targetFile.getPath();
        }
        return cacheFile;
    }

    @Override
    public void getProvinceInfo() {
        mClientService.getProvincesInfo().subscribe(provinces -> {
            provinceList = new ArrayList<>();

            for (int i = 0; i < provinces.length; i++) {
                provinceList.add(provinces[i]);
                Constants.PROVINCES_LIST.add(provinces[i]);
            }
            if (clientView != null) {

                clientView.loadProvince(provinceList);
            }
            if (addClinetView != null) {

                addClinetView.loadProvince(provinceList);
            }
            if (provinceView != null) {
                provinceView.loadProvince(provinceList);
            } else if (meProfileView != null) {
                meProfileView.setProvinces(provinces);
            }
        }, throwable -> {
            Log.i("ClientApi", throwable.getMessage()+"");
        });

    }

    @Override
    public void deleteClient(String id) {
        mClientService.deleteClient(id).observeOn(AndroidSchedulers.mainThread()).subscribe(
                feedback -> {
                    if (feedback.isSuccess()) {
                        ToastUtils.showToast("删除成功");
                        addClinetView.dismissAddClientUI();
                    }
                }, throwable -> {
                    Log.i("ClientApi", throwable.getMessage()+"");
                }
        );

    }

    public void saveDataToCache(Clients clients, String path) {
        Observable.just(clients)
                .flatMap(clients1 -> Observable.just(JsonUtils.saveClientsToJson(clients1, path)))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(success -> {
                    if (success) {
                        ToastUtils.showToastForTest("保存成功");
                    }
                }, throwable -> Log.i("ClientApi", throwable.getMessage()+""));
    }

    public void readDataFromCache(String path) {
        Observable.just(path)
                .flatMap(path1 -> Observable.just(JsonUtils.getClientsFromJson(path1)))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(clients -> {
                    clientView.loadClients(clients);
                }, throwable -> {
                    Log.i("ClientApi", throwable.getMessage()+"");
//                    ToastUtils.showToast("请检查网络");
                });
    }

    public Clients readDataFromCache() {
        String path = ((MainApplication) mApplactionContext).getLogCacheDir();
        return JsonUtils.getClientsFromJson(path);
    }

    @Override
    public void attachView(@NonNull BaseView view) {
        clientView = (ClientContract.ClientView) view;
    }

    @Override
    public void detachView() {
        clientView = null;
    }

    public void getClientsInfoWithoutCache() {
        mClientService.getClientInfo(Constants.CURRENT_TOKEN).observeOn(AndroidSchedulers.mainThread())
                .subscribe(clients -> {
                    clientView.hideDataLoading();
                    clientView.loadClients(clients);
                }, throwable -> {
                    Log.i("ClientApi", throwable.getMessage()+"");
                    if (clientView != null) {
                        clientView.hideDataLoading();
                    }
                });
    }

    public void getTagsInfo() {
        mClientService.getClientInfo(Constants.CURRENT_TOKEN).observeOn(AndroidSchedulers.mainThread())
                .subscribe(clients -> {
                    if (mTagsView!=null) {
                        mTagsView.loadTags(clients.getData().getSearchConditions());
                    }
                }, throwable -> {
                    Log.i("ClientApi", throwable.getMessage()+"");
                });
    }

    public void setTagsView(ClientContract.TagsView tagsView) {
        mTagsView = tagsView;
    }
}
