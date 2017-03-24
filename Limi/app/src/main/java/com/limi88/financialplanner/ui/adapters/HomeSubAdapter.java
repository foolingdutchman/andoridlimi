package com.limi88.financialplanner.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limi88.financialplanner.R;

import java.util.List;

/**
 * Created by hehao
 * Date on 16/12/6.
 * Email: hao.he@sunanzq.com
 */
public class HomeSubAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private String[] testdata={"11","22","33","44"};
    int TYPE1=1;
    private Context mContext;

    public HomeSubAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder=null;
        if (viewType==TYPE1) {
            holder = new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.home_sublist_item1, parent, false));
        }
       return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).mTextView.setText(testdata[position]);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE1;
    }

    @Override
    public int getItemCount() {
        return testdata.length;
    }

    class  ViewHolder extends  RecyclerView.ViewHolder{
        TextView mTextView;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView= (TextView) itemView.findViewById(R.id.tv_home_sub_title);
        }
    }
}
