package com.limi88.financialplanner.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limi88.financialplanner.pojo.home.Intro;
import com.limi88.financialplanner.R;

import java.util.List;

/**
 * Created by hehao on 16/9/9.
 */
public class IssuranceIntroAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Intro> introList;
    private Context context;

    public IssuranceIntroAdapter(List<Intro> introList, Context context) {
        this.introList = introList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.issurance_intro_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ((ViewHolder) holder).nameText.setText(introList.get(position).getName());
            ((ViewHolder) holder).valueText.setText(introList.get(position).getValue());
        }
    }

    @Override
    public int getItemCount() {
        return introList.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameText;
        TextView valueText;

        public ViewHolder(View itemView) {
            super(itemView);
            nameText= (TextView) itemView.findViewById(R.id.tv_issue_name);
            valueText= (TextView) itemView.findViewById(R.id.tv_issue_value);
        }
    }
}
