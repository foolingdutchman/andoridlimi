package com.limi88.financialplanner.services;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.limi88.financialplanner.ui.base.DownloadInterface;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.ToastUtils;

import java.io.File;

/**
 * Created by hehao
 * Date on 16/11/8.
 * Email: hao.he@sunanzq.com
 */
public class CompleteReceiver extends BroadcastReceiver {

    private DownloadManager downloadManager;
    private String path;
    private long lastDownloadId = 0L;
    private long myDwonloadID = 1L;
    private SharedPreferences sPreferences;
    private DownloadInterface mDownloadInterface;
    private boolean isDownLoading = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        sPreferences = context.getSharedPreferences(Constants.USER_FLAG, Context.MODE_PRIVATE);
        lastDownloadId = sPreferences.getLong(Constants.APP_ID_FLAG, 0);
        String action = intent.getAction();
        queryDownloadStatus();
        if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            installApp(context, intent);
            lastDownloadId=myDwonloadID;
//
        } else if (action.equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
            Toast.makeText(context, "点击", Toast.LENGTH_LONG).show();
        }
    }

    private void installApp(Context context, Intent intent) {
        ToastUtils.showToast("下载完成");
        if (mDownloadInterface!=null) {
            mDownloadInterface.showDownLoadComplete();
        }
        lastDownloadId = sPreferences.getLong(Constants.APP_ID_FLAG, 0);
        myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
        //TODO 判断这个id与之前的id是否相等，如果相等说明是之前的那个要下载的文件
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(myDwonloadID);
        Cursor cursor = downloadManager.query(query);
        int columnCount = cursor.getColumnCount();

        //TODO 这里把所有的列都打印一下，有什么需求，就怎么处理,文件的本地路径就是path
        while (cursor.moveToNext()) {
            for (int j = 0; j < columnCount; j++) {
                String columnName = cursor.getColumnName(j);
                String string = cursor.getString(j);
                if (columnName.equals("local_uri")) {
                    path = string;
                }
                if (string != null) {
                    Log.i("download", "path:------" + columnName + ":-----" + string);

                } else {
                    Log.i("download", "path:------" + columnName + ":-----null");
                }
            }
        }
        cursor.close();
        //如果sdcard不可用时下载下来的文件，那么这里将是一个内容提供者的路径，这里打印出来，有什么需求就怎么样处理
        if (path!=null&&path.startsWith("content:")) {
            cursor = context.getContentResolver().query(Uri.parse(path), null, null, null, null);
            columnCount = cursor.getColumnCount();
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
            Intent install = new Intent(Intent.ACTION_VIEW);
//            Uri downloadFileUri = downloadManager.getUriForDownloadedFile(myDwonloadID);
            File file = new File(path);
            Uri downloadFileUri = Uri.fromFile(file);
//                if (downloadFileUri.getPath().contains("content:")) {
//
//                }

            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
            context.startActivity(install);
        }
    }

    public void setDownloadInterface(DownloadInterface downloadInterface) {
        mDownloadInterface = downloadInterface;
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

            // Display the status
            Log.d("tag", sb.toString());
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Log.v("tag", "STATUS_PAUSED");
                    isDownLoading = false;
                    if (mDownloadInterface!=null) {
                        mDownloadInterface.showDownLoadPause();
                    }
                case DownloadManager.STATUS_PENDING:
                    Log.v("tag", "STATUS_PENDING");
                    isDownLoading = false;
                case DownloadManager.STATUS_RUNNING:
                    //正在下载，不做任何事情
                    Log.v("tag", "STATUS_RUNNING");
                    isDownLoading = true;

                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    //完成
                    Log.v("tag", "下载完成");
                    isDownLoading = false;
//                  dowanloadmanager.remove(lastDownloadId);
                    break;
                case DownloadManager.STATUS_FAILED:
                    //清除已下载的内容，重新下载
                    Log.v("tag", "STATUS_FAILED");
                    if (mDownloadInterface!=null) {
                        mDownloadInterface.showDownLoadFailed();
                    }
                    isDownLoading = false;
                    downloadManager.remove(lastDownloadId);
                    break;
            }
        }
    }

}

