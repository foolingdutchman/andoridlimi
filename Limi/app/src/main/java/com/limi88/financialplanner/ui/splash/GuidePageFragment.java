package com.limi88.financialplanner.ui.splash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hehao.library.base.BaseLazyFragment;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.ui.MainActivity;
import com.limi88.financialplanner.ui.login.LoginActivity;
import com.limi88.financialplanner.util.Constants;

import butterknife.Bind;


/**
 * Created by hehao
 * Date on 16/10/17.
 * Email: hao.he@sunanzq.com
 */
public class GuidePageFragment extends BaseLazyFragment implements View.OnClickListener {

    @Bind(R.id.btn_open_app)
    ImageView mBtnOpenApp;
    @Bind(R.id.rl_guide_bg)
    RelativeLayout mLayout;
    @Bind(R.id.iv_guide_page)
    ImageView mIvGuidePage;
    @Bind(R.id.tv_title)
    TextView mTitleText;
    @Bind(R.id.tv_hint)
    TextView mHintText;
    private int pageNum = 0;
    private int totalPage = 1;
    private int imgRes;
    private String[] mTitleArray;
    private String[]  mHintArray;
    private int[] bgRes={
            R.mipmap.bg_guide_bg1,
            R.mipmap.bg_guide_bg2,
            R.mipmap.bg_guide_bg3,
            R.mipmap.bg_guide_bg4,
    };

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
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
        mTitleArray=getResources().getStringArray(R.array.guide_title_array);
        mHintArray=getResources().getStringArray(R.array.guide_hint_array);

        if (pageNum < totalPage - 1) {

            mBtnOpenApp.setVisibility(View.INVISIBLE);
        } else {
            mBtnOpenApp.setVisibility(View.VISIBLE);
        }
        mBtnOpenApp.setOnClickListener(this);
        mIvGuidePage.setImageResource(imgRes);
        mLayout.setBackground(getResources().getDrawable(bgRes[pageNum]));

        mTitleText.setText(mTitleArray[pageNum]);
        mHintText.setText(mHintArray[pageNum]);
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.guide_page_layout;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_open_app) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            initAPP();
            getActivity().finish();
        }
    }

    private  void initAPP(){
        getActivity().getSharedPreferences(Constants.INIT_FLAG,getActivity().MODE_PRIVATE).edit().putBoolean(Constants.APP_INIT,true).commit();
    }
}
