package com.limi88.financialplanner.ui.mine;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hehao.library.base.BaseFragmentActivity;
import com.hehao.library.netstatus.NetUtils;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.pojo.user.User;
import com.limi88.financialplanner.ui.base.DownloadInterface;
import com.limi88.financialplanner.services.CompleteReceiver;
import com.limi88.financialplanner.util.Constants;
import com.umeng.analytics.MobclickAgent;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import cn.bingoogolapple.badgeview.BGABadgeFrameLayout;

public class VersionActivity extends BaseFragmentActivity implements DownloadInterface {


    @Bind(R.id.ll_left_btn)
    LinearLayout mLlLeftBtn;
    @Bind(R.id.common_title)
    TextView mCommonTitle;
    @Bind(R.id.tv_header_back)
    TextView mLeftTitle;
    @Bind(R.id.tv_version_title)
    TextView mTvVersionTitle;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;
    @Bind(R.id.tv_version)
    TextView mTvVersion;
    @Bind(R.id.badgeView)
    BGABadgeFrameLayout mBadgeView;
    @Bind(R.id.ll_download)
    LinearLayout mDownloadLayout;
    private User mUser;
    private String version;
    private SharedPreferences mSharedPreferences;
    private CompleteReceiver mReceiver;
    private Intent mIntent;
    private DownloadManager downloadManager;
    private long lastDownloadId;
    private WeakHandler mHandler;
    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_version;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        mLlLeftBtn.setVisibility(View.VISIBLE);
        mCommonTitle.setText("我");
        mCommonTitle.setVisibility(View.GONE);
        mLeftTitle.setVisibility(View.VISIBLE);
        mLeftTitle.setText("我");

        mIntent = getIntent();
        Bundle bundle = mIntent.getExtras();
        mUser = bundle.getParcelable(Constants.USER_FLAG);
        if (mUser != null && mUser.getData() != null) {
            version = mUser.getData().getAndroidVersion().getVersion();
            mTvVersionTitle.setText("理米 V" + version);
            if (version.equals(DataCenter.getAppVersion())) {
                mTvVersion.setText("当前最新");

            } else {
                mTvVersion.setText("v" + version + "下载");
                mBadgeView.showCirclePointBadge();
            }
        }
        mReceiver = new CompleteReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.DOWNLOAD_COMPLETE");
        filter.addAction("android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED");
        registerReceiver(mReceiver, filter);
        mReceiver.setDownloadInterface(this);
        initHandler();
    }

    private void initHandler() {
        if (mHandler == null) {
            mHandler = new WeakHandler(this) {
                @Override
                public void handleMessage(Message msg) {
                    queryDownloadStatus();
                    sendEmptyMessageDelayed(0,1000);
                }
            };
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        killmThread();
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }


    public void backPre(View view) {
        finish();
    }

    public void download(View view) {
        if (!version.equals(DataCenter.getAppVersion())) {
            downLoadNewVersion();
            mHandler.sendEmptyMessageDelayed(0,1000);
//            mDownloadLayout.setClickable(false);

        }
    }

    private void downLoadNewVersion() {
        downloadManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
        showDownLoadVersion();
        String url = mUser.getData().getAndroidVersion().getFileUrl();
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        //设置允许使用的网络类型，这里是移动网络和wifi都可以
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        //禁止发出通知，既后台下载，如果要使用这一句必须声明一个权限：android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
        //request.setShowRunningNotification(false);
        //不显示下载界面
        request.setVisibleInDownloadsUi(false);
        /*设置下载后文件存放的位置,如果sdcard不可用，那么设置这个将报错，因此最好不设置
        如果sdcard可用，下载后的文件在/mnt/sdcard/Android/data/packageName/files
        目录下面，如果sdcard不可用,设置了下面这个将报错，不设置，下载后的文件在/cache这个目录下面*/
//request.setDestinationInExternalFilesDir(this, null, "tar.apk");
        lastDownloadId = downloadManager.enqueue(request);
//TODO 把id保存好，在接收者里面要用，最好保存在Preferences里面
        mSharedPreferences = this.getSharedPreferences(Constants.USER_FLAG, Context.MODE_PRIVATE);
        mSharedPreferences.edit().putLong(Constants.APP_ID_FLAG, lastDownloadId).commit();
    }

    @Override
    public void showDownLoadVersion() {
        mProgressBar.setVisibility(View.VISIBLE);
        mTvVersion.setVisibility(View.GONE);
        mDownloadLayout.setClickable(false);
    }

    @Override
    public void observeDownLoad() {

    }

    @Override
    public void showDownLoadPause() {
        mProgressBar.setVisibility(View.GONE);
        mTvVersion.setVisibility(View.VISIBLE);
        mDownloadLayout.setClickable(true);
        mTvVersion.setText("网络错误");
    }

    @Override
    public void showDownLoadComplete() {
        mProgressBar.setVisibility(View.GONE);
        mTvVersion.setVisibility(View.VISIBLE);
        mDownloadLayout.setClickable(true);
    }

    @Override
    public void showDownLoadFailed() {
        mProgressBar.setVisibility(View.GONE);
        mTvVersion.setVisibility(View.VISIBLE);
        mTvVersion.setText("网络错误");
        mDownloadLayout.setClickable(true);
    }



    private void queryDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(lastDownloadId);
        Cursor c = downloadManager.query(query);
        if (c != null && c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));

            int reasonIdx = c.getColumnIndex(DownloadManager.COLUMN_REASON);
            int titleIdx = c.getColumnIndex(DownloadManager.COLUMN_TITLE);
            int fileSizeIdx =
                    c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
            int bytesDLIdx =
                    c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
            String title = c.getString(titleIdx);
            int fileSize = c.getInt(fileSizeIdx);
            int bytesDL = c.getInt(bytesDLIdx);

            // Translate the pause reason to friendly text.
            int reason = c.getInt(reasonIdx);
            StringBuilder sb = new StringBuilder();
            sb.append(title).append("\n");
            sb.append("Downloaded ").append(bytesDL).append(" / ").append(fileSize);
            String string=sb.toString();

            // Display the status
            Log.d("tag", sb.toString());
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.v("tag", "STATUS_PAUSED");
                    showDownLoadPause();
                case DownloadManager.STATUS_PENDING:
                    Log.v("tag", "STATUS_PENDING");
                case DownloadManager.STATUS_RUNNING:
                    //正在下载，不做任何事情
                    Log.v("tag", "STATUS_RUNNING:----"+string);
                    showDownLoadVersion();
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    //完成
                    Log.v("tag", "下载完成");
//                  dowanloadmanager.remove(lastDownloadId);
                    showDownLoadComplete();
                    break;
                case DownloadManager.STATUS_FAILED:
                    //清除已下载的内容，重新下载
                    Log.v("tag", "STATUS_FAILED");
                    downloadManager.remove(lastDownloadId);
                    showDownLoadFailed();
                    break;
            }
        }
    }
    private void killmThread() {
        if (mHandler != null) mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
    }

    static class WeakHandler extends Handler {
        WeakReference<Activity> mWeakReference;

        public WeakHandler(Activity activity) {
            mWeakReference = new WeakReference<Activity>(activity);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
