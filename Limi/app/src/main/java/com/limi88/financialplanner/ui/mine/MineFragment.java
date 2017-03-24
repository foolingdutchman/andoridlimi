package com.limi88.financialplanner.ui.mine;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.di.component.FragmentComponent;
import com.limi88.financialplanner.greendao.bean.AccountBean;
import com.limi88.financialplanner.pojo.user.User;
import com.limi88.financialplanner.ui.MainActivity;
import com.limi88.financialplanner.ui.adapters.MineAdapter;
import com.limi88.financialplanner.ui.base.BackHandlerHelper;
import com.limi88.financialplanner.ui.base.BaseSafeFragment;
import com.limi88.financialplanner.ui.base.BaseWebViewActivity;
import com.limi88.financialplanner.ui.base.DownloadInterface;
import com.limi88.financialplanner.ui.base.FragmentBackHandler;
import com.limi88.financialplanner.ui.login.LawFragment;

import com.limi88.financialplanner.ui.login.LoginActivity;

import com.limi88.financialplanner.ui.widget.LoadingDialog;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.ToastUtils;
import com.limi88.financialplanner.util.VersionUpateUtils;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.umeng.analytics.MobclickAgent;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.Bind;
import cn.bingoogolapple.badgeview.BGABadgeRelativeLayout;

/**
 * Created by hehao on 16/9/1.
 */
public class MineFragment extends BaseSafeFragment implements FragmentBackHandler, MineContract.MineView, DownloadInterface {
    @Bind(R.id.common_title)
    TextView titleview;
    @Bind(R.id.root_view)
    LinearLayout mLinearLayout;
    @Bind(R.id.common_right_action)
    ImageView mRightAction;
    @Bind(R.id.badgeView)
    BGABadgeRelativeLayout mBadgeLayout;
    @Bind(R.id.rv_me_selections)
    RecyclerView mRecyclerView;
    private MineAdapter adapter;
    Bundle bundle;
    Intent mIntent;
    private LoadingDialog mDialog;

    private User user;
    @Inject
    MinePresenter mPresenter;
    SharedPreferences mSharedPreferences;
    private boolean isDataLoaded = false;
    String url = "";
    private long downloadId;
    private WeakHandler mHandler;
    private RelativeLayout.LayoutParams mLayoutParams;
    private View downLoadView;

    @Override
    protected void onFirstUserVisible() {
        if (!isDataLoaded) {
            loadUserInfoToUI();
        }
    }

    @Override
    protected void onUserVisible() {
        ((MainActivity) getActivity()).reloadUserUI();
        if (!isDataLoaded) {
            loadUserInfoToUI();
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
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mLinearLayout.setVisibility(View.VISIBLE);
        ToastUtils.showToastForTest("isSigned in MineFrament: " + DataCenter.isSigned());
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }

        MobclickAgent.onPageStart("MainScreen");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        MobclickAgent.onPageEnd("MainScreen");
    }

