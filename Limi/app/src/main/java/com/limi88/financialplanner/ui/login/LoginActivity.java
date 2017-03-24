package com.limi88.financialplanner.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hehao.library.netstatus.NetUtils;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.di.component.ActivityComponent;
import com.limi88.financialplanner.greendao.bean.AccountBean;
import com.limi88.financialplanner.pojo.account.Acount;
import com.limi88.financialplanner.pojo.version.VersionInfo;
import com.limi88.financialplanner.ui.MainActivity;
import com.limi88.financialplanner.ui.base.BaseLimiActivity;
import com.limi88.financialplanner.ui.base.BackHandlerHelper;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.ToastUtils;
import com.limi88.financialplanner.util.ValidatorUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.lang.ref.WeakReference;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by liushuai on 16/9/3.
 */
public class LoginActivity extends BaseLimiActivity implements LoginContract.View {


    @Inject
    LoginPresenter mPresenter;

    @Bind(R.id.mobile_edit_text)
    EditText mobileEditText;
    @Bind(R.id.code_edit_text)
    EditText codeEditText;
    @Bind(R.id.login_sendCode)
    Button sendCodeBtn;
    @Bind(R.id.login_button)
    Button loginButton;
    @Bind(R.id.iv_agree)
    ImageView mImageView;
    @Bind(R.id.iv_login_wechat)
    ImageView mWXView;
    @Bind(R.id.tv_read)
    TextView mTextView;
    private MaterialDialog mDialog;
    private SharedPreferences mSharedPreferences;

    private boolean isSendCodeAble = true;
    private boolean isLoginAble = true;
    private  boolean isWXBtnClicked =false;
    private boolean isAgree = true;
    private UMAuthListener umAuthListener;
    private WeakHandler mHandler;
    private String mUrl;
    private Intent intent;
    private boolean isWXbinded=false;
    private boolean isQuitApp=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        PushAgent.getInstance(getApplicationContext()).onAppStart();
        initHandler();
        initializeInjector();
        initView();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        mImageView.setSelected(true);
        mWXView.setSelected(true);
        intent = getIntent();
        mUrl = intent.getStringExtra(Constants.WEB_PAGE_DETAIL_LINK);
        SpannableString string = new SpannableString("我已阅读并同意《用户使用协议》");
        string.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.color_blue_agreement));       //设置文件颜色
                ds.setUnderlineText(true);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                getSupportFragmentManager().beginTransaction().replace(R.id.log_container, new LawFragment(), "lawfragment").commit();

            }
        }, 7, string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mTextView.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        mTextView.append(string);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
        umAuthListener = new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
//                Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
                mPresenter.logInByWX(data);
            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
//                Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
                isWXBtnClicked =false;
            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
