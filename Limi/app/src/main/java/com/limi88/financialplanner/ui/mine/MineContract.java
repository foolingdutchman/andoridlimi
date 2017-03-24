package com.limi88.financialplanner.ui.mine;


import com.limi88.financialplanner.greendao.bean.AccountBean;
import com.limi88.financialplanner.pojo.costumer.Province;
import com.limi88.financialplanner.pojo.user.User;
import com.limi88.financialplanner.ui.base.BasePresenter;
import com.limi88.financialplanner.ui.base.BaseView;

/**
 * Created by hehao on 16/9/19.
 */
public interface MineContract {
    interface MineView extends BaseView {

        void showDataLoading();

        void hideDataLoading();

        void loadAccount(AccountBean accountBean);

        void loadUserInfoToUI(User user);
        void loadUserInfoToUI();
        void setDataLoaded(boolean isDataLoaded);
        void dismissUpDateUserUI();
        void upLoadUerInfo(User user);

        void gotoLogin();
    }
  interface MeProfileView{

      void setCity(Province province);

      void setProvinces(Province[] provinces);
      void dismiss();
  }

    interface  FeedbackView extends BaseView{
      void  dismissFeedbackByUpate();
    }

    interface Presenter extends BasePresenter<BaseView> {
        void getAccountInfo();

        void getUserInfo();

        void uploadUserInfo(User user);
    }



}
