package com.limi88.financialplanner.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hehao.library.base.BaseFragmentActivity;
import com.hehao.library.netstatus.NetUtils;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.di.component.ActivityComponent;
import com.limi88.financialplanner.ui.MainActivity;
import com.limi88.financialplanner.util.ToastUtils;
import com.limi88.financialplanner.wxapi.WXEntryActivity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;

public class BindWXActivity extends BaseFragmentActivity implements View.OnClickListener,LoginContract.BindWXView{

   @Bind(R.id.wx_bind_button)
    Button mWXbindButton;
    @Bind(R.id.tv_skip)
    TextView mSkipText;
    @Inject
    LoginPresenter mPresenter;
    private UMAuthListener umAuthListener;
    private boolean isWXBtnClicked=false;
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_bind_wx;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        initializeInjector();
        mPresenter.setWXView(this);
        mWXbindButton.setOnClickListener(this);
        mSkipText.setOnClickListener(this);
        initClickedText();
        initWXAuthListener();
    }

    private void initClickedText() {
        String text=getResources().getString(R.string.skip_to_start);
        SpannableString spStr=new SpannableString(text);
        spStr.setSpan(new ClickableSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.color_white60));       //设置文件颜色
                ds.setUnderlineText(true);      //设置下划线
            }

            @Override
            public void onClick(View widget) {
                Log.i("Login", "onTextClick........");
                BindWXActivity.this.startActivity(new Intent(BindWXActivity.this, MainActivity.class));
                BindWXActivity.this.finish();
            }
        }, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSkipText.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        mSkipText.append(spStr);
        mSkipText.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
    }

    @Override
    protected void onStop() {
        super.onStop();
//        mPresenter.setWXView(null);
    }

    private void initWXAuthListener() {
        umAuthListener = new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
//                Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
                mPresenter.bindWithWX(data);
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
    public void initializeInjector() {
        ActivityComponent component = ActivityComponent.Initializer.init(this);
        component.inject(this);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wx_bind_button:
                if (!isWXBtnClicked) {
                    isWXBtnClicked =true;
                    UMShareAPI mShareAPI = UMShareAPI.get(BindWXActivity.this);
                    mShareAPI.doOauthVerify(BindWXActivity.this, SHARE_MEDIA.WEIXIN, umAuthListener);
                }
                break;
            case R.id.tv_skip:
                this.startActivity(new Intent(this, MainActivity.class));
                this.finish();
                break;
        }
    }

    @Override
    public void onWXbindSuccess() {
        ToastUtils.showToastForTest("绑定成功");
        this.startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

    @Override
    public void onWXbindFailed() {
        isWXBtnClicked =false;
    }
}
