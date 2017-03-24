package com.limi88.financialplanner.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hehao.library.base.BaseLazyFragment;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.di.component.FragmentComponent;
import com.limi88.financialplanner.pojo.home.servicetools.ServiceTools;
import com.limi88.financialplanner.ui.adapters.ServicePageAdaptter;
import com.limi88.financialplanner.ui.base.FragmentBackHandler;

import javax.inject.Inject;

/**
 * Created by hehao
 * Date on 16/10/20.
 * Email: hao.he@sunanzq.com
 */
public class ServiceToolsFragment extends BaseLazyFragment implements HomeContract.ServiceToolView, FragmentBackHandler {

    private  RecyclerView mRvHomeServiceTools;
    private ServicePageAdaptter mPageAdaptter;
    private ServiceTools mServiceTools;
    private TextView mTextView;
    private LinearLayout leftView;
    @Inject
    HomePresenter mPresenter;

    /**
     * when fragment is visible for the first time, here we can do some initialized work or refresh data only once
     */
    @Override
    protected void onFirstUserVisible() {

    }

    /**
     * this method like the fragment's lifecycle method onResume()
     */
    @Override
    protected void onUserVisible() {

    }

    /**
     * this method like the fragment's lifecycle method onPause()
     */
    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeInjector();

        mTextView= (TextView) view.findViewById(R.id.common_title);

        leftView= (LinearLayout) view.findViewById(R.id.ll_left_btn);
        leftView.setVisibility(View.VISIBLE);
        leftView.setOnClickListener(v -> this.getSupportFragmentManager().beginTransaction().remove(this).commit());
        mTextView.setText("全部工具");
        mRvHomeServiceTools = (RecyclerView) view.findViewById(R.id.rv_home_service_tools);
        mPageAdaptter = new ServicePageAdaptter(mServiceTools, getActivity());
        mRvHomeServiceTools.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRvHomeServiceTools.setAdapter(mPageAdaptter);
        mPresenter.setServiceToolView(this);
        mPresenter.loadServiceData();
    }

    /**
     * get loading target view
     */
    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    /**
     * init all views and add events
     */
    @Override
    protected void initViewsAndEvents() {

    }

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_service_tools;
    }

    /**
     * is bind eventBus
     *
     * @return
     */
    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    private void initializeInjector() {

        FragmentComponent component = FragmentComponent.Initializer.init(this);
        component.inject(this);

    }

    @Override
    public void showLoadingHomeData() {

    }

    @Override
    public void hideLoadingHomeData() {

    }

    @Override
    public void loadDataToUI(ServiceTools mServiceTools) {
        Log.i("homeApi", mServiceTools.getData().getKeys().get(0));
        mPageAdaptter.setTools(mServiceTools);
        mPageAdaptter.notifyDataSetChanged();
    }


    @Override
    public boolean onBackPressed() {
        getSupportFragmentManager().beginTransaction().remove(this).commit();
        return true;
    }
}