    @Override
    protected void initViewsAndEvents() {


        initializeInjector();
        mRightAction.setImageResource(R.mipmap.ic_message_bell);
        mRightAction.setVisibility(View.VISIBLE);
        mRightAction.setOnClickListener(v1 -> {
            if (bundle == null) {
                bundle = new Bundle();
            } else {
                bundle.clear();
            }
            if (mIntent != null) {
                mIntent = null;
            }
            mIntent = new Intent(MineFragment.this.getActivity(), BaseWebViewActivity.class);
            bundle.putInt(Constants.WEB_PAGE_TAG, Constants.NEWS_TAG);
            bundle.putString(Constants.WEB_MINE_LINK, Constants.HOST + "/users/" + user.getData().getId() + "/message");
            mIntent.putExtras(bundle);
            getActivity().startActivityForResult(mIntent, Constants.MINE_FLAG);
        });

        mDialog =((MainActivity)getActivity()).getLoadingDialog();

        titleview.setText(R.string.home_title_mine);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new MineAdapter(getActivity(), user);
        adapter.setOnItemClickListener((v, position, o) -> {
            if (bundle == null) {
                bundle = new Bundle();
            } else {
                bundle.clear();
            }
            if (mIntent != null) {
                mIntent = null;
            }
            mIntent = new Intent(MineFragment.this.getActivity(), BaseWebViewActivity.class);
            bundle.putInt(Constants.WEB_PAGE_TAG, Constants.WEB_MINE_TAG);
            if (position == 4 || position == 5)
                bundle.putString(Constants.WEB_MINE_NAME, o.toString());
            switch (position) {
                case 0:
                    if (v.getId() == R.id.iv_me_icon) {
            if (bundle == null) {
                bundle = new Bundle();
            } else {
                bundle.clear();
            }
            if (mIntent != null) {
                mIntent = null;
            }
            mIntent = new Intent(MineFragment.this.getActivity(), BaseWebViewActivity.class);
            bundle.putInt(Constants.WEB_PAGE_TAG, Constants.WEB_MINE_TAG);
            bundle.putString(Constants.WEB_MINE_LINK, Constants.USER_MINE_URL);
            mIntent.putExtras(bundle);
            getActivity().startActivityForResult(mIntent, Constants.MINE_FLAG);
//                        if (user != null && user.getData() != null) {
//                            if (mIntent != null) {
//                                mIntent = null;
//                            }
//                            mIntent = new Intent(getActivity(), MeProfileActivity.class);
//                            if (bundle == null) {
//                                bundle = new Bundle();
//                            } else {
//                                bundle.clear();
//                            }
//                            bundle.putParcelable(Constants.USER_FLAG, user);
//                            mIntent.putExtras(bundle);
//                            startActivityForResult(mIntent, Constants.UPDATE_USER_INFO_CODE);
//                        }
                    } else if (v.getId() == R.id.iv_me_2Dcode) {
                        if (bundle == null) {
                            bundle = new Bundle();
                        } else {
                            bundle.clear();
                        }
                        if (mIntent != null) {
                            mIntent = null;
                        }
                        mIntent = new Intent(MineFragment.this.getActivity(), BaseWebViewActivity.class);
                        bundle.putInt(Constants.WEB_PAGE_TAG, Constants.WEB_MINE_TAG);
                        bundle.putString(Constants.WEB_MINE_NAME, getString(R.string.home_title_mine));
                        bundle.putString(Constants.WEB_MINE_LINK, Constants.MINE_2DCODE_URL);
                        mIntent.putExtras(bundle);
                        getActivity().startActivityForResult(mIntent, Constants.MINE_FLAG);
                    } else if (v.getId() == R.id.ll_card_btn) {
                        bundle.putString(Constants.WEB_MINE_LINK, Constants.USER_PROFILE_URL);
                        mIntent.putExtras(bundle);
                        getActivity().startActivityForResult(mIntent, Constants.MINE_FLAG);
                    } else if (v.getId() == R.id.ll_favor_btn) {
                        bundle.putString(Constants.WEB_MINE_LINK, Constants.PRODUCTS_FAVOR_URL);
//                    bundle.putString(Constants.WEB_MINE_LINK, "https://dev.sunanzq.com/fin_product/fav?tab-info");
                        mIntent.putExtras(bundle);
                        getActivity().startActivityForResult(mIntent, Constants.MINE_FLAG);
                    }
                    break;
                case 1:

//                    if (!"true".equals(user.isUserAuthenticated())) {

                        bundle.putString(Constants.WEB_MINE_LINK, Constants.MINE_AUTHENTICATION_URL);
                        mIntent.putExtras(bundle);
                        getActivity().startActivityForResult(mIntent, Constants.MINE_FLAG);
//                    }

                    break;
                case 2:
//                    bundle.putString(Constants.WEB_MINE_NAME,"我的话题");
                    bundle.putString(Constants.WEB_MINE_LINK, Constants.HOST + "/users/" + user.getData().getId() + "/posts");
                    mIntent.putExtras(bundle);
                    getActivity().startActivityForResult(mIntent, Constants.MINE_FLAG);
                    break;
                case 3:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new LawFragment()).commit();
                    break;
                case 4:
                    startActivityForResult(new Intent(getActivity(), FeedbackActivity.class), Constants.FEEDBACK_CODE);

                    break;
                case 5:
                    if (user.getData() != null && !user.getData().getAndroidVersion().getVersion().equals(DataCenter.getAppVersion())) {
                        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                != PackageManager.PERMISSION_GRANTED) {
                            //申请WRITE_EXTERNAL_STORAGE, CAMERA权限
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    Constants.WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                        } else {
                            downloadId = VersionUpateUtils.init(getActivity()).updateVersion(this, user.getData().getAndroidVersion().getFileUrl());
                        }

                    }
                    break;
                case 6:
                    logout();
                    break;
            }


        });
        mRecyclerView.setAdapter(adapter);

    }

