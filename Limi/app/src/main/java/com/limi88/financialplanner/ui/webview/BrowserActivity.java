package com.limi88.financialplanner.ui.webview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hehao.library.base.BaseFragmentActivity;
import com.hehao.library.netstatus.NetUtils;
import com.limi88.financialplanner.BuildConfig;
import com.limi88.financialplanner.MainApplication;
import com.limi88.financialplanner.R;
import com.limi88.financialplanner.pojo.JsMessage;
import com.limi88.financialplanner.pojo.clients.Real;
import com.limi88.financialplanner.ui.customers.NewCustomersActivity;
import com.limi88.financialplanner.ui.login.LoginActivity;
import com.limi88.financialplanner.ui.widget.LoadingDialog;
import com.limi88.financialplanner.util.Constants;
import com.limi88.financialplanner.util.SecurityUtils;
import com.limi88.financialplanner.util.ShareUtils;
import com.limi88.financialplanner.util.ToastUtils;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.smtt.utils.TbsLog;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import anet.channel.util.StringUtils;
import butterknife.Bind;

public class BrowserActivity extends BaseFragmentActivity {


	/**
	 * 作为一个浏览器的示例展示出来，采用android+web的模式
	 */
	private WebView mWebView;
	private ViewGroup mViewParent;
//	private ImageButton mBack;
//	private ImageButton mForward;
//	private ImageButton mRefresh;
//	private ImageButton mExit;
//	private ImageButton mHome;
//	private ImageButton mMore;
//	private ImageButton mClearData;
//	private ImageButton mOpenFile;
//	private Button mGo;
//	private EditText mUrl;

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
	private String limitAgent;
	private ValueCallback<Uri[]> mValueCallback;
	private LoadingDialog mAlertDialog;
	private boolean isSelectFile=false;
	private int selectImgMax = 3;//选取图片最大数量
	private String code;
	private String tonce;
	private int photosType = 1;//图库类型

    private WebSettings webSetting;
	private RelativeLayout mMenu;
	
	private static final String mHomeUrl =  "https://dev.sunanzq.com";
	private static final String TAG = "SdkDemo";
	private static final int MAX_LENGTH = 14;
	private boolean mNeedTestPage = false;

	private final int disable = 120;
	private final int enable = 255;

//	private ProgressBar mPageLoadingProgressBar = null;

	private ValueCallback<Uri> uploadFile;

