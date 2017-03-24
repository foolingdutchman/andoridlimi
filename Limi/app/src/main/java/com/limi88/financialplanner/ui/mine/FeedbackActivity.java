package com.limi88.financialplanner.ui.mine;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hehao.library.base.BaseFragmentActivity;
import com.hehao.library.netstatus.NetUtils;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.di.component.ActivityComponent;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.ValidatorUtils;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import butterknife.Bind;

public class FeedbackActivity extends BaseFragmentActivity implements MineContract.FeedbackView,View.OnClickListener{

    @Bind(R.id.ll_left_btn)
    LinearLayout leftAction;

    @Bind(R.id.common_title)
    TextView mCommonTitle;
    @Bind(R.id.tv_header_back)
    TextView mLeftTitle;
    @Bind(R.id.et_feedtext)
    EditText mEtFeedtext;
    @Bind(R.id.et_contact)
    EditText mEtContact;
    @Bind(R.id.ll_warnning)
    LinearLayout mLlWarnning;
    @Bind(R.id.btn_feedback)
    Button mBtnFeedback;

    @Bind(R.id.iv_bottomline1)
    ImageView mImageView1;
    @Bind(R.id.iv_bottomline2)
    ImageView mImageView2;

    private String contact;
    private String feedString;

    @Inject
    MinePresenter mPresenter;
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_feedback;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        initializeInjector();
        mPresenter.setFeedbackView(this);
        mCommonTitle.setText("意见反馈");
        mCommonTitle.setVisibility(View.GONE);
        mLeftTitle.setVisibility(View.VISIBLE);
        mLeftTitle.setText("意见反馈");

        leftAction.setVisibility(View.VISIBLE);
        mEtContact.setSelected(true);
        mBtnFeedback.setOnClickListener(this);
        mBtnFeedback.setClickable(false);
        mBtnFeedback.setSelected(false);
        setResult(Constants.FEEDBACK_CODE);

        String phone = DataCenter.getCurrentUser().getData().getPhone();
        if (phone!=null&&phone!="") {
            mEtContact.setText(phone);
        }
        mEtContact.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mImageView2.setVisibility(View.VISIBLE);
                hideWarnning();
            } else {
                mImageView2.setVisibility(View.INVISIBLE);
                if (checkNameValid()) {
                    hideWarnning();
                } else showWarnning();

                if (checkNameValid() && checkContentValid()) {
                    feedButtonValid();
                } else feedButtonUnValid();
            }
        });
        mEtFeedtext.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mImageView1.setVisibility(View.VISIBLE);
            } else {
               mImageView1.setVisibility(View.INVISIBLE);
                if (checkNameValid() && checkContentValid()) {
                    feedButtonValid();
                } else feedButtonUnValid();
            }
        });

        mEtContact.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard();
//                mImageView2.setVisibility(View.INVISIBLE);
//                if (checkNameValid()) {
//                    hideWarnning();
//                } else showWarnning();
//
//                if (checkNameValid() && checkContentValid()) {
//                    feedButtonValid();
//                } else feedButtonUnValid();
            }

            return true;
        });
        mEtContact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (checkNameValid() && checkContentValid()) {
                    feedButtonValid();
                } else feedButtonUnValid();
            }
        });

        mEtFeedtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (checkNameValid() && checkContentValid()) {
                    feedButtonValid();
                } else feedButtonUnValid();
            }
        });
        mEtFeedtext.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                mImageView1.setVisibility(View.INVISIBLE);
//                if (checkNameValid() && checkContentValid()) {
//                    feedButtonValid();
//                } else feedButtonUnValid();
            }
            return true;
        });


    }

    private void hideKeyboard() {
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }


    @Override
    public void onStop() {
        super.onStop();
        mPresenter.setFeedbackView(null);
    }

    private void feedButtonValid() {
        mBtnFeedback.setSelected(true);
        mBtnFeedback.setClickable(true);

    }

    private void feedButtonUnValid() {
        mBtnFeedback.setSelected(false);
        mBtnFeedback.setClickable(false);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_feedback:
                contact = mEtContact.getText().toString().trim();
                feedString = mEtFeedtext.getText().toString().trim();

                postFeedback(contact, feedString);
                break;

        }
    }

    private void postFeedback(String phone, String content) {
        mPresenter.feedback(phone, content);
    }



    private void initializeInjector() {
        ActivityComponent component = ActivityComponent.Initializer.init(this);
        component.inject(this);
    }

    @Override
    public void dismissFeedbackByUpate() {
//        setResult(Constants.FEEDBACK_SUCCESS_CODE);
       finish();
    }

    public void showWarnning() {
        mLlWarnning.setVisibility(View.VISIBLE);
        mEtContact.setSelected(false);

    }

    public void hideWarnning() {

        mLlWarnning.setVisibility(View.INVISIBLE);
        mEtContact.setSelected(true);
    }


    public boolean checkNameValid() {
        String contact = mEtContact.getText().toString();
        if (ValidatorUtils.isMobile(contact)) {
            return true;
        } else return false;

    }

    public boolean checkContentValid() {
        String content = mEtFeedtext.getText().toString();

        if (content.length() == 0 || content == null) {
            return false;
        } else return true;

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

    public void backPre(View view) {
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
