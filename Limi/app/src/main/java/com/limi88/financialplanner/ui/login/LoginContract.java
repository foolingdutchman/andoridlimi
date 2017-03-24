package com.limi88.financialplanner.ui.login;






import com.limi88.financialplanner.pojo.account.Acount;
import com.limi88.financialplanner.ui.base.BasePresenter;
import com.limi88.financialplanner.ui.base.BaseView;
import com.limi88.financialplanner.greendao.bean.AccountBean;

/**
 * Created by xu on 16/5/31.
 */
public interface LoginContract {

    interface View extends BaseView {

        void showLoginLoading();
        void hideLoginLoading();

        void showSendCodeLoading();
        void hideSendCodeLoading();

        void finishWithLogin(Acount mAcount);
        void saveAccountInfo(AccountBean account );
        void saveAccountInfo(Acount account);
    }
    interface BindWXView extends BaseView{
        void onWXbindSuccess();
        void onWXbindFailed();
    }

    interface Presenter extends BasePresenter<View> {

        void login(String mobile, String code);
        void register(String mobile, String code);
        void reset(String mobile, String code);
        void sendCode(String mobile);
    }
}

