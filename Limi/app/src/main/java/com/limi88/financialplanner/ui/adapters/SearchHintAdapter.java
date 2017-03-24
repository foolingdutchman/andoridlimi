package com.limi88.financialplanner.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.limi88.financialplanner.R;
import com.limi88.financialplanner.greendao.bean.Search;

import java.util.List;

/**
 * Created by liushuai on 16/9/16.
 */
public class SearchHintAdapter extends BaseAdapter {

    private Context context;

    private View.OnClickListener onClickListener;
    private List<Search> stringList;
    String[] aaa = {"1", "2", "2", "4", "5"};
    private int TOTAL_TYPE = 2;
    private int TYPE1 = 0;
    private int TYPE_FOOT = 1;

    public SearchHintAdapter(Context context, List<Search> stringList) {
        this.context = context;
        this.stringList = stringList;
    }

    public void setStringList(List<Search> stringList) {
        this.stringList = stringList;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        if (stringList.size() != 0) {
            return stringList.size() + 1;
        } else return 0;

    }

    @Override
    public Object getItem(int i) {
        if (i<stringList.size()) {
            return stringList.get(i);
        }
        else return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder ;
        if (stringList.size() != 0) {
            if (getItemViewType(i) == TYPE1) {

                if (view == null) {
                    holder = new ViewHolder();
                    view = LayoutInflater.from(context).inflate(R.layout.search_hint_item, viewGroup, false);
                    holder.textView = (TextView) view.findViewById(R.id.tv_history_item);
                    holder.deleteView = (RelativeLayout) view.findViewById(R.id.rl_delete);
                    view.setTag(holder);
                } else holder = (ViewHolder) view.getTag();
                holder.textView.setText(stringList.get(i).getSearchTitle());
                holder.deleteView.setTag(i);
                holder.deleteView.setOnClickListener(onClickListener);
            } else if (getItemViewType(i) == TYPE_FOOT) {

                view = LayoutInflater.from(context).inflate(R.layout.search_list_footer, viewGroup, false);
                holder = new ViewHolder();
                holder.textView = (TextView) view.findViewById(R.id.tv_history_footer);
                view.setTag(holder);
                holder = (ViewHolder) view.getTag();

            }
        }
        return view;
    }

    @Override
    public int getViewTypeCount() {
        return TOTAL_TYPE;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == stringList.size()) {
            return TYPE_FOOT;
        } else return TYPE1;
    }



    class ViewHolder {
        TextView textView;
        RelativeLayout deleteView;
    }
}
