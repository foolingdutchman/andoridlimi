package com.limi88.financialplanner.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.pojo.home.CommonTool;
import com.limi88.financialplanner.ui.widget.OnItemClickListener;
import com.limi88.financialplanner.util.Constants;

import java.util.List;

import cn.bingoogolapple.badgeview.BGABadgeFrameLayout;

/**
 * Created by hehao
 * Date on 16/10/28.
 * Email: hao.he@sunanzq.com
 */
public class ServiceToolAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

    public ServiceToolAdapter(Context context, List<CommonTool> toolsData) {
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

                    CommonTool tool = mToolsData.get(position);
                    ((ViewHolder) holder).mTextView.setText(tool.getName());
                ((ViewHolder) holder).mTextView.setSelected(true);
                    String status = mToolsData.get(position).getState();
                if (status.equals("upgrade")) {
//                    ((ViewHolder) holder).mBadgeView.showTextBadge("升级");
                    ((ViewHolder) holder).mBadgeView.setImageResource(R.mipmap.ic_home_update);
                    ((ViewHolder) holder).mBadgeView.setPadding(0,5,10,0);
                } else if (status.equals("new")) {
//                    ((ViewHolder) holder).mBadgeView.showTextBadge("新");
                    ((ViewHolder) holder).mBadgeView.setImageResource(R.mipmap.ic_home_new);
                    ((ViewHolder) holder).mBadgeView.setPadding(0,5,10,0);
                } else if (status.equals("future")) {
                    ((ViewHolder) holder).mTextView.setSelected(false);
                }
                String icon = Constants.HOST + tool.getAvatar();
                    Glide.with(context).load(icon)
                            .placeholder(R.mipmap.ic_home_tool_waiting)
//                        .crossFade()
//                        .bitmapTransform(new CropCircleTransformation(context))
                            .into(((ViewHolder) holder).mImageView);

            }
            holder.itemView.setOnClickListener(v -> mOnItemClickListener.onItemClick(v, null, position));

        }
    }


    @Override
    public int getItemCount() {

        if (mToolsData != null && mToolsData.size() != 0) {
            return mToolsData.size();
        } else
            return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView mImageView;
        ImageView mBadgeView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_title);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_icon);
            mBadgeView = (ImageView) itemView.findViewById(R.id.iv_item_badge);
        }
    }
}
