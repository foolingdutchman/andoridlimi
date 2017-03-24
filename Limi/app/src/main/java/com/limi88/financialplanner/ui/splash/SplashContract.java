package com.limi88.financialplanner.ui.splash;

import com.limi88.financialplanner.pojo.ad.Ad;
import com.limi88.financialplanner.ui.base.BasePresenter;
import com.limi88.financialplanner.ui.base.BaseView;

/**
 * Created by hehao
 * Date on 16/12/15.
 * Email: hao.he@sunanzq.com
 */
public interface SplashContract {

    interface SplashView extends BaseView{
        void loadDataToUI();
        void loadDataToUI(Ad mAd);
    }
    interface Presenter extends BasePresenter<BaseView>{
        void getAds();
    }
}
