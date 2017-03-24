package com.limi88.financialplanner.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.ui.widget.OnItemClickListener;

import java.util.List;

import jp.wasabeef.glide.transformations.CropSquareTransformation;

/**
 * Created by hehao
 * Date on 16/12/8.
 * Email: hao.he@sunanzq.com
 */
public class TopicGallaryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    int TYPE1=1;
    private Context mContext;
    private List<String> images;
    private OnItemClickListener mOnItemClickListener;
    public TopicGallaryAdapter(Context context,List<String>images) {
        this.images=images;
        this.mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder=null;
        if (viewType==TYPE1) {
            holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_topic_gallary_item, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            String thumb=images.get(position);
            Glide.with(mContext).load(thumb)
                    .placeholder(R.mipmap.bg_home_sub_item)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .crossFade()
                    .bitmapTransform(new CropSquareTransformation(mContext))
                    .into(((ViewHolder) holder).mImageView);
            ((ViewHolder) holder).mImageView.setOnClickListener(view -> {
                view.setTag("imamgesFromGallary");
                mOnItemClickListener.onItemClick(view,images,position);
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE1;
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class  ViewHolder extends  RecyclerView.ViewHolder{
        ImageView mImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            mImageView= (ImageView) itemView.findViewById(R.id.iv_topic_gallary_item);
        }
    }
}

