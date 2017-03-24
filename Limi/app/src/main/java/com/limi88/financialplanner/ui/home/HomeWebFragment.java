package com.limi88.financialplanner.ui.home;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.limi88.financialplanner.BuildConfig;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.pojo.JsMessage;
import com.limi88.financialplanner.ui.MainActivity;
import com.limi88.financialplanner.ui.base.BaseSafeFragment;
import com.limi88.financialplanner.ui.base.BaseWebViewActivity;
import com.limi88.financialplanner.ui.base.FragmentBackHandler;
import com.limi88.financialplanner.ui.login.LoginActivity;
import com.limi88.financialplanner.ui.widget.LoadingDialog;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.SecurityUtils;
import com.limi88.financialplanner.util.ToastUtils;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.File;
import java.util.HashMap;
import java.util.Stack;

import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;
import gun0912.tedbottompicker.TedBottomPicker;


/**
 * Created by hehao
 * Date on 16/11/3.
 * Email: hao.he@sunanzq.com
 */
public class HomeWebFragment extends BaseSafeFragment implements FragmentBackHandler, View.OnClickListener {
    @Bind(R.id.common_title)
    TextView mCommonTitle;
    @Bind(R.id.webcontainer)
    RelativeLayout mWebcontainer;
    @Bind(R.id.iv_error_reload)
    ImageView reloadView;
    @Bind(R.id.ll_left_btn)
    LinearLayout mLeftActionBtn;
    @Bind(R.id.common_right_action)
    ImageView mRightAction;
    @Bind(R.id.tv_header_back)
    TextView mLeftView;
    private WebView mWebView;
    private LinearLayout mLinearLayout;

    private LayoutInflater layoutInflater;
    private Bundle bundle;
    private Intent mIntent;
    private String targetUrl;
    private String cacheDirPath;
    private boolean isVisible = false;
    private LoadingDialog mAlertDialog;
    private String code;
    private String tonce;
    private UMShareListener umShareListener;
    private Dialog mDialog;
    private View inflate;
    private Window dialogWindow;
    private WindowManager mWidowManager;
    private WindowManager.LayoutParams lp;
    private Display d;
    private int selectImgMax = 3;//选取图片最大数量
    private int photosType = 1;//图库类型
    private ValueCallback<Uri[]> mValueCallback;
    private ValueCallback<Uri> mUploadMessage;
    private String title = "理米理财规划师";
    private Stack<String> titles = new Stack<>();
    private int getWeiXinShareTimes = 0;

    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {
        isVisible = true;
    }

    @Override
    protected void onUserInvisible() {
        isVisible = false;
    }

