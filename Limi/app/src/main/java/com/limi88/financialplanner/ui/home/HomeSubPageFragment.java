package com.limi88.financialplanner.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.extras.recyclerview.PullToRefreshRecyclerView;
import com.hehao.library.base.BaseLazyFragment;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.di.component.FragmentComponent;
import com.limi88.financialplanner.pojo.BaseData;
import com.limi88.financialplanner.pojo.home.Product;
import com.limi88.financialplanner.pojo.hotnews.HotNews;
import com.limi88.financialplanner.pojo.topics.Datum;

import com.limi88.financialplanner.pojo.topics.Topic;
import com.limi88.financialplanner.ui.MainActivity;
import com.limi88.financialplanner.ui.adapters.HomeSubAdapter;
import com.limi88.financialplanner.ui.adapters.HotNewsAdapter;
import com.limi88.financialplanner.ui.adapters.TopicAdapter;
import com.limi88.financialplanner.ui.base.BaseWebViewActivity;
import com.limi88.financialplanner.ui.login.LoginActivity;
import com.limi88.financialplanner.ui.widget.LimiFooterView;
import com.limi88.financialplanner.ui.widget.LimiHeaderLayout;
import com.limi88.financialplanner.ui.widget.MyLinearLayoutManager;
import com.limi88.financialplanner.ui.widget.OnItemClickListener;
import com.limi88.financialplanner.util.Constants;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import anet.channel.util.StringUtils;
import butterknife.Bind;

/**
 * Created by hehao
 * Date on 16/12/5.
 * Email: hao.he@sunanzq.com
 */
public class HomeSubPageFragment extends BaseLazyFragment implements HomeContract.SubView {
    @Bind(R.id.prv_home_sub)
    PullToRefreshRecyclerView mRefreshRecyclerView;
    @Bind(R.id.iv_topic_button)
    ImageView mTopicbutton;
    private RecyclerView mRecyclerView;
    private HomeContract.HomeView mHomeView;
    private HomeSubAdapter mAdapter;
    private List<Datum> mTopicList = new ArrayList<>();
    private List<com.limi88.financialplanner.pojo.hotnews.Datum> mNewsList = new ArrayList<>();
    private String path;
    private RecyclerView.Adapter mSubAdapter;
    private HomePresenter mPresenter;
    private MyLinearLayoutManager mLayoutManager;
    private boolean isLoaded;
    private int page = 1;
    private int itemNum = -1;
    private int lastitemNum;
    private Intent mIntent;
    private boolean isUserVisible=false;
    private boolean isRefreshing=false;
    public boolean isLoaded() {
        return isLoaded;
    }

