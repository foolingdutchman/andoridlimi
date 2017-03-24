package com.limi88.financialplanner.ui.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limi88.financialplanner.R;
import com.limi88.financialplanner.util.Constants;

import java.util.ArrayList;

/**
 * Created by hehao on 16/9/13.
 */
public class SearchSelectionAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<String> data;
    private View.OnClickListener onClickListener;
    private ArrayList<View> mSelectionList=new ArrayList<View>();
    private Bundle bundle;
    private String selected;


    public SearchSelectionAdapter(Context context, ArrayList<String> data,String selected) {
        this.context = context;
        this.data = data;
        this.selected=selected;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.product_filter_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (data != null) {
            bundle=new Bundle();
            bundle.putInt(Constants.SEARCH_POSITION_FLAG, position);
            if (selected == null || selected.equals("")) {
                if (position == 0) {
                    ((Holder) holder).textView.setText(context.getString(R.string.search_condition_all));
                    ((Holder) holder).textView.setSelected(true);
                    ((Holder) holder).textView.setTag(bundle);

                } else {
                    ((Holder) holder).textView.setText(data.get(position - 1).trim());

                    ((Holder) holder).textView.setTag(bundle);
                }

            } else {
                if (position == 0) {
                    ((Holder) holder).textView.setText(context.getString(R.string.search_condition_all));

                    ((Holder) holder).textView.setTag(bundle);

                } else if (selected.equals(data.get(position - 1).trim())){
                    ((Holder) holder).textView.setText(data.get(position - 1).trim());
                    ((Holder) holder).textView.setSelected(true);
                    ((Holder) holder).textView.setTag(bundle);
                }
                else {
                    ((Holder) holder).textView.setText(data.get(position - 1).trim());

                    ((Holder) holder).textView.setTag(bundle);
                }
            }

            if (position != 0) {
                bundle.putString(Constants.SEARCH_CONDITION_FLAG, data.get(position - 1));
                bundle.putBoolean(Constants.SEARCH_ISSELECTED_FLAG, false);
            } else {
                bundle.putBoolean(Constants.SEARCH_ISSELECTED_FLAG, true);
                bundle.putString(Constants.SEARCH_CONDITION_FLAG, "全部选择");
            }

            mSelectionList.add( ((Holder) holder).textView);
        }

        ((Holder)holder).textView.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size() + 1;
        } else return 0;
    }

    public void setNotAllSelected() {
        if (mSelectionList.size()!=0) {

                mSelectionList.get(0).setSelected(false);

        }

    }
    public void setNoSelected() {
        if (mSelectionList.size()!=0) {
            for (int i = 0; i < mSelectionList.size(); i++) {
                mSelectionList.get(i).setSelected(false);
            }
        }

    }

    class Holder extends RecyclerView.ViewHolder {

        TextView textView;

        public Holder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_product_filter_item);
        }
        public void setSelected(boolean flag){
            textView.setSelected(flag);
        }
    }
}
