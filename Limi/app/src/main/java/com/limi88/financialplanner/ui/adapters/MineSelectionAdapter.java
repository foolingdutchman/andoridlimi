package com.limi88.financialplanner.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.limi88.financialplanner.R;

/**
 * Created by hehao on 16/9/2.
 */
public class MineSelectionAdapter extends BaseAdapter {
    private Context context;
    private CharSequence[] titles;
    private int TYPE1 = 0;
    private int TYPE2 = 1;
    private int TOTAL_TYPE = 2;
    private int[] icons = {
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher,
            R.mipmap.ic_launcher
    };


    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MineSelectionAdapter(Context context) {
        this.context = context;
        titles = context.getResources().getTextArray(R.array.mine_selections_array);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return titles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return TOTAL_TYPE;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 2) {
            return TYPE2;
        } else return TYPE1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            if (getItemViewType(position) == TYPE1) {


                convertView = LayoutInflater.from(context).inflate(R.layout.minelist_item1, parent, false);
            } else {
                convertView = LayoutInflater.from(context).inflate(R.layout.minelist_item2, parent, false);
            }

            holder.mImageView = (ImageView) convertView.findViewById(R.id.iv_item_icon);
            holder.mTextView = (TextView) convertView.findViewById(R.id.tv_item_title);
            convertView.setTag(holder);
        } else holder = (ViewHolder) convertView.getTag();
        holder.mTextView.setText(titles[position]);
        holder.mImageView.setImageResource(icons[position]);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(v,position,titles[position]+"");
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView mTextView;
        ImageView mImageView;
    }

    public interface OnItemClickListener {
        void OnItemClick(View v, int position, Object o);
    }
}