    public void setIsLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }

    public void setHomeView(HomeContract.HomeView homeView) {
        mHomeView = homeView;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    protected void onFirstUserVisible() {
        if (path.equals("hot")) {
            loadDataToUI();
        } else
            mRefreshRecyclerView.setRefreshing();
    }

    public void setPresenter(HomePresenter presenter) {
        mPresenter = presenter;
    }


    @Override
    protected void onUserVisible() {

        isUserVisible=true;
        if (mRefreshRecyclerView != null) {
            mRefreshRecyclerView.onRefreshComplete();
            mRefreshRecyclerView.setRefreshing();
        } else {
            loadDataToUI();
        }

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
        initializeInjector();
        if (mPresenter == null) return;
        mPresenter.addSubView(path, this);
        mRecyclerView = mRefreshRecyclerView.getRefreshableView();
        mRecyclerView.setId(R.id.rv_home_sub);
        mAdapter = new HomeSubAdapter(getActivity());
        if (path.equals("topics")) {
            mTopicbutton.setVisibility(View.VISIBLE);
            mTopicbutton.setOnClickListener(v -> {
                        if (!DataCenter.isSigned()) {
                            goLogin();
                        } else
                            toWebView(Constants.HOME_TOPICS_NEW, Constants.GO_DETAIL_TAG);
                    }
            );
        }
        mLayoutManager=new MyLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setAdapter(mAdapter);
        mRefreshRecyclerView.setHasTransientState(false); // 设置没有上拉阻力
        mRefreshRecyclerView.setMode(PullToRefreshBase.Mode.BOTH);
        mRefreshRecyclerView.setHeaderLayout(new LimiHeaderLayout(getActivity()));
        mRefreshRecyclerView.setFooterLayout(new LimiFooterView(getActivity()));

        mRefreshRecyclerView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<RecyclerView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                page = 1;
                reloadDataToUI();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                if (!isRefreshing) {
                    page++;
                    attachData(page);
                    isRefreshing=true;
                }


            }
        });
        if (isUserVisible) {
            mRefreshRecyclerView.setRefreshing();
        }
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mLayoutManager.isLastVisibleItem(itemNum)) {
                    if (!isRefreshing) {
                        page++;
                        attachData(page);
                        isRefreshing=true;
                    }
                }
            }
        });

    }

    private void initializeInjector() {

        FragmentComponent component = FragmentComponent.Initializer.init(this);
        component.inject(this);

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home_subpage;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void loadDataToUI(BaseData baseData) {
        if (baseData != null) {
            if (baseData instanceof HotNews) {
                mSubAdapter = new HotNewsAdapter(getActivity(), (HotNews) baseData);
                mNewsList = ((HotNews) baseData).getData();
                itemNum = itemNum + ((HotNews) baseData).getData().size();
            } else if (baseData instanceof Topic) {
                mSubAdapter = new TopicAdapter(getActivity(), (Topic) baseData);
                mTopicList = ((Topic) baseData).getData();
                itemNum = itemNum + ((Topic) baseData).getData().size();
                ((TopicAdapter) mSubAdapter).setOnItemClickListener((v, obj, position) -> {
                    if (v.getTag().toString().equals("favor")) {
                        mPresenter.postTopicLikes(((Datum) obj).getId());
                    } else if (v.getTag().toString().equals("comment")) {
                        toWebView(Constants.HOME_TOPICS_DETAIL_BASE + ((Datum) obj).getId(), Constants.GO_DETAIL_TAG);
                    } else if (v.getTag().toString().equals("images")) {
                        List<String> imageList = ((Datum) obj).getMediumImages();
                        gotoImageGallary(imageList, 0);
                    } else if (v.getTag().toString().equals("imamgesFromGallary")) {
                        gotoImageGallary((List<String>) obj, position);
                    }
                });
            }
            mRecyclerView.setAdapter(mSubAdapter);
            isLoaded = true;
            mRefreshRecyclerView.onRefreshComplete();
        }


    }

    private void goLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivityForResult(intent, Constants.HOME_FLAG);
    }

    private void gotoImageGallary(List<String> images, int position) {
        mIntent = new Intent(getActivity(), ImageGallaryActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("images", (ArrayList<String>) images);
        bundle.putInt("position", position);
        mIntent.putExtras(bundle);
        startActivity(mIntent);
    }

    private void toWebView(String path, int webPageTagValue) {
        toWebView(path, Constants.WEB_PAGE_DETAIL_LINK, webPageTagValue);
    }

    private void toWebView(String path, String linkKey, int webPageTagValue) {
        if (!StringUtils.isBlank(path)) {
            Bundle bu = new Bundle();
            String url = path.contains("http") ? path : Constants.HOST + path;
//            String url = path;
            bu.putInt(Constants.WEB_PAGE_TAG, webPageTagValue);
            bu.putString(linkKey, url);
            mIntent = new Intent(getActivity(), BaseWebViewActivity.class);
            mIntent.putExtras(bu);
            getActivity().startActivity(mIntent);
        }
    }

    @Override
    public void attachData(BaseData baseData) {
        int currentNum=0;
        if (mSubAdapter instanceof HotNewsAdapter) {
            mNewsList.addAll(((HotNews) baseData).getData());
            ((HotNewsAdapter) mSubAdapter).attachData(mNewsList);
            lastitemNum = itemNum;
            currentNum= ((HotNews) baseData).getData().size();
            itemNum = itemNum + ((HotNews) baseData).getData().size();
        } else if (mSubAdapter instanceof TopicAdapter) {
            mTopicList.addAll(((Topic) baseData).getData());
            ((TopicAdapter) mSubAdapter).attachData(mTopicList);
            lastitemNum = itemNum;
            itemNum = itemNum + ((Topic) baseData).getData().size();
            currentNum= ((Topic) baseData).getData().size();
        }
        mAdapter.notifyDataSetChanged();
        mRefreshRecyclerView.onRefreshComplete();
        if (currentNum!=0) {
            mRecyclerView.scrollToPosition(lastitemNum);
        }
        isRefreshing=false;
    }

    @Override
    public void loadDataToUI() {
        if (mPresenter != null) {

            mPresenter.loadSubPageData(path, page);
        }
    }

    public void reloadDataToUI() {
        if (mPresenter != null) {
            mPresenter.loadSubPageData(path, page);
        }
    }

    @Override
    public void attachData(int page) {
        mPresenter.attachSubPageData(path, page);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.removeSubView(path);
    }
}
