package com.limi88.financialplanner.ui.customers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.hehao.library.base.BaseFragmentActivity;
import com.hehao.library.netstatus.NetUtils;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.di.component.ActivityComponent;

import com.limi88.financialplanner.pojo.costumer.Province;
import com.limi88.financialplanner.ui.mine.MineContract;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.ToastUtils;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ProvincePickActivity extends BaseFragmentActivity implements ClientContract.ProvinceView {


    @Bind(R.id.common_left_action)
    ImageView mCommonLeftAction;
    @Bind(R.id.ll_left_btn)
    LinearLayout mLlLeftBtn;
    @Bind(R.id.tv_header_back)
    TextView mTvHeaderBack;

    @Bind(R.id.common_title)
    TextView mCommonTitle;
    @Bind(R.id.lv_province)
    ListView mLvProvince;
    @Inject
    ClientPresenter mPresenter;

    private ClientContract.AddClientView addClientView;
    private MineContract.MeProfileView mineView;


    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_province_pick;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
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
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }


    private ArrayAdapter<String> mAdapter;
    private String[] provinces = {};
    private List<Province> provinceList;



    public void setAddClientView(ClientContract.AddClientView addClientView) {
        this.addClientView = addClientView;
    }

    public void setMineView(MineContract.MeProfileView mineView) {
        this.mineView = mineView;
    }


    protected void initViewsAndEvents() {
        initializeInjector();
        mLlLeftBtn.setVisibility(View.VISIBLE);
        mCommonTitle.setText(R.string.province_pick);
        mCommonTitle.setVisibility(View.INVISIBLE);
        mTvHeaderBack.setVisibility(View.VISIBLE);
        mTvHeaderBack.setText("地区列表");
        initData();
        initAdapter();
        mLvProvince.setAdapter(mAdapter);
        mLvProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (addClientView != null) {
                    addClientView.setCity(provinceList.get(position));
                } else if (mineView != null) {
                    mineView.setCity(provinceList.get(position));
                }
                ToastUtils.showToastForTest(provinceList.get(position).getName() + "");
                Intent intent=new Intent();
                intent.putExtra(Constants.PROVINCE_FLAG,provinceList.get(position).getName() + "");
                setResult(Constants.PROVINCE_PICK_SUCCESS,intent);
                finish();
            }
        });
    }


    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }




    private void initData() {
        mPresenter.setProvinceView(this);
        mPresenter.getProvinceInfo();
    }

    private void initAdapter() {
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, provinces);
    }

    private void initializeInjector() {
        ActivityComponent component = ActivityComponent.Initializer.init(this);
        component.inject(this);
    }


    @Override
    public void loadProvince(List<Province> provinceList) {
        this.provinceList = provinceList;
        List<String> provinces = new ArrayList<>();
        for (int i = 0; i < provinceList.size(); i++) {
            provinces.add(provinceList.get(i).getName());
        }
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, provinces);
        mLvProvince.setAdapter(mAdapter);
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

}