//    private void updateVersion() {
//        mSharedPreferences = getActivity().getSharedPreferences(Constants.USER_FLAG, Context.MODE_PRIVATE);
//        downloadId = mSharedPreferences.getLong(Constants.APP_ID_FLAG, -1);
//        if (downloadId != -1) {
//            installVersion();
//        } else if (!DataCenter.isDownLoadNewVersion()) {
//            downLoadNewVersion();
//        }
//    }

//    private void installVersion() {
//        //打开本地已下载文件
//        if (downloadManager == null) {
//            downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
//        }
//        String path = VersionUpateUtils.getDownloadPath(downloadManager, downloadId);
//        if (path != null && !path.equals("")) {
//            Intent install = new Intent(Intent.ACTION_VIEW);
//            if (path.startsWith("content:")) {
//                Cursor cursor = getActivity().getContentResolver().query(Uri.parse(path), null, null, null, null);
//                int columnCount = cursor.getColumnCount();
//                while (cursor.moveToNext()) {
//                    for (int j = 0; j < columnCount; j++) {
//                        String columnName = cursor.getColumnName(j);
//                        String string = cursor.getString(j);
//                        if (columnName.equals("_data")) {
//                            path = string;
//                        }
//                        if (string != null) {
//                            Log.i("download", "content:------" + columnName + ":-----" + string);
//                        } else {
//                            Log.i("download", "content:------" + columnName + ":-----null");
//                        }
//                    }
//                }
//                cursor.close();
////            Uri downloadFileUri = downloadManager.getUriForDownloadedFile(myDwonloadID);
//            }
//            File file = new File(path);
//            Uri downloadFileUri = Uri.fromFile(file);
//            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
//            getActivity().startActivity(install);
//        } else {
//            downLoadNewVersion();
//        }
//    }
//
//
//    private void downLoadNewVersion() {
//
//        downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
//        showDownLoadVersion();
//        String url = user.getData().getAndroidVersion().getFileUrl();
//        Uri uri = Uri.parse(url);
//        DownloadManager.Request request = new DownloadManager.Request(uri);
//
//        //设置允许使用的网络类型，这里是移动网络和wifi都可以
//        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
//        //禁止发出通知，既后台下载，如果要使用这一句必须声明一个权限：android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
//        //request.setShowRunningNotification(false);
//        //不显示下载界面
//        request.setVisibleInDownloadsUi(false);
//        /*设置下载后文件存放的位置,如果sdcard不可用，那么设置这个将报错，因此最好不设置
//        如果sdcard可用，下载后的文件在/mnt/sdcard/Android/data/packageName/files
//        目录下面，如果sdcard不可用,设置了下面这个将报错，不设置，下载后的文件在/cache这个目录下面*/
//        //request.setDestinationInExternalFilesDir(this, null, "tar.apk");
////            request.setDestinationInExternalFilesDir(getActivity(), Environment.DIRECTORY_DOWNLOADS, BuildConfig.APPLICATION_ID + "-" + BuildConfig.VERSION_NAME + ".apk");
//        downloadId = downloadManager.enqueue(request);
//        //TODO 把id保存好，在接收者里面要用，最好保存在Preferences里面
//        mSharedPreferences = getActivity().getSharedPreferences(Constants.USER_FLAG, Context.MODE_PRIVATE);
//        mSharedPreferences.edit().putLong(Constants.APP_ID_FLAG, downloadId).commit();
//        observeDownLoad();
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                downloadId = VersionUpateUtils.init(getActivity()).updateVersion(this, user.getData().getAndroidVersion().getFileUrl());

                Log.i("permission", "WRITE_EXTERNAL_STORAGE_REQUEST_CODE------------permited");
            } else {
                // Permission Denied
                Log.i("permission", "WRITE_EXTERNAL_STORAGE_REQUEST_CODE------------denied");
            }
        }

    }

    private void logout() {
        DataCenter.clearToken();
        mSharedPreferences = getActivity().getSharedPreferences(Constants.USER_FLAG, mContext.MODE_PRIVATE);
        mSharedPreferences.edit().putString(Constants.TOKEN_FLAG, "").commit();
        ((MainActivity) getActivity()).gotoPage(0);
        ((MainActivity) getActivity()).reload();

        DataCenter.logout();

        CookieSyncManager.createInstance(mContext);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

        CookieSyncManager.getInstance().sync();
        startActivity(new Intent(getActivity(), LoginActivity.class));
        getActivity().finish();
    }


    private void initializeInjector() {
        FragmentComponent component = FragmentComponent.Initializer.init(this);
        component.inject(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_mine;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public boolean onBackPressed() {
        return BackHandlerHelper.handleBackPress(this);
    }

    @Override
    public void showDataLoading() {
        mDialog.show();
    }

    @Override
    public void hideDataLoading() {
        mDialog.dismiss();
    }

    @Override
    public void loadAccount(AccountBean accountBean) {
    }

    @Override
    public void loadUserInfoToUI(User user) {
        hideDataLoading();
        if (user != null && user.getData() != null) {
            this.user = user;
            adapter.setUser(user);
            adapter.notifyDataSetChanged();
            DataCenter.setCurrentUser(user);
            if (user.getData().getNewInfoCount() > 0 && user.getData().getNewInfoCount() < 100) {
                mBadgeLayout.showTextBadge(user.getData().getNewInfoCount() + "");
                mLayoutParams = (RelativeLayout.LayoutParams) mBadgeLayout.getLayoutParams();
                mLayoutParams.setMargins(0, 2, 24, 0);
                mBadgeLayout.setLayoutParams(mLayoutParams);
                mBadgeLayout.setPadding(0, 0, 24, 0);
//                mBadgeLayout.showTextBadge("99+");
                startAnim(mBadgeLayout);
            } else if (user.getData().getNewInfoCount() >= 100) {
                mBadgeLayout.showTextBadge("99+");
                mLayoutParams = (RelativeLayout.LayoutParams) mBadgeLayout.getLayoutParams();
                mLayoutParams.setMargins(0, 2, 10, 0);
                mBadgeLayout.setLayoutParams(mLayoutParams);
                mBadgeLayout.setPadding(0, 0, 40, 0);
                startAnim(mBadgeLayout);
            } else {
                mBadgeLayout.hiddenBadge();
            }

            setDataLoaded(true);
        }
        if (mHandler == null && DataCenter.isDownLoadNewVersion()) {
            observeDownLoad();
        }

    }

    public void startAnim(View view) {
        AnimationSet animationSet = new AnimationSet(false);
        RotateAnimation mAnim1 = new RotateAnimation(-15, Animation.RELATIVE_TO_SELF, 0.5f, 0f);
        mAnim1.setDuration(75);
        RotateAnimation mAnim2 = new RotateAnimation(30, Animation.RELATIVE_TO_SELF, 0.5f, 0f);
        mAnim2.setDuration(150);
        RotateAnimation mAnim3 = new RotateAnimation(-15, Animation.RELATIVE_TO_SELF, 0.5f, 0f);
        mAnim3.setDuration(75);
        animationSet.addAnimation(mAnim1);
        animationSet.addAnimation(mAnim2);
        animationSet.addAnimation(mAnim3);
        view.startAnimation(animationSet);
    }

    @Override
    public void loadUserInfoToUI() {
        showDataLoading();
        mPresenter.getUserInfo();

    }

    @Override
    public void setDataLoaded(boolean isDataLoaded) {
        this.isDataLoaded = isDataLoaded;
    }

    @Override
    public void dismissUpDateUserUI() {
//        getSupportFragmentManager().beginTransaction().remove(meProfileFragment).commit();
    }

    @Override
    public void upLoadUerInfo(User user) {
        mPresenter.uploadUserInfo(user);
    }

    @Override
    public void gotoLogin() {
        getActivity().startActivity(new Intent(getActivity(),LoginActivity.class));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MainApplication.getRefWatcher(getActivity());
        refWatcher.watch(this);
    }

    @Override
    public void showDownLoadPause() {
        adapter.showDownLoadPause();
    }

    @Override
    public void showDownLoadComplete() {
        adapter.showDownLoadSuccess();
        DataCenter.setIsDownLoadNewVersion(true);
    }

    @Override
    public void showDownLoadFailed() {
        adapter.showDownLoadFailed();
    }

    @Override
    public void showDownLoadVersion() {
        adapter.showDownLoadVersion();

    }

    private void killmThread() {
        if (mHandler != null) mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }

    static class WeakHandler extends Handler {
        WeakReference<Fragment> mWeakReference;

        public WeakHandler(Fragment fragment) {
            mWeakReference = new WeakReference<Fragment>(fragment);
        }
    }

    @Override
    public void observeDownLoad() {
        mHandler = new WeakHandler(this) {
            @Override
            public void handleMessage(Message msg) {
                VersionUpateUtils.init(getActivity()).queryDownloadStatus(downloadId, MineFragment.this);
                sendEmptyMessageDelayed(0, 1000);
            }
        };
        mHandler.sendEmptyMessageDelayed(0, 1000);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        killmThread();
    }
}