	private URL mIntentUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.TRANSLUCENT);



	}
	private void webViewTransportTest(){
		X5WebView.setSmallWebViewEnabled(true);
	}

	private void changGoForwardButton(WebView view) {
//		if (view.canGoBack())
//			mBack.setAlpha(enable);
//		else
//			mBack.setAlpha(disable);
//		if (view.canGoForward())
//			mForward.setAlpha(enable);
//		else
//			mForward.setAlpha(disable);
//		if (view.getUrl()!=null && view.getUrl().equalsIgnoreCase(mHomeUrl)) {
//			mHome.setAlpha(disable);
//			mHome.setEnabled(false);
//		} else {
//			mHome.setAlpha(enable);
//			mHome.setEnabled(true);
//		}
	}

	private void initProgressBar() {
//		mPageLoadingProgressBar = (ProgressBar) findViewById(R.id.progressBar1);// new
																				// ProgressBar(getApplicationContext(),
																				// null,
																				// android.R.attr.progressBarStyleHorizontal);
//		mPageLoadingProgressBar.setMax(100);
//		mPageLoadingProgressBar.setProgressDrawable(this.getResources()
//				.getDrawable(R.drawable.color_progressbar));
	}





	private void init() {

		//
		//mWebView = new DemoWebView(this);
		Log.e("0819", " before is " + System.currentTimeMillis());
		mWebView = new WebView(this);
		Log.e("0819", " after is " + System.currentTimeMillis());


		Log.w("grass", "Current SDK_INT:" + Build.VERSION.SDK_INT);

		mViewParent.addView(mWebView, new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.FILL_PARENT,
				FrameLayout.LayoutParams.FILL_PARENT));

		initProgressBar();

		mWebView.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}

			@Override
			public WebResourceResponse shouldInterceptRequest(WebView view,
															  WebResourceRequest request) {
				// TODO Auto-generated method stub

				Log.e("should", "request.getUrl().toString() is " + request.getUrl().toString());

				return super.shouldInterceptRequest(view, request);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				moreMenuClose();
				// mTestHandler.sendEmptyMessage(MSG_OPEN_TEST_URL);
				mTestHandler.sendEmptyMessageDelayed(MSG_OPEN_TEST_URL, 5000);// 5s?
				if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 16)
					changGoForwardButton(view);
				/* mWebView.showLog("test Log"); */

			}
		});

		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String title) {
				TbsLog.d(TAG, "title: " + title);
//				if (mUrl == null)
//					return;
//				if (!mWebView.getUrl().equalsIgnoreCase(mHomeUrl)) {
//					if (title != null && title.length() > MAX_LENGTH)
//						mUrl.setText(title.subSequence(0, MAX_LENGTH) + "...");
//					else
//						mUrl.setText(title);
//				} else {
//					mUrl.setText("");
//				}
			}

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
//				mPageLoadingProgressBar.setProgress(newProgress);
//				if (mPageLoadingProgressBar != null && newProgress != 100) {
//					mPageLoadingProgressBar.setVisibility(View.VISIBLE);
//				} else if (mPageLoadingProgressBar != null) {
//					mPageLoadingProgressBar.setVisibility(View.GONE);
//				}
			}
		});

		mWebView.setDownloadListener(new DownloadListener() {

			@Override
			public void onDownloadStart(String arg0, String arg1, String arg2,
										String arg3, long arg4) {
				TbsLog.d(TAG, "url: " + arg0);
				new AlertDialog.Builder(BrowserActivity.this)
						.setTitle("�Ƿ�����")
						.setPositiveButton("yes",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
														int which) {
										Toast.makeText(
												BrowserActivity.this,
												"fake message: i'll download...",
												Toast.LENGTH_SHORT).show();
									}
								})
						.setNegativeButton("no",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
														int which) {
										// TODO Auto-generated method stub
										Toast.makeText(
												BrowserActivity.this,
												"fake message: refuse download...",
												Toast.LENGTH_SHORT).show();
									}
								})
						.setOnCancelListener(
								new DialogInterface.OnCancelListener() {

									@Override
									public void onCancel(DialogInterface dialog) {
										// TODO Auto-generated method stub
										Toast.makeText(
												BrowserActivity.this,
												"fake message: refuse download...",
												Toast.LENGTH_SHORT).show();
									}
								}).show();
			}
		});


		webSetting = mWebView.getSettings();
		webSetting.setAllowFileAccess(true);
		webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		webSetting.setSupportZoom(true);
		webSetting.setBuiltInZoomControls(true);
		webSetting.setUseWideViewPort(true);
		webSetting.setSupportMultipleWindows(false);
		//webSetting.setLoadWithOverviewMode(true);
		webSetting.setAppCacheEnabled(true);
		//webSetting.setDatabaseEnabled(true);
		webSetting.setDomStorageEnabled(true);
		webSetting.setJavaScriptEnabled(true);
		webSetting.setGeolocationEnabled(true);
		webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
		webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
		webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
		webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
				.getPath());
		// webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
