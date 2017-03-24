package com.limi88.financialplanner.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.limi88.financialplanner.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liushuai on 16/9/16.
 */
public class ProductSortAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;



    private CharSequence[] titles;
    private List<ImageView> iconList= new ArrayList<ImageView>();


    private OnItemClickListener onItemClickListener;

    public ProductSortAdapter(Context context, CharSequence[] titles) {
        this.context = context;
        this.titles = titles;
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public void setTitles(CharSequence[] titles) {
        this.titles = titles;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.product_sort_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (titles != null) {
            ((Holder) holder).mTextView.setText(titles[position]);
            if (position == 0)
                ((Holder) holder).mImageView.setVisibility(View.VISIBLE);
            ((Holder) holder).mTextView.setTag(titles[position]);
            iconList.add(((Holder) holder).mImageView);

                    ((Holder) holder).mTextView.setOnClickListener( v ->{
                onItemClickListener.OnItemClick(v,iconList.get(position),position);
            });
        }

    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public void setSelectedFlag(int position) {
        if (iconList.size()!=0) {
            for (int i = 0; i < iconList.size(); i++) {
                iconList.get(i).setVisibility(View.INVISIBLE);
            }
            iconList.get(position).setVisibility(View.VISIBLE);
        }

    }

    class Holder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView mImageView;

        public Holder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.tv_item1);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_check1);

        }
    }

    public interface OnItemClickListener{
        void OnItemClick(View v, Object obj, int position);
    }
}
