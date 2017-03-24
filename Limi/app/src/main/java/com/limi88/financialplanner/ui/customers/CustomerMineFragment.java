package com.limi88.financialplanner.ui.customers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.hehao.library.base.BaseLazyFragment;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.pojo.clients.Real;
import com.limi88.financialplanner.pojo.clients.SearchConditions;
import com.limi88.financialplanner.ui.adapters.MyClientAdapter;
import com.limi88.financialplanner.ui.base.BaseWebViewActivity;


import com.limi88.financialplanner.ui.widget.BaseBottomTab;
import com.limi88.financialplanner.ui.widget.LimiFooterView;
import com.limi88.financialplanner.ui.widget.LimiHeaderLayout;
import com.limi88.financialplanner.ui.widget.MyFloatLayoutView;
import com.limi88.financialplanner.ui.widget.OnItemClickListener;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.ToastUtils;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by hehao
 * Date on 16/9/20.
 * Email: hao.he@limi88.com
 */
public class CustomerMineFragment extends BaseLazyFragment implements View.OnClickListener {
    @Bind(R.id.iv_filter)
    ImageView mFilterView;
    @Bind(R.id.iv_add)
    ImageView mAddView;
    @Bind(R.id.prv_customers)
    PullToRefreshRecyclerView mRefreshRecyclerView;

    @Bind(R.id.et_search_input)
    EditText mSearchInput;
    @Bind(R.id.ll_search_bg)
    LinearLayout mSearchBg;
    @Bind(R.id.iv_hint_no_client)
    ImageView mIvNoClinent;
    private PopupWindow popupWindow;
    private View contentView;
    RadioButton button1;
    RadioButton button2;
    RadioButton button3;
    RadioGroup group;
    private FrameLayout mFrameLayout1;
    private FrameLayout mFrameLayout2;
    private ScrollView mScrollView;
    private MyFloatLayoutView myFloatLayoutView;
    private String isSigned = "";
    private String catogary = "";
    private List<String> mTagList = new ArrayList<>();
    private Bundle bundle;
    private List<Real> mReals = new ArrayList<>();
    private List<Real> filterdmReals = new ArrayList<>();
    private List<Real> searChedReals = new ArrayList<>();
    private SearchConditions searchConditions;
    private ClientContract.ClientView mClientView;
    private MyClientAdapter myClientAdapter;
    private boolean isTagLoaded = false;
    private OnItemClickListener onItemClickListener;
    private RecyclerView mRecycleView;
    private ClientPresenter mPresenter;
    private int lastY;


    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        mAddView.setOnClickListener(this);
        mFilterView.setOnClickListener(this);
        mFilterView.setSelected(false);
        mAddView.setOnClickListener(this);
        myClientAdapter = new MyClientAdapter(getActivity(), mReals);
        mRecycleView=  mRefreshRecyclerView.getRefreshableView();
        mRecycleView.setId(R.id.rv_customers_mine);
        mRefreshRecyclerView.setHasTransientState(false); // 设置没有上拉阻力
        mRefreshRecyclerView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mRefreshRecyclerView.setHeaderLayout(new LimiHeaderLayout(getActivity()));
        mRefreshRecyclerView.setFooterLayout(new LimiFooterView(getActivity()));
        mRefreshRecyclerView.setPullToRefreshEnabled(false);
//        mRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {
//
//            @Override
//            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
//                if (mClientView!=null) {
//                    mClientView.loadDataToUI();
//                }
//            }
//
//            @Override
//            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
////                if (mClientView!=null) {
////                    mClientView.loadDataToUI();
////                }
//                return;
////                page++;
////                productView.attachData(page);
////                mRecycleView.scrollToPosition(itemNum);
//            }
//        });

        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecycleView.setAdapter(myClientAdapter);
        onItemClickListener = (v, obj, position) -> {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putParcelable(Constants.MODIFY_CUSTOMER_FLAG,mReals.get(position));
            bundle.putInt(Constants.WEB_PAGE_TAG, Constants.CUSTOMER_DETAIL_TAG);
            bundle.putString(Constants.WEB_PAGE_DETAIL_NAME, "客户");

            Log.i("customer", Constants.CLIENTS_URL + "/" + mReals.get(position).getId());
            bundle.putString(Constants.WEB_PAGE_DETAIL_LINK, Constants.CLIENTS_URL
                    + "/" + mReals.get(position).getId());
            Constants.CHOOSE_CUSTOMER=mReals.get(position);
            Intent mIntent = new Intent(CustomerMineFragment.this.getActivity(), BaseWebViewActivity.class);
            mIntent.putExtras(bundle);
            getActivity().startActivityForResult(mIntent, Constants.CLIENTS_FLAG);
        };
        myClientAdapter.setOnItemClickListener(onItemClickListener);
        mRecycleView.setOnTouchListener((v, event) -> {
            mRecycleView.setFocusable(true);
            mRecycleView.setFocusableInTouchMode(true);
            mRecycleView.requestFocus();
            return  false;
        });
        mSearchInput.setOnFocusChangeListener((v1, hasFocus) -> {
            if (hasFocus) {
                mSearchBg.setVisibility(View.INVISIBLE);
            } else if (mSearchInput.getText().toString().equals("")) {
                mSearchBg.setVisibility(View.VISIBLE);
                hideKeyboard();
            }


        });
        mSearchInput.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //隐藏软键盘
                hideKeyboard();
                //添加搜索
                startSearching(mSearchInput.getText().toString());
//                mSearchInput.setText("");
            }
            return true;
        });
