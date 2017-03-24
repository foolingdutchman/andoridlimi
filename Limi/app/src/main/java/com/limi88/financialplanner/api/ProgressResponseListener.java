package com.limi88.financialplanner.api;

/**
 * Created by hehao on 16/9/26.
 */
public interface ProgressResponseListener {
    void onResponseProgress(long bytesRead, long contentLength, boolean done);

}