    @Override
    protected void initViewsAndEvents() {
        mCommonTitle.setText(title);
        mLeftActionBtn.setOnClickListener(this);
        mRightAction.setOnClickListener(this);
        cacheDirPath = ((MainApplication) getActivity().getApplication()).getLogCacheDir() + "/web";
        reloadView.setOnClickListener(v -> reloadWebView());
        mAlertDialog = new LoadingDialog(getActivity());
        mWebView = new WebView(getActivity().getApplicationContext());
        mWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mWebcontainer.addView(mWebView);
        umShareListener = new UMShareListener() {
            @Override
            public void onResult(SHARE_MEDIA platform) {
                Log.d("plat", "platform" + platform);
                ToastUtils.showToastForTest(platform + " 分享成功啦");
            }

            @Override
            public void onError(SHARE_MEDIA platform, Throwable t) {
                ToastUtils.showToastForTest(platform + " 分享失败啦");
                if (t != null) {
                    Log.d("throw", "throw:" + t.getMessage());
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA platform) {
                ToastUtils.showToastForTest(platform + " 分享取消啦");
            }
        };
        targetUrl = Constants.HOST + "?access_token=" + Constants.CURRENT_TOKEN;
        initWebView(targetUrl);
        runWebViewProcess();
    }

    public void reloadHomeWeb() {
        targetUrl = Constants.HOST + "?access_token=" + Constants.CURRENT_TOKEN;
        initWebView(targetUrl);
        runWebViewProcess();
        reSetTitleBarStyle();
    }


    private String limitAgent;
    private HashMap<String, String> extraHeaders;

    private void runWebViewProcess() {
        if (limitAgent == null) limitAgent = settings.getUserAgentString() + "limi88 -v"+ BuildConfig.VERSION_NAME;
        if (extraHeaders == null) {
            extraHeaders = new HashMap<>();
            settings.setUserAgentString(limitAgent);
        }
        extraHeaders.put(Constants.WEB_HEADER_TAG, Constants.CURRENT_TOKEN);
        extraHeaders.put("LimiAgent", limitAgent);
        tonce = SecurityUtils.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        extraHeaders.put("code", code);
        extraHeaders.put("tonce", tonce);
        mWebView.loadUrl(targetUrl, extraHeaders);
    }

    private WebSettings settings;

    private void initWebView(String targetUrl) {
        if (settings == null) {
            settings = mWebView.getSettings();
            settings.setJavaScriptEnabled(true);//启用js
            settings.setJavaScriptCanOpenWindowsAutomatically(true);//js和android交互
            String cacheDirPath = ((MainApplication) getActivity().getApplication()).getLogCacheDir() + "/web";
            settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
            // 建议缓存策略为，判断是否有网络，有的话，使用LOAD_DEFAULT,无网络时，使用LOAD_CACHE_ELSE_NETWORK
//            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 设置缓存模式
            settings.setCacheMode(WebSettings.LOAD_DEFAULT); // 设置缓存模式
            // 开启DOM storage API 功能
            settings.setDomStorageEnabled(true);
            // 开启database storage API功能
            settings.setDatabaseEnabled(true);
            Log.i("cachePath", cacheDirPath);
            // 设置数据库缓存路径
            settings.setDatabasePath(cacheDirPath); // API 19 deprecated
            settings.setAppCachePath(cacheDirPath); //设置缓存的指定路径
            settings.setAllowFileAccess(true); // 允许访问文件
            settings.setAppCacheEnabled(true); //设置H5的缓存打开,默认关闭
            settings.setUseWideViewPort(true);//设置webview自适应屏幕大小
            //设置，可能的话使所有列的宽度不超过屏幕宽度
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

            settings.setLoadWithOverviewMode(true);//设置webview自适应屏幕大小
            settings.setDomStorageEnabled(true);//设置可以使用localStorage
            settings.setSupportZoom(false);//关闭zoom按钮
            settings.setBuiltInZoomControls(false);//关闭zoom
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mWebView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            mWebView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            mWebView.getSettings().setLoadsImagesAutomatically(false);
        }
        mWebView.setWebViewClient(new WebViewClient() {
            //网页加载开始时调用，显示加载提示旋转进度条
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
                Log.i("webstart", url);
                dismissErrorView();
                if (isVisible) {
                    mAlertDialog.show();
                }

//                if (mProgressBar != null)
//                    mProgressBar.setVisibility(android.view.View.VISIBLE);
            }

            //网页加载完成时调用，隐藏加载提示旋转进度条
            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
//                initShareBtn(url);
                mAlertDialog.dismiss();

//                if (mProgressBar != null) {
//                    mProgressBar.setVisibility(android.view.View.GONE);
//                }

                if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
                    mWebView.getSettings().setLoadsImagesAutomatically(true);
                }
            }

            //网页加载失败时调用，隐藏加载提示旋转进度条
            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // TODO Auto-generated method stub
                super.onReceivedError(view, errorCode, description, failingUrl);
                showErrorView();
//                if (mProgressBar != null) {
//                    mProgressBar.setVisibility(android.view.View.GONE);
//                }
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) { // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                Log.i("shouldOverRide", url);
                mWebView.stopLoading();
                return false;
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }

            @Override
            public void onReceivedTitle(WebView webView, String s) {
                super.onReceivedTitle(webView, s);

            }

            @Override
            public void onGeolocationPermissionsHidePrompt() {
                super.onGeolocationPermissionsHidePrompt();
            }

            @Override
            public void onGeolocationPermissionsShowPrompt(final String origin, final GeolocationPermissionsCallback callback) {
                callback.invoke(origin, true, false);//注意个函数，第二个参数就是是否同意定位权限，第三个是是否希望内核记住
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
            //=========HTML5定位==========================================================


            //=========多窗口的问题==========================================================
            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(mWebView);
                resultMsg.sendToTarget();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView webView, String url, String message, JsResult jsResult) {
                Log.i("Catch Confirm in JS: ", message);
                if (message.startsWith("{")) {
                    return parseMessageFromJs(url, message);
                } else {
                    return super.onJsConfirm(webView, url, message, jsResult);
                }
            }

