package com.limi88.financialplanner.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.limi88.financialplanner.R;

import java.util.ArrayList;

/**
 * Created by liushuai on 16/9/16.
 */
public class MyProductSortView extends LinearLayout {
    private LinearLayout.LayoutParams layoutParams;
    LayoutInflater layoutInflater;
    ArrayList<LinearLayout> itemList=new ArrayList<LinearLayout>();
    private Context context;
    private TextView mTextView;
    private ImageView mImageView;
    private OnItemClickeListenser onItemClickeListenser;
    private CharSequence[] titles;
    public MyProductSortView(Context context) {
        super(context);
    }

    public MyProductSortView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        this.setOrientation(VERTICAL);
        layoutInflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setTitles(CharSequence[] titles) {
        this.titles = titles;
        for (int i = 0; i < titles.length; i++) {
           LinearLayout itemView= new LinearLayout(context);
           layoutInflater.inflate(R.layout.product_sort_item,itemView);
            mTextView= (TextView) itemView.findViewById(R.id.tv_item1);
            mImageView= (ImageView) itemView.findViewById(R.id.iv_check1);
            mTextView.setText(titles[i]);
            final int position = i;
            final ImageView temp=mImageView;
            mTextView.setOnClickListener(v ->{ if (onItemClickeListenser!=null){
                onItemClickeListenser.OnItemClicked(v,temp, position);
            }});
            itemList.add(itemView);
            this.addView(itemView);

        }
    }

    public void setOnItemClickeListenser(OnItemClickeListenser onItemClickeListenser) {
        this.onItemClickeListenser = onItemClickeListenser;
    }

    interface OnItemClickeListenser{
        void OnItemClicked(View v, View flag, int positon);
    }

}
