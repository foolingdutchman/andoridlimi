package com.limi88.financialplanner.util;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.limi88.financialplanner.api.DataCenter;
import com.limi88.financialplanner.ui.base.DownloadInterface;

import java.io.File;

/**
 * Created by hehao
 * Date on 16/11/15.
 * Email: hao.he@sunanzq.com
 */
public class VersionUpateUtils {
    private static VersionUpateUtils mUtils;
    private SharedPreferences mSharedPreferences;
    private Context mContext;
    private long downloadId;
    private DownloadManager downloadManager;
    private String downloadUrl;


    /**
     * 初始化下载组件
     *
     * @param mContext
     *
     * @return
     */
    public static VersionUpateUtils init(Context mContext) {
        if (mUtils == null) {
            mUtils = new VersionUpateUtils();
        }
        mUtils.setmContext(mContext);

        return mUtils;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 查询下载进度
     */

    public void queryDownloadStatus(long lastDownloadId, DownloadInterface mInterface) {
        DownloadManager.Query query = new DownloadManager.Query();
        if (downloadManager==null) {
            downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        }
        query.setFilterById(lastDownloadId);
        Cursor c = null;
        try {
            c = downloadManager.query(query);
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
                String string = sb.toString();

                // Display the status
                Log.d("tag", sb.toString());
                switch (status) {
                    case DownloadManager.STATUS_PAUSED:
                        Log.v("tag", "STATUS_PAUSED");
                        mInterface.showDownLoadPause();
                    case DownloadManager.STATUS_PENDING:
                        Log.v("tag", "STATUS_PENDING");
                    case DownloadManager.STATUS_RUNNING:
                        //正在下载，不做任何事情
                        Log.v("tag", "STATUS_RUNNING:----" + string);
//                    mInterface.showDownLoadVersion();
                        break;
                    case DownloadManager.STATUS_SUCCESSFUL:
                        //完成
                        Log.v("tag", "下载完成");
//                  dowanloadmanager.remove(lastDownloadId);
                        mInterface.showDownLoadComplete();
                        break;
                    case DownloadManager.STATUS_FAILED:
                        //清除已下载的内容，重新下载
                        Log.v("tag", "STATUS_FAILED");
                        downloadManager.remove(lastDownloadId);
                        mInterface.showDownLoadFailed();
                        break;
                }
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }

    }

    /**
     * 获取文件保存的路径
     *
     * @param downloadId an ID for the download, unique across the system.
     *                   This ID is used to make future calls related to this download.
     * @return file path
     */
    public static String getDownloadPath(DownloadManager downloadManager, long downloadId) {
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        Cursor c = downloadManager.query(query);
        if (c != null) {
            try {
                if (c.moveToFirst()) {
                    return c.getString(c.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI));
                }
            } finally {
                c.close();
            }
        }
        return null;
    }

    /**
     * 获取保存文件的地址
     *
     * @param downloadId an ID for the download, unique across the system.
     *                   This ID is used to make future calls related to this download.
     */
    public static Uri getDownloadUri(DownloadManager downloadManager, long downloadId) {
        return downloadManager.getUriForDownloadedFile(downloadId);
    }

    /**
     * 更新版本
     */

    public long updateVersion(DownloadInterface mInterface, String downloadUrl) {
        mSharedPreferences = mContext.getSharedPreferences(Constants.USER_FLAG, Context.MODE_PRIVATE);
        downloadId = mSharedPreferences.getLong(Constants.APP_ID_FLAG, -1);
        if (downloadId != -1) {
            installVersion(mInterface,downloadUrl);
        } else if (!DataCenter.isDownLoadNewVersion()) {
           downLoadNewVersion(mInterface,downloadUrl);
        }
        return downloadId;
    }

    /**
     * 后台更新
     */
    public void updateBackground(String downloadUrl) {
        mSharedPreferences = mContext.getSharedPreferences(Constants.USER_FLAG, Context.MODE_PRIVATE);
        downloadId = mSharedPreferences.getLong(Constants.APP_ID_FLAG, -1);
        if (downloadId != -1) {
            installVersion(null,downloadUrl);
        } else if (!DataCenter.isDownLoadNewVersion()) {
            downLoadBackground(downloadUrl);
        }
    }

    /**
     * 新版本安装
     */

    public void installVersion(DownloadInterface mInterface,String downloadUrl) {
        //打开本地已下载文件
        if (downloadManager == null) {
            downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        }
        String path = VersionUpateUtils.getDownloadPath(downloadManager, downloadId);
        if (path != null && !path.equals("")) {
            Intent install = new Intent(Intent.ACTION_VIEW);
            if (path.startsWith("content:")) {
                Cursor cursor = mContext.getContentResolver().query(Uri.parse(path), null, null, null, null);
                int columnCount = cursor.getColumnCount();
                while (cursor.moveToNext()) {
                    for (int j = 0; j < columnCount; j++) {
                        String columnName = cursor.getColumnName(j);
                        String string = cursor.getString(j);
                        if (columnName.equals("_data")) {
                            path = string;
                        }
                        if (string != null) {
                            Log.i("download", "content:------" + columnName + ":-----" + string);
                        } else {
                            Log.i("download", "content:------" + columnName + ":-----null");
                        }
                    }
                }
                cursor.close();
//            Uri downloadFileUri = downloadManager.getUriForDownloadedFile(myDwonloadID);
            }
            File file = new File(path);
            Uri downloadFileUri = Uri.fromFile(file);
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
            mContext.startActivity(install);
        } else {
            downLoadNewVersion(mInterface,downloadUrl);
        }
    }

    /**
     * 下载并更新界面
     *
     * @param mInterface
     */

    public long downLoadNewVersion(DownloadInterface mInterface,String downloadUrl) {

        downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);


        Uri uri = Uri.parse(downloadUrl);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setVisibleInDownloadsUi(false);
        downloadId = downloadManager.enqueue(request);
        mSharedPreferences = mContext.getSharedPreferences(Constants.USER_FLAG, Context.MODE_PRIVATE);
        mSharedPreferences.edit().putLong(Constants.APP_ID_FLAG, downloadId).commit();
        if (mInterface!=null) {
            mInterface.showDownLoadVersion();
            mInterface.observeDownLoad();
        }
        return downloadId;
    }

    public long downLoadBackground(String downloadUrl) {
        if (downloadUrl != null && !downloadUrl.equals("")) {
            downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);

            Uri uri = Uri.parse(downloadUrl);
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
//            request.setDestinationInExternalFilesDir(getActivity(), Environment.DIRECTORY_DOWNLOADS, BuildConfig.APPLICATION_ID + "-" + BuildConfig.VERSION_NAME + ".apk");
            downloadId = downloadManager.enqueue(request);
            //TODO 把id保存好，在接收者里面要用，最好保存在Preferences里面
            mSharedPreferences = mContext.getSharedPreferences(Constants.USER_FLAG, Context.MODE_PRIVATE);
            mSharedPreferences.edit().putLong(Constants.APP_ID_FLAG, downloadId).commit();
        }
        return downloadId;
    }

}
