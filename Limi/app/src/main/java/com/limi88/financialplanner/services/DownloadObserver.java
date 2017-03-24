package com.limi88.financialplanner.services;

import android.database.ContentObserver;
import android.os.Handler;

/**
 * Created by hehao
 * Date on 16/11/11.
 * Email: hao.he@sunanzq.com
 */
public class DownloadObserver extends ContentObserver {
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public DownloadObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
    }
}
