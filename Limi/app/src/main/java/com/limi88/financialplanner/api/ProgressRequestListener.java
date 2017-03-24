package com.limi88.financialplanner.api;

/**
 * Created by hehao on 16/9/26.
 */
public interface ProgressRequestListener {
    void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}
