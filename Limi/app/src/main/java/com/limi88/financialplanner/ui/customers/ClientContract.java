package com.limi88.financialplanner.ui.customers;

import com.limi88.financialplanner.pojo.clients.Clients;
import com.limi88.financialplanner.pojo.clients.SearchConditions;
import com.limi88.financialplanner.pojo.costumer.Province;
import com.limi88.financialplanner.ui.base.BasePresenter;
import com.limi88.financialplanner.ui.base.BaseView;

import java.util.List;
import java.util.Map;

/**
 * Created by hehao
 * Date on 16/9/21.
 * Email: hao.he@limi88.com
 */
public interface ClientContract {
    interface ClientView extends BaseView {

        void showDataLoading();

        void hideDataLoading();

        void loadClients(Clients clients);

        void loadDataToUI();

        void loadProvince(List<Province> provinces);
        void setDataLoaded(boolean isDataLoaded);

    }

    interface AddClientView {
        void setCity(Province provinceId);

        void loadProvince(List<Province> provinces);
        void showDataLoading();

        void hideDataLoading();
        void setTags(String tags);

        void dismissAddClientUI();
    }

    interface Presenter extends BasePresenter<BaseView> {
        void getClientsInfo();

        void creatNewClient(Map<String, String> uploadList);

        void getProvinceInfo();

        void deleteClient(String id);
    }

    interface ProvinceView extends BaseView {

        void loadProvince(List<Province> provinces);
    }
    interface TagsView extends BaseView {

        void loadTags(SearchConditions  mSearchConditions);
    }

}
