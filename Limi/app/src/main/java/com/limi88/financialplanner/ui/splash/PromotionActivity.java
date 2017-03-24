package com.limi88.financialplanner.ui.splash;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.hehao.library.base.BaseFragmentActivity;
import com.hehao.library.netstatus.NetUtils;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.di.component.ActivityComponent;
import com.limi88.financialplanner.pojo.ad.Ad;
import com.limi88.financialplanner.ui.MainActivity;
import com.limi88.financialplanner.ui.login.LoginActivity;
import com.limi88.financialplanner.ui.login.LoginPresenter;
import com.limi88.financialplanner.util.Constants;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;

public class PromotionActivity extends Activity implements SplashContract.SplashView {
    private WeakHandler mHandler;
    private SharedPreferences mPreferences;
    private  String mToken;
    private ImageView mSkipView;
    @Bind(R.id.iv_ad)
    ImageView mAdView;
    @Inject
    SplashPresenter mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);
        initViewsAndEvents();
    }



    protected void initViewsAndEvents() {
        ButterKnife.bind(this);
        if (isAppInited()) {
            initializeInjector();
            mPresenter.attachView(this);
            initAdsPage();
        } else {

            startActivity(new Intent(this,WelcomeActivity.class));
            this.finish();
        }

    }

    private void initAdsPage() {

        mSkipView= (ImageView) findViewById(R.id.iv_skip);

        loadDataToUI();
        initHandler();
        mPreferences = getSharedPreferences(Constants.USER_FLAG, MODE_PRIVATE);
        mToken = mPreferences.getString(Constants.TOKEN_FLAG, "");
        if (mToken != "") {
            DataCenter.setSignedIn(mToken);
        }
        Message message = Message.obtain();
        message.obj = 2;
        mHandler.sendMessageDelayed(message, 1000);
    }


    private void initHandler() {
        if (mHandler == null) {
            mHandler = new WeakHandler(this) {
                @Override
                public void handleMessage(Message msg) {
                    int lastSeconds = (int) msg.obj;
                    if (lastSeconds > 0) {
                        msg = Message.obtain();
                        msg.obj = lastSeconds - 1;
                        sendMessageDelayed(msg, 1000);
                    } else {
                        finishAndKillmThread();
                    }

                }
            };
        }
    }

    private void finishAndKillmThread() {
        if (mHandler != null) mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
        if (DataCenter.isSigned()) {
            startActivity(new Intent(this,MainActivity.class));
        }else
        startActivity(new Intent(this, LoginActivity.class));
        this.finish();
    }
    public void skip(View view) {
        finishAndKillmThread();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        finishAndKillmThread();
    }

    @Override
    public void loadDataToUI() {
        mPresenter.getAds();
    }

    @Override
    public void loadDataToUI(Ad mAd) {

        if (mAd!=null&&mAd.getData()!=null) {
            mSkipView.setVisibility(View.VISIBLE);
            mSkipView.setOnClickListener(v -> finishAndKillmThread());
           String adUrl= mAd.getData().getFiles().get(0).getUrl();
            Glide.with(this).load(adUrl)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .crossFade()
//                    .bitmapTransform(new CropSquareTransformation(this))

                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            int width=resource.getWidth();
                            int height=resource.getHeight();

                            int wwidth=(PromotionActivity.this).getWindowManager().getDefaultDisplay().getWidth();
                            int wHeight=(PromotionActivity.this).getWindowManager().getDefaultDisplay().getHeight();
                           int ivWidth=mAdView.getMeasuredWidth();
                           int ivHeight =mAdView.getMeasuredHeight();
                            float index=((float)ivHeight)/ivWidth;
                            int viewHeight= (int) (width*index);
                            Bitmap bitmap=  Bitmap.createBitmap(resource, 0, 0, width, viewHeight);
                                mAdView.setImageBitmap(bitmap);

                        }
                    });
        }

    }

    static class WeakHandler extends Handler {
        WeakReference<Activity> mWeakReference;

        public WeakHandler(Activity activity) {
            mWeakReference = new WeakReference<Activity>(activity);
        }
    }
    private boolean isAppInited(){

        return getSharedPreferences(Constants.INIT_FLAG,MODE_PRIVATE).getBoolean(Constants.APP_INIT,false);
    }
    private void initializeInjector() {
        ActivityComponent component = ActivityComponent.Initializer.init(this);
        component.inject(this);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter!=null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }
}
