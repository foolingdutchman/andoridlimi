package com.limi88.financialplanner.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.limi88.financialplanner.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bingoogolapple.badgeview.BGABadgeFrameLayout;


/**
 * Created by Administrator on 2016/7/15.
 */

public class BaseBottomTab extends RecyclerView {
    Integer[] tabIcons;
    final TypedArray titles = getResources().obtainTypedArray(R.array.home_tab_array);
    private boolean[] itemselected = {true, false, false, false};
    private Context context;
    private MyRecyclerAdapter adapter;
    private OnItemClickListener mOnItemClickListener;
    private List<BGABadgeFrameLayout> mBadgeList= new ArrayList<>();
    private boolean isShow=false;
    private int selectPosition=-1;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }


    public void setRes(Integer[] tabIcons) {
        this.tabIcons = tabIcons;
        adapter.notifyDataSetChanged();
    }

    public BaseBottomTab(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
    }

    public BaseBottomTab(Context context) {
        super(context);
        this.context = context;
    }

    public BaseBottomTab(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }
    public void showBadge(int position,boolean isShow) {
        this.selectPosition=position;
        this.isShow=isShow;
        if (mBadgeList.size()!=0) {
            if (isShow) {
                mBadgeList.get(position).showCirclePointBadge();
                mBadgeList.get(position).setPadding(0,0,4,0);
            } else mBadgeList.get(position).hiddenBadge();
        }
    }

    class MyRecyclerAdapter extends Adapter<MyRecyclerAdapter.ViewHolder> {

        ViewHolder holder;
        public OnClickListener onClickListener;
        private OnLongClickListener onLongClickListener;


        public void setOnClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;

        }


        public void itemSelected(int position) {
            for (int i = 0; i < itemselected.length; i++) {
                itemselected[i] = false;
            }
            itemselected[position] = true;
            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_tab_icon, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            holder.title.setText(titles.getText(position % tabIcons.length));
            holder.icon.setBackgroundResource(tabIcons[position]);
            mBadgeList.add(position, holder.mBadgeLayout);
            holder.title.setSelected(itemselected[position]);
            holder.icon.setSelected(itemselected[position]);
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(v -> mOnItemClickListener.onItemClick(v, null, position));
            if (isShow&&selectPosition==position) {
                holder.mBadgeLayout.showCirclePointBadge();
                holder.mBadgeLayout.setPadding(0,0,4,0);
            }
        }

        @Override
        public int getItemCount() {
            int count = 0;
            if (titles != null) {
                count = tabIcons.length;
            }
            return count;
        }

        /**
         * RecyclerAdapter's ViewHolder.
         */
        class ViewHolder extends RecyclerView.ViewHolder {
            boolean isSelected = false;
            @Bind(R.id.tv_title)
            TextView title;
            @Bind(R.id.iv_icon)
            ImageView icon;
            @Bind(R.id.badgeView)
            BGABadgeFrameLayout mBadgeLayout;
            /**
             * ViewHolder.
             *
             * @param view view
             */
            public ViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

    }

    public void initTab() {
        this.setLayoutManager(new GridLayoutManager(context, 4));
        adapter = new MyRecyclerAdapter();
        this.setAdapter(adapter);
        this.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_MOVE ||
                    event.getAction() == MotionEvent.ACTION_SCROLL) {
                return true;
            } else return false;
        }
        );

    }

    @Override
    public MyRecyclerAdapter getAdapter() {
        return adapter;
    }


    public void itemSelected(int position) {
        adapter.itemSelected(position);
    }

}
