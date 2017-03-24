package com.limi88.financialplanner.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.hehao.library.base.BaseLazyFragment;
import com.hehao.library.utils.LogUtils;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.util.ToastUtils;
import com.limi88.financialplanner.util.ValidatorUtils;
import com.squareup.leakcanary.RefWatcher;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hehao
 * Date on 16/10/9.
 * Email: hao.he@sunanzq.com
 */
public class ForgetPassWordFrgament extends BaseLazyFragment implements View.OnClickListener {

    LoginPresenter mPresenter;
    @Bind(R.id.mobile_edit_text)
    EditText mMobileEditText;
    @Bind(R.id.code_edit_text)
    EditText mCodeEditText;
    @Bind(R.id.et_verify_code)
    EditText mEtVerifyCode;
    @Bind(R.id.reset_button)
    Button mResetButton;
    @Bind(R.id.btn_back)
    Button mBtnBack;

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
        mResetButton.setOnClickListener(this);
        mBtnBack.setOnClickListener(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_forget_password;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }


    @Override
    public void onClick(View view) {
        String mobile = mMobileEditText.getText().toString().trim();
        String code = mCodeEditText.getText().toString().trim();
        switch (view.getId()) {
            case R.id.reset_button:
                if (!ValidatorUtils.isMobile(mobile)) {
                    ToastUtils.showToast(R.string.mobile_text_error);
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.showToast(R.string.code_text_error);
                    return;
                }
                LogUtils.debug("----code ", code);
                mPresenter.reset(mobile, code);
                break;
            case R.id.btn_back:
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
