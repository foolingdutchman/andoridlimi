package com.limi88.financialplanner.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.ui.MainActivity;
import com.limi88.financialplanner.ui.base.BaseWebViewActivity;
import com.limi88.financialplanner.ui.login.LoginActivity;
import com.limi88.financialplanner.ui.widget.ImageCycleView;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.pojo.home.Product;
import com.limi88.financialplanner.util.Constants;

import java.util.List;

import javax.inject.Singleton;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by hehao on 16/9/12.
 */
public class ProductSubPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int VIEW_TYPE4 = 4;
    private int VIEW_TYPE5 = 5;
    private int VIEW_TYPE6 = 6;
    private int VIEW_TYPE_END = 7;


    private Context context;
    //    private List<HomeTestBean> mDataList;

    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;

    private List<Product> mHomeData;
    private Intent mIntent;
    private String lastType = "";

    @Singleton
    private Bundle bundle;

    public void setmHomeData(List<Product> mHomeData) {
        this.mHomeData = mHomeData;
    }

    public ProductSubPageAdapter(Context context, List<Product> mHomeData) {
        this.context = context;
        this.mHomeData = mHomeData;
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
        if (viewType == VIEW_TYPE4) {
            holder = new ViewHolder4(LayoutInflater.from(context).inflate(R.layout.productlist_item_trust, parent, false));
        } else if (viewType == VIEW_TYPE5) {
            holder = new ViewHolder5(LayoutInflater.from(context).inflate(R.layout.productlist_item_issurance, parent, false));
        } else if (viewType == VIEW_TYPE_END) {
            holder = new ViewHolder6(LayoutInflater.from(context).inflate(R.layout.common_footer, parent, false));
        } else if (viewType == VIEW_TYPE6) {
            holder = new FundViewHolder(LayoutInflater.from(context).inflate(R.layout.productlist_item_fund, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i("holder", getItemViewType(position) + "------position");
        if (holder instanceof ViewHolder4) {
            if (mHomeData != null) {
                Product product = mHomeData.get(position);

                lastType = product.getType();
                if (product.getShortName() != null) {
                    ((ViewHolder4) holder).mTilteView.setText(product.getShortName());
                } else ((ViewHolder4) holder).mTilteView.setText(product.getName());

                if (context.getString(R.string.non_level).equals(product.getLevel())) {
                    ((ViewHolder4) holder).mGradeView.setVisibility(View.INVISIBLE);

                } else ((ViewHolder4) holder).mGradeView.setText(product.getLevel());

                if (product.getProductTypeName().equals(context.getString(R.string.tag_total_package))) {
                    ((ViewHolder4) holder).mTag1.setVisibility(View.VISIBLE);
                    ((ViewHolder4) holder).mTag1.setText(context.getString(R.string.tag_total_package));
                }
                if (product.getStatus().equals(context.getString(R.string.status_hot))) {
                    ((ViewHolder4) holder).mTag2.setVisibility(View.VISIBLE);
                    ((ViewHolder4) holder).mTag2.setText(context.getString(R.string.status_hot));
                }
                //设置投资领域
                ((ViewHolder4) holder).mAreaView.setText(product.getInvestArea());


                if (product.getRatesDesc().size() != 0) {
                    if (product.getRatesDesc().size() == 1) {
                        ((ViewHolder4) holder).mexpectRewardMin.setText(product.getRatesDesc().get(0).getExpectRate());
                        ((ViewHolder4) holder).mDash.setVisibility(View.INVISIBLE);
                        ((ViewHolder4) holder).mexpectRewardMax.setVisibility(View.INVISIBLE);
                        ((ViewHolder4) holder).mexpectRewardMaxPerc.setVisibility(View.INVISIBLE);

                    } else {
                        Double minExpectRate = Double.valueOf(product.getRatesDesc().get(0).getExpectRate());
                        Double minRebate = Double.valueOf(product.getRatesDesc().get(0).getExpectRate());
                        Double maxExpectRate = Double.valueOf(product.getRatesDesc().get(0).getExpectRate());
                        Double maxRebate = Double.valueOf(product.getRatesDesc().get(0).getExpectRate());

                        for (int i = 0; i < product.getRatesDesc().size(); i++) {
                            Double expectRate = Double.valueOf(product.getRatesDesc().get(i).getExpectRate());
                            String rebateString = product.getRatesDesc().get(i).getRebatePercent();
//                            Double Rabate = Double.valueOf(rebateString.substring(0, rebateString.length()-1));

                            if (expectRate > maxExpectRate) maxExpectRate = expectRate;
                            if (expectRate < minExpectRate) minExpectRate = expectRate;
                        }
                        if (maxExpectRate - minExpectRate <= 0.001) {
                            ((ViewHolder4) holder).mexpectRewardMin.setText(minExpectRate + "");
                            ((ViewHolder4) holder).mDash.setVisibility(View.INVISIBLE);
                            ((ViewHolder4) holder).mexpectRewardMax.setVisibility(View.INVISIBLE);
                            ((ViewHolder4) holder).mexpectRewardMaxPerc.setVisibility(View.INVISIBLE);
                        } else {
                            ((ViewHolder4) holder).mexpectRewardMin.setText(minExpectRate + "");
                            ((ViewHolder4) holder).mexpectRewardMax.setText(maxExpectRate + "");
                        }

                        ((ViewHolder4) holder).mexpectRewardMin.setText(minExpectRate + "");
                        ((ViewHolder4) holder).mexpectRewardMax.setText(maxExpectRate + "");
                    }
                    ((ViewHolder4) holder).mMinInvest.setText(context.getString(R.string.min_start_money) + product.getMinStartMoneyDesc());
                    ((ViewHolder4) holder).mPreiod.setText(context.getString(R.string.period_desc) + product.getPeriodDesc());

                    //设置佣金比例

                    String rebate = product.getRatesDesc().get(0).getRebatePercent();

                    if (product.getRatesDesc().size() != 0
                            && !context.getString(R.string.common_hint_load).equals(rebate)
                            && !context.getString(R.string.common_hint_auth).equals(rebate)) {
                        Double max_rebate = Double.valueOf(rebate);
                        Double min_rebate = Double.valueOf(rebate);
                        for (int i = 0; i < product.getRatesDesc().size(); i++) {
                            String currentBack = product.getRatesDesc().get(i).getRebatePercent();
                            if (product.getRatesDesc().get(i).getRebatePercent().contains("%")) {
                                currentBack = currentBack.substring(0, currentBack.length() - 2);
                                rebate = rebate.substring(0, rebate.length() - 2);
                            }
                            Double dcurrent = Double.valueOf(currentBack);

                            if (dcurrent > max_rebate) {
                                max_rebate = dcurrent;
                            }
                            if (dcurrent < min_rebate) {
                                min_rebate = dcurrent;
                            }

                        }
                        if (max_rebate - min_rebate >= 0.001) {
                            rebate = min_rebate + "% -" + max_rebate + "%";
                        } else rebate = min_rebate + "%";

                    }

                    SpannableString reward = new SpannableString(context.getString(R.string.product_reward_perc) + rebate);
                    reward.setSpan(new RelativeSizeSpan(1.2f), 4, reward.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    reward.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_blue100)), 4, reward.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

                    ((ViewHolder4) holder).mCharge.setText(reward);
                    //添加响应事件
                    bundle = new Bundle();
                    if (rebate.equals(context.getString(R.string.common_hint_load))) {
                        bundle.putInt(Constants.WEB_PAGE_TAG, Constants.NO_LOGIN_TAG);
                    }
                    if (rebate.equals(context.getString(R.string.common_hint_auth))) {
                        bundle.putInt(Constants.WEB_PAGE_TAG, Constants.NO_AUTHENTICATION_TAG);
                    }

                    ((ViewHolder4) holder).mCharge.setTag(bundle);
                    ((ViewHolder4) holder).mCharge.setOnClickListener(onClickListener);
                    Log.i("holder", "getProgress:---------" + product.getProgress());
                    if (product.getProgress() != null) {
                        ((ViewHolder4) holder).mProgressBar.setProgress(Float.valueOf(product.getProgress()));
                        ((ViewHolder4) holder).mProgressBar.setProgressText("已募集" + product.getProgress() + "%");
                    }
                    Bundle bundle = new Bundle();
                    bundle.putInt(Constants.WEB_PAGE_TAG, Constants.GO_DETAIL_TAG);
                    bundle.putString(Constants.WEB_PAGE_DETAIL_NAME, product.getName());
                    bundle.putString(Constants.WEB_PAGE_DETAIL_LINK, product.getWebLink());

                    holder.itemView.setTag(bundle);

                    holder.itemView.setOnClickListener(onClickListener);
                    holder.itemView.setOnLongClickListener(onLongClickListener);
                }
            }

        } else if (holder instanceof ViewHolder5) {
            if (mHomeData != null) {
                Product product = mHomeData.get(position);
                lastType = product.getType();
                ((ViewHolder5) holder).mSecurityTitle.setText(product.getName());
                ((ViewHolder5) holder).mMinAge.setText("承保年龄: " + product.getAgeSprd());
                ((ViewHolder5) holder).mSecurityPeriod.setText("保险期间: " + product.getInsurSprd());
                //设置开门红标签
                if (product.getTags().size()!=0) {
                    ((ViewHolder5) holder).mTag2.setVisibility(View.VISIBLE);
                    ((ViewHolder5) holder).mTag2.setText(product.getTags().get(0).getName());
                }
                //设置佣金比例
                String rebate="";
                if (product.getRatesDesc().size()!=0) {
                    rebate = product.getRatesDesc().get(0).getRebatePercent();
                }
                if ( !context.getString(R.string.common_hint_load).equals(rebate)
                        && !context.getString(R.string.common_hint_auth).equals(rebate)
                        &&!rebate.equals("")
                 ) {
                    rebate=rebate.trim();
                    Double max_rebate = Double.valueOf(rebate);
                    Double min_rebate = Double.valueOf(rebate);
                    for (int i = 0; i < product.getRatesDesc().size(); i++) {
                        String currentBack = product.getRatesDesc().get(i).getRebatePercent();
                        if (product.getRatesDesc().get(i).getRebatePercent().contains("%")) {
                            currentBack = currentBack.substring(0, currentBack.length() - 2);
                            rebate = rebate.substring(0, rebate.length() - 2);
                        }
                        Double dcurrent = Double.valueOf(currentBack);

                        if (dcurrent > max_rebate) {
                            max_rebate = dcurrent;
                        }
                        if (dcurrent < min_rebate) {
                            min_rebate = dcurrent;
                        }

                    }
                    if (max_rebate - min_rebate >= 0.001) {
                        rebate = min_rebate + "% -" + max_rebate + "%";
                    } else rebate = min_rebate + "%";
                }
//                else {
//                    if (DataCenter.getDeviceToken() == null) {
//                        rebate = "登录可见";
//                    }
//                }
                SpannableString maxrebate = new SpannableString(context.getString(R.string.product_issurance_max_rebate) +" "+ rebate);
                maxrebate.setSpan(new RelativeSizeSpan(1.2f), 6, maxrebate.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                maxrebate.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_blue100)), 6, maxrebate.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

                ((ViewHolder5) holder).mSecurityCharge.setText(maxrebate);


                Glide.with(context).load(Constants.HOST + product.getInsurOrg().getAvatar().getUrl())
                        .placeholder(R.mipmap.ic_product_default)
                        .crossFade()
                        .into(((ViewHolder5) holder).mSecurityIcon);
                IssuranceIntroAdapter issuranceAdapter = new IssuranceIntroAdapter(product.getIntro(), context);
                ((ViewHolder5) holder).mRecycleView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                ((ViewHolder5) holder).mRecycleView.setAdapter(issuranceAdapter);

                Bundle bundle = new Bundle();
                bundle.putInt(Constants.WEB_PAGE_TAG, Constants.GO_DETAIL_TAG);
                bundle.putString(Constants.WEB_PAGE_DETAIL_NAME, product.getName());
                bundle.putString(Constants.WEB_PAGE_DETAIL_LINK, product.getWebLink());
                if ("登录可见".equals(rebate)) {
                    ((ViewHolder5) holder).mSecurityCharge.setOnClickListener(v -> ((MainActivity) context).startActivityForResult(new Intent(context, LoginActivity.class), Constants.PRODUCTS_FLAG));
                } else if ("认证可见".equals(rebate)) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt(Constants.WEB_PAGE_TAG, Constants.GO_DETAIL_TAG);
                    bundle1.putString(Constants.WEB_PAGE_DETAIL_LINK, Constants.MINE_AUTHENTICATION_URL);
                    mIntent = new Intent(context, BaseWebViewActivity.class);
                    mIntent.putExtras(bundle1);
                    ((ViewHolder5) holder).mSecurityCharge.setOnClickListener(v -> ((MainActivity) context).startActivityForResult(mIntent, Constants.PRODUCTS_FLAG));

                }
                ((ViewHolder5) holder).mRecycleView.setTag(bundle);
                ((ViewHolder5) holder).mRecycleView.setOnClickListener(onClickListener);
                holder.itemView.setTag(bundle);
                holder.itemView.setOnClickListener(onClickListener);
                holder.itemView.setOnLongClickListener(onLongClickListener);
            }
        } else if (holder instanceof FundViewHolder) {
            if (mHomeData != null) {
                Product product = mHomeData.get(position);

                lastType = product.getType();

                //设置标题及标签
                if (product.getShortName() != null) {
                    ((FundViewHolder) holder).mTilteView.setText(product.getShortName());
                } else ((FundViewHolder) holder).mTilteView.setText(product.getName());

                if (context.getString(R.string.non_level).equals(product.getLevel())) {
                    ((FundViewHolder) holder).mGradeView.setVisibility(View.INVISIBLE);

                } else ((FundViewHolder) holder).mGradeView.setText(product.getLevel());

                if (product.getProductTypeName().equals(context.getString(R.string.tag_total_package))) {
                    ((FundViewHolder) holder).mTag1.setVisibility(View.VISIBLE);
                    ((FundViewHolder) holder).mTag1.setText(context.getString(R.string.tag_total_package));
                }
                if (product.getStatus().equals(context.getString(R.string.status_hot))) {
                    ((FundViewHolder) holder).mTag2.setVisibility(View.VISIBLE);
                    ((FundViewHolder) holder).mTag2.setText(context.getString(R.string.status_hot));
                }

                //设置投资领域
                ((FundViewHolder) holder).mAreaView.setText(product.getInvestArea());

                //设置起投金额

                if (product.getRatesDesc().size() != 0) {
                    SpannableString reward = new SpannableString(product.getMinStartMoneyDesc() + "以上");
                    reward.setSpan(new RelativeSizeSpan(0.5f), reward.length() - 2, reward.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    ((FundViewHolder) holder).mexpectRewardMin.setText(reward);

                }

                //设置前端佣金与后端佣金
                if (product.type.equals(context.getResources().getString(R.string.product_type_security))
                        ) {

                    String max_front_rebate = product.getRatesDesc().get(0).getFrontendRebatePercent();

                    String max_back_rebate = product.getRatesDesc().get(0).getBackendRebatePercent();

                    if (product.getRatesDesc().size() != 0
                            && !context.getString(R.string.common_hint_load).equals(max_front_rebate)
                            && !context.getString(R.string.common_hint_auth).equals(max_front_rebate)
                            ) {
                        for (int i = 0; i < product.getRatesDesc().size(); i++) {
                            String currentBack = product.getRatesDesc().get(i).getBackendRebatePercent();

                            if (product.getRatesDesc().get(i).getBackendRebatePercent().contains("%")) {
                                currentBack = currentBack.substring(0, currentBack.length() - 2);
                                max_back_rebate = max_back_rebate.substring(0, max_back_rebate.length() - 2);
                            }

                            Double dcurrent = Double.valueOf(currentBack);
                            Double dmax_back = Double.valueOf(max_back_rebate);
                            if (dcurrent > dmax_back) {
                                max_back_rebate = currentBack;
                            }

                            String currentFore = product.getRatesDesc().get(i).getFrontendRebatePercent();
                            if (!context.getString(R.string.common_hint_load).equals(currentFore)
                                    || !context.getString(R.string.common_hint_load).equals(max_front_rebate)) {
                                if (currentFore.contains("%")) {
                                    currentFore = currentFore.substring(0, currentFore.length() - 2);
                                    max_front_rebate = max_front_rebate.substring(0, max_front_rebate.length() - 2);
                                }
                            }

                            Double bcurrent = Double.valueOf(currentFore);
                            Double bmax_fore = Double.valueOf(max_front_rebate);
                            if (bcurrent > bmax_fore) {
                                max_front_rebate = product.getRatesDesc().get(i).getFrontendRebatePercent();
                            }
                        }
                    }
                    //前端佣金文字格式设置
                    SpannableString fore_rebate = new SpannableString(context.getString(R.string.expect_fore_rebate) + max_front_rebate + "%");
                    fore_rebate.setSpan(new RelativeSizeSpan(1.2f), 4, fore_rebate.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    fore_rebate.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_blue100)), 4, fore_rebate.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    ((FundViewHolder) holder).mMinInvest.setText(fore_rebate);
                    //后端佣金文字格式设置
                    SpannableString back_rebate = new SpannableString(context.getString(R.string.expect_back_rebate) + max_back_rebate + "%");
                    back_rebate.setSpan(new RelativeSizeSpan(1.2f), 4, fore_rebate.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    back_rebate.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_blue100)), 4, fore_rebate.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    ((FundViewHolder) holder).mPreiod.setText(back_rebate);
                    //设置基金经理名称
                    String manager = "";
                    if (product.getManagerTeam().getName() != null) {
                        manager = product.getManagerTeam().getName();
                    }
                    SpannableString name = new SpannableString(context.getString(R.string.product_fund_mananger) + manager);
                    name.setSpan(new RelativeSizeSpan(1.2f), 4, name.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    name.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_blue100)), 4, name.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    ((FundViewHolder) holder).mCharge.setText(name);


                } else if (product.type.equals(context.getResources().getString(R.string.product_type_innovation))
                        || product.type.equals(context.getResources().getString(R.string.product_type_equity))) {

                    ((FundViewHolder) holder).mMinInvest.setText(context.getString(R.string.expect_reward) + product.getMinStartMoneyDesc());
                    ((FundViewHolder) holder).mPreiod.setText(context.getString(R.string.product_period_limit) + product.getPeriodLimit());
                    //设置返佣比例
                    String max_front_rebate = product.getRatesDesc().get(0).getFrontendRebatePercent();
                    String max_back_rebate = product.getRatesDesc().get(0).getBackendRebatePercent();
                    if (product.getRatesDesc().size() != 0
                            && !context.getString(R.string.common_hint_load).equals(max_front_rebate)
                            && !context.getString(R.string.common_hint_auth).equals(max_front_rebate)) {
                        for (int i = 0; i < product.getRatesDesc().size(); i++) {
                            String currentBack = product.getRatesDesc().get(i).getBackendRebatePercent();
                            if (currentBack != null) {
                                if (currentBack.contains("%")) {
                                    currentBack = currentBack.substring(0, currentBack.length() - 2);
                                    max_back_rebate = max_back_rebate.substring(0, max_back_rebate.length() - 2);
                                }
                                Double dcurrent = Double.valueOf(currentBack);
                                Double dmax_back = Double.valueOf(max_back_rebate);
                                if (dcurrent > dmax_back) {
                                    max_back_rebate = currentBack;
                                }
                            }

                            String currentFore = product.getRatesDesc().get(i).getFrontendRebatePercent();
                            if (currentFore != null) {
                                if (!context.getString(R.string.common_hint_load).equals(currentFore)
                                        || !context.getString(R.string.common_hint_load).equals(max_front_rebate)) {
                                    if (currentFore.contains("%")) {
                                        currentFore = currentFore.substring(0, currentFore.length() - 2);
                                        max_front_rebate = max_front_rebate.substring(0, max_front_rebate.length() - 2);
                                    }
                                }
                                Double bcurrent = Double.valueOf(currentFore);
                                Double bmax_fore = Double.valueOf(max_front_rebate);
                                if (bcurrent > bmax_fore) {
                                    max_front_rebate = product.getRatesDesc().get(i).getFrontendRebatePercent();
                                }
                            }

                        }
                    }
                    String reward = "";
                    if (max_back_rebate != null && !max_back_rebate.equals(max_front_rebate)) {
                        reward = max_back_rebate + "%-" + max_front_rebate + "%";
                    } else if (max_back_rebate != null) reward = max_back_rebate + "%";
                    SpannableString spreward = new SpannableString(context.getString(R.string.product_reward_perc) + reward);
                    spreward.setSpan(new RelativeSizeSpan(1.2f), 4, spreward.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    spreward.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.color_blue100)), 4, spreward.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                    ((FundViewHolder) holder).mCharge.setText(spreward);
                }


                Bundle bundle = new Bundle();
                bundle.putInt(Constants.WEB_PAGE_TAG, Constants.GO_DETAIL_TAG);
                bundle.putString(Constants.WEB_PAGE_DETAIL_NAME, product.getName());
                bundle.putString(Constants.WEB_PAGE_DETAIL_LINK, product.getWebLink());

                holder.itemView.setTag(bundle);

                holder.itemView.setOnClickListener(onClickListener);
                holder.itemView.setOnLongClickListener(onLongClickListener);

            }
        }
    }


    @Override
    public int getItemViewType(int position) {

        if (position >= 0 && position < getItemCount() - 1) {
            String type = mHomeData.get(position).getType();
            Log.i("holder", "type-----" + type + "---end");
            if (type.equals(context.getString(R.string.product_type_trust))
                    || type.equals(context.getString(R.string.product_type_assetmanagment))
                    || type.equals(context.getString(R.string.product_type_bond))) {
                return VIEW_TYPE4;
            } else if (type.equals(context.getString(R.string.product_type_issurance))) {

                return VIEW_TYPE5;
            } else if (type.equals(context.getString(R.string.product_type_security))
                    || type.equals(context.getString(R.string.product_type_equity))
                    || context.getString(R.string.product_type_innovation).equals(type)) {
                return VIEW_TYPE6;
            }
            return VIEW_TYPE4;
        }
        return VIEW_TYPE_END;
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mHomeData != null) {
            count = mHomeData.size() + 1;
        }
        return count;
    }

    public class ViewHolder1 extends RecyclerView.ViewHolder {
        @Bind(R.id.ic_home_banner)
        ImageCycleView mImageCycleView;


        public ViewHolder1(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public class ViewHolder3 extends RecyclerView.ViewHolder {

        //        @Bind(R.id.tv_item_title)
        TextView mSectionTitle;


        public ViewHolder3(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mSectionTitle = (TextView) itemView.findViewById(R.id.tv_item_title);
        }
    }

    public class ViewHolder4 extends RecyclerView.ViewHolder {

        //        @Bind(R.id.tv_item_title)
        TextView mTilteView;
        TextView mGradeView;
        TextView mexpectRewardMin;
        TextView mexpectRewardMax;
        TextView mexpectRewardMaxPerc;
        TextView mMinInvest;
        TextView mPreiod;
        TextView mCharge;
        TextView mDash;
        TextView mTag1;
        TextView mTag2;
        TextView mAreaView;
        TextRoundCornerProgressBar mProgressBar;


        public ViewHolder4(View itemView) {
            super(itemView);
            mTilteView = (TextView) itemView.findViewById(R.id.tv_home_trust_item_name);
            mGradeView = (TextView) itemView.findViewById(R.id.tv_grade);
            mexpectRewardMin = (TextView) itemView.findViewById(R.id.tv_home_turst_reward_min);
            mexpectRewardMax = (TextView) itemView.findViewById(R.id.tv_home_turst_reward_max);
            mMinInvest = (TextView) itemView.findViewById(R.id.tv_home_trust_min_invest);
            mPreiod = (TextView) itemView.findViewById(R.id.tv_home_trust_period);
            mCharge = (TextView) itemView.findViewById(R.id.tv_home_trust_charge_perc);
            mDash = (TextView) itemView.findViewById(R.id.tv_home_trust_dash);
            mTag1 = (TextView) itemView.findViewById(R.id.home_trust_tag1);
            mTag2 = (TextView) itemView.findViewById(R.id.home_trust_tag2);
            mAreaView = (TextView) itemView.findViewById(R.id.tv_home_trust_item_area);
            mexpectRewardMaxPerc = (TextView) itemView.findViewById(R.id.tv_max_pec);
            mProgressBar = (TextRoundCornerProgressBar) itemView.findViewById(R.id.rp_homelist_trust_item);
        }
    }

    public class ViewHolder5 extends RecyclerView.ViewHolder {

        TextView mTag2;
        TextView mSecurityTitle;
        ImageView mSecurityIcon;
        TextView mMinAge;
        TextView mSecurityPeriod;
        TextView mSecurityCharge;
        RecyclerView mRecycleView;


        public ViewHolder5(View itemView) {
            super(itemView);
            mSecurityTitle = (TextView) itemView.findViewById(R.id.tv_home_security_item_name);
            mMinAge = (TextView) itemView.findViewById(R.id.tv_home_Security_min_age);
            mSecurityPeriod = (TextView) itemView.findViewById(R.id.tv_home_Security_period);
            mSecurityCharge = (TextView) itemView.findViewById(R.id.tv_home_security_item_charge);
            mRecycleView = (RecyclerView) itemView.findViewById(R.id.rv_home_item_sucurity);
            mSecurityIcon = (ImageView) itemView.findViewById(R.id.iv_security_item_icon);
            mTag2 = (TextView) itemView.findViewById(R.id.home_trust_tag2);
        }
    }

    public class FundViewHolder extends RecyclerView.ViewHolder {

        //        @Bind(R.id.tv_item_title)

        TextView mTilteView;

        TextView mGradeView;
        TextView mexpectRewardMin;
        TextView mexpectRewardMaxPerc;
        TextView mMinInvest;
        TextView mPreiod;
        TextView mCharge;
        TextView mTag1;
        TextView mTag2;
        TextView mAreaView;

        public FundViewHolder(View itemView) {
            super(itemView);
            mTilteView = (TextView) itemView.findViewById(R.id.tv_home_trust_item_name);
            mGradeView = (TextView) itemView.findViewById(R.id.tv_grade);
            mexpectRewardMin = (TextView) itemView.findViewById(R.id.tv_home_turst_reward_min);

            mMinInvest = (TextView) itemView.findViewById(R.id.tv_home_trust_min_invest);
            mPreiod = (TextView) itemView.findViewById(R.id.tv_home_trust_period);
            mCharge = (TextView) itemView.findViewById(R.id.tv_home_trust_charge_perc);
            mTag1 = (TextView) itemView.findViewById(R.id.home_trust_tag1);
            mTag2 = (TextView) itemView.findViewById(R.id.home_trust_tag2);
            mexpectRewardMaxPerc = (TextView) itemView.findViewById(R.id.tv_max_pec);
            mAreaView = (TextView) itemView.findViewById(R.id.tv_home_trust_item_area);
        }
    }


    public class ViewHolder6 extends RecyclerView.ViewHolder {

        //        @Bind(R.id.tv_item_title)


        public ViewHolder6(View itemView) {
            super(itemView);


        }
    }

}

