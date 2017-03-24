package com.limi88.financialplanner.ui.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.limi88.financialplanner.R;
import com.limi88.financialplanner.util.Constants;

/**
 * Created by Administrator on 2016-08-23.
 */
public class SectionItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private View.OnClickListener onClickListener;
    private View.OnLongClickListener onLongClickListener;

    private CharSequence[] mCalculatorTitles ;
    private int[] mCalculatorIcons={
            R.mipmap.ic_home_retired_plan,
            R.mipmap.ic_home_eduction_cal,
            R.mipmap.ic_home_customer_cal,
            R.mipmap.ic_home_reward_cal,
            R.mipmap.ic_home_mycard,

    };

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
    }




    public SectionItemAdapter( Context context) {
        this.context=context;
        mCalculatorTitles = context.getResources().getTextArray(R.array.home_calculators_array);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder3 holder = new ViewHolder3(LayoutInflater.from(context).inflate(R.layout.homelist_recommend_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder3) {
            ((ViewHolder3) holder).mTitle.setText(mCalculatorTitles[position]);
                    ((ViewHolder3) holder).mShot.setImageResource(mCalculatorIcons[position]);
            Bundle bundle=new Bundle();
            bundle.putString(Constants.WEB_CALCULATOR_TAG_TITLE,mCalculatorTitles[position].toString());
            bundle.putInt(Constants.WEB_CALCULATOR_TAG_ID,position);
            holder.itemView.setTag(bundle);
        }

        holder.itemView.setOnClickListener(onClickListener);
        holder.itemView.setOnLongClickListener(onLongClickListener);

    }

    @Override
    public int getItemCount() {

        return mCalculatorTitles.length;

    }

    public class ViewHolder3 extends RecyclerView.ViewHolder {
//        @Bind(R.id.iv_videoshot)
        ImageView mShot;
//        @Bind(R.id.tv_videotitle)
        TextView mTitle;



        public ViewHolder3(View itemView) {
            super(itemView);
//            ButterKnife.bind(itemView);

            mTitle= (TextView) itemView.findViewById(R.id.tv_recommend_title);
            mShot= (ImageView) itemView.findViewById(R.id.iv_recommend_icon);
        }
    }
}