//        mRefreshLayout.setOnRefreshListener(() -> mClientView.loadDataToUI());
    }

    private void hideKeyboard() {
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }

    private void startSearching(String s) {
        searChedReals.clear();
        if (filterdmReals.size() != 0) {
            for (int i = 0; i < filterdmReals.size(); i++) {
                if (filterdmReals.get(i).getName() != null ? filterdmReals.get(i).getName().contains(s) : false ||
                        filterdmReals.get(i).getLevel() != null ? filterdmReals.get(i).getLevel().contains(s): false ||
                        filterdmReals.get(i).getTags() != null ? filterdmReals.get(i).getTags().contains(s) : false ||
                        filterdmReals.get(i).getStatus() != null ? filterdmReals.get(i).getStatus().contains(s) :false ||
                        filterdmReals.get(i).getPhone() != null ? filterdmReals.get(i).getPhone().contains(s) :false) {
                    searChedReals.add(filterdmReals.get(i));
                }
            }
        } else if (mReals.size() != 0) {
            for (int i = 0; i < mReals.size(); i++) {
                if (mReals.get(i).getName() != null ? mReals.get(i).getName().contains(s) : false ||
                        mReals.get(i).getLevel() != null ? mReals.get(i).getLevel().contains(s) : false ||
                        mReals.get(i).getTags() != null ? mReals.get(i).getTags().contains(s) : false ||
                        mReals.get(i).getStatus() != null ? mReals.get(i).getStatus().contains(s) : false ||
                        mReals.get(i).getPhone() != null ? mReals.get(i).getPhone().contains(s) : false) {
                    searChedReals.add(mReals.get(i));
                }
            }
        }
        myClientAdapter.setmRealClients(searChedReals);
        myClientAdapter.notifyDataSetChanged();
    }

    public void setmClientView(ClientContract.ClientView mClientView) {
        this.mClientView = mClientView;
    }

    public void finishRefresh() {
        if (mRefreshRecyclerView!=null) {
            mRefreshRecyclerView.onRefreshComplete();
        }

    }

    public void setmReals(List<Real> mReals) {
        this.mReals = mReals;
        myClientAdapter = new MyClientAdapter(getActivity(), mReals);
        myClientAdapter.setOnItemClickListener(onItemClickListener);
        mRecycleView.setAdapter(myClientAdapter);
        if (mReals.size() == 0) {
            mIvNoClinent.setVisibility(View.VISIBLE);
        } else {
            mIvNoClinent.setVisibility(View.INVISIBLE);
        }
//        myClientAdapter.notifyDataSetChanged();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_customer_myclient;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add:
                    this.startActivityForResult(new Intent(getActivity(),NewCustomersActivity.class),Constants.NEW_CLIENTS_CODE);
//                addCustomerFragment = new AddCustomerFragment();
//                addCustomerFragment.setmPresenter(mPresenter);
//                addCustomerFragment.setSearchConditions(searchConditions);
//                getActivity().getSupportFragmentManager().beginTransaction()
//                        .replace(R.id.main_container, addCustomerFragment, Constants.NEW_CLIENTS_TAG).commit();
//                addCustomerFragment.setmClientView(mClientView);
                break;
            case R.id.iv_filter:
                showPopUpWindow(v);
                break;
            case R.id.tv_cancel:
                popupWindow.dismiss();
                break;
            case R.id.tv_clear:
                isSigned = "";
                catogary = "";
                mTagList.clear();
                mFilterView.setSelected(false);
                doResetList();
                popupWindow.dismiss();
                initPopUpWindow();
                break;
            case R.id.tv_confirm:
                doFilterClients();
//                mFilterView.setSelected(true);
                popupWindow.dismiss();
                break;

        }
    }

    private void doResetList() {
        filterdmReals = mReals;
        myClientAdapter.setmRealClients(filterdmReals);
        myClientAdapter.notifyDataSetChanged();
    }

    private void doFilterClients() {
        filterdmReals=null;
        filterdmReals=new ArrayList<>();
        if (catogary.equals("") && isSigned.equals("") && mTagList.size() == 0) {
            mFilterView.setSelected(false);
            filterdmReals = mReals;
        } else {
            for (int i = 0; i < mReals.size(); i++) {
                if (!catogary.equals("") && isSigned.equals("") && mTagList.size() == 0) {
                    if (mReals.get(i).getLevel().equals(catogary)) {
                        filterdmReals.add(mReals.get(i));
                    }
                } else if (catogary.equals("") && !isSigned.equals("") && mTagList.size() == 0) {
                    if (mReals.get(i).getStatus().equals(isSigned)) {
                        filterdmReals.add(mReals.get(i));
                    }
                } else if (catogary.equals("") && isSigned.equals("") && mTagList.size() != 0) {
                    for (int j = 0; j < mTagList.size(); j++) {
                        if (mReals.get(i).getTags().contains(mTagList.get(j))) {
                            filterdmReals.add(mReals.get(i));
                        }
                    }
                } else if (!catogary.equals("") && !isSigned.equals("") && mTagList.size() == 0) {
                    if (mReals.get(i).getStatus().equals(isSigned) && mReals.get(i).getLevel().equals(catogary)) {
                        filterdmReals.add(mReals.get(i));
                    }
                } else if (!catogary.equals("") && isSigned.equals("") && mTagList.size() != 0) {
                    if (mReals.get(i).getLevel().equals(catogary)) {
                        for (int j = 0; j < mTagList.size(); j++) {
                            if (mReals.get(i).getTags().contains(mTagList.get(j))) {
                                filterdmReals.add(mReals.get(i));
                            }
                        }
                    }
                } else if (catogary.equals("") && !isSigned.equals("") && mTagList.size() != 0) {
                    if (mReals.get(i).getStatus().equals(isSigned)) {
                        for (int j = 0; j < mTagList.size(); j++) {
                            if (mReals.get(i).getTags().contains(mTagList.get(j))) {
                                filterdmReals.add(mReals.get(i));
                            }
                        }
                    }

                } else if (!catogary.equals("") && !isSigned.equals("") && mTagList.size() != 0) {
                    if (mReals.get(i).getStatus().equals(isSigned) && mReals.get(i).getLevel().equals(catogary)) {
                        for (int j = 0; j < mTagList.size(); j++) {
                            if (mReals.get(i).getTags().contains(mTagList.get(j))) {
                                filterdmReals.add(mReals.get(i));
                            }
                        }
                    }
                }
            }
            mFilterView.setSelected(true);
        }

        myClientAdapter.setmRealClients(filterdmReals);
        myClientAdapter.notifyDataSetChanged();
    }

    private void showPopUpWindow(View v) {

        if (popupWindow == null) {
            initPopUpWindow();
        }
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("mengdd", "onTouch : ");
                return false;
            }
        });
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.alpha(0)));
        backgroundAlpha(0.8f);
        // 设置好参数之后再show
        popupWindow.showAsDropDown(v);

    }

    private void initPopUpWindow() {

        // 一个自定义的布局，作为显示的内容
        contentView = LayoutInflater.from(getActivity()).inflate(
                R.layout.popup_myclient, null);
        TextView mSelection1 = (TextView) contentView.findViewById(R.id.tv_selection1);
        TextView mSelection2 = (TextView) contentView.findViewById(R.id.tv_selection2);
        TextView mSelection3 = (TextView) contentView.findViewById(R.id.tv_selection3);
        mSelection1.setSelected(true);
        mSelection2.setSelected(false);
        mSelection3.setSelected(false);
        TextView mCancelView = (TextView) contentView.findViewById(R.id.tv_cancel);
        TextView mClearView = (TextView) contentView.findViewById(R.id.tv_clear);
        TextView mConfirmView = (TextView) contentView.findViewById(R.id.tv_confirm);
        mCancelView.setOnClickListener(this);
        mClearView.setOnClickListener(this);
        mConfirmView.setOnClickListener(this);
        mFrameLayout1 = (FrameLayout) contentView.findViewById(R.id.frame1);
        mFrameLayout2 = (FrameLayout) contentView.findViewById(R.id.frame2);
        myFloatLayoutView = (MyFloatLayoutView) contentView.findViewById(R.id.mfv_tags);
        mScrollView= (ScrollView) contentView.findViewById(R.id.scv_tag);
        button1 = (RadioButton) mFrameLayout1.findViewById(R.id.rb_s1_item1);
        button2 = (RadioButton) mFrameLayout1.findViewById(R.id.rb_s1_item2);
        button3 = (RadioButton) mFrameLayout1.findViewById(R.id.rb_s1_item3);
        group = (RadioGroup) mFrameLayout1.findViewById(R.id.rg_s1);

        group.setOnCheckedChangeListener((group1, checkedId) -> {
            switch (group1.getCheckedRadioButtonId()) {
                case R.id.rb_s1_item1:
                    isSigned = "";
                    break;
                case R.id.rb_s1_item2:
                    isSigned = button2.getText().toString();
                    break;
                case R.id.rb_s1_item3:
                    isSigned = button3.getText().toString();
                    break;

            }
            ToastUtils.showToastForTest("issigned:" + isSigned);

        });
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        //选择是否签单
        mSelection1.setOnClickListener(v -> {
            mFrameLayout1.setVisibility(View.VISIBLE);
            mFrameLayout2.setVisibility(View.GONE);
            mScrollView.setVisibility(View.GONE);
            mSelection1.setSelected(true);
            mSelection2.setSelected(false);
            mSelection3.setSelected(false);
            button1 = (RadioButton) mFrameLayout1.findViewById(R.id.rb_s1_item1);
            button2 = (RadioButton) mFrameLayout1.findViewById(R.id.rb_s1_item2);
            button3 = (RadioButton) mFrameLayout1.findViewById(R.id.rb_s1_item3);
            group = (RadioGroup) mFrameLayout1.findViewById(R.id.rg_s1);

            group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (group.getCheckedRadioButtonId()) {
                        case R.id.rb_s1_item1:
                            isSigned = "";
                            break;
                        case R.id.rb_s1_item2:
                            isSigned = button2.getText().toString();
                            break;
                        case R.id.rb_s1_item3:
                            isSigned = button3.getText().toString();
                            break;

                    }
                    ToastUtils.showToastForTest("issigned:" + isSigned);

                }
            });

        });
        //选择类别
        mSelection2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFrameLayout1.setVisibility(View.GONE);
                mFrameLayout2.setVisibility(View.VISIBLE);
                mScrollView.setVisibility(View.GONE);
                mSelection1.setSelected(false);
                mSelection2.setSelected(true);
                mSelection3.setSelected(false);
                button1 = (RadioButton) mFrameLayout2.findViewById(R.id.rb_s2_item1);
                button2 = (RadioButton) mFrameLayout2.findViewById(R.id.rb_s2_item2);
                button3 = (RadioButton) mFrameLayout2.findViewById(R.id.rb_s2_item3);

                group = (RadioGroup) mFrameLayout2.findViewById(R.id.rg_s2);
                group.setOnCheckedChangeListener((group1, checkedId) -> {
                    switch (group1.getCheckedRadioButtonId()) {
                        case R.id.rb_s2_item1:
                            catogary = "";
                            break;
                        case R.id.rb_s2_item2:
                            catogary = "A";
                            break;
                        case R.id.rb_s2_item3:
                            catogary = "B";
                            break;
                        case R.id.rb_s2_item4:
                            catogary = "C";
                            break;
                        case R.id.rb_s2_item5:
                            catogary = "D";
                            break;

                    }
                    ToastUtils.showToastForTest("catogary:" + catogary);
                });

            }
        });
        //选择标签
        mSelection3.setOnClickListener(v -> {
            if (mTagList == null) {
                mTagList = new ArrayList<String>();
            }
            mFrameLayout1.setVisibility(View.GONE);
            mFrameLayout2.setVisibility(View.GONE);
            mScrollView.setVisibility(View.VISIBLE);
            mSelection1.setSelected(false);
            mSelection2.setSelected(false);
            mSelection3.setSelected(true);


            myFloatLayoutView.setOnItemClickListener((v1, obj, position) -> {
                if (!(boolean) (v1.getTag())) {
                    v1.setTag(true);
                    v1.setSelected(true);
                    mTagList.add(obj.toString());
                } else if (mTagList.size() != 0) {
                    mTagList.remove(obj.toString());
                    v1.setSelected(false);
                    v1.setTag(false);
                }
            });

            if (!isTagLoaded) {
                myFloatLayoutView.setmNames(searchConditions.getCustomerTags());
                isTagLoaded = true;
            }

        });

        popupWindow.setOnDismissListener(new PoponDismissListener());
    }

    public void setSearchConditions(SearchConditions searchConditions) {
        this.searchConditions = searchConditions;
        doFilterClients();
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    class PoponDismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f);
        }

    }

    public void setmPresenter(ClientPresenter mPresenter) {
        this.mPresenter = mPresenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("MainScreen");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("MainScreen");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MainApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }
}