//		webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
		//webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
		// webSetting.setPreFectch(true);
		long time = System.currentTimeMillis();
		if (mIntentUrl == null) {
			mWebView.loadUrl(mHomeUrl);
		} else {
			mWebView.loadUrl(mIntentUrl.toString());
		}
		TbsLog.d("time-cost", "cost time: "
				+ (System.currentTimeMillis() - time));
		CookieSyncManager.createInstance(this);
		CookieSyncManager.getInstance().sync();
	}

	private void moreMenuClose()
	{
		if (mMenu!=null && mMenu.getVisibility()== View.VISIBLE)
		{
			mMenu.setVisibility(View.GONE);
//			mMore.setImageDrawable(getResources().getDrawable(R.drawable.theme_toolbar_btn_menu_fg_normal));
		}
	}

	private void initBtnListenser() {
//		mBack = (ImageButton) findViewById(R.id.btnBack1);
//		mForward = (ImageButton) findViewById(R.id.btnForward1);
//		mRefresh = (ImageButton) findViewById(R.id.btnRefresh1);
//		mExit = (ImageButton) findViewById(R.id.btnExit1);
//		mHome = (ImageButton) findViewById(R.id.btnHome1);
//		mGo = (Button) findViewById(R.id.btnGo1);
//		mUrl = (EditText) findViewById(R.id.editUrl1);
//		mMore = (ImageButton) findViewById(R.id.btnMore);
//		mMenu = (RelativeLayout) findViewById(R.id.menuMore);
//		mClearData = (ImageButton) findViewById(R.id.btnClearData);
//		mOpenFile = (ImageButton) findViewById(R.id.btnOpenFile);
//
//		if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 16)
//		{
//			mBack.setAlpha(disable);
//			mForward.setAlpha(disable);
//			mHome.setAlpha(disable);
//		}
//		mHome.setEnabled(false);
//
//
//
//		mBack.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				moreMenuClose();
//				if (mWebView != null && mWebView.canGoBack())
//					mWebView.goBack();
//			}
//		});
//
//		mForward.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				moreMenuClose();
//				if (mWebView != null && mWebView.canGoForward())
//					mWebView.goForward();
//			}
//		});
//
//		mRefresh.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				moreMenuClose();
//				if (mWebView != null)
//					mWebView.reload();
//			}
//		});
//
//
//		mGo.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				moreMenuClose();
//				String url = mUrl.getText().toString();
//						mWebView.loadUrl(url);
//				mWebView.requestFocus();
//			}
//		});
//
//		mMore.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (mMenu.getVisibility() == View.GONE)
//				{
//					mMenu.setVisibility(View.VISIBLE);
////                    mMore.setImageDrawable(getResources().getDrawable(R.drawable.theme_toolbar_btn_menu_fg_pressed));
//				}else{
//					mMenu.setVisibility(View.GONE);
////					mMore.setImageDrawable(getResources().getDrawable(R.drawable.theme_toolbar_btn_menu_fg_normal));
//				}
//			}
//		});
//
//		mClearData.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				moreMenuClose();
////				QbSdk.clearAllWebViewCache(getApplicationContext(),false);
//				//QbSdk.reset(getApplicationContext());
//			}
//		});
//
//		mOpenFile.setOnClickListener(new OnClickListener()
//		{
//
//			@Override
//			public void onClick(View v)
//			{
//				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//				intent.setType("*/*");
//				intent.addCategory(Intent.CATEGORY_OPENABLE);
//				try
//				{
//					BrowserActivity.this.startActivityForResult(Intent.createChooser(intent, "choose file"), 1);
//				}
//				catch (android.content.ActivityNotFoundException ex)
//				{
//					Toast.makeText(BrowserActivity.this, "完成", Toast.LENGTH_LONG).show();
//				}
//			}
//		});
//
//		mUrl.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//			@Override
//			public void onFocusChange(View v, boolean hasFocus) {
//				moreMenuClose();
//				if (hasFocus) {
//					mGo.setVisibility(View.VISIBLE);
//					mRefresh.setVisibility(View.GONE);
//					if (null == mWebView.getUrl()) return;
//					if (mWebView.getUrl().equalsIgnoreCase(mHomeUrl)) {
//						mUrl.setText("");
//						mGo.setText("首页");
//						mGo.setTextColor(0X6F0F0F0F);
//					} else {
//						mUrl.setText(mWebView.getUrl());
//						mGo.setText("进入");
//						mGo.setTextColor(0X6F0000CD);
//					}
//				} else {
//					mGo.setVisibility(View.GONE);
//					mRefresh.setVisibility(View.VISIBLE);
//					String title = mWebView.getTitle();
//					if (title != null && title.length() > MAX_LENGTH)
//						mUrl.setText(title.subSequence(0, MAX_LENGTH) + "...");
//					else
//						mUrl.setText(title);
//					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//				}
//			}
//
//		});
//
//		mUrl.addTextChangedListener(new TextWatcher() {
//
//			@Override
//			public void afterTextChanged(Editable s) {
//				// TODO Auto-generated method stub
//
//				String url = null;
//				if (mUrl.getText() != null) {
//					url = mUrl.getText().toString();
//				}
//
//				if (url == null
//						|| mUrl.getText().toString().equalsIgnoreCase("")) {
//					mGo.setText("请输入网址");
//					mGo.setTextColor(0X6F0F0F0F);
//				} else {
//					mGo.setText("进入");
//					mGo.setTextColor(0X6F0000CD);
//				}
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before,
//					int count) {
//				// TODO Auto-generated method stub
//
//			}
//
//		});
//
//		mHome.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				moreMenuClose();
//				if (mWebView != null)
//					mWebView.loadUrl(mHomeUrl);
//			}
//		});
//
//
//
//
//		mExit.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				android.os.Process.killProcess(Process.myPid());
//			}
//
//		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		boolean ret = super.onCreateOptionsMenu(menu);
//		getMenuInflater().inflate(R.menu.main, menu);
		return ret;
	}



	boolean[] m_selected = new boolean[] { true, true, true, true, false,
			false, true };



	private enum TEST_ENUM_FONTSIZE {
		FONT_SIZE_SMALLEST, FONT_SIZE_SMALLER, FONT_SIZE_NORMAL, FONT_SIZE_LARGER, FONT_SIZE_LARGEST
	};

	private TEST_ENUM_FONTSIZE m_font_index = TEST_ENUM_FONTSIZE.FONT_SIZE_NORMAL;





	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mWebView != null && mWebView.canGoBack()) {
				mWebView.goBack();
				if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 16)
					changGoForwardButton(mWebView);
				return true;
			} else
				return super.onKeyDown(keyCode, event);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		TbsLog.d(TAG, "onActivityResult, requestCode:" + requestCode
				+ ",resultCode:" + resultCode);

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case 0:
				if (null != uploadFile) {
					Uri result = data == null || resultCode != RESULT_OK ? null
							: data.getData();
					uploadFile.onReceiveValue(result);
					uploadFile = null;
				}
				break;
			case 1: 
				
				Uri uri = data.getData();
				String path = uri.getPath();

				
				break;
			default:
				break;
			}
		} 
		else if (resultCode == RESULT_CANCELED) {
			if (null != uploadFile) {
				uploadFile.onReceiveValue(null);
				uploadFile = null;
			}

		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		if (intent == null || mWebView == null || intent.getData() == null)
			return;
		mWebView.loadUrl(intent.getData().toString());
	}

	@Override
	protected void onDestroy() {
		if (mWebView != null)
			mWebView.destroy();
		super.onDestroy();
	}

	@Override
	protected void getBundleExtras(Bundle extras) {

	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.activity_browser;
	}

	@Override
	protected View getLoadingTargetView() {
		return null;
	}

	@Override
	protected void initViewsAndEvents() {
		Intent intent = getIntent();
		if (intent != null) {
			try {
				mIntentUrl = new URL(intent.getData().toString());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (NullPointerException e) {

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//
		try {
			if (Integer.parseInt(android.os.Build.VERSION.SDK) >= 11) {
				getWindow()
						.setFlags(
								android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
								android.view.WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*getWindow().addFlags(
				android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
		setContentView(R.layout.activity_browser);
		mViewParent = (ViewGroup) findViewById(R.id.web_container);

		initBtnListenser();

		this.webViewTransportTest();

		mTestHandler.sendEmptyMessageDelayed(MSG_INIT_UI, 10);
		titleView.setVisibility(View.VISIBLE);
		leftAction.setVisibility(View.VISIBLE);
		mCommontitle.setVisibility(View.GONE);
		mWebView = new WebView(getApplicationContext());
		mAlertDialog =new LoadingDialog(this);
		mWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		mFrameLayout.addView(mWebView);
		umShareListener = new UMShareListener() {
			@Override
			public void onResult(SHARE_MEDIA platform) {
				Log.d("plat", "platform" + platform);
				ToastUtils.showToastForTest(platform + " 分享成功啦");
//				dismissShareWindow();
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
//		rightActionView.setOnClickListener(this);
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
			}  else if (bundle.getInt(Constants.WEB_PAGE_TAG) == Constants.FROM_HOME_WEB) {
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
			}  else if (bundle.getInt(Constants.WEB_PAGE_TAG) == Constants.NEWS_TAG) {
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
	private void initWebView(String targetUrl) {
		if (webSetting == null&&mWebView!=null) {
			webSetting = mWebView.getSettings();
			webSetting.setJavaScriptEnabled(true);//启用js
			webSetting.setJavaScriptCanOpenWindowsAutomatically(true);//js和android交互
			String cacheDirPath = ((MainApplication) getApplication()).getLogCacheDir() + "/web";
			webSetting.setAppCachePath(cacheDirPath); //设置缓存的指定路径
			webSetting.setAllowFileAccess(true); // 允许访问文件
			webSetting.setAppCacheEnabled(true); //设置H5的缓存打开,默认关闭
			webSetting.setUseWideViewPort(true);//设置webview自适应屏幕大小
			//设置，可能的话使所有列的宽度不超过屏幕宽度
			webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);

			webSetting.setLoadWithOverviewMode(true);//设置webview自适应屏幕大小
			webSetting.setDomStorageEnabled(true);//设置可以使用localStorage
			webSetting.setSupportZoom(false);//关闭zoom按钮
			webSetting.setBuiltInZoomControls(false);//关闭zoom
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

				}else mLeftAction.setImageResource(R.mipmap.ic_login_back);
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

				if (url.contains("tel:")) {
					mWebView.stopLoading();
					response = false;
				}else if (url.equals(Constants.HOST + "/")) {

					mWebView.stopLoading();
					response = false;
					setResult(Constants.HOME_FLAG);
					BrowserActivity.this.finish();
				} else {
					view.loadUrl(url, extraHeaders);
				}
				initShareBtn(url);
				return response;

			}

			@TargetApi(Build.VERSION_CODES.LOLLIPOP)
			@Override
			public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
				BrowserActivity.this.logRequest(request.getRequestHeaders());
				request.getRequestHeaders();
				return super.shouldInterceptRequest(view, request);
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

					BrowserActivity.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), Constants.FILECHOOSER_RESULTCODE);

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

		});

		mWebView.setDownloadListener((url, userAgent, contentDisposition, mimetype, contentLength) -> {
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(url));
			startActivity(i);
		});
	}
	private void runWebViewProcess() {
		if (limitAgent == null) limitAgent = webSetting.getUserAgentString() + "limi88- v"+ BuildConfig.VERSION_NAME;
		if (extraHeaders == null) {
			extraHeaders = new HashMap<>();
			webSetting.setUserAgentString(limitAgent);
		}

		tonce = SecurityUtils.getTimestamp();
		code = SecurityUtils.getHMACSHA1(tonce);
		extraHeaders.put(Constants.WEB_HEADER_TAG, Constants.CURRENT_TOKEN);
		extraHeaders.put("LimiAgent", limitAgent);
		extraHeaders.put("code", code);
		extraHeaders.put("tonce", tonce);
		mWebView.loadUrl(targetUrl, extraHeaders);
	}
	private void initShareBtn(String url) {
		if (ShareUtils.isLinkShareAble(url)) {
			rightActionView.setVisibility(View.VISIBLE);
		} else {
			rightActionView.setVisibility(View.GONE);
		}
	}
	private JsMessage jm;
	private int LOGIN_CODE = 1;
	private boolean parseMessageFromJs(String url, String message) {
		jm = JsMessage.initFromString(message);
		if (jm.getType().equals(JsMessage.MODEL_TYPE)) {
			if (jm.getParams().get("name").equals("Customer")) {
				setResult(Constants.MODIFY_CUSTOMER_CODE);
				if (mReal != null) {
					Intent mIntent = new Intent(this, NewCustomersActivity.class);
					mIntent.putExtras(bundle);
					startActivityForResult(mIntent, Constants.MODIFY_CUSTOMER_CODE);
				}
//                finish();

			}


		} else if (jm.getType().equals(JsMessage.LOGIN_TYPE)) {
			Intent mIntent=new Intent(BrowserActivity.this, LoginActivity.class);
			mIntent.putExtra(Constants.WEB_PAGE_DETAIL_LINK,jm.getParams().get("url"));
			startActivityForResult(mIntent, Constants.LOGIN_SUCCESS_CODE);
		} else if (jm.getType().equals(JsMessage.WEIXIN_SHARE_TYPE)) {
			if (jm.getParams().get("title") == null) {
//				getWeixinShareCOnfig();
			} else {
//				popUpShareWindow();
			}
		} else if (jm.getType().equals(JsMessage.GOHOME_TYPE)){
			finish();
		}
		return false;
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
	protected void onNetworkConnected(NetUtils.NetType type) {

	}

	@Override
	protected void onNetworkDisConnected() {

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

	public static final int MSG_OPEN_TEST_URL = 0;
	public static final int MSG_INIT_UI = 1;
	private final int mUrlStartNum = 0;
	private int mCurrentUrl = mUrlStartNum;
	private Handler mTestHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_OPEN_TEST_URL:
				if (!mNeedTestPage) {
					return;
				}

				String testUrl = "file:///sdcard/outputHtml/html/"
						+ Integer.toString(mCurrentUrl) + ".html";
				if (mWebView != null) {
					mWebView.loadUrl(testUrl);
				}

				mCurrentUrl++;
				break;
			case MSG_INIT_UI:
				init();
				break;
			}
			super.handleMessage(msg);
		}
	};


}
