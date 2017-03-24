package com.limi88.financialplanner.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.pojo.home.CommonTool;
import com.limi88.financialplanner.ui.widget.OnItemClickListener;
import com.limi88.financialplanner.util.Constants;

import java.util.List;

import cn.bingoogolapple.badgeview.BGABadgeFrameLayout;
import cn.bingoogolapple.badgeview.BGABadgeRelativeLayout;
import jp.wasabeef.glide.transformations.CropSquareTransformation;

/**
 * Created by hehao
 * Date on 16/10/18.
 * Email: hao.he@sunanzq.com
 */
public class ToolsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CommonTool> mToolsData;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private int[] imgRes = {
            R.mipmap.ic_home_tool_waiting,
            R.mipmap.ic_home_tool_waiting,
            R.mipmap.ic_home_tool_waiting,
            R.mipmap.ic_home_tool_waiting,
            R.mipmap.ic_home_tool_waiting,
            R.mipmap.ic_home_tool_waiting,
            R.mipmap.ic_home_tool_waiting,
    };
    private String[] titleList;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public ToolsAdapter(Context context, List<CommonTool> toolsData) {
        this.context = context;
        mToolsData = toolsData;
        titleList = context.getResources().getStringArray(R.array.tools_array);
    }

    public void setToolsData(List<CommonTool> toolsData) {
        mToolsData = toolsData;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_sub_item_tool, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            if (mToolsData != null && mToolsData.size() != 0) {
                if (position != getItemCount() - 1) {
                    CommonTool tool = mToolsData.get(position);
                    ((ViewHolder) holder).mTextView.setText(tool.getName());
                    ((ViewHolder) holder).mTextView.setSelected(true);
                    String status = mToolsData.get(position).getState();
                    if (status.equals("upgrade")) {
//                        ((ViewHolder) holder).mBadgeView.showTextBadge("升级");
                      ((ViewHolder) holder).mBadgeView.setImageResource(R.mipmap.ic_home_update);
                        ((ViewHolder) holder).mBadgeView.setPadding(0,5,10,0);
                    } else if (status.equals("init")) {
                        ((ViewHolder) holder).mBadgeView.setImageResource(R.mipmap.ic_home_new);
                        ((ViewHolder) holder).mBadgeView.setPadding(0,5,10,0);

                    } else if (status.equals("future")) {
                        ((ViewHolder) holder).mTextView.setSelected(false);
                    }
                    String icon = Constants.HOST + tool.getAvatar();
                    Glide.with(context).load(icon)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.mipmap.ic_home_tool_waiting)
//                        .crossFade()
//                        .bitmapTransform(new CropSquareTransformation(context))
                            .into(((ViewHolder) holder).mImageView);
                } else {
                    ((ViewHolder) holder).mTextView.setText("全部工具");

                    ((ViewHolder) holder).mTextView.setSelected(true);
                    String icon="https://image.limi88.com/assets/business_development/more_widgets_icon.png";
                    Glide.with(context).load(icon)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.mipmap.ic_home_tool_waiting)
//                        .crossFade()
//                        .bitmapTransform(new CropSquareTransformation(context))
                            .into(((ViewHolder) holder).mImageView);
//                    ((ViewHolder) holder).mImageView.setImageResource(R.mipmap.ic_home_all_tools);
                }

            } else {
                if (position < getItemCount() - 1) {
                    ((ViewHolder) holder).mImageView.setImageResource(imgRes[position]);
                    ((ViewHolder) holder).mTextView.setText(titleList[position]);
                } else {
                    ((ViewHolder) holder).mTextView.setText("全部工具");
                    ((ViewHolder) holder).mTextView.setSelected(true);
                    ((ViewHolder) holder).mImageView.setImageResource(R.mipmap.ic_home_all_tools);
                }


            }
//            else {
//                if (position < getItemCount() - 1) {
//                    ((ViewHolder) holder).mImageView.setImageResource(imgRes[position]);
//                    ((ViewHolder) holder).mTextView.setText(titleList[position]);
//                } else {
//                    ((ViewHolder) holder).mTextView.setText("全部工具");
//                    ((ViewHolder) holder).mTextView.setSelected(true);
//                   ((ViewHolder) holder).mImageView.setImageResource(R.mipmap.ic_home_all_tools);
//                }
//
//            }
            holder.itemView.setOnClickListener(v -> mOnItemClickListener.onItemClick(v, null, position));

        }
    }


    @Override
    public int getItemCount() {

        if (mToolsData != null && mToolsData.size() != 0) {
            return mToolsData.size() + 1;
        } else
            return 8;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView mImageView;
        ImageView mBadgeView;
//        BGABadgeRelativeLayout mBadgeView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_title);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_icon);
            mBadgeView = (ImageView) itemView.findViewById(R.id.iv_item_badge);
//            mBadgeView = (BGABadgeRelativeLayout) itemView.findViewById(R.id.badgeView);
        }
    }
}
