package com.limi88.financialplanner.ui.adapters;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.limi88.financialplanner.pojo.newyearopenning.NewYearOpenning;

/**
 * Created by hehao
 * Date on 16/12/7.
 * Email: hao.he@sunanzq.com
 */
public class OpenningAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private NewYearOpenning mOpenning;
    public OpenningAdapter(Context context, NewYearOpenning openning) {
        this.mContext=context;
        this.mOpenning=openning;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
