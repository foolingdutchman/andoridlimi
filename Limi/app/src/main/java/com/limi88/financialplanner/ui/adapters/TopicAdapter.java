package com.limi88.financialplanner.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.pojo.topics.Datum;
import com.limi88.financialplanner.pojo.topics.Topic;
import com.limi88.financialplanner.ui.MainActivity;
import com.limi88.financialplanner.ui.login.LoginActivity;
import com.limi88.financialplanner.ui.widget.MyExpandableTextView;
import com.limi88.financialplanner.ui.widget.OnItemClickListener;
import com.limi88.financialplanner.util.Constants;

import java.util.ArrayList;
import java.util.List;

import cn.carbs.android.expandabletextview.library.ExpandableTextView;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by hehao
 * Date on 16/12/7.
 * Email: hao.he@sunanzq.com
 */
public class TopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements MyExpandableTextView.OnExpandListener {
    private Context mContext;
    private Topic mTopic;
    private int TYPE1 = 1;
    private SparseArray<Integer> mPositionsAndStates = new SparseArray<>();
    private List<Datum> mDatumList = new ArrayList<>();
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public TopicAdapter(Context context, Topic data) {
        this.mContext = context;
        mTopic = data;
        mDatumList = mTopic.getData();
    }

    public void attachData(List<Datum> datumList) {
        mDatumList=datumList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == TYPE1) {
            holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_topics_item1, parent, false));
        }
        return holder;
    }

    //只要在getview时为其赋值为准确的宽度值即可，无论采用何种方法
    private int etvWidth = 0;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            if (mDatumList != null && mDatumList.size() != 0) {
                Datum topic = mDatumList.get(position);
                String url = "";
                String content = topic.getContent();
                if (etvWidth == 0) {
                    ((ViewHolder) holder).etv.post(new Runnable() {
                        @Override
                        public void run() {
                            etvWidth = ((ViewHolder) holder).etv.getWidth();
                        }
                    });
                }

                ((ViewHolder) holder).etv.setTag(position);
                ((ViewHolder) holder).etv.setExpandListener(this);
                Integer state = mPositionsAndStates.get(position);
                ((ViewHolder) holder).etv.updateForRecyclerView(content.toString(), etvWidth, state == null ? 0 : state);//第一次getview时肯定为etvWidth为0
                ((ViewHolder) holder).mNameText.setText(topic.getUser().getName());
                Glide.with(mContext).load(topic.getUser().getAvatarUrl())
                        .placeholder(R.mipmap.ic_default_avatar)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .crossFade()
                        .bitmapTransform(new CropCircleTransformation(mContext))
                        .into(((ViewHolder) holder).mAvatar);

                ((ViewHolder) holder).mCommentText.setText(topic.getCommentNum() + "");
                ((ViewHolder) holder).mTimeText.setText(topic.getTimesAgo() + "");

                if (topic.isHot()) {
                    ((ViewHolder) holder).mHotTag.setVisibility(View.VISIBLE);
                } else ((ViewHolder) holder).mHotTag.setVisibility(View.GONE);
                if (topic.getImages().size() == 1) {
                    Glide.with(mContext).load(topic.getImages().get(0)).asBitmap().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            int bitmapWidth = resource.getWidth();
                            int bitmapHeight = resource.getHeight();
                            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
                            if (resource.getHeight() <= resource.getWidth()) {
                                ((ViewHolder) holder).mHeightImage.setVisibility(View.GONE);
                                ((ViewHolder) holder).mWideImage.setVisibility(View.VISIBLE);
                                ((ViewHolder) holder).mWideImage.setImageBitmap(resource);
                                ((ViewHolder) holder).mRecyclerView.setVisibility(View.GONE);
                                ((ViewHolder) holder).itemView.measure(w, h);
                                ((ViewHolder) holder).mWideImage.measure(w, h);
                                int itemwidth = ((ViewHolder) holder).itemView.getWidth();
                                int imgheight = ((ViewHolder) holder).mWideImage.getMeasuredHeight();
                                double index = itemwidth / bitmapWidth;
                                imgheight = (int) (imgheight * index);
                                ViewGroup.LayoutParams lp = ((ViewHolder) holder).mWideImage.getLayoutParams();
                                lp.height = imgheight;
                                lp.width = itemwidth;
                                ((ViewHolder) holder).mWideImage.setLayoutParams(lp);
                            ((ViewHolder) holder).mWideImage.setOnClickListener(view -> {
                                view.setTag("images");
                                mOnItemClickListener.onItemClick(view,topic,position);
                            });
                            } else {
                                ((ViewHolder) holder).mWideImage.setVisibility(View.GONE);
                                ((ViewHolder) holder).mHeightImage.setVisibility(View.VISIBLE);
                                ((ViewHolder) holder).mHeightImage.setImageBitmap(resource);
                                ((ViewHolder) holder).mRecyclerView.setVisibility(View.GONE);
                                ((ViewHolder) holder).mHeightImage.measure(w,h);
                                int height=((ViewHolder) holder).mHeightImage.getMeasuredHeight();
                                int width=((ViewHolder) holder).mHeightImage.getMeasuredWidth();
                                int originWeight=((ViewHolder) holder).mHeightImage.getHeight();
                                int originWidth=((ViewHolder) holder).mHeightImage.getWidth();
                                double index=500.0/bitmapHeight;
                                width= (int) (width*index);
                                ViewGroup.LayoutParams lp = ((ViewHolder) holder).mHeightImage.getLayoutParams();
                                lp.height = 500;
                                lp.width = width;
                                ((ViewHolder) holder).mHeightImage.setLayoutParams(lp);
                                ((ViewHolder) holder).mHeightImage.setOnClickListener(view -> {
                                    view.setTag("images");
                                    mOnItemClickListener.onItemClick(view,topic,position);
                                });
                            }
                        }
                    }); //方法中设置asBitmap可以设置回调类型

                } else if (topic.getImages().size() > 1) {
                    ((ViewHolder) holder).mRecyclerView.setVisibility(View.VISIBLE);
                    ((ViewHolder) holder).mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
                    TopicGallaryAdapter mAdapter = new TopicGallaryAdapter(mContext, topic.getImages());
                     mAdapter.setOnItemClickListener(mOnItemClickListener);
                    ((ViewHolder) holder).mRecyclerView.setAdapter(mAdapter);
                    ((ViewHolder) holder).mWideImage.setVisibility(View.GONE);
                    ((ViewHolder) holder).mHeightImage.setVisibility(View.GONE);
                } else {
                    ((ViewHolder) holder).mRecyclerView.setVisibility(View.GONE);
                    ((ViewHolder) holder).mWideImage.setVisibility(View.GONE);
                    ((ViewHolder) holder).mHeightImage.setVisibility(View.GONE);
                }
                final int[] likeNum = {topic.getLikeNum()};
                ((ViewHolder) holder).mFavorText.setText(likeNum[0] + "");
                ((ViewHolder) holder).favorIcon.setOnClickListener(v -> {
                    if (!DataCenter.isSigned()) {
                        goLogin();
                    } else {
                        if (v.isSelected()) {
                            v.setSelected(false);
                            likeNum[0]--;
                            ((ViewHolder) holder).mFavorText.setText(likeNum[0] + "");
                        } else {
                            v.setSelected(true);
                            likeNum[0]++;
                            ((ViewHolder) holder).mFavorText.setText(likeNum[0] + "");
                        }
                        v.setTag("favor");
                        mOnItemClickListener.onItemClick(v, topic, position);
                    }
                });
                ((ViewHolder) holder).mCommentIcon.setOnClickListener(v -> {
                    v.setTag("comment");
                    mOnItemClickListener.onItemClick(v, topic, position);
                });
                ((ViewHolder) holder).mContentView.setOnClickListener(v -> {
                    v.setTag("comment");
                    mOnItemClickListener.onItemClick(v, topic, position);
                });
            }
        }
    }

    private void goLogin() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        ((MainActivity) mContext).startActivityForResult(intent, Constants.HOME_FLAG);
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE1;
    }

    @Override
    public int getItemCount() {
        if (mDatumList.size() != 0) {
            return mDatumList.size();
        }
        return 0;
    }

    @Override
    public void onExpand(MyExpandableTextView view) {
        Object obj = view.getTag();
        if (obj != null && obj instanceof Integer) {
            mPositionsAndStates.put((Integer) obj, view.getExpandState());
        }
    }

    @Override
    public void onShrink(MyExpandableTextView view) {
        Object obj = view.getTag();
        if (obj != null && obj instanceof Integer) {
            mPositionsAndStates.put((Integer) obj, view.getExpandState());
        }
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mNameText;
        TextView mTimeText;
        TextView mCommentText;
        TextView mFavorText;
        ImageView mHotTag;
        RecyclerView mRecyclerView;
        ImageView mAvatar;
        ImageView mWideImage;
        ImageView mHeightImage;
        MyExpandableTextView etv;
        ImageView favorIcon;
        LinearLayout mCommentIcon;
        LinearLayout mContentView;

        public ViewHolder(View itemView) {
            super(itemView);
            mNameText = (TextView) itemView.findViewById(R.id.tv_topic_username);
            mTimeText = (TextView) itemView.findViewById(R.id.tv_topic_time);
            mCommentText = (TextView) itemView.findViewById(R.id.tv_topic_comment);
            mFavorText = (TextView) itemView.findViewById(R.id.tv_topic_favor);
            mHotTag = (ImageView) itemView.findViewById(R.id.iv_topic_hottag);
            mAvatar = (ImageView) itemView.findViewById(R.id.iv_topic_user);
            mWideImage = (ImageView) itemView.findViewById(R.id.iv_topic_show_wide);
            mHeightImage = (ImageView) itemView.findViewById(R.id.iv_topic_show_height);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.rv_topic_show_gallary);
            etv = (MyExpandableTextView) itemView.findViewById(R.id.etv_topic_content);
            favorIcon = (ImageView) itemView.findViewById(R.id.iv_topic_favor);
            mCommentIcon = (LinearLayout) itemView.findViewById(R.id.ll_comment);
            mContentView = (LinearLayout) itemView.findViewById(R.id.ll_topic_item);
        }
    }
}
