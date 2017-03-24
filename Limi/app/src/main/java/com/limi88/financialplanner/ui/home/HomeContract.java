package com.limi88.financialplanner.ui.home;

import com.limi88.financialplanner.pojo.BaseData;
import com.limi88.financialplanner.pojo.home.servicetools.ServiceTools;
import com.limi88.financialplanner.ui.base.BasePresenter;
import com.limi88.financialplanner.pojo.home.HomeData;
import com.limi88.financialplanner.ui.base.BaseView;

/**
 * Created by hehao
 * Date on 16/10/8.
 * Email: hao.he@limi88.com
 */
public interface HomeContract {
    interface HomeView extends BaseView {
        void showLoadingHomeData();

        void hideLoadingHomeData();

        void showErrorView();

        void hideErrorView();

        void loadDataToUI(HomeData mHomeData);

        void loadDataToUI();


    }

    interface ServiceToolView extends BaseView {
        void showLoadingHomeData();

        void hideLoadingHomeData();

        void loadDataToUI(ServiceTools mServiceTools);

    }

    interface SubView extends BaseView {

        void loadDataToUI(BaseData baseData);

        void loadDataToUI();
        void attachData(int page);
        void attachData(BaseData baseData);

    }

    interface Presenter extends BasePresenter<BaseView> {
        void loadHomeData(String mToken);

        void loadServiceData();

        void loadSubPageData(String path, int page);
        void attachSubPageData(String path, int page);
    }


}
