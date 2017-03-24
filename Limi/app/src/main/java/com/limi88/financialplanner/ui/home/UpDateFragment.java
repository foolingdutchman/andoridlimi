package com.limi88.financialplanner.ui.home;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hehao.library.base.BaseLazyFragment;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.pojo.version.VersionInfo;
import com.limi88.financialplanner.ui.base.DownloadInterface;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.VersionUpateUtils;

import java.lang.ref.WeakReference;

import butterknife.Bind;

/**
 * Created by hehao
 * Date on 16/11/9.
 * Email: hao.he@sunanzq.com
 */
public class UpDateFragment extends BaseLazyFragment implements View.OnClickListener ,DownloadInterface {
    @Bind(R.id.tv_update_title)
    TextView mTvUpdateTitle;
    @Bind(R.id.tv_update_hint)
    TextView mTvUpdateHint;
    @Bind(R.id.tv_update_detail)
    TextView mTvUpdateDetail;
    @Bind(R.id.tv_update_cancel)
    RelativeLayout mTvUpdateCancel;
    @Bind(R.id.btn_update_confirm)
    Button mBtnUpdateConfirm;
    private SharedPreferences mSharedPreferences;
    private VersionInfo mInfo;
    private DownloadManager downloadManager;

    private long downloadId;
    private WeakHandler mHandler;
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
        if (mInfo!=null) {
            if (mInfo.getData().isNeedUpdate()) {
                mTvUpdateTitle.setText("必要版本更新");
                mTvUpdateCancel.setVisibility(View.GONE);
            }else mTvUpdateTitle.setText("发现新版本");
            mTvUpdateHint.setText("V"+mInfo.getData().getVersion()+"版本新特性");
            mTvUpdateDetail.setText(mInfo.getData().isNote());
        }
        mTvUpdateCancel.setOnClickListener(this);
        mBtnUpdateConfirm.setOnClickListener(this);

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_update;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_update_cancel:
                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
                break;
            case R.id.btn_update_confirm:
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE, CAMERA权限
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            Constants.WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    VersionUpateUtils.init(getActivity()).updateBackground(mInfo.getData().getFileUrl());
                    getSupportFragmentManager().beginTransaction().remove(this).commit();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                VersionUpateUtils.init(getActivity()).updateBackground(mInfo.getData().getFileUrl());
                getSupportFragmentManager().beginTransaction().remove(this).commit();
                Log.i("permission", "WRITE_EXTERNAL_STORAGE_REQUEST_CODE------------permitted");
            } else {
                // Permission Denied
                Log.i("permission", "WRITE_EXTERNAL_STORAGE_REQUEST_CODE------------denied");
            }
        }

    }

//    private void downLoadNewVersion() {
//        if (!DataCenter.isDownLoadNewVersion()) {
//            downloadManager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
//            String url = mInfo.getData().getFileUrl();
//            downloadId = VersionUpateUtils.excuteDownload(downloadManager, url, this);
//
//            mBtnUpdateConfirm.setClickable(false);
//            mSharedPreferences = getActivity().getSharedPreferences(Constants.USER_FLAG, Context.MODE_PRIVATE);
//            mSharedPreferences.edit().putLong(Constants.APP_ID_FLAG, downloadId).commit();
//            observeDownLoad();
//            DataCenter.setIsDownLoadNewVersion(true);
//        }
//
//    }

    public void setVersionInfo(VersionInfo info) {
        mInfo=info;
    }

    @Override
    public void showDownLoadPause() {
        mBtnUpdateConfirm.setClickable(false);
    }

    @Override
    public void showDownLoadComplete() {
        mBtnUpdateConfirm.setClickable(true);
    }

    @Override
    public void showDownLoadFailed() {
        mBtnUpdateConfirm.setClickable(true);
    }

    @Override
    public void showDownLoadVersion() {
        mBtnUpdateConfirm.setClickable(false);
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

   public void observeDownLoad(){
        mHandler=new WeakHandler(this){
            @Override
            public void handleMessage(Message msg) {
                VersionUpateUtils.init(getActivity()).queryDownloadStatus(downloadId,UpDateFragment.this);
                sendEmptyMessageDelayed(0,1000);
            }
        };
       mHandler.sendEmptyMessageDelayed(0,1000);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        killmThread();
    }
}
