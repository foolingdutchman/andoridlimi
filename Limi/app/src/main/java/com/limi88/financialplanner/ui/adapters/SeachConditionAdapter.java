package com.limi88.financialplanner.ui.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.limi88.financialplanner.R;
import com.limi88.financialplanner.pojo.searchcondition.SearchCondition;
import com.limi88.financialplanner.util.ConditionUtils;
import com.limi88.financialplanner.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hehao on 16/9/13.
 */
public class SeachConditionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private SearchCondition searchCondition;
    private ArrayList<ArrayList<String>> data;
    private int TYPE_1 = 1;
    private int TYPE_2 = 2;
    private int TYPE_FOOTER = 3;
    private View.OnClickListener onClickListener;
    private CharSequence[] titles;

    private HashMap<String ,String>selections;
    private ItemClickListener itemClickListener;


    public SeachConditionAdapter(Context context, SearchCondition searchCondition,HashMap<String ,String>selections) {
        this.context = context;
        this.searchCondition = searchCondition;
        this.selections=selections;

        data = new ArrayList<>();
        if (searchCondition != null) {

            for (int i = 0; i < searchCondition.getData().getKeys().size(); i++) {
                String key = searchCondition.getData().getKeys().get(i);
                data.add((ArrayList<String>) searchCondition.getData().getDatalist().get(key));
            }
        }


    }

    public void setSearchCondition(SearchCondition searchCondition) {
        this.searchCondition = searchCondition;
        if (searchCondition != null) {
            data.clear();
            for (int i = 0; i < searchCondition.getData().getKeys().size(); i++) {
                String key = searchCondition.getData().getKeys().get(i);
                data.add((ArrayList<String>) searchCondition.getData().getDatalist().get(key));
            }
        }
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == TYPE_1) {
            holder = new TitleHolder(LayoutInflater.from(context).inflate(R.layout.homelist_hot_title, parent, false));
        } else if (viewType == TYPE_2) {
            holder = new ContentHolder(LayoutInflater.from(context).inflate(R.layout.product_search_item, parent, false));
        } else if (viewType == TYPE_FOOTER) {
            holder = new FooterHolder(LayoutInflater.from(context).inflate(R.layout.common_footer, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (data != null && position < getItemCount() - 1) {
            String key = searchCondition.getData().getKeys().get(position / 2);
            final String[] condition = {""};
            if (getItemViewType(position) == TYPE_1) {
                ((TitleHolder) holder).textView.setText(ConditionUtils.transForm(key));
            } else if (getItemViewType(position) == TYPE_2) {
                ((ContentHolder) holder).mRecyclerView.setLayoutManager(new GridLayoutManager(context, 3));
                SearchSelectionAdapter adapter;
                if (key!=null) {
                    adapter = new SearchSelectionAdapter(context, data.get(position / 2),selections.get(key));

                }else {
                    adapter = new SearchSelectionAdapter(context, data.get(position / 2), null);
                }
                ((ContentHolder) holder).mRecyclerView.setAdapter(adapter);
                adapter.setOnClickListener(view -> {
                    Bundle bundle = (Bundle) view.getTag();
                    if (bundle.getInt(Constants.SEARCH_POSITION_FLAG) == 0) {
                        adapter.setNoSelected();
                        view.setSelected(true);
                        selections.remove(key);
                    } else {
                        adapter.setNotAllSelected();
                        if (!bundle.getBoolean(Constants.SEARCH_ISSELECTED_FLAG)) {
                            view.setSelected(true);
                            bundle.putBoolean(Constants.SEARCH_ISSELECTED_FLAG, true);
                            view.setTag(bundle);
                            condition[0] += "," + bundle.getString(Constants.SEARCH_CONDITION_FLAG);
                        } else if (bundle.getBoolean(Constants.SEARCH_ISSELECTED_FLAG)&& condition[0].lastIndexOf(",")!=0) {
                            view.setSelected(false);
                            bundle.putBoolean(Constants.SEARCH_ISSELECTED_FLAG, false);
                            view.setSelected(false);
                            view.setTag(bundle);
                            condition[0].replace("," + bundle.getString(Constants.SEARCH_CONDITION_FLAG), "");
                        }
                        bundle.putString(Constants.SEARCH_KEY_FLAG,key);
                        itemClickListener.OnItemClick(view,bundle,position);
                    }
                });
            }

        }

    }



    @Override
    public int getItemCount() {
        if (data.size() != 0) {
            return 2 * (data.size()) + 1;
        } else return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else if (position % 2 == 0) {
            return TYPE_1;
        } else return TYPE_2;
    }

    class TitleHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public TitleHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_item_cat);
        }
    }

    class ContentHolder extends RecyclerView.ViewHolder {
        RecyclerView mRecyclerView;

        public ContentHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.rv_product_filtter_item);
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder {

        public FooterHolder(View itemView) {
            super(itemView);
        }
    }

    public interface ItemClickListener{
        void OnItemClick(View v,Object obj,int position);

    }
}
