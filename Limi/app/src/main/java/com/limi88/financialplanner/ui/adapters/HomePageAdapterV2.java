package com.limi88.financialplanner.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.pojo.home.Banner;
import com.limi88.financialplanner.pojo.home.CommonTool;
import com.limi88.financialplanner.pojo.home.HomeData;
import com.limi88.financialplanner.ui.MainActivity;
import com.limi88.financialplanner.ui.base.BaseWebViewActivity;
import com.limi88.financialplanner.ui.home.ServiceToolsFragment;
import com.limi88.financialplanner.ui.login.LoginActivity;
import com.limi88.financialplanner.ui.widget.ImageCycleView;
import com.limi88.financialplanner.ui.widget.MarqueeView;
import com.limi88.financialplanner.util.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import anet.channel.util.StringUtils;
import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by hehao on 16/8/16.
 */
public class HomePageAdapterV2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private GridLayoutManager mSectionLayoutMannager;
    private int VIEW_TYPE1 = 1;
    private int VIEW_TYPE2 = 2;
    private int VIEW_TYPE3 = 3;
    private int VIEW_TYPE_END = 4;


    private Context context;
    //    private List<HomeTestBean> mDataList;
    private CharSequence[] mCalculatorTitles;
    private int[] mCalculatorIcons = {
            R.mipmap.ic_home_retired_plan,
            R.mipmap.ic_home_eduction_cal,
            R.mipmap.ic_home_customer_cal,
            R.mipmap.ic_home_reward_cal,
            R.mipmap.ic_home_mycard,

    };
    private List<CommonTool> mToolsData;
    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;
    private HomeData mHomeData;
    private Intent mIntent;
    private String lastType = "";
    private List<String> headLines;
    private List<String> headLineLinks;

    @Singleton
    private Bundle bundle;


    public void setmDataList(HomeData mDataList) {
        this.mHomeData = mDataList;
    }


    public HomePageAdapterV2(Context context, HomeData mHomeData) {

        this.context = context;
        this.mHomeData = mHomeData;
//        mCalculatorTitles = context.getResources().getTextArray(R.array.home_calculators_array);
        if (mHomeData != null) {
            headLines = new ArrayList<>();
            headLineLinks = new ArrayList<>();
            mToolsData = mHomeData.getData().getCommonTools();
        }

    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == VIEW_TYPE1) {
            holder = new ViewHolder1(LayoutInflater.from(context).inflate(R.layout.homelist_item1, parent, false));
        } else if (viewType == VIEW_TYPE2) {
            holder = new ViewHolder2(LayoutInflater.from(context).inflate(R.layout.home_item_hotline, parent, false));
        } else if (viewType == VIEW_TYPE3) {
            holder = new ViewHolder3(LayoutInflater.from(context).inflate(R.layout.home_item_tools, parent, false));
        } else if (viewType == VIEW_TYPE_END) {
            holder = new ViewHolder6(LayoutInflater.from(context).inflate(R.layout.common_footer, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i("holder", getItemViewType(position) + "------position");
        if (holder instanceof ViewHolder1) {

            if (mHomeData != null) {

                List<Banner> mBannerList = mHomeData.getData().getBanners();
                if (mBannerList.size() != 0) {
                    ((ViewHolder1) holder).mImageCycleView.setCycleDelayed(2000);
                    ((ViewHolder1) holder).mImageCycleView.setIndicationStyle(ImageCycleView.IndicationStyle.COLOR, Color.GRAY, Color.WHITE, 0.8f);

                    List imageInfoList = new ArrayList();
                    for (int i = 0; i < mBannerList.size(); i++) {
                        imageInfoList.add(new ImageCycleView.ImageInfo(mBannerList.get(i).getImageUrl(), mBannerList.get(i).isSign() + "", mBannerList.get(i).getTargetLink()));
                    }
                    ((ViewHolder1) holder).mImageCycleView.loadData(imageInfoList, imageInfo -> {
                        ImageView imageView = new ImageView(context);
                        Glide.with(context).load(imageInfo.image.toString())
                                .placeholder(R.mipmap.bg_default_banner)
                                .crossFade()
                                .into(imageView);
                        return imageView;
                    });
                    ((ViewHolder1) holder).mImageCycleView.setOnPageClickListener((imageView, imageInfo) -> {
                        if (imageInfo.text.equals("true") && !DataCenter.isSigned()) {
                            goLoginWithLink(imageInfo.value);

                        } else {
                            toWebView(imageInfo.value, Constants.WEB_CALCULATOR_TAG_LINK, Constants.WEB_CALCULATOR_TAG);
                        }
                    });
                }
            }

        } else if (holder instanceof ViewHolder2) {
            if (mHomeData != null) {
                // 设置滚动头条

                if (mHomeData.getData().getLimiHeadlines().size() != 0) {
                    for (int i = 0; i < mHomeData.getData().getLimiHeadlines().size(); i++) {
                        String tilte = mHomeData.getData().getLimiHeadlines().get(i).getTitle();
                        String content = mHomeData.getData().getLimiHeadlines().get(i).getContent();
                        content = content.split("</p>")[0].substring(3);
                        String headline = "[" + tilte + "] " + content;
                        headLines.add(headline);
                        headLineLinks.add(mHomeData.getData().getLimiHeadlines().get(i).getWebLink());
                    }
                    ((ViewHolder2) holder).mSectionTitle.startWithList(headLines);
                    ((ViewHolder2) holder).mSectionTitle.setOnItemClickListener((position1, textView) -> {
                        toWebView(headLineLinks.get(position1), Constants.GO_DETAIL_TAG);
                    });
                }
            }


        } else if (holder instanceof ViewHolder3) {
            ((ViewHolder3) holder).mSectionRecyclerView.setLayoutManager(new GridLayoutManager(context, 4));
            mToolsData = mHomeData.getData().getCommonTools();
            ToolsAdapter itemAdapter = new ToolsAdapter(context, mToolsData);
            ((ViewHolder3) holder).mSectionRecyclerView.setAdapter(itemAdapter);
            itemAdapter.setOnItemClickListener((v, obj, itemPosition) -> {
                if (mToolsData != null && mToolsData.size() != 0) {
                    if (itemPosition < mToolsData.size()) {
                        String path = mHomeData.getData().getCommonTools().get(itemPosition).getUrl().toString();

                        if (mToolsData.get(itemPosition).getSign() && !DataCenter.isSigned()) {
                           goLoginWithLink(path);
                        } else {
                            toWebView(path, Constants.PRODUCTS_PAGE_FLAG);
                        }
                    } else {
                        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new ServiceToolsFragment()).commit();
                    }
                }
            });
        }
    }

    private void goLoginWithLink(String value) {
        Intent intent=  new Intent(context, LoginActivity.class);
        intent.putExtra(Constants.WEB_PAGE_DETAIL_LINK,value);
        ((MainActivity) context).startActivityForResult(intent, Constants.HOME_FLAG);
    }


    private void toWebView(String path, String linkKey, int webPageTagValue) {
        if (!StringUtils.isBlank(path)) {
            Bundle bu = new Bundle();
            String url = path.contains("http") ? path : Constants.HOST + path;
//            String url = path;
            bu.putInt(Constants.WEB_PAGE_TAG, webPageTagValue);
            bu.putString(linkKey, url);
            mIntent = new Intent(context, BaseWebViewActivity.class);
            mIntent.putExtras(bu);
            context.startActivity(mIntent);
        }
    }

    private void toWebView(String path, int webPageTagValue) {
        toWebView(path, Constants.WEB_PAGE_DETAIL_LINK, webPageTagValue);
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return VIEW_TYPE1;
        } else if (position == 1) {
            return VIEW_TYPE2;
        } else if (position < getItemCount() - 1) {

            return VIEW_TYPE3;
        }
        return VIEW_TYPE_END;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mHomeData != null) {
            count = 4;
        }
        return count;
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        @Bind(R.id.ic_home_banner)
        ImageCycleView mImageCycleView;


        public ViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {

        MarqueeView mSectionTitle;


        public ViewHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mSectionTitle = (MarqueeView) itemView.findViewById(R.id.mv_hint);
        }
    }

    class ViewHolder3 extends RecyclerView.ViewHolder {

        //        @Bind(R.id.tv_item_title)
        RecyclerView mSectionRecyclerView;


        public ViewHolder3(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mSectionRecyclerView = (RecyclerView) itemView.findViewById(R.id.rv_home_tools);
        }
    }


    class ViewHolder6 extends RecyclerView.ViewHolder {

        //        @Bind(R.id.tv_item_title)
        public ViewHolder6(View itemView) {
            super(itemView);
        }
    }

}
