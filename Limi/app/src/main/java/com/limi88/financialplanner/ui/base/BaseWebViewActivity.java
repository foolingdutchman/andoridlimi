package com.limi88.financialplanner.ui.base;

import android.annotation.TargetApi;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hehao.library.base.BaseFragmentActivity;
import com.hehao.library.netstatus.NetUtils;
import com.limi88.financialplanner.BuildConfig;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.pojo.JsMessage;
import com.limi88.financialplanner.pojo.clients.Real;
import com.limi88.financialplanner.ui.customers.NewCustomersActivity;
import com.limi88.financialplanner.ui.login.LoginActivity;
import com.limi88.financialplanner.ui.webview.JavaScriptInterface;
import com.limi88.financialplanner.ui.webview.X5WebView;
import com.limi88.financialplanner.ui.widget.LoadingDialog;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.SecurityUtils;
import com.limi88.financialplanner.util.ShareUtils;
import com.limi88.financialplanner.util.ToastUtils;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import anet.channel.util.StringUtils;
import butterknife.Bind;
import cn.pedant.SweetAlert.SweetAlertDialog;
import gun0912.tedbottompicker.TedBottomPicker;

public class BaseWebViewActivity extends BaseFragmentActivity implements View.OnClickListener {
    @Bind(R.id.tv_header_back)
    TextView titleView;
    @Bind(R.id.common_title)
    TextView mCommontitle;
    @Bind(R.id.common_right_action)
    ImageView rightActionView;
    @Bind(R.id.ll_left_btn)
    LinearLayout leftAction;
    @Bind(R.id.common_left_action)
    ImageView mLeftAction;
    @Bind(R.id.web_container)
    FrameLayout mFrameLayout;
    //    @Bind(R.id.wv_products)
    private WebView mWebView;
    @Bind(R.id.progressBar)
    ProgressBar mProgressBar;

    @Bind(R.id.has_net)
    RelativeLayout hasNetView;
    @Bind(R.id.ll_errorview)
    LinearLayout errorNetView;

    private String title;
    private String targetUrl;
    private HashMap<String, String> extraHeaders;
    private UMShareListener umShareListener;
    private Dialog mDialog;
    private View inflate;
    private Window dialogWindow;
    private WindowManager mWidowManager;
    private WindowManager.LayoutParams lp;
    private Display d;
    private ImageView reloadImage;
    private Real mReal;
    private Bundle bundle;
    private ValueCallback<Uri> mUploadMessage;

