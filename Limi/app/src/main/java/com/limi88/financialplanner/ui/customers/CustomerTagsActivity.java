package com.limi88.financialplanner.ui.customers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hehao.library.base.BaseFragmentActivity;
import com.hehao.library.netstatus.NetUtils;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.di.component.ActivityComponent;
import com.limi88.financialplanner.pojo.clients.SearchConditions;
import com.limi88.financialplanner.ui.widget.MyFloatLayoutView;
import com.limi88.financialplanner.ui.widget.TagInputLayout;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.JsonUtils;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import javax.inject.Inject;

import butterknife.Bind;


public class CustomerTagsActivity extends BaseFragmentActivity implements View.OnClickListener, ClientContract.TagsView {


    @Bind(R.id.ll_left_btn)
    LinearLayout mLlLeftBtn;
    @Bind(R.id.common_title)
    TextView mCommonTitle;
    @Bind(R.id.tv_header_back)
    TextView mLefttitle;
    @Bind(R.id.tag_input_layout)
    TagInputLayout mTagInputLayout;
    @Bind(R.id.myfloatlayout)
    MyFloatLayoutView mMyfloatlayout;
    @Bind(R.id.btn_tags_confirm)
    Button mBtnTagsConfirm;

    private SearchConditions searchConditions;
    private Intent mIntent;
    private String tags;

    @Inject
    ClientPresenter mPresenter;
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_customer_tags;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {

        initializeInjector();
        mPresenter.setTagsView(this);
        mIntent = getIntent();
        Bundle bundle = mIntent.getExtras();
        if (mPresenter.readDataFromCache()!=null&&mPresenter.readDataFromCache().getData()!=null) {
           searchConditions=mPresenter.readDataFromCache().getData().getSearchConditions();
        }else mPresenter.getTagsInfo();
        tags = bundle.getString("tags");
        mLlLeftBtn.setVisibility(View.VISIBLE);
        if (tags != null && !"暂未设置".equals(tags)) {
            String[] tagArray = tags.split(",");
            mTagInputLayout.setTagArray(tagArray);
        }


        mBtnTagsConfirm.setOnClickListener(this);
        mCommonTitle.setVisibility(View.GONE);
        mLefttitle.setVisibility(View.VISIBLE);
        mLefttitle.setText(R.string.customers_add_tag);
        initData();
        mMyfloatlayout.setOnItemClickListener((v, obj, position) -> {
            CharSequence[] array = mTagInputLayout.getTagArray();
            for (int i = 0; i < array.length; i++) {
                if (array[i].equals((String) obj)) {
                    mTagInputLayout.removeViewAt(i);
                    return;
                }
            }
            mTagInputLayout.generateTag((String) obj);
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




    private void initData() {
        if (searchConditions != null) {
            mMyfloatlayout.setmNames(searchConditions.getCustomerTags());
        }


    }


    private void initializeInjector() {
        ActivityComponent component = ActivityComponent.Initializer.init(this);
        component.inject(this);
    }



    @Override
    public void onClick(View view) {
        tags = "";
        if (view.getId() == R.id.btn_tags_confirm) {
            for (int i = 0; i < mTagInputLayout.getTagArray().length; i++) {
                tags = tags + mTagInputLayout.getTagArray()[i] + ",";
            }
            Intent intent = new Intent();
            intent.putExtra(Constants.TAGS_FLAG, tags);
            setResult(Constants.TAG_PICK_SUCCESS, intent);
            finish();

        }
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MainApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }


    public void backPre(View view) {
        this.finish();
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

    @Override
    public void loadTags(SearchConditions mSearchConditions) {
        this.searchConditions=mSearchConditions;
        initData();
    }
}


