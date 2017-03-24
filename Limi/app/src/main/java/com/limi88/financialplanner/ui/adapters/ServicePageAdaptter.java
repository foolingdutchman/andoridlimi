package com.limi88.financialplanner.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limi88.financialplanner.R;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.pojo.home.CommonTool;
import com.limi88.financialplanner.pojo.home.servicetools.ServiceTools;
import com.limi88.financialplanner.ui.MainActivity;
import com.limi88.financialplanner.ui.base.BaseWebViewActivity;
import com.limi88.financialplanner.ui.login.LoginActivity;
import com.limi88.financialplanner.util.Constants;

import java.util.List;

import anet.channel.util.StringUtils;

/**
 * Created by hehao
 * Date on 16/10/20.
 * Email: hao.he@sunanzq.com
 */
public class ServicePageAdaptter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ServiceTools mTools;
    private Context mContext;
    private Intent mIntent;

    public ServicePageAdaptter(ServiceTools tools, Context context) {
        mTools = tools;
        mContext = context;
    }

    public void setTools(ServiceTools tools) {
        mTools = tools;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.service_tools_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            if (mTools != null) {
                String key = mTools.getData().getKeys().get(position);
                List<CommonTool> commonTools = mTools.getData().getToolMap().get(key);
                ((ViewHolder) holder).mTextView.setText(commonTools.get(0).getCat());
                ServiceToolAdapter toolsAdapter = new ServiceToolAdapter(mContext, commonTools);
                ((ViewHolder) holder).mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                ((ViewHolder) holder).mRecyclerView.setAdapter(toolsAdapter);
                toolsAdapter.setOnItemClickListener((v, obj, position1) -> {
                    Bundle bundle = new Bundle();
                    if (commonTools != null && commonTools.size() != 0) {
                        if (position1 < commonTools.size()) {
                            String path = commonTools.get(position1).getUrl().toString();
                            if (commonTools.get(position1).getSign() && !DataCenter.isSigned()) {
                                goLoginWithLink(path);
                            } else {
                                if (!StringUtils.isBlank(path)){
                                    bundle.putInt(Constants.WEB_PAGE_TAG, Constants.PRODUCTS_PAGE_FLAG);
                                    bundle.putString(Constants.WEB_PAGE_DETAIL_NAME, commonTools.get(position1).getName());
                                    bundle.putString(Constants.WEB_PAGE_DETAIL_LINK, Constants.HOST + path);
                                    mIntent = new Intent(mContext, BaseWebViewActivity.class);
                                    mIntent.putExtras(bundle);
                                    mContext.startActivity(mIntent);
                                }
                            }
                        }
                    }
                });
            }
        }

    }
    private void goLoginWithLink(String value) {
        Intent intent=  new Intent(mContext, LoginActivity.class);
        intent.putExtra(Constants.WEB_PAGE_DETAIL_LINK,value);
        ((MainActivity) mContext).startActivityForResult(intent, Constants.HOME_FLAG);
    }
    @Override
    public int getItemCount() {
        if (mTools != null) {
            return mTools.getData().getKeys().size();
        } else return 0;
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView mRecyclerView;
        TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.rv_tools_item);
            mTextView = (TextView) itemView.findViewById(R.id.tv_service_title);
        }
    }
}