    private ValueCallback<Uri[]> mValueCallback;
    private LoadingDialog mAlertDialog;
    private boolean isSelectFile = false;
    private int selectImgMax = 3;//选取图片最大数量
    private String code;
    private String tonce;
    private int photosType = 1;//图库类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QbSdk.allowThirdPartyAppDownload(true);
        PushAgent.getInstance(this).onAppStart();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (targetUrl.contains("insu") || targetUrl.contains("costumers")) {
            runWebViewProcess();
        }

        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_base_web_view;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        titleView.setVisibility(View.VISIBLE);
        leftAction.setVisibility(View.VISIBLE);
        mCommontitle.setVisibility(View.GONE);
        if (mWebView == null)
            mWebView = new WebView(this);
        mAlertDialog = new LoadingDialog(this);
        mWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mFrameLayout.addView(mWebView);
        umShareListener = new UMShareListener() {
            @Override
            public void onResult(SHARE_MEDIA platform) {
                Log.d("plat", "platform" + platform);
                ToastUtils.showToastForTest(platform + " 分享成功啦");
                dismissShareWindow();
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
        rightActionView.setOnClickListener(this);
        bundle = getIntent().getExtras();
        if (bundle != null) {

            if (bundle.getInt(Constants.WEB_PAGE_TAG) == Constants.WEB_CALCULATOR_TAG) {
                title = bundle.getString(Constants.WEB_CALCULATOR_TAG_TITLE);
                targetUrl = bundle.getString(Constants.WEB_CALCULATOR_TAG_LINK);
                setResult(Constants.LOGIN_SUCCESS_CODE);
            } else if (bundle.getInt(Constants.WEB_PAGE_TAG) == Constants.GO_DETAIL_TAG) {
                title = bundle.getString(Constants.WEB_PAGE_DETAIL_NAME);
                targetUrl = bundle.getString(Constants.WEB_PAGE_DETAIL_LINK);
                setResult(Constants.LOGIN_SUCCESS_CODE);
            } else if (bundle.getInt(Constants.WEB_PAGE_TAG) == Constants.FROM_HOME_WEB) {
                title = bundle.getString(Constants.WEB_PAGE_DETAIL_NAME);
                targetUrl = bundle.getString(Constants.WEB_PAGE_DETAIL_LINK);
                setResult(Constants.FROM_HOME_WEB);
            } else if (bundle.getInt(Constants.WEB_PAGE_TAG) == Constants.WEB_FAVOR_TAG) {
                targetUrl = bundle.getString(Constants.WEB_FAVOR_LINK);
                title = bundle.getString(Constants.WEB_FAVOR_NAME);
                setResult(Constants.LOGIN_SUCCESS_CODE);
            } else if (bundle.getInt(Constants.WEB_PAGE_TAG) == Constants.WEB_MINE_TAG) {
                targetUrl = bundle.getString(Constants.WEB_MINE_LINK);
                title = bundle.getString(Constants.WEB_MINE_NAME);
                setResult(Constants.LOGIN_SUCCESS_CODE);
            } else if (bundle.getInt(Constants.WEB_PAGE_TAG) == Constants.NEWS_TAG) {
                targetUrl = bundle.getString(Constants.WEB_MINE_LINK);
                title = bundle.getString(Constants.WEB_MINE_NAME);
                setResult(Constants.NEWS_TAG);
            } else if (bundle.getInt(Constants.WEB_PAGE_TAG) == Constants.CUSTOMER_DETAIL_TAG) {
                title = bundle.getString(Constants.WEB_PAGE_DETAIL_NAME);
                targetUrl = bundle.getString(Constants.WEB_PAGE_DETAIL_LINK);
                mReal = bundle.getParcelable(Constants.MODIFY_CUSTOMER_FLAG);
                setResult(Constants.NO_MODIFY_CUSTOMER_CODE);
            } else if (bundle.getInt(Constants.WEB_PAGE_TAG) == Constants.PRODUCTS_PAGE_FLAG) {
                targetUrl = bundle.getString(Constants.WEB_PAGE_DETAIL_LINK);
                title = bundle.getString("产品详情");
            }
        }

        titleView.setText(title);
        targetUrl = targetUrl + "?access_token=" + Constants.CURRENT_TOKEN;
        initShareBtn(targetUrl);
        initWebView(targetUrl);
        runWebViewProcess();
    }

    private void dismissShareWindow() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    private void initShareBtn(String url) {
        if (ShareUtils.isLinkShareAble(url)) {
            rightActionView.setVisibility(View.VISIBLE);
        } else {
            rightActionView.setVisibility(View.GONE);
        }
    }

    private void popUpShareWindow() {

        if (mDialog == null) {
            mDialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        }
        //填充对话框的布局
        inflate = LayoutInflater.from(this).inflate(R.layout.popup_share_window, null);
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
        mWidowManager = this.getWindowManager();
        lp = dialogWindow.getAttributes();
        d = mWidowManager.getDefaultDisplay(); // 获取屏幕宽、高用
//        lp.height = (int) (d.getHeight() * 0.4); // 高度设置为屏幕的0.5
        lp.width = (int) (d.getWidth() * 0.95); // 宽度设置为屏幕的0.8
        lp.y = 20;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);
        mDialog.show();//显示对话框
    }

    private String limitAgent;

    private void runWebViewProcess() {
        if (limitAgent == null)
            limitAgent = settings.getUserAgentString() + "limi88- v" + BuildConfig.VERSION_NAME;
        if (extraHeaders == null) {
            extraHeaders = new HashMap<>();
            settings.setUserAgentString(limitAgent);
        }

        tonce = SecurityUtils.getTimestamp();
        code = SecurityUtils.getHMACSHA1(tonce);
        extraHeaders.put(Constants.WEB_HEADER_TAG, Constants.CURRENT_TOKEN);
        extraHeaders.put("LimiAgent", limitAgent);
        extraHeaders.put("code", code);
        extraHeaders.put("tonce", tonce);
        mWebView.loadUrl(targetUrl, extraHeaders);
    }

