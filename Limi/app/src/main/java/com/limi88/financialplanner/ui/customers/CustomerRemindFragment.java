package com.limi88.financialplanner.ui.customers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.hehao.library.base.BaseLazyFragment;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.pojo.clients.Reminds;
import com.limi88.financialplanner.ui.adapters.CustomerRemindAdapter;
import com.limi88.financialplanner.ui.base.BaseWebViewActivity;
import com.limi88.financialplanner.ui.widget.OnItemClickListener;
import com.limi88.financialplanner.util.Constants;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import butterknife.Bind;

/**
 * Created by hehao
 * Date on 16/9/20.
 * Email: hao.he@limi88.com
 */
public class CustomerRemindFragment extends BaseLazyFragment {

    @Bind(R.id.rv_birthday)
    RecyclerView mRecycleView;
    @Bind(R.id.tv_customer_no_remind)
    LinearLayout mHint;
    private Reminds data;
    private CustomerRemindAdapter mAdapter;
    private Bundle bundle;
    private OnItemClickListener onItemClickListener;
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
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        if (mAdapter == null) {
            mAdapter = new CustomerRemindAdapter(getActivity(), data);
            mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            mRecycleView.setAdapter(mAdapter);
        } else {
            mAdapter.setmReminds(data);
            mAdapter.notifyDataSetChanged();
        }
        onItemClickListener = new OnItemClickListener() {
            @Override
            public void onItemClick(View v, Object obj, int position) {
                if (bundle == null) {
                    bundle = new Bundle();
                }

                bundle.putInt(Constants.WEB_PAGE_TAG, Constants.GO_DETAIL_TAG);
                bundle.putString(Constants.WEB_PAGE_DETAIL_NAME, "客户");
                int id=-1;
                if (position<data.getToday().size()) {
                    id=data.getToday().get(position).getId();
                    Constants.CHOOSE_CUSTOMER=data.getToday().get(position);
                }else if (position >= data.getToday().size() && position < data.getToday().size() + data.getTomorrow().size()) {
                    id=data.getTomorrow().get(position-data.getToday().size()).getId();
                    Constants.CHOOSE_CUSTOMER=  data.getTomorrow().get(position - data.getToday().size());

                } else {
                    id=data.getNextWeek().get(position-data.getToday().size()-data.getTomorrow().size()).getId();
                    Constants.CHOOSE_CUSTOMER= data.getNextWeek().get(position-data.getToday().size()-data.getTomorrow().size());
                }
                bundle.putString(Constants.WEB_PAGE_DETAIL_LINK, Constants.CLIENTS_URL
                        + "/" + id);
                Intent mIntent = new Intent(CustomerRemindFragment.this.getActivity(), BaseWebViewActivity.class);
                mIntent.putExtras(bundle);
                getActivity().startActivityForResult(mIntent, Constants.CLIENTS_FLAG);
            }
        };
        mAdapter.setOnItemClickListener(onItemClickListener);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_costomer_remind;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }


    public void setData(Reminds data) {
        this.data = data;
        if (data!=null&&(data.getToday().size()+data.getTomorrow().size()+data.getNextWeek().size())!=0) {
            mHint.setVisibility(View.GONE);
        }else mHint.setVisibility(View.VISIBLE);

        if (mAdapter == null) {
            mAdapter = new CustomerRemindAdapter(getActivity(), data);
            mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            mRecycleView.setAdapter(mAdapter);
        } else {
            mAdapter.setmReminds(data);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MainApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
