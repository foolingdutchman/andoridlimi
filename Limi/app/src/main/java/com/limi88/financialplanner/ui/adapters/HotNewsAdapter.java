package com.limi88.financialplanner.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.pojo.hotnews.Datum;
import com.limi88.financialplanner.pojo.hotnews.HotNews;
import com.limi88.financialplanner.ui.base.BaseWebViewActivity;
import com.limi88.financialplanner.util.Constants;

import java.util.ArrayList;
import java.util.List;

import anet.channel.util.StringUtils;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;

/**
 * Created by hehao
 * Date on 16/12/7.
 * Email: hao.he@sunanzq.com
 */
public class HotNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private HotNews mHotNews;
    int TYPE1 = 1;
    int TYPE2 = 2;
    private List<Datum> mDatumList = new ArrayList<>();
    private Intent mIntent;

    public HotNewsAdapter(Context context, HotNews hotNews) {
        this.mContext = context;
        this.mHotNews = hotNews;
        mDatumList = mHotNews.getData();
    }

    public void attachData(List<Datum> datumList) {
        this.mDatumList = datumList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == TYPE1) {
            holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_sublist_item1, parent, false));
        } else if (viewType == TYPE2) {
            holder = new ViewHolder2(LayoutInflater.from(mContext).inflate(R.layout.home_sublist_item2, parent, false));

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mDatumList.size() != 0) {
            Datum hotNew = mDatumList.get(position);
            if (holder instanceof ViewHolder) {
                ((ViewHolder) holder).mTextView.setText(hotNew.getTitle());
                ((ViewHolder) holder).mTagtext.setText(hotNew.getTag());
                ((ViewHolder) holder).mTimetext.setText(hotNew.getTimesAgo());
                String thumb = hotNew.getAvatarUrl();
                Glide.with(mContext).load(thumb)
                        .placeholder(R.mipmap.bg_home_sub_item)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .crossFade()
//                        .bitmapTransform(new CropSquareTransformation(mContext))
                        .into(((ViewHolder) holder).mThumb);
                holder.itemView.setOnClickListener(v -> {
                    int id = hotNew.getId();
                    toWebView(Constants.HOME_NEWS_DETAIL_BASE + id, Constants.GO_DETAIL_TAG);
                });

            } else if (holder instanceof ViewHolder2) {
              ((ViewHolder2) holder).mTextView.setText(hotNew.getTitle());
                String thumb = hotNew.getAvatarUrl();
                Glide.with(mContext).load(thumb)
                        .placeholder(R.mipmap.bg_default_banner)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .crossFade()
//                        .bitmapTransform(new CropSquareTransformation(mContext))
                        .into(((ViewHolder2) holder).mThumb);
                holder.itemView.setOnClickListener(v -> {
                    int id = hotNew.getId();
                    toWebView(hotNew.getOuterLink(), Constants.GO_DETAIL_TAG);
                });
            }
        }
    }

    private void toWebView(String path, int webPageTagValue) {
        toWebView(path, Constants.WEB_PAGE_DETAIL_LINK, webPageTagValue);
    }

    private void toWebView(String path, String linkKey, int webPageTagValue) {
        if (!StringUtils.isBlank(path)) {
            Bundle bu = new Bundle();
            String url = path.contains("http") ? path : Constants.HOST + path;
//            String url = path;
            bu.putInt(Constants.WEB_PAGE_TAG, webPageTagValue);
            bu.putString(linkKey, url);
            mIntent = new Intent(mContext, BaseWebViewActivity.class);
            mIntent.putExtras(bu);
            mContext.startActivity(mIntent);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDatumList.size() != 0) {
            if (!mDatumList.get(position).getOuterLink().equals("")) {
                return TYPE2;
            } else return TYPE1;
        }
        return TYPE1;
    }

    @Override
    public int getItemCount() {
        if (mDatumList.size() != 0) {
            return mDatumList.size();
        } else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        TextView mTagtext;
        TextView mTimetext;
        ImageView mThumb;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_home_sub_title);
            mTagtext = (TextView) itemView.findViewById(R.id.tv_item_cat);
            mTimetext = (TextView) itemView.findViewById(R.id.tv_item_time);
            mThumb = (ImageView) itemView.findViewById(R.id.iv_home_sub_thumb);
        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView mThumb;

        public ViewHolder2(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_home_subitem_title);

            mThumb = (ImageView) itemView.findViewById(R.id.iv_home_subitem_avatar);
        }
    }
}