    private WebSettings settings;
    private WebChromeClient webChromeClient;

    private void initWebView(String targetUrl) {
        if (settings == null && mWebView != null) {
            settings = mWebView.getSettings();
            settings.setJavaScriptEnabled(true);//启用js
            settings.setJavaScriptCanOpenWindowsAutomatically(true);//js和android交互
            mWebView.addJavascriptInterface(BaseWebViewActivity.this, "AndroidBridge");

            String cacheDirPath = ((MainApplication) getApplication()).getLogCacheDir() + "/web";
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
                if (mProgressBar != null)
                    mProgressBar.setVisibility(android.view.View.VISIBLE);
            }

            //网页加载完成时调用，隐藏加载提示旋转进度条
            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                initShareBtn(url);
                if (mWebView.canGoBack()) {
                    mLeftAction.setImageResource(R.mipmap.ic_common_cancel);

                } else mLeftAction.setImageResource(R.mipmap.ic_login_back);
                if (mProgressBar != null) {
                    mProgressBar.setVisibility(android.view.View.GONE);
                }

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
                if (mProgressBar != null) {
                    mProgressBar.setVisibility(android.view.View.GONE);
                }
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) { // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                boolean response = true;
                Log.i("shouldOverRide", url);
                if (url.startsWith("jsbridge")){

                    parseMessageFromJs(url.split("jsbridge://")[1]);
                    mWebView.stopLoading();


                } else if (url.contains("tel:")) {
                    mWebView.stopLoading();
                    response = false;
                } else if (url.equals(Constants.HOST + "/")) {
                    mWebView.stopLoading();
                    response = false;
                    setResult(Constants.HOME_FLAG);
                    BaseWebViewActivity.this.finish();
                } else {
                    view.loadUrl(url, extraHeaders);
                }
                initShareBtn(url);
                return response;

            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                BaseWebViewActivity.this.logRequest(request.getRequestHeaders());
                request.getRequestHeaders();
                return super.shouldInterceptRequest(view, request);
            }
        });

        if (webChromeClient == null)
            webChromeClient = new WebChromeClient() {


                @Override
                public void onReceivedIcon(WebView view, Bitmap icon) {
                    super.onReceivedIcon(view, icon);
                }

                @Override
                public void onReceivedTitle(WebView webView, String s) {
                    super.onReceivedTitle(webView, s);
                    if (StringUtils.isBlank(title) || mWebView.canGoBack()) {
                        titleView.setText(mWebView.getTitle());
                    } else titleView.setText(title);
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

//                    if (ContextCompat.checkSelfPermission(BaseWebViewActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                            != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(BaseWebViewActivity.this, Manifest.permission.CAMERA)
//                            != PackageManager.PERMISSION_GRANTED) {
//                        //申请WRITE_EXTERNAL_STORAGE, CAMERA权限
//                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
//                                Constants.WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
//                    } else {
//                        getPhoto();
//                    }


                        i = new Intent(Intent.ACTION_GET_CONTENT);

                        i.addCategory(Intent.CATEGORY_OPENABLE);

                        i.setType("image/*");

                        BaseWebViewActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), Constants.FILECHOOSER_RESULTCODE);

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
                            if (!mAlertDialog.isShow()) {
                                mAlertDialog.show();
                            }

                        }

