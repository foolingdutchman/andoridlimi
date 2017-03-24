package com.limi88.financialplanner.ui.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by hehao
 * Date on 16/12/6.
 * Email: hao.he@sunanzq.com
 */
public class MyLinearLayoutManager extends LinearLayoutManager {

    private RecyclerView.Recycler mRecycler;
    private boolean isScrollEnabled=true;
    public MyLinearLayoutManager(Context context) {
        super(context);
    }

    public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);

    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);
        mRecycler = recycler;
    }

    public boolean isgetTop(int position){
        int firstVisibleItemPosition = findFirstVisibleItemPosition();
//        Log.i("home","position:---"+position+"---firstVisibleItemPosition:----"+firstVisibleItemPosition);
        if (position==firstVisibleItemPosition) {
            return true;
        }else return false;
    }
    public boolean isLastVisibleItem(int position){
        int lastVisibleItemPosition = findLastVisibleItemPosition();
//        Log.i("home","position:---"+position+"---firstVisibleItemPosition:----"+firstVisibleItemPosition);
        if (position==lastVisibleItemPosition) {
            return true;
        }else return false;
    }

    public int getScrollY() {
        int scrollY = getPaddingTop();
        int firstVisibleItemPosition = findFirstVisibleItemPosition();

        if (firstVisibleItemPosition >= 0 && firstVisibleItemPosition < getItemCount()) {
            for (int i = 0; i < firstVisibleItemPosition; i++) {
                View view = mRecycler.getViewForPosition(i);
                if (view == null) {
                    continue;
                }
                if (view.getMeasuredHeight() <= 0) {
                    measureChildWithMargins(view, 0, 0);
                }
                RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) view.getLayoutParams();
                scrollY += lp.topMargin;
                scrollY += getDecoratedMeasuredHeight(view);
                scrollY += lp.bottomMargin;
                mRecycler.recycleView(view);
            }

            View firstVisibleItem = findViewByPosition(firstVisibleItemPosition);
            RecyclerView.LayoutParams firstVisibleItemLayoutParams = (RecyclerView.LayoutParams) firstVisibleItem.getLayoutParams();
            scrollY += firstVisibleItemLayoutParams.topMargin;
            scrollY -= getDecoratedTop(firstVisibleItem);
        }

        return scrollY;
    }

    public void setScrollEnabled(boolean scrollEnabled) {
        isScrollEnabled = scrollEnabled;
    }

    @Override
    public boolean canScrollVertically() {
        return isScrollEnabled&&super.canScrollVertically();
    }
}