//                Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
                isWXBtnClicked =false;
            }
        };

        mImageView.setOnClickListener(v -> {
            if (isAgree) {
                mImageView.setSelected(false);
                mWXView.setSelected(false);
                ToastUtils.showToast("不同意");
                isAgree = false;
                loginButton.setSelected(false);
                loginButton.setClickable(false);
            } else {
                mImageView.setSelected(true);
                mWXView.setSelected(true);
                ToastUtils.showToast("已同意");
                isAgree = true;
                if (codeEditText.getText().toString().length() == 6 &&
                        ValidatorUtils.isMobile(mobileEditText.getText().toString())) {
                    loginButton.setSelected(true);
                    loginButton.setClickable(true);
                } else {
                    loginButton.setSelected(false);
                    loginButton.setClickable(false);
                }
            }
        });
        mobileEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String inputText = s.toString();
                if (ValidatorUtils.isMobile(inputText) && isSendCodeAble) {
                    sendCodeBtn.setSelected(true);
                    sendCodeBtn.setClickable(true);
                } else {
                    sendCodeBtn.setSelected(false);
                    sendCodeBtn.setClickable(false);
                }
            }
        });
        codeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 6 && isAgree &&
                        ValidatorUtils.isMobile(mobileEditText.getText().toString())) {

                    loginButton.setSelected(true);
                    loginButton.setClickable(true);


                } else {
                    loginButton.setClickable(false);
                    loginButton.setSelected(false);
                }
            }
        });
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
    protected void onResume() {
        super.onResume();

        MobclickAgent.onResume(this);
        if (mHandler==null) {
            initHandler();
        }
        if (DataCenter.isSigned()) {
            this.finish();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.attachView(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.detachView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        killmThread();

    }


    public void initializeInjector() {
        ActivityComponent component = ActivityComponent.Initializer.init(this);
        component.inject(this);
    }

    private void initView() {
        mDialog = new MaterialDialog.Builder(this).progress(true, 0).build();

    }

    @Override
    public void showLoginLoading() {
        if (!isFinishing() && !mDialog.isShowing()) {
            mDialog.setContent("正在登录");
        }
    }

    @Override
    public void hideLoginLoading() {
        if (!isFinishing() && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void showSendCodeLoading() {
        if (!isFinishing() && !mDialog.isShowing()) {
            mDialog.setContent("正在注册");
        }
    }

    @Override
    public void hideSendCodeLoading() {
        if (!isFinishing() && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void finishWithLogin(Acount mAcount) {
        if (mUrl != null && mUrl != "") {
            Intent intent1 = new Intent();
            intent1.putExtra(Constants.WEB_PAGE_DETAIL_LINK, mUrl);
            setResult(Constants.LOGIN_SUCCESS_CODE, intent1);
        } else
            setResult(Constants.LOGIN_SUCCESS_CODE);
        isWXBtnClicked =false;
        killmThread();
        if (mAcount!=null) {
            isWXbinded=mAcount.getData().isBindWeixin();
        }

        if (isWXbinded) {
            this.startActivity(new Intent(this, MainActivity.class));
        }else this.startActivity(new Intent(this,BindWXActivity.class));
        this.finish();
    }

    @Override
    protected void onDestroy() {
        killmThread();
        super.onDestroy();
    }

    @Override
    public void saveAccountInfo(AccountBean account) {
        String mToken = account.getUserToken();
        String mobile = account.getUserMobile();
        mSharedPreferences = getSharedPreferences(Constants.USER_FLAG, MODE_PRIVATE);
        mSharedPreferences.edit().putString(Constants.TOKEN_FLAG, mToken).commit();
        mSharedPreferences.edit().putString(Constants.MOBILE_FLAG, mobile).commit();
        DataCenter.setSignedIn(mToken);
//        initCurrentUser(mToken);
    }

    @Override
    public void saveAccountInfo(Acount account) {
        String mToken = account.getData().getAccessToken();
        String mobile = account.getData().getPhone();
        mSharedPreferences = getSharedPreferences(Constants.USER_FLAG, MODE_PRIVATE);
        mSharedPreferences.edit().putString(Constants.TOKEN_FLAG, mToken).commit();
        mSharedPreferences.edit().putString(Constants.MOBILE_FLAG, mobile).commit();
        DataCenter.setSignedIn(mToken);
    }

    @OnClick({R.id.login_sendCode, R.id.login_button, R.id.iv_login_wechat})
    public void onClick(View view) {
        String mobile = mobileEditText.getText().toString().trim();
        String code = codeEditText.getText().toString().trim();
        switch (view.getId()) {

            case R.id.login_button:
                if (mobile.equals("")) {
                    ToastUtils.showToast(R.string.mobile_text_empty);
                }else
                if (!ValidatorUtils.isMobile(mobile)) {
                    ToastUtils.showToast(R.string.mobile_text_error);
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtils.showToast(R.string.code_text_blank);
                    return;
                }
                loginButton.setSelected(true);
                showLoginLoading();
                mPresenter.login(mobile, code);
//                codeEditText.setText("");
                break;
            case R.id.login_sendCode:
                if (ValidatorUtils.isMobile(mobile)) {
                    frozenSendCode();
                    mPresenter.sendCode(mobile);
                }
                break;
            case R.id.iv_login_wechat:

                if (isAgree&&!isWXBtnClicked) {
                    isWXBtnClicked =true;
//                    ToastUtils.showToast("内测版未开通微信登录功能");
                    UMShareAPI mShareAPI = UMShareAPI.get(LoginActivity.this);
                    mShareAPI.doOauthVerify(LoginActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);

                }
                break;
        }
    }

    private int seconds = 60;
    private int fromAuthCode = 1;

    private void frozenSendCode() {
        isSendCodeAble = false;
        sendCodeBtn.setSelected(false);
        sendCodeBtn.setClickable(false);

        Message message = mHandler.obtainMessage();
        message.what = fromAuthCode;
        message.obj = seconds;
        mHandler.sendMessageDelayed(message, 1000);
    }

    private void initHandler() {
        if (mHandler == null) {
            mHandler = new WeakHandler(this) {
                @Override
                public void handleMessage(Message msg) {
                    int lastSeconds = (int) msg.obj;
                    if (sendCodeBtn != null) {
                        if (lastSeconds > 0) {
                            sendCodeBtn.setText("剩余" + lastSeconds + "秒");
                            msg = Message.obtain();
                            msg.what = fromAuthCode;
                            msg.obj = lastSeconds - 1;
                            sendMessageDelayed(msg, 1000);
                        } else {
                            isSendCodeAble = true;
                            sendCodeBtn.setText("获取验证码");
                            sendCodeBtn.setClickable(true);
                            sendCodeBtn.setSelected(true);
                        }
                    }
                }
            };
        }
    }

    @Override
    public void backPre(View v) {
        finish();
    }

    @Override
    public void showUpdateUI(VersionInfo info) {

    }

    @Override
    public void showNewInfoHint(boolean isShow) {

    }

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            if (!isQuitApp) {
                ToastUtils.showToast(getResources().getString(R.string.notice_quit_app));
                isQuitApp = true;
            } else {
                killmThread();
                LoginActivity.this.finish();
            }
        }
    }

    private void killmThread() {
        if (mHandler != null) mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }

    static class WeakHandler extends Handler {
        WeakReference<Activity> mWeakReference;

        public WeakHandler(Activity activity) {
            mWeakReference = new WeakReference<Activity>(activity);
        }
    }
}