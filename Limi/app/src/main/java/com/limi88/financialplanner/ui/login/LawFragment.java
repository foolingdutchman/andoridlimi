package com.limi88.financialplanner.ui.login;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hehao.library.base.BaseLazyFragment;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.ui.base.FragmentBackHandler;

import butterknife.Bind;


/**
 * Created by hehao
 * Date on 16/10/19.
 * Email: hao.he@sunanzq.com
 */
public class LawFragment extends BaseLazyFragment implements FragmentBackHandler {


    @Bind(R.id.ll_left_btn)
    LinearLayout mLlLeftBtn;
    @Bind(R.id.common_left_action)
    ImageView mImageView;
    @Bind(R.id.tv_header_back)
    TextView mLeftTitle;
    @Bind(R.id.common_title)
    TextView mCommonTitle;

    /**
     * when fragment is visible for the first time, here we can do some initialized work or refresh data only once
     */
    @Override
    protected void onFirstUserVisible() {

    }

    /**
     * this method like the fragment's lifecycle method onResume()
     */
    @Override
    protected void onUserVisible() {

    }

    /**
     * this method like the fragment's lifecycle method onPause()
     */
    @Override
    protected void onUserInvisible() {

    }

    /**
     * get loading target view
     */
    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    /**
     * init all views and add events
     */
    @Override
    protected void initViewsAndEvents() {
        mLlLeftBtn.setVisibility(View.VISIBLE);
        mCommonTitle.setVisibility(View.GONE);
        mLeftTitle.setVisibility(View.VISIBLE);
        mLeftTitle.setText(getActivity().getString(R.string.mine_item_laws));
        mLlLeftBtn.setOnClickListener(v -> getSupportFragmentManager().beginTransaction().remove(this).commit());
    }

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_law;
    }

    /**
     * is bind eventBus
     *
     * @return
     */
    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }


    @Override
    public boolean onBackPressed() {
        this.getSupportFragmentManager().beginTransaction().remove(this).commit();
        return true;
    }
}
