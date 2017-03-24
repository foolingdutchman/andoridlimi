package com.limi88.financialplanner.ui.base;

/**
 * Created by hehao
 * Date on 16/11/11.
 * Email: hao.he@sunanzq.com
 */
public interface DownloadInterface {
    void showDownLoadPause();

    void showDownLoadComplete();

    void showDownLoadFailed();

    void showDownLoadVersion();

    void observeDownLoad();
}
