package com.limi88.financialplanner.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.pojo.user.User;
import com.limi88.financialplanner.ui.mine.MeProfileActivity;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.RequestHelper;
import com.limi88.financialplanner.util.ValidatorUtils;

import cn.bingoogolapple.badgeview.BGABadgeFrameLayout;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by hehao on 16/9/2.
 */
public class MineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private CharSequence[] titles;
    private int TYPE1 = 0;
    private int TYPE2 = 1;
    private int TYPE_HEAD = 2;
    private int TYPE_QUIT = 3;
    private int TOTAL_TYPE = 4;
    private ProgressBar mProgressBar;
    private TextView mTextView;
    private View itemView;
    private BGABadgeFrameLayout mFrameLayout;
    private int[] icons = {
            R.mipmap.ic_me_auth,
            R.mipmap.ic_me_topics_v2,
            R.mipmap.ic_me_agreement,
            R.mipmap.ic_me_feedback,
            R.mipmap.ic_me_version};
    private User mUser;
    private OnItemClickListener onItemClickListener;
    private boolean hasNews = true;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MineAdapter(Context context, User mUser) {
        this.context = context;
        this.mUser = mUser;
        titles = context.getResources().getTextArray(R.array.mine_selections_array);
//        hasNews=mUser.getData().getNewInfo();

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == TYPE1) {
            holder = new ViewHolder1(LayoutInflater.from(context).inflate(R.layout.minelist_item1, parent, false));

        } else if (viewType == TYPE2) {
            holder = new ViewHolder2(LayoutInflater.from(context).inflate(R.layout.minelist_item2, parent, false));
        } else if (viewType == TYPE_HEAD) {
            holder = new ViewHolderHead(LayoutInflater.from(context).inflate(R.layout.minelist_head, parent, false));
        } else if (viewType == TYPE_QUIT) {
            holder = new ViewHolderQuit(LayoutInflater.from(context).inflate(R.layout.minelist_quit, parent, false));
        }
        return holder;
    }

    public void setUser(User user) {
        mUser = user;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return TYPE_HEAD;
        } else if (position == 2||position==3) {
            return TYPE1;
        } else if (position == 6) {
            return TYPE_QUIT;
        } else return TYPE2;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder1) {

            ((ViewHolder1) holder).mImageView1.setImageResource(icons[position - 1]);
            ((ViewHolder1) holder).mTextView1.setText(titles[position - 1]);
            holder.itemView.setOnClickListener(v -> onItemClickListener.OnItemClick(v, position, titles[position - 1] + ""));

        } else if (holder instanceof ViewHolder2) {
            ((ViewHolder2) holder).mImageView1.setImageResource(icons[position - 1]);
            ((ViewHolder2) holder).mTextView1.setText(titles[position - 1]);
            if (mUser != null && mUser.getData() != null && mUser.isUserAuthenticated() != null) {
                if (position == 1) {
                    ((ViewHolder2) holder).mBottomLineView.setVisibility(View.INVISIBLE);
                    ((ViewHolder2) holder).mImageView3.setVisibility(View.GONE);
                    holder.itemView.setClickable(false);
                    if (mUser.isUserAuthenticated().equals("true")) {
                        ((ViewHolder2) holder).versionView.setText("已认证");
                        ((ViewHolder2) holder).versionView.setTextColor(context.getResources().getColor(R.color.color_blue100));
                        ((ViewHolder2) holder).mImageView2.setVisibility(View.VISIBLE);
                    } else if (mUser.isUserAuthenticated().equals("false")) {
                        ((ViewHolder2) holder).mImageView2.setVisibility(View.GONE);
                        ((ViewHolder2) holder).mImageView3.setVisibility(View.VISIBLE);
                        holder.itemView.setClickable(true);
                    } else {
                        ((ViewHolder2) holder).versionView.setText("审核中...");
                        ((ViewHolder2) holder).versionView.setTextColor(context.getResources().getColor(R.color.me_color_non_authed));
                        ((ViewHolder2) holder).mImageView2.setVisibility(View.GONE);
                    }
                } else if (position == 5) {
                    if (DataCenter.isDownLoadNewVersion()) {
                        ((ViewHolder2) holder).versionView.setVisibility(View.GONE);
                    }else
                    ((ViewHolder2) holder).versionView.setVisibility(View.VISIBLE);
                    ((ViewHolder2) holder).mImageView3.setVisibility(View.GONE);
                    if (!DataCenter.getAppVersion().equals(mUser.getData().getAndroidVersion().getVersion())) {
                        ((ViewHolder2) holder).mImageView1.setImageResource(R.mipmap.ic_me_new_version);
                        ((ViewHolder2) holder).versionView.setText("V" + mUser.getData().getAndroidVersion().getVersion());
                        ((ViewHolder2) holder).versionView.setBackgroundResource(R.drawable.shape_me_version);
                        ((ViewHolder2) holder).versionView.setPadding(6,0,6,0);
                                ((ViewHolder2) holder).versionView.setTextColor(context.getResources().getColor(R.color.color_white));
                        mProgressBar = ((ViewHolder2) holder).mProgressBar;
                        mTextView = ((ViewHolder2) holder).versionView;
                    } else {
                        mTextView = ((ViewHolder2) holder).versionView;
                        ((ViewHolder2) holder).versionView.setText("当前最新");
                        ((ViewHolder2) holder).versionView.setPadding(6, 0, 6, 0);
                        ((ViewHolder2) holder).versionView.setTextColor(context.getResources().getColor(R.color.me_color_non_authed));
                        ((ViewHolder2) holder).mFrameLayout2.hiddenBadge();
                        holder.itemView.setClickable(false);
                    }
                    itemView = holder.itemView;
                    mFrameLayout = ((ViewHolder2) holder).mFrameLayout2;
                }
            }
            holder.itemView.setOnClickListener(v -> onItemClickListener.OnItemClick(v, position, titles[position - 1] + ""));

        } else if (holder instanceof ViewHolderHead) {
            if (mUser != null && mUser.getData() != null && mUser.isUserAuthenticated() != null) {
                if (mUser.getData().getName() != null && mUser.getData().getName() != "") {
                    ((ViewHolderHead) holder).mNameView.setText(mUser.getData().getName());
                } else {
                    String phone = mUser.getData().getPhone();
                    if (phone != null && !phone.equals("") ) {
                        ((ViewHolderHead) holder).mNameView.setText(mUser.getData().getPhone());
                    }
                }
                if (mUser.isUserAuthenticated().equals("true")) {
                    ((ViewHolderHead) holder).mAuthView.setVisibility(View.VISIBLE);
                }else ((ViewHolderHead) holder).mAuthView.setVisibility(View.GONE);
                Glide.with(context)
                        .load(mUser.getData().getAvatarUrl())
                        .placeholder(R.mipmap.ic_me_logo)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .crossFade()
                        .bitmapTransform(new CropCircleTransformation(context))
                        .into(((ViewHolderHead) holder).mIconView);
                ((ViewHolderHead) holder).mIconView.setOnClickListener(v -> onItemClickListener.OnItemClick(v, position, Constants.UPDATE_USER_INFO_CODE));
                ((ViewHolderHead) holder).m2DcodeView.setOnClickListener(v -> onItemClickListener.OnItemClick(v, position, Constants.MINE_2DCODE_URL));
                ((ViewHolderHead) holder).mCardView.setOnClickListener(v -> onItemClickListener.OnItemClick(v, position, Constants.USER_PROFILE_URL));
                ((ViewHolderHead) holder).mFavorView.setOnClickListener(v -> onItemClickListener.OnItemClick(v, position, Constants.PRODUCTS_FAVOR_URL));
            }
        } else if (holder instanceof ViewHolderQuit) {
            ((ViewHolderQuit) holder).mLinearLayout.setOnClickListener(v -> onItemClickListener.OnItemClick(v, position, null));
        }


    }


    public interface OnItemClickListener {
        void OnItemClick(View v, int position, Object o);
    }

    @Override
    public int getItemCount() {
        return titles.length + 2;
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
        ImageView mImageView1;
        TextView mTextView1;
        BGABadgeFrameLayout mFrameLayout1;

        public ViewHolder1(View itemView) {
            super(itemView);
            mImageView1 = (ImageView) itemView.findViewById(R.id.iv_item_icon);
            mTextView1 = (TextView) itemView.findViewById(R.id.tv_item_title);
            mFrameLayout1 = (BGABadgeFrameLayout) itemView.findViewById(R.id.icon_badgeView);

        }
    }

    class ViewHolder2 extends RecyclerView.ViewHolder {

        BGABadgeFrameLayout mFrameLayout2;
        ImageView mImageView1;
        ImageView mImageView2;
        ImageView mImageView3;
        ImageView mBottomLineView;
        TextView mTextView2;
        TextView mTextView1;
        TextView versionView;
        ProgressBar mProgressBar;

        public ViewHolder2(View itemView) {
            super(itemView);

            mImageView1 = (ImageView) itemView.findViewById(R.id.iv_item_icon);
            mImageView2 = (ImageView) itemView.findViewById(R.id.iv_item_badge);
            mImageView3 = (ImageView) itemView.findViewById(R.id.iv_item_arrow);
            mBottomLineView = (ImageView) itemView.findViewById(R.id.iv_dev_bottom);
            mTextView1 = (TextView) itemView.findViewById(R.id.tv_item_title);
            mTextView2 = (TextView) itemView.findViewById(R.id.tv_item_status);
            versionView = (TextView) itemView.findViewById(R.id.tv_version);
            mFrameLayout2 = (BGABadgeFrameLayout) itemView.findViewById(R.id.icon_badgeView);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    public void showDownLoadVersion() {
        mProgressBar.setVisibility(View.VISIBLE);
        mTextView.setVisibility(View.GONE);
        itemView.setClickable(false);
    }

    public void showDownLoadFailed() {
        mProgressBar.setVisibility(View.GONE);
        mTextView.setVisibility(View.VISIBLE);
        mFrameLayout.hiddenBadge();
        mTextView.setText("网络错误");
        itemView.setClickable(true);
        mTextView.setPadding(6, 0, 6, 0);
    }

    public void showDownLoadSuccess() {
        mProgressBar.setVisibility(View.GONE);
        mTextView.setVisibility(View.VISIBLE);
        itemView.setClickable(true);
        mFrameLayout.hiddenBadge();
        mTextView.setText("下载完成");
        mTextView.setPadding(6,0,6,0);
    }

    public void showDownLoadPause() {
        mProgressBar.setVisibility(View.GONE);
        mTextView.setVisibility(View.VISIBLE);
        mTextView.setText("网络错误");
        mTextView.setPadding(6,0,6,0);
        itemView.setClickable(false);
        mFrameLayout.hiddenBadge();
    }

    class ViewHolderHead extends RecyclerView.ViewHolder {
        ImageView mIconView;
        ImageView m2DcodeView;
        ImageView mAuthView;
        TextView mNameView;
        LinearLayout mCardView;
        LinearLayout mFavorView;

        public ViewHolderHead(View itemView) {
            super(itemView);
            mCardView = (LinearLayout) itemView.findViewById(R.id.ll_card_btn);
            mFavorView = (LinearLayout) itemView.findViewById(R.id.ll_favor_btn);
            mIconView = (ImageView) itemView.findViewById(R.id.iv_me_icon);
            m2DcodeView = (ImageView) itemView.findViewById(R.id.iv_me_2Dcode);
            mNameView = (TextView) itemView.findViewById(R.id.tv_me_name);
            mAuthView= (ImageView) itemView.findViewById(R.id.iv_logo_auth);
        }
    }

    class ViewHolderQuit extends RecyclerView.ViewHolder {
        LinearLayout mLinearLayout;

        public ViewHolderQuit(View itemView) {
            super(itemView);
            mLinearLayout = (LinearLayout) itemView.findViewById(R.id.ll_logout);
        }
    }
}
