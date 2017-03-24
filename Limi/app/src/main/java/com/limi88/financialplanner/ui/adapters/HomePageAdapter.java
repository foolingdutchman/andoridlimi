//package com.limi88.financialplanner.ui.adapters;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Color;
//import android.os.Bundle;
//import android.support.v7.widget.GridLayoutManager;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.text.SpannableString;
//import android.text.Spanned;
//import android.text.style.ForegroundColorSpan;
//import android.text.style.RelativeSizeSpan;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;
//import com.bumptech.glide.Glide;
//import com.limi88.financialplanner.api.DataCenter;
//import com.limi88.financialplanner.pojo.clients.Data;
//import com.limi88.financialplanner.pojo.home.HomeData;
//import com.limi88.financialplanner.pojo.home.Product;
//import com.limi88.financialplanner.ui.login.LoginActivity;
//import com.limi88.financialplanner.util.Constants;
//import com.limi88.financialplanner.R;
//import com.limi88.financialplanner.pojo.home.Banner;
//import com.limi88.financialplanner.ui.base.BaseWebViewActivity;
//import com.limi88.financialplanner.ui.widget.ImageCycleView;
//import com.limi88.financialplanner.ui.widget.MarqueeView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.inject.Singleton;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//
//
///**
// * Created by hehao on 16/8/16.
// */
//public class HomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private GridLayoutManager mSectionLayoutMannager;
//    private int VIEW_TYPE1 = 1;
//    private int VIEW_TYPE2 = 2;
//    private int VIEW_TYPE3 = 3;
//    private int VIEW_TYPE4 = 4;
//    private int VIEW_TYPE5 = 5;
//    private int VIEW_TYPE6 = 6;
//    private int VIEW_TYPE_END = 7;
//
//
//    private Context context;
//    //    private List<HomeTestBean> mDataList;
//    private CharSequence[] mCalculatorTitles;
//    private int[] mCalculatorIcons = {
//            R.mipmap.ic_home_retired_plan,
//            R.mipmap.ic_home_eduction_cal,
//            R.mipmap.ic_home_customer_cal,
//            R.mipmap.ic_home_reward_cal,
//            R.mipmap.ic_home_mycard,
//
//    };
//
//    private View.OnClickListener onClickListener;
//    private View.OnLongClickListener onLongClickListener;
//    private HomeData mHomeData;
//    private Intent mIntent;
//    private String lastType = "";
//    private List<String> headLines;
//    private List<String> headLineLinks;
//
//    @Singleton
//    private Bundle bundle;
//
//    public void setmDataList(HomeData mDataList) {
//        this.mHomeData = mHomeData;
//    }
//
//    public HomePageAdapter(Context context, HomeData mHomeData) {
//        this.context = context;
//        this.mHomeData = mHomeData;
//        mCalculatorTitles = context.getResources().getTextArray(R.array.home_calculators_array);
//        headLines = new ArrayList<>();
//        headLineLinks = new ArrayList<>();
//    }
//
//    public void setOnClickListener(View.OnClickListener onClickListener) {
//        this.onClickListener = onClickListener;
//    }
//
//    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
//        this.onLongClickListener = onLongClickListener;
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        RecyclerView.ViewHolder holder = null;
//        if (viewType == VIEW_TYPE1) {
//            holder = new ViewHolder1(LayoutInflater.from(context).inflate(R.layout.homelist_item1, parent, false));
//
//        } else if (viewType == VIEW_TYPE2) {
//            holder = new ViewHolder2(LayoutInflater.from(context).inflate(R.layout.homelist_item2, parent, false));
//        } else if (viewType == VIEW_TYPE3) {
//            holder = new ViewHolder3(LayoutInflater.from(context).inflate(R.layout.homelist_hot_title, parent, false));
//        } else if (viewType == VIEW_TYPE4) {
//            holder = new ViewHolder4(LayoutInflater.from(context).inflate(R.layout.homelist_item_trust, parent, false));
//        } else if (viewType == VIEW_TYPE5) {
//            holder = new ViewHolder5(LayoutInflater.from(context).inflate(R.layout.homelist_item_issurence, parent, false));
//        } else if (viewType == VIEW_TYPE_END) {
//            holder = new ViewHolder6(LayoutInflater.from(context).inflate(R.layout.common_footer, parent, false));
//        } else if (viewType == VIEW_TYPE6) {
//            holder = new FundViewHolder(LayoutInflater.from(context).inflate(R.layout.homelist_item_fund, parent, false));
//        }
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        Log.i("holder", getItemViewType(position) + "------position");
//        if (holder instanceof ViewHolder1) {
//
//            List<Banner> mBannerList = new ArrayList<Banner>();
//            mBannerList = mHomeData.getData().getBanners();
//            if (mBannerList.size() != 0) {
//                ((ViewHolder1) holder).mImageCycleView.setCycleDelayed(2000);
//                ((ViewHolder1) holder).mImageCycleView.setIndicationStyle(ImageCycleView.IndicationStyle.COLOR, Color.GRAY, Color.WHITE, 0.8f);
//
//                List imageInfoList = new ArrayList();
//                for (int i = 0; i < mBannerList.size(); i++) {
//                    imageInfoList.add(new ImageCycleView.ImageInfo(mBannerList.get(i).getImageUrl(), "", mBannerList.get(i).getTargetLink()));
//                }
//                ((ViewHolder1) holder).mImageCycleView.loadData(imageInfoList, new ImageCycleView.LoadImageCallBack() {
//                    @Override
//                    public ImageView loadAndDisplay(ImageCycleView.ImageInfo imageInfo) {
//                        ImageView imageView = new ImageView(context);
//                        Glide.with(context).load(imageInfo.image.toString())
//                                .placeholder(R.mipmap.ic_launcher)
//                                .crossFade()
//                                .into(imageView);
//                        return imageView;
//                    }
//                });
//                ((ViewHolder1) holder).mImageCycleView.setOnPageClickListener(new ImageCycleView.OnPageClickListener() {
//                    @Override
//                    public void onClick(View imageView, ImageCycleView.ImageInfo imageInfo) {
//                        bundle = new Bundle();
//                        bundle.putInt(Constants.WEB_PAGE_TAG, Constants.WEB_CALCULATOR_TAG);
//                        bundle.putString(Constants.WEB_CALCULATOR_TAG_LINK, imageInfo.value);
//                        mIntent = new Intent(context, BaseWebViewActivity.class);
//                        mIntent.putExtras(bundle);
//                        context.startActivity(mIntent);
//                    }
//                });
//
//            }
//
//
//        } else if (holder instanceof ViewHolder2) {
//            if (mHomeData != null) {
//                // 设置滚动头条
//
//                if (mHomeData.getData().getLimiHeadlines().size() != 0) {
//                    for (int i = 0; i < mHomeData.getData().getLimiHeadlines().size(); i++) {
//                        String tilte = mHomeData.getData().getLimiHeadlines().get(i).getTitle();
//                        String content = mHomeData.getData().getLimiHeadlines().get(i).getContent();
//                        content = content.split("</p>")[0].substring(3);
//                        String headline = "[" + tilte + "] " + content;
//                        headLines.add(headline);
//                        headLineLinks.add(mHomeData.getData().getLimiHeadlines().get(i).getWebLink());
//
//                    }
//
//                    ((ViewHolder2) holder).mSectionTitle.startWithList(headLines);
//                    ((ViewHolder2) holder).mSectionTitle.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(int position, TextView textView) {
//                            Bundle bundle = new Bundle();
//                            bundle.putInt(Constants.WEB_PAGE_TAG, Constants.GO_DETAIL_TAG);
//                            bundle.putString(Constants.WEB_PAGE_DETAIL_NAME, mHomeData.getData().getLimiHeadlines().get(position).getTitle());
//                            bundle.putString(Constants.WEB_PAGE_DETAIL_LINK, headLineLinks.get(position));
//                            mIntent = new Intent(context, BaseWebViewActivity.class);
//                            mIntent.putExtras(bundle);
//                            context.startActivity(mIntent);
//                        }
//                    });
//
//                }
//            }
//
//            ((ViewHolder2) holder).mSectionRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
//
//            SectionItemAdapter itemAdapter = new SectionItemAdapter(context);
//            ((ViewHolder2) holder).mSectionRecyclerView.setAdapter(itemAdapter);
//            itemAdapter.setOnClickListener(v -> {
//                Bundle bundle = (Bundle) v.getTag();
//                int itmePosition = bundle.getInt(Constants.WEB_CALCULATOR_TAG_ID);
//                switch (itmePosition) {
//                    case 0:
//                        bundle.putString(Constants.WEB_CALCULATOR_TAG_LINK, Constants.CALCULATOR_RETERMENT_URL);
//                        break;
//                    case 1:
//                        bundle.putString(Constants.WEB_CALCULATOR_TAG_LINK, Constants.CALCULATOR_EDUCATION_URL);
//                        break;
//                    case 2:
//                        bundle.putString(Constants.WEB_CALCULATOR_TAG_LINK, Constants.INVEST_RISK_TEST_URL);
//                        break;
//                    case 3:
//                        bundle.putString(Constants.WEB_CALCULATOR_TAG_LINK, Constants.CALCULATOR_IRR_URL);
//                        break;
//                    case 4:
//                        bundle.putString(Constants.WEB_CALCULATOR_TAG_LINK, Constants.USER_PROFILE_URL);
//                        break;
//                }
//                if (!DataCenter.isSigned() && Constants.NEED_LOGIN_URLS.contains(bundle.getString(Constants.WEB_CALCULATOR_TAG_LINK))) {
//                    mIntent = new Intent(context, LoginActivity.class);
//                } else {
//                    bundle.putInt(Constants.WEB_PAGE_TAG, Constants.WEB_CALCULATOR_TAG);
//                    mIntent = new Intent(context, BaseWebViewActivity.class);
//                }
//                mIntent.putExtras(bundle);
//                context.startActivity(mIntent);
//            });
//        } else if (holder instanceof ViewHolder4) {
//            if (mHomeData != null) {
//                Product product = mHomeData.getData().getProducts().get(position - 2);
//
//
//                if (!product.getType().equals(lastType) && product.getType().equals(context.getResources().getString(R.string.product_type_trust))) {
//                    ((ViewHolder4) holder).mCatView.setVisibility(View.VISIBLE);
//                    ((ViewHolder4) holder).mCatView.setText(context.getResources().getString(R.string.product_typename_trust));
//                } else if (!product.getType().equals(lastType) && product.getType().equals(context.getResources().getString(R.string.product_type_assetmanagment))) {
//                    ((ViewHolder4) holder).mCatView.setVisibility(View.VISIBLE);
//                    ((ViewHolder4) holder).mCatView.setText(context.getResources().getString(R.string.product_typename_assetmanagment));
//                } else if (!product.getType().equals(lastType) && product.getType().equals(context.getResources().getString(R.string.product_type_bond))) {
//                    ((ViewHolder4) holder).mCatView.setVisibility(View.VISIBLE);
//                    ((ViewHolder4) holder).mCatView.setText(context.getResources().getString(R.string.product_typename_bond));
//                }
//                lastType = product.getType();
//                if (product.getShortName() != null) {
//                    ((ViewHolder4) holder).mTilteView.setText(product.getShortName());
//                } else ((ViewHolder4) holder).mTilteView.setText(product.getName());
//
//                if (context.getString(R.string.non_level).equals(product.getLevel())) {
//                    ((ViewHolder4) holder).mGradeView.setVisibility(View.INVISIBLE);
//
//                } else ((ViewHolder4) holder).mGradeView.setText(product.getLevel());
//
//                if (product.getProductTypeName().equals(context.getString(R.string.tag_total_package))) {
//                    ((ViewHolder4) holder).mTag1.setVisibility(View.VISIBLE);
//                    ((ViewHolder4) holder).mTag1.setText(context.getString(R.string.tag_total_package));
//                }
//                if (product.getStatus().equals(context.getString(R.string.status_hot))) {
//                    ((ViewHolder4) holder).mTag2.setVisibility(View.VISIBLE);
//                    ((ViewHolder4) holder).mTag2.setText(context.getString(R.string.status_hot));
//                }
//
//
//                if (product.getRatesDesc().size() != 0) {
//                    if (product.getRatesDesc().size() == 1) {
//                        ((ViewHolder4) holder).mexpectRewardMin.setText(product.getRatesDesc().get(0).getExpectRate());
//                        ((ViewHolder4) holder).mDash.setVisibility(View.INVISIBLE);
//                        ((ViewHolder4) holder).mexpectRewardMax.setVisibility(View.INVISIBLE);
//                        ((ViewHolder4) holder).mexpectRewardMaxPerc.setVisibility(View.INVISIBLE);
//
//                    } else {
//                        Double minExpectRate = Double.valueOf(product.getRatesDesc().get(0).getExpectRate());
//                        Double minRebate = Double.valueOf(product.getRatesDesc().get(0).getExpectRate());
//                        Double maxExpectRate = Double.valueOf(product.getRatesDesc().get(0).getExpectRate());
//                        Double maxRebate = Double.valueOf(product.getRatesDesc().get(0).getExpectRate());
//
//                        for (int i = 0; i < product.getRatesDesc().size(); i++) {
//                            Double expectRate = Double.valueOf(product.getRatesDesc().get(i).getExpectRate());
//                            String rebateString = product.getRatesDesc().get(i).getRebatePercent();
////                            Double Rabate = Double.valueOf(rebateString.substring(0, rebateString.length()-1));
//
//                            if (expectRate > maxExpectRate) maxExpectRate = expectRate;
//                            if (expectRate < minExpectRate) minExpectRate = expectRate;
//                        }
//                        if (maxExpectRate - minExpectRate <= 0.001) {
//                            ((ViewHolder4) holder).mexpectRewardMin.setText(minExpectRate + "");
//                            ((ViewHolder4) holder).mDash.setVisibility(View.INVISIBLE);
//                            ((ViewHolder4) holder).mexpectRewardMax.setVisibility(View.INVISIBLE);
//                            ((ViewHolder4) holder).mexpectRewardMaxPerc.setVisibility(View.INVISIBLE);
//                        } else {
//                            ((ViewHolder4) holder).mexpectRewardMin.setText(minExpectRate + "");
//                            ((ViewHolder4) holder).mexpectRewardMax.setText(maxExpectRate + "");
//                        }
//
//                    }
//                    ((ViewHolder4) holder).mMinInvest.setText(context.getString(R.string.min_start_money) + product.getMinStartMoneyDesc());
//                    ((ViewHolder4) holder).mPreiod.setText(context.getString(R.string.period_desc) + product.getPeriodDesc());
//
//                    //设置佣金比例
//
//                    String rebate = product.getRatesDesc().get(0).getRebatePercent();
//
//                    if (product.getRatesDesc().size() != 0
//                            && !context.getString(R.string.common_hint_load).equals(rebate)
//                            && !context.getString(R.string.common_hint_auth).equals(rebate)) {
//                        Double max_rebate = Double.valueOf(rebate);
//                        Double min_rebate = Double.valueOf(rebate);
//                        for (int i = 0; i < product.getRatesDesc().size(); i++) {
//                            String currentBack = product.getRatesDesc().get(i).getRebatePercent();
//                            if (product.getRatesDesc().get(i).getRebatePercent().contains("%")) {
//                                currentBack = currentBack.substring(0, currentBack.length() - 2);
//                                rebate = rebate.substring(0, rebate.length() - 2);
//                            }
//                            Double dcurrent = Double.valueOf(currentBack);
//
//                            if (dcurrent > max_rebate) {
//                                max_rebate = dcurrent;
//                            }
//                            if (dcurrent < min_rebate) {
//                                min_rebate = dcurrent;
//                            }
//
//                        }
//                        if (max_rebate - min_rebate >= 0.001) {
//                            rebate = min_rebate + "% -" + max_rebate + "%";
//                        } else rebate = min_rebate + "%";
//
//                    }
//
//                    SpannableString reward = new SpannableString(context.getString(R.string.product_reward_perc) + rebate);
//                    reward.setSpan(new RelativeSizeSpan(1.2f), 4, reward.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                    reward.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_blue100)), 4, reward.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//
//                    ((ViewHolder4) holder).mCharge.setText(reward);
//                    //添加响应事件
//                    bundle = new Bundle();
//                    if (rebate.equals(context.getString(R.string.common_hint_load))) {
//                        bundle.putInt(Constants.WEB_PAGE_TAG, Constants.NO_LOGIN_TAG);
//                    }
//                    if (rebate.equals(context.getString(R.string.common_hint_auth))) {
//                        bundle.putInt(Constants.WEB_PAGE_TAG, Constants.NO_AUTHENTICATION_TAG);
//                    }
//
//                    ((ViewHolder4) holder).mCharge.setTag(bundle);
//                    ((ViewHolder4) holder).mCharge.setOnClickListener(onClickListener);
//                    Log.i("holder", "getProgress:---------" + product.getProgress());
//                    if (product.getProgress() != null) {
//                        ((ViewHolder4) holder).mProgressBar.setProgress(Float.valueOf(product.getProgress()));
//                        ((ViewHolder4) holder).mProgressBar.setProgressText("已募集" + product.getProgress() + "%");
//                    }
//                    Bundle bundle = new Bundle();
//                    bundle.putInt(Constants.WEB_PAGE_TAG, Constants.GO_DETAIL_TAG);
//                    bundle.putString(Constants.WEB_PAGE_DETAIL_NAME, product.getName());
//                    bundle.putString(Constants.WEB_PAGE_DETAIL_LINK, product.getWebLink());
//
//                    holder.itemView.setTag(bundle);
//
//                    holder.itemView.setOnClickListener(onClickListener);
//                    holder.itemView.setOnLongClickListener(onLongClickListener);
//                }
//            }
//
//        } else if (holder instanceof ViewHolder5) {
//            if (mHomeData != null) {
//                Product product = mHomeData.getData().getProducts().get(position - 2);
//                if (!lastType.equals(product.getType())) {
//                    ((ViewHolder5) holder).mCatView.setVisibility(View.VISIBLE);
//                    ((ViewHolder5) holder).mCatView.setText(R.string.product_typename_issurance);
//                }
//                lastType = product.getType();
//                ((ViewHolder5) holder).mSecurityTitle.setText(product.getName());
//                ((ViewHolder5) holder).mMinAge.setText("承保年龄: " + product.getAgeSprd());
//                ((ViewHolder5) holder).mSecurityPeriod.setText("保险期间: " + product.getInsurSprd());
//                //设置佣金比例
//
//                String rebate = product.getRatesDesc().get(0).getRebatePercent();
//
//                if (product.getRatesDesc().size() != 0
//                        && !context.getString(R.string.common_hint_load).equals(rebate)
//                        && !context.getString(R.string.common_hint_auth).equals(rebate)) {
//                    Double max_rebate = Double.valueOf(rebate);
//                    Double min_rebate = Double.valueOf(rebate);
//                    for (int i = 0; i < product.getRatesDesc().size(); i++) {
//                        String currentBack = product.getRatesDesc().get(i).getRebatePercent();
//                        if (product.getRatesDesc().get(i).getRebatePercent().contains("%")) {
//                            currentBack = currentBack.substring(0, currentBack.length() - 2);
//                            rebate = rebate.substring(0, rebate.length() - 2);
//                        }
//                        Double dcurrent = Double.valueOf(currentBack);
//
//                        if (dcurrent > max_rebate) {
//                            max_rebate = dcurrent;
//                        }
//                        if (dcurrent < min_rebate) {
//                            min_rebate = dcurrent;
//                        }
//                    }
//                    if (max_rebate - min_rebate >= 0.001) {
//                        rebate = min_rebate + "% -" + max_rebate + "%";
//                    } else rebate = min_rebate + "%";
//                }
//
//                SpannableString maxrebate = new SpannableString(context.getString(R.string.product_issurance_max_rebate) + rebate);
//                maxrebate.setSpan(new RelativeSizeSpan(1.2f), 6, maxrebate.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                maxrebate.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_blue100)), 6, maxrebate.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//
//                ((ViewHolder5) holder).mSecurityCharge.setText(maxrebate);
//                Log.i("home", "Avatar:" + product.getOrgAvatarUrl());
//                Glide.with(context).load(product.getOrgAvatarUrl())
//                        .placeholder(R.mipmap.ic_launcher)
//                        .crossFade()
//                        .into(((ViewHolder5) holder).mSecurityIcon);
//                IssuranceIntroAdapter issuranceAdapter = new IssuranceIntroAdapter(product.getIntro(), context);
//                ((ViewHolder5) holder).mRecycleView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//                ((ViewHolder5) holder).mRecycleView.setAdapter(issuranceAdapter);
//
//                Bundle bundle = new Bundle();
//                bundle.putInt(Constants.WEB_PAGE_TAG, Constants.GO_DETAIL_TAG);
//                bundle.putString(Constants.WEB_PAGE_DETAIL_NAME, product.getName());
//                bundle.putString(Constants.WEB_PAGE_DETAIL_LINK, product.getWebLink());
//
//                holder.itemView.setTag(bundle);
//
//                holder.itemView.setOnClickListener(onClickListener);
//                holder.itemView.setOnLongClickListener(onLongClickListener);
//            }
//        } else if (holder instanceof FundViewHolder) {
//            if (mHomeData != null) {
//                Product product = mHomeData.getData().getProducts().get(position - 2);
//                //设置分类
//                if (!product.getType().equals(lastType) && product.getType().equals(context.getResources().getString(R.string.product_type_security))) {
//                    ((FundViewHolder) holder).mCatView.setVisibility(View.VISIBLE);
//                    ((FundViewHolder) holder).mCatView.setText(context.getResources().getString(R.string.product_typename_security));
//                } else if (!product.getType().equals(lastType) && product.getType().equals(context.getResources().getString(R.string.product_type_equity))) {
//                    ((FundViewHolder) holder).mCatView.setVisibility(View.VISIBLE);
//                    ((FundViewHolder) holder).mCatView.setText(context.getResources().getString(R.string.product_typename_equity));
//                } else if (!product.getType().equals(lastType) && product.getType().equals(context.getResources().getString(R.string.product_type_innovation))) {
//                    ((FundViewHolder) holder).mCatView.setVisibility(View.VISIBLE);
//                    ((FundViewHolder) holder).mCatView.setText(context.getResources().getString(R.string.product_typename_innovation));
//                }
//                lastType = product.getType();
//
//                //设置标题及标签
//                if (product.getShortName() != null) {
//                    ((FundViewHolder) holder).mTilteView.setText(product.getShortName());
//                } else ((FundViewHolder) holder).mTilteView.setText(product.getName());
//
//                if (context.getString(R.string.non_level).equals(product.getLevel())) {
//                    ((FundViewHolder) holder).mGradeView.setVisibility(View.INVISIBLE);
//
//                } else ((FundViewHolder) holder).mGradeView.setText(product.getLevel());
//
//                if (product.getProductTypeName().equals(context.getString(R.string.tag_total_package))) {
//                    ((FundViewHolder) holder).mTag1.setVisibility(View.VISIBLE);
//                    ((FundViewHolder) holder).mTag1.setText(context.getString(R.string.tag_total_package));
//                }
//                if (product.getStatus().equals(context.getString(R.string.status_hot))) {
//                    ((FundViewHolder) holder).mTag2.setVisibility(View.VISIBLE);
//                    ((FundViewHolder) holder).mTag2.setText(context.getString(R.string.status_hot));
//                }
//
//                //设置起投金额
//
//                if (product.getRatesDesc().size() != 0) {
//                    SpannableString reward = new SpannableString(product.getMinStartMoneyDesc() + "以上");
//                    reward.setSpan(new RelativeSizeSpan(0.5f), reward.length() - 2, reward.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                    ((FundViewHolder) holder).mexpectRewardMin.setText(reward);
//
//                }
//
//                //设置前端佣金与后端佣金
//                if (product.type.equals(context.getResources().getString(R.string.product_type_security))
//                        ) {
//
//                    String max_front_rebate = product.getRatesDesc().get(0).getFrontendRebatePercent();
//
//                    String max_back_rebate = product.getRatesDesc().get(0).getBackendRebatePercent();
//
//                    if (product.getRatesDesc().size() != 0
//                            && !context.getString(R.string.common_hint_load).equals(max_front_rebate)
//                            && !context.getString(R.string.common_hint_auth).equals(max_front_rebate)
//                            ) {
//                        for (int i = 0; i < product.getRatesDesc().size(); i++) {
//                            String currentBack = product.getRatesDesc().get(i).getBackendRebatePercent();
//
//                            if (product.getRatesDesc().get(i).getBackendRebatePercent().contains("%")) {
//                                currentBack = currentBack.substring(0, currentBack.length() - 2);
//                                max_back_rebate = max_back_rebate.substring(0, max_back_rebate.length() - 2);
//                            }
//
//                            Double dcurrent = Double.valueOf(currentBack);
//                            Double dmax_back = Double.valueOf(max_back_rebate);
//                            if (dcurrent > dmax_back) {
//                                max_back_rebate = currentBack;
//                            }
//
//                            String currentFore = product.getRatesDesc().get(i).getFrontendRebatePercent();
//                            if (!context.getString(R.string.common_hint_load).equals(currentFore)
//                                    || !context.getString(R.string.common_hint_load).equals(max_front_rebate)) {
//                                if (currentFore.contains("%")) {
//                                    currentFore = currentFore.substring(0, currentFore.length() - 2);
//                                    max_front_rebate = max_front_rebate.substring(0, max_front_rebate.length() - 2);
//                                }
//                            }
//                            Double bcurrent = Double.valueOf(currentFore);
//                            Double bmax_fore = Double.valueOf(max_front_rebate);
//                            if (bcurrent > bmax_fore) {
//                                max_front_rebate = product.getRatesDesc().get(i).getFrontendRebatePercent();
//                            }
//                        }
//                    }
//                    //前端佣金文字格式设置
//                    SpannableString fore_rebate = new SpannableString(context.getString(R.string.expect_fore_rebate) + max_front_rebate + "%");
//                    fore_rebate.setSpan(new RelativeSizeSpan(1.2f), 4, fore_rebate.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                    fore_rebate.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_blue100)), 4, fore_rebate.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                    ((FundViewHolder) holder).mMinInvest.setText(fore_rebate);
//                    //后端佣金文字格式设置
//                    SpannableString back_rebate = new SpannableString(context.getString(R.string.expect_back_rebate) + max_back_rebate + "%");
//                    back_rebate.setSpan(new RelativeSizeSpan(1.2f), 4, fore_rebate.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                    back_rebate.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_blue100)), 4, fore_rebate.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                    ((FundViewHolder) holder).mPreiod.setText(back_rebate);
//                    //设置基金经理名称
//                    String manager = "";
//                    if (product.getManagerTeam().getName() != null) {
//                        manager = product.getManagerTeam().getName();
//                    }
//                    SpannableString name = new SpannableString(context.getString(R.string.product_fund_mananger) + manager);
//                    name.setSpan(new RelativeSizeSpan(1.2f), 4, name.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                    name.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_blue100)), 4, name.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                    ((FundViewHolder) holder).mCharge.setText(name);
//
//
//                } else if (product.type.equals(context.getResources().getString(R.string.product_type_innovation))
//                        || product.type.equals(context.getResources().getString(R.string.product_type_equity))) {
//
//                    ((FundViewHolder) holder).mMinInvest.setText(context.getString(R.string.expect_reward) + product.getMinStartMoneyDesc());
//                    ((FundViewHolder) holder).mPreiod.setText(context.getString(R.string.product_period_limit) + product.getPeriodLimit());
//                    //设置返佣比例
//                    String max_front_rebate = product.getRatesDesc().get(0).getFrontendRebatePercent();
//                    String max_back_rebate = product.getRatesDesc().get(0).getBackendRebatePercent();
//                    if (product.getRatesDesc().size() != 0
//                            && !context.getString(R.string.common_hint_load).equals(max_front_rebate)
//                            && !context.getString(R.string.common_hint_auth).equals(max_front_rebate)) {
//                        for (int i = 0; i < product.getRatesDesc().size(); i++) {
//                            String currentBack = product.getRatesDesc().get(i).getBackendRebatePercent();
//                            if (product.getRatesDesc().get(i).getBackendRebatePercent().contains("%")) {
//                                currentBack = currentBack.substring(0, currentBack.length() - 2);
//                                max_back_rebate = max_back_rebate.substring(0, max_back_rebate.length() - 2);
//                            }
//                            Double dcurrent = Double.valueOf(currentBack);
//                            Double dmax_back = Double.valueOf(max_back_rebate);
//                            if (dcurrent > dmax_back) {
//                                max_back_rebate = currentBack;
//                            }
//                            String currentFore = product.getRatesDesc().get(i).getFrontendRebatePercent();
//                            if (!context.getString(R.string.common_hint_load).equals(currentFore)
//                                    || !context.getString(R.string.common_hint_load).equals(max_front_rebate)) {
//                                if (currentFore.contains("%")) {
//                                    currentFore = currentFore.substring(0, currentFore.length() - 2);
//                                    max_front_rebate = max_front_rebate.substring(0, max_front_rebate.length() - 2);
//                                }
//                            }
//                            Double bcurrent = Double.valueOf(currentFore);
//                            Double bmax_fore = Double.valueOf(max_front_rebate);
//                            if (bcurrent > bmax_fore) {
//                                max_front_rebate = product.getRatesDesc().get(i).getFrontendRebatePercent();
//                            }
//                        }
//                    }
//                    String reward = "";
////                    if (max_back_rebate != null && !max_back_rebate.equals(max_front_rebate)) {
////                        reward = max_back_rebate + "%-" + max_front_rebate+"%";
////                    } else
//                    if (max_back_rebate != null) reward = max_back_rebate + "%";
//                    SpannableString spreward = new SpannableString(context.getString(R.string.product_reward_perc) + reward);
//                    spreward.setSpan(new RelativeSizeSpan(1.2f), 4, spreward.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                    spreward.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_blue100)), 4, spreward.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//                    ((FundViewHolder) holder).mCharge.setText(spreward);
//                }
//
//
//                Bundle bundle = new Bundle();
//                bundle.putInt(Constants.WEB_PAGE_TAG, Constants.GO_DETAIL_TAG);
//                bundle.putString(Constants.WEB_PAGE_DETAIL_NAME, product.getName());
//                bundle.putString(Constants.WEB_PAGE_DETAIL_LINK, product.getWebLink());
//
//                holder.itemView.setTag(bundle);
//
//                holder.itemView.setOnClickListener(onClickListener);
//                holder.itemView.setOnLongClickListener(onLongClickListener);
//
//            }
//        }
//    }
//
//
//    @Override
//    public int getItemViewType(int position) {
//
//        if (position == 0) {
//            return VIEW_TYPE1;
//        } else if (position == 1) {
//            return VIEW_TYPE2;
//        } else if (position >= 2 && position <= getItemCount() - 2) {
//            String type = mHomeData.getData().getProducts().get(position - 2).getType();
//            Log.i("holder", "type-----" + type + "---end");
//            if (type.equals(context.getString(R.string.product_type_trust))
//                    || type.equals(context.getString(R.string.product_type_assetmanagment))
//                    || type.equals(context.getString(R.string.product_type_bond))) {
//                return VIEW_TYPE4;
//            } else if (type.equals(context.getString(R.string.product_type_issurance))) {
//
//                return VIEW_TYPE5;
//            } else if (type.equals(context.getString(R.string.product_type_security))
//                    || type.equals(context.getString(R.string.product_type_equity))
//                    || context.getString(R.string.product_type_innovation).equals(type)) {
//                return VIEW_TYPE6;
//            }
//            return VIEW_TYPE4;
//        }
//        return VIEW_TYPE_END;
//    }
//
//    @Override
//    public int getItemCount() {
//        int count = 0;
//        if (mHomeData != null) {
//            count = mHomeData.getData().getProducts().size() + 3;
//        }
//        return count;
//    }
//
//    class ViewHolder1 extends RecyclerView.ViewHolder {
//        @Bind(R.id.ic_home_banner)
//        ImageCycleView mImageCycleView;
//
//
//        public ViewHolder1(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//        }
//    }
//
//    class ViewHolder2 extends RecyclerView.ViewHolder {
//        //        @Bind(R.id.rv_home_recommend)
//        RecyclerView mSectionRecyclerView;
//        //        @Bind(R.id.tv_item_title)
//        MarqueeView mSectionTitle;
//
//
//        public ViewHolder2(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//            mSectionRecyclerView = (RecyclerView) itemView.findViewById(R.id.rv_home_recommend);
//            mSectionTitle = (MarqueeView) itemView.findViewById(R.id.mv_hint);
//        }
//    }
//
//    class ViewHolder3 extends RecyclerView.ViewHolder {
//
//        //        @Bind(R.id.tv_item_title)
//        TextView mSectionTitle;
//
//
//        public ViewHolder3(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//            mSectionTitle = (TextView) itemView.findViewById(R.id.tv_item_title);
//        }
//    }
//
//    class ViewHolder4 extends RecyclerView.ViewHolder {
//
//        //        @Bind(R.id.tv_item_title)
//        TextView mTilteView;
//        TextView mCatView;
//        TextView mGradeView;
//        TextView mexpectRewardMin;
//        TextView mexpectRewardMax;
//        TextView mexpectRewardMaxPerc;
//        TextView mMinInvest;
//        TextView mPreiod;
//        TextView mCharge;
//        TextView mDash;
//        TextView mTag1;
//        TextView mTag2;
//        TextRoundCornerProgressBar mProgressBar;
//
//
//        public ViewHolder4(View itemView) {
//            super(itemView);
//            mTilteView = (TextView) itemView.findViewById(R.id.tv_home_trust_item_name);
//            mCatView = (TextView) itemView.findViewById(R.id.tv_item_cat);
//            mGradeView = (TextView) itemView.findViewById(R.id.tv_grade);
//            mexpectRewardMin = (TextView) itemView.findViewById(R.id.tv_home_turst_reward_min);
//            mexpectRewardMax = (TextView) itemView.findViewById(R.id.tv_home_turst_reward_max);
//            mMinInvest = (TextView) itemView.findViewById(R.id.tv_home_trust_min_invest);
//            mPreiod = (TextView) itemView.findViewById(R.id.tv_home_trust_period);
//            mCharge = (TextView) itemView.findViewById(R.id.tv_home_trust_charge_perc);
//            mDash = (TextView) itemView.findViewById(R.id.tv_home_trust_dash);
//            mTag1 = (TextView) itemView.findViewById(R.id.home_trust_tag1);
//            mTag2 = (TextView) itemView.findViewById(R.id.home_trust_tag2);
//            mexpectRewardMaxPerc = (TextView) itemView.findViewById(R.id.tv_max_pec);
//            mProgressBar = (TextRoundCornerProgressBar) itemView.findViewById(R.id.rp_homelist_trust_item);
//        }
//    }
//
//    class ViewHolder5 extends RecyclerView.ViewHolder {
//
//
//        TextView mCatView;
//        TextView mSecurityTitle;
//        ImageView mSecurityIcon;
//        TextView mMinAge;
//        TextView mSecurityPeriod;
//        TextView mSecurityCharge;
//        RecyclerView mRecycleView;
//
//
//        public ViewHolder5(View itemView) {
//            super(itemView);
//            ButterKnife.bind(this, itemView);
//            mSecurityTitle = (TextView) itemView.findViewById(R.id.tv_home_security_item_name);
//            mMinAge = (TextView) itemView.findViewById(R.id.tv_home_Security_min_age);
//            mSecurityPeriod = (TextView) itemView.findViewById(R.id.tv_home_Security_period);
//            mSecurityCharge = (TextView) itemView.findViewById(R.id.tv_home_security_item_charge);
//            mRecycleView = (RecyclerView) itemView.findViewById(R.id.rv_home_item_sucurity);
//            mSecurityIcon = (ImageView) itemView.findViewById(R.id.iv_security_item_icon);
//            mCatView = (TextView) itemView.findViewById(R.id.tv_item_cat);
//        }
//    }
//
//    class FundViewHolder extends RecyclerView.ViewHolder {
//
//        //        @Bind(R.id.tv_item_title)
//
//        TextView mTilteView;
//        TextView mCatView;
//        TextView mGradeView;
//        TextView mexpectRewardMin;
//        TextView mexpectRewardMaxPerc;
//        TextView mMinInvest;
//        TextView mPreiod;
//        TextView mCharge;
//        TextView mTag1;
//        TextView mTag2;
//
//        public FundViewHolder(View itemView) {
//            super(itemView);
//            mTilteView = (TextView) itemView.findViewById(R.id.tv_home_trust_item_name);
//            mGradeView = (TextView) itemView.findViewById(R.id.tv_grade);
//            mexpectRewardMin = (TextView) itemView.findViewById(R.id.tv_home_turst_reward_min);
//
//            mMinInvest = (TextView) itemView.findViewById(R.id.tv_home_trust_min_invest);
//            mPreiod = (TextView) itemView.findViewById(R.id.tv_home_trust_period);
//            mCharge = (TextView) itemView.findViewById(R.id.tv_home_trust_charge_perc);
//            mTag1 = (TextView) itemView.findViewById(R.id.home_trust_tag1);
//            mTag2 = (TextView) itemView.findViewById(R.id.home_trust_tag2);
//            mexpectRewardMaxPerc = (TextView) itemView.findViewById(R.id.tv_max_pec);
//            mCatView = (TextView) itemView.findViewById(R.id.tv_item_cat);
//        }
//    }
//
//
//    class ViewHolder6 extends RecyclerView.ViewHolder {
//
//        //        @Bind(R.id.tv_item_title)
//
//
//        public ViewHolder6(View itemView) {
//            super(itemView);
//
//
//        }
//    }
//
//}