//                    if (progressbar.getVisibility() == View.GONE)
//
//                        progressbar.setVisibility(View.VISIBLE);
//
//                    progressbar.setProgress(newProgress);

                    }

                    super.onProgressChanged(view, newProgress);

                }

            };
        mWebView.setWebChromeClient(webChromeClient);

        mWebView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

    }

    @JavaScriptInterface
    public void parseMessageFromJs(String message) {
        Log.i("Catch Js Message", message);
        jm = JsMessage.initFromString(message);
        if (jm.getType().equals(JsMessage.MODEL_TYPE)) {
            if (jm.getParams().get("name").equals("Customer")) {
                setResult(Constants.MODIFY_CUSTOMER_CODE);
                if (mReal != null) {
                    Intent mIntent = new Intent(BaseWebViewActivity.this, NewCustomersActivity.class);
                    mIntent.putExtras(bundle);
                    startActivityForResult(mIntent, Constants.MODIFY_CUSTOMER_CODE);
                }

            }

        } else if (jm.getType().equals(JsMessage.LOGIN_TYPE)) {
            Intent mIntent = new Intent(BaseWebViewActivity.this, LoginActivity.class);
            mIntent.putExtra(Constants.WEB_PAGE_DETAIL_LINK, jm.getParams().get("url"));
            startActivityForResult(mIntent, Constants.LOGIN_SUCCESS_CODE);
        } else if (jm.getType().equals(JsMessage.WEIXIN_SHARE_TYPE)) {
            if (jm.getParams().get("title") == null) {
                getWeixinShareCOnfig();
            } else {
                popUpShareWindow();
            }
        } else if (jm.getType().equals(JsMessage.GOHOME_TYPE)) {
            finish();
        }
    }

    private Uri getPhoto() {
        final Uri[] uri = new Uri[1];
        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(this)
                .setOnImageSelectedListener(
                        uri1 -> {
                            uri[0] = uri1;
                            mUploadMessage.onReceiveValue(uri1);
                        })
                .create();
        tedBottomPicker.show(getSupportFragmentManager());
        return uri[0];
    }

    private JsMessage jm;
    private int LOGIN_CODE = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.MODIFY_CUSTOMER_CODE) {
            if (resultCode == Constants.MODIFY_CUSTOMER_CODE) {
                if (data!=null) {
                    targetUrl = data.getStringExtra(Constants.WEB_PAGE_DETAIL_LINK);
                }
                mWebView.loadUrl(targetUrl, extraHeaders);
            } else if (resultCode == Constants.DELETE_CUSTOMER_CODE) {
                finish();
            }
        } else if (requestCode == Constants.LOGIN_SUCCESS_CODE && requestCode == Constants.LOGIN_SUCCESS_CODE) {
            mWebView.loadUrl(data.getStringExtra(Constants.WEB_PAGE_DETAIL_LINK));
        }

        if (requestCode == Constants.FILECHOOSER_RESULTCODE) {

            if (this.photosType <= 0) {//调用自定义图库

                uploadImgFromMyPhotos();

            } else {//调用系统图库

                uploadImgFromSysPhotos(resultCode, data);

            }

        }
    }

    private void newWin(WebSettings mWebSettings) {
        //html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
        //然后 复写 WebChromeClient的onCreateWindow方法
        mWebSettings.setSupportMultipleWindows(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    private void saveData(WebSettings mWebSettings) {
        //有时候网页需要自己保存一些关键数据,Android WebView 需要自己设置
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebSettings.setAppCacheEnabled(true);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        mWebSettings.setAppCachePath(appCachePath);
    }


    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {
        errorNetView.setVisibility(View.VISIBLE);
        hasNetView.setVisibility(View.GONE);
        reloadImage = (ImageView) findViewById(R.id.iv_error_reload);
        reloadImage.setOnClickListener(v -> {
            if (NetUtils.isNetworkAvailable(this)) {
                errorNetView.setVisibility(View.GONE);
                hasNetView.setVisibility(View.VISIBLE);
                mWebView.reload();
            }
        });
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            if (mProgressBar != null) {
                mProgressBar.setVisibility(View.INVISIBLE);
                mProgressBar = null;
            }
            if (mAlertDialog != null) {
                mAlertDialog = null;
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    public String getCurrentTitle() {
        return title;
    }

    public void setCurrentTitle(String title) {
        this.title = title;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public void logRequest(Map<String, String> requests) {
        Set<String> keyset = requests.keySet();

        Iterator<String> it = keyset.iterator();
        Log.i("webview", "start------------------------------");
        while (it.hasNext()) {
            String key = it.next();
            String value = requests.get(key);
            Log.i("webview", key + "----" + value);

        }
        Log.i("webview", "end------------------------------");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == Constants.WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
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

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.setTag(null);
            mWebView.clearHistory();
            mWebView.destroy();
            mWebView = null;
            mAlertDialog = null;
            mDialog = null;

        }
        try {
            Thread.currentThread().sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.onDestroy();

    }


    private void
    getWeixinShareCOnfig() {
        mWebView.loadUrl("javascript:LimiJSBridge.getCurWeixinConfig()");
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_right_action:
                if (targetUrl.contains("mp.weixin.qq.com")) {
                    getShareContentFromPage();
                } else
                    getWeixinShareCOnfig();
                break;
            case R.id.iv_share_wechat:
                new ShareAction(BaseWebViewActivity.this).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withText(jm.getParams().get("desc"))
                        .withTitle(jm.getParams().get("title"))
                        .withExtra(new UMImage(this, jm.getParams().get("imgUrl")))
                        .withMedia(new UMImage(this, jm.getParams().get("imgUrl")))
                        .withTargetUrl(jm.getParams().get("link"))
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.iv_share_wxcircle:
                new ShareAction(BaseWebViewActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                        .withText(jm.getParams().get("desc"))
                        .withTitle(jm.getParams().get("title"))
                        .withMedia(new UMImage(this, jm.getParams().get("imgUrl")))
                        .withExtra(new UMImage(this, jm.getParams().get("imgUrl")))
                        .withTargetUrl(jm.getParams().get("link"))
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.tv_share_cancel:
                mDialog.dismiss();
                break;
        }
    }

    public void backPre(View v) {

        this.finish();

    }

    private void uploadImgFromSysPhotos(int resultCode, Intent intent) {

        if (mUploadMessage != null) {//5.0以下

            Uri result = intent == null || resultCode != RESULT_OK ? null

                    : intent.getData();

            mUploadMessage.onReceiveValue(result);

            mUploadMessage = null;

        } else if (mValueCallback != null) {//5.0+

            Uri[] uris = new Uri[1];

            uris[0] = intent == null || resultCode != RESULT_OK ? null

                    : intent.getData();

            if (uris[0] != null) {

                mValueCallback.onReceiveValue(uris);
                mWebView.stopLoading();
            }

            mValueCallback = null;

        }

    }


    /**
     * 上传图片，调用自己图库 与h5 file标签交互
     *
     * @author linjinpeng 2015年11月30日 12:22:23
     */

    private void uploadImgFromMyPhotos() {

//        if (mValueCallback != null) {//5.0+
//
//            Uri[] uris = MediaSelectHelper.getImgPathToUriArray();
//
//            if (uris != null){
//
//                mValueCallback.onReceiveValue(uris);
//
//            }
//
//            mValueCallback = null;
//
//        } else if (mUploadMessage != null) {//5.0及以下
//
//            Uri uri = MediaSelectHelper.getImgPathToUri();
//
//            mUploadMessage.onReceiveValue(uri);
//
//            mUploadMessage = null;
//
//        }

    }


    /**
     * js调用 setSelectImgMax 设置本地图片选取图片数量的最大值
     *
     * @param selectImgMax 默认值为1
     * @param photosType   type<=0?调用自己的图库:调用系统图库
     * @author linjinpeng 2015年11月30日 12:17:48
     */

    @JavascriptInterface

    public void setSelectImgMax(int selectImgMax, int photosType) {

        this.selectImgMax = selectImgMax;

        this.photosType = photosType;

    }

    public void getShareContentFromPage() {

        String o = "{type: 'weixin_share', params: {title: msg_title, link: msg_link, desc: msg_desc, imgUrl: msg_cdn_url}}";
        mWebView.evaluateJavascript("(function(){ return " + o + "})();", s ->
        {
            Log.i("SHARE", s);
            jm = JsMessage.initFromString(s);
            jm.getParams().put("link", targetUrl);
            popUpShareWindow();
        });


    }
}