            @Override
            public boolean onJsAlert(WebView webView, String s, String message, JsResult jsResult) {
                ToastUtils.showToast(message);
                return false;
            }


            // For Android 5.0+
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback,
                                             android.webkit.WebChromeClient.FileChooserParams fileChooserParams) {
                mValueCallback = valueCallback;
                selectImgMax = selectImgMax > 1 ? selectImgMax : 1;
                goToPhotos(selectImgMax);
                return true;
            }

            // For Android 3.0+

            public void openFileChooser(ValueCallback uploadMsg) {
                Log.i("WebView", "openFileCHooser1:------" + uploadMsg);

                mUploadMessage = uploadMsg;

                selectImgMax = 1;

                goToPhotos(selectImgMax);

            }


            //3.0--版本

            public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
                Log.i("WebView", "openFileCHooser2:------" + uploadMsg);
                mUploadMessage = uploadMsg;
                openFileChooser(uploadMsg);

            }


            // For Android 4.1
            @Override
            public void openFileChooser(ValueCallback uploadMsg, String acceptType,

                                        String capture) {
                Log.i("WebView", "openFileCHooser3:------" + uploadMsg);
                mUploadMessage = uploadMsg;
                openFileChooser(uploadMsg);

            }


            /**

             * 进入本地图库

             *

             * @param select_image_max //设置选取最大值

             */

            @TargetApi(Build.VERSION_CODES.M)
            private void goToPhotos(int select_image_max) {

                Intent i;

                if (photosType <= 0) {//进入自定义图库

//                    i = new Intent(WebviewAct.this, MediaSelectActivity.class);
//
//                    i.putExtra("select_mode", 2);
//
//                    i.putExtra("select_image_max", select_image_max);
//
//                    WebviewAct.this.startActivityForResult(i, FILECHOOSER_RESULTCODE);

                } else {//进入系统图库

//                    if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
//                            != PackageManager.PERMISSION_GRANTED) {
//                        //申请WRITE_EXTERNAL_STORAGE, CAMERA权限
//                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
//                                Constants.WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
//                    } else {
//                        getPhoto();
//                    }

                    i

                            = new Intent(Intent.ACTION_GET_CONTENT);

                    i.addCategory(Intent.CATEGORY_OPENABLE);

                    i.setType("image/*");

                    getActivity().startActivityForResult(Intent.createChooser(i, "File Chooser"), Constants.FILECHOOSER_RESULTCODE);

                }

            }

            @Override

            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress == 100) {
                    if (mAlertDialog != null) {
                        mAlertDialog.dismiss();
                    }
                } else {
                    if (mAlertDialog != null) {

                        mAlertDialog.show();
                    }
                }

                super.onProgressChanged(view, newProgress);

            }


        });


        mWebView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
    }

    private void showErrorView() {
        mWebView.setVisibility(View.GONE);

    }

    private void dismissErrorView() {
        mWebView.setVisibility(View.VISIBLE);
    }

    private void reloadWebView() {
        mWebView.loadUrl(targetUrl, extraHeaders);
    }

    public void clearWebViewCache() {
        // 清理WebView缓存数据库
        try {
            getActivity().deleteDatabase("webview.db");
            getActivity().deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // WebView缓存文件
        File appCacheDir = new File(cacheDirPath);
        Log.e("webview", "appCacheDir path=" + cacheDirPath);

        File webviewCacheDir = new File(cacheDirPath);
        Log.e("webview", "appCacheDir path=" + webviewCacheDir.getAbsolutePath());

        // 删除webView缓存目录
        if (webviewCacheDir.exists()) {
            deleteFile(webviewCacheDir);
        }
        // 删除webView缓存，缓存目录
        if (appCacheDir.exists()) {
            deleteFile(appCacheDir);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                getPhoto();
                Log.i("permission", "WRITE_EXTERNAL_STORAGE_REQUEST_CODE------------permited");
            } else {
                // Permission Denied
                Log.i("permission", "WRITE_EXTERNAL_STORAGE_REQUEST_CODE------------denied");
            }
        }
        if (requestCode == Constants.CAMERA_REQUEST_CODE) {

        }
    }

    public void deleteFile(File file) {
        Log.i("webview", "delete file path=" + file.getAbsolutePath());
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.e("webview", "delete file no exists " + file.getAbsolutePath());
        }
    }

    private JsMessage jm;

    private boolean parseMessageFromJs(String url, String message) {

        jm = JsMessage.initFromString(message);
        if (jm.getType().equals(JsMessage.LOGIN_TYPE)) {
            Intent mIntent=new Intent(getActivity(), LoginActivity.class);
//            mIntent.putExtra(Constants.WEB_PAGE_DETAIL_LINK, jm.getParams().get("url"));
            startActivityForResult(mIntent, Constants.LOGIN_SUCCESS_CODE);
        } else if (jm.getType().equals(JsMessage.WEIXIN_SHARE_TYPE)) {
            if (jm.getParams().get("title") == null) {
                if (getWeiXinShareTimes <= 3) {
                    getWeixinShareCOnfig();
                    getWeiXinShareTimes++;
                }

            } else {
                popUpShareWindow();
            }
        } else if (jm.getType().equals(JsMessage.F7_TYPE)) {

            HashMap<String, String> params = jm.getParams();

            targetUrl = params.get("url");
            title = params.get("title");
            if (title != "" && title != null) {
                loadWebAsSubPage(targetUrl, title);
            }

        } else if (jm.getType().equals(JsMessage.BACK_TYPE)) {
            HashMap<String, String> params = jm.getParams();
            targetUrl = params.get("url");
            goBackStyle(targetUrl);
            //打开二级界面
        } else if (jm.getType().equals(JsMessage.NEW_WEB_TYPE)) {
            HashMap<String, String> params = jm.getParams();
            targetUrl = params.get("url");
            title = params.get("title");
            if (bundle != null) {
                bundle = null;
            }
            bundle = new Bundle();
            mIntent = new Intent(getActivity(), BaseWebViewActivity.class);
            bundle.putInt(Constants.WEB_PAGE_TAG, Constants.FROM_HOME_WEB);
            bundle.putString(Constants.WEB_PAGE_DETAIL_LINK, targetUrl);
            mIntent.putExtras(bundle);
            startActivityForResult(mIntent, Constants.HOME_FLAG);

        }else if (jm.getType().equals(JsMessage.SHOWBAR_TYPE)) {
            ((MainActivity) getActivity()).showBottomTab();
        }else if (jm.getType().equals(JsMessage.HIDEBAR_TYPE)) {
            ((MainActivity) getActivity()).hideBottomTab();
        }
        return false;

    }

    private void goBackStyle(String url) {
        if (!mWebView.canGoBack()) {
            reSetTitleBarStyle();
        } else {

            if (!titles.isEmpty()) {
                titles.pop();
                if (!titles.empty()) {
                    title = titles.peek();
                    mLeftView.setVisibility(View.VISIBLE);
                    mLeftView.setText(title);
                    mCommonTitle.setVisibility(View.GONE);
                } else {
                    reSetTitleBarStyle();
                }


            }
            if (url.contains("news_details") && !url.contains("comment")) {
                mRightAction.setVisibility(View.VISIBLE);
            } else mRightAction.setVisibility(View.GONE);

        }
    }

    public void reSetTitleBarStyle() {
        mLeftActionBtn.setVisibility(View.GONE);
        mRightAction.setVisibility(View.GONE);
        title = "理米理财规划师";
        mLeftView.setVisibility(View.GONE);
        mCommonTitle.setVisibility(View.VISIBLE);
        mCommonTitle.setText(title);
        ((MainActivity) getActivity()).showBottomTab();
        titles.clear();
    }

    private void loadWebAsSubPage(String url, String title) {
        if (!title.equals("理米理财规划师")) {
            mLeftActionBtn.setVisibility(View.VISIBLE);
            mLeftView.setVisibility(View.VISIBLE);
            mLeftView.setText(title);
            mCommonTitle.setVisibility(View.GONE);
            ((MainActivity) getActivity()).hideBottomTab();

            if (url.contains("news_details") && !url.contains("comment")) {
                mRightAction.setVisibility(View.VISIBLE);
            } else mRightAction.setVisibility(View.GONE);
            titles.push(title);
        } else {
            reSetTitleBarStyle();
        }


//        mWebView.loadUrl(url);

    }

    private void popUpShareWindow() {
        if (mDialog == null) {
            mDialog = new Dialog(getActivity(), R.style.ActionSheetDialogStyle);
        }
        //填充对话框的布局
        inflate = LayoutInflater.from(getActivity()).inflate(R.layout.popup_share_window, null);
        //初始化控件
        ImageView wechatIcon = (ImageView) inflate.findViewById(R.id.iv_share_wechat);
        ImageView wxCircleIcon = (ImageView) inflate.findViewById(R.id.iv_share_wxcircle);
        TextView cancelShareText = (TextView) inflate.findViewById(R.id.tv_share_cancel);
        wechatIcon.setOnClickListener(this);
        wxCircleIcon.setOnClickListener(this);
        cancelShareText.setOnClickListener(this);
        //将布局设置给Dialog
        mDialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        dialogWindow = mDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        mWidowManager = getActivity().getWindowManager();
        lp = dialogWindow.getAttributes();
        d = mWidowManager.getDefaultDisplay(); // 获取屏幕宽、高用
//        lp.height = (int) (d.getHeight() * 0.4); // 高度设置为屏幕的0.5
        lp.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.8
        lp.y = 20;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);
        mDialog.show();//显示对话框
    }

    private void getWeixinShareCOnfig() {
        mWebView.loadUrl("javascript:LimiJSBridge.getCurWeixinConfig()");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home_web;
    }


    @Override
    public boolean onBackPressed() {

        if (mWebView.canGoBack()) {
//           goBackStyle(targetUrl);
            mWebView.goBack();
            if (!mWebView.canGoBack()) {
                reSetTitleBarStyle();
            }
            return true;
        } else {
            return false;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.setTag(null);
            mWebView.clearHistory();
            mWebView.destroy();
            mWebView = null;
            mAlertDialog=null;
            mDialog=null;

        }
        try {
            Thread.currentThread().sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public Uri getPhoto() {
        final Uri[] uri = new Uri[1];
        TedBottomPicker tedBottomPicker = new TedBottomPicker
                .Builder(getActivity())
                .showCameraTile(false)
                .setOnImageSelectedListener(
                        uri1 -> {
                            uri[0] = uri1;
                            mUploadMessage.onReceiveValue(uri1);
                        })
                .create();
        tedBottomPicker.show(getSupportFragmentManager());
        return uri[0];
    }

    public void uploadImgFromSysPhotos(int resultCode, Intent intent) {
        if (mUploadMessage != null) {//5.0以下

            Uri result = intent == null || resultCode != Activity.RESULT_OK ? null

                    : intent.getData();

            mUploadMessage.onReceiveValue(result);

            mUploadMessage = null;

        } else if (mValueCallback != null) {//5.0+

            Uri[] uris = new Uri[1];

            uris[0] = intent == null || resultCode != Activity.RESULT_OK ? null

                    : intent.getData();

            if (uris[0] != null) {

                mValueCallback.onReceiveValue(uris);
                mWebView.stopLoading();
            }

            mValueCallback = null;

        }

    }

    @Override
    public void onClick(View v) {
        String desc=jm.getParams().get("desc");
        if (desc==null||desc.equals("")) {
            desc=" ";
        }
        String title=jm.getParams().get("title");
        String imgUrl=jm.getParams().get("imgUrl");
        String link=jm.getParams().get("link");
        switch (v.getId()) {
            case R.id.common_right_action:
                getWeixinShareCOnfig();
                break;
            case R.id.iv_share_wechat:

                new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withText(desc)
                        .withTitle(title)
                        .withExtra(new UMImage(getActivity(), imgUrl))
                        .withMedia(new UMImage(getActivity(), imgUrl))
                        .withTargetUrl(link)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.iv_share_wxcircle:

                new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withText(desc)
                        .withTitle(title)
                        .withExtra(new UMImage(getActivity(), imgUrl))
                        .withMedia(new UMImage(getActivity(), imgUrl))
                        .withTargetUrl(link)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.tv_share_cancel:
                mDialog.dismiss();
                break;
            case R.id.ll_left_btn:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                    if (!mWebView.canGoBack()) {
                        reSetTitleBarStyle();
                    }
                }

//                goBackStyle(targetUrl);
                break;
        }
    }
}
