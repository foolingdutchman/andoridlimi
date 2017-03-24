package com.limi88.financialplanner.ui.login;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hehao.library.base.BaseLazyFragment;
import com.hehao.library.utils.LogUtils;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.ui.base.FragmentBackHandler;
import com.limi88.financialplanner.util.ToastUtils;
import com.limi88.financialplanner.util.ValidatorUtils;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hehao
 * Date on 16/10/8.
 * Email: hao.he@limi88.com
 */
public class RegisterFragment extends BaseLazyFragment implements FragmentBackHandler, View.OnClickListener {
    @Bind(R.id.common_title)
    TextView mCommonTitle;
    @Bind(R.id.mobile_edit_text)
    EditText mMobileEditText;
    @Bind(R.id.code_edit_text)
    EditText mCodeEditText;
    @Bind(R.id.btn_register)
    Button mBtnRegister;
    @Bind(R.id.login_button)
    Button mBtnLogin;
    private LoginPresenter mPresenter;

    public void setPresenter(LoginPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        mBtnRegister.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_register;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public boolean onBackPressed() {
        this.getSupportFragmentManager().beginTransaction().remove(this).commit();
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainScreen");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainScreen");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onClick(View view) {

        String mobile = mMobileEditText.getText().toString().trim();
        String code = mCodeEditText.getText().toString().trim();
        switch (view.getId()) {
            case R.id.btn_register:
//                mPresenter.sendCode(mobile);
                if (!ValidatorUtils.isMobile(mobile)) {
                    ToastUtils.showToast(R.string.mobile_text_error);
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.showToast(R.string.code_text_error);
                    return;
                }
                LogUtils.debug("----code ", code);

                mPresenter.register(mobile, code);
                break;
            case R.id.login_button:
                this.getSupportFragmentManager().beginTransaction().remove(this).commit();
                break;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MainApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
