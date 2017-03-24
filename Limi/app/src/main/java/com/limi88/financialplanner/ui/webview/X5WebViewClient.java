package com.limi88.financialplanner.ui.webview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsPromptResult;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by hehao
 * Date on 16/10/11.
 * Email: hao.he@sunanzq.com
 */
public class X5WebViewClient extends WebViewClient {
    /**
     * 防止加载网页时调起系统浏览器
     */
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

    public void onReceivedHttpAuthRequest(WebView webview,
                                          com.tencent.smtt.export.external.interfaces.HttpAuthHandler httpAuthHandlerhost, String host,
                                          String realm) {
        boolean flag = httpAuthHandlerhost.useHttpAuthUsernamePassword();
    }

}
