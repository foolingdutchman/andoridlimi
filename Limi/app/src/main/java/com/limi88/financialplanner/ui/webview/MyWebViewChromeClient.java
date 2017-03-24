//package com.limi88.financialplanner.ui.webview;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.text.TextUtils;
//import android.view.View;
//import android.webkit.JavascriptInterface;
//
//import com.tencent.smtt.sdk.ValueCallback;
//import com.tencent.smtt.sdk.WebChromeClient;
//import com.tencent.smtt.sdk.WebView;
//import com.tencent.smtt.sdk.WebViewClient;
//
///**
// * Created by hehao
// * Date on 16/11/3.
// * Email: hao.he@sunanzq.com
// */
//public class MyWebViewChromeClient extends WebChromeClient {
//
//    // For Android 5.0+
//
//    @Override
//
//    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback,
//
//                                     android.webkit.WebChromeClient.FileChooserParams fileChooserParams) {
//
//        mValueCallback = valueCallback;
//
//        selectImgMax = selectImgMax > 1 ? selectImgMax : 1;
//
//        goToPhotos(selectImgMax);
//
//        return true;
//
//    }
//
//
//
//    // For Android 3.0+
//
//    public void openFileChooser(ValueCallback uploadMsg) {
//
//        mUploadMessage = uploadMsg;
//
//        selectImgMax = 1;
//
//        goToPhotos(selectImgMax);
//
//    }
//
//
//    //3.0--版本
//
//    public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
//
//        openFileChooser(uploadMsg);
//
//    }
//
//
//    // For Android 4.1
//
//    public void openFileChooser(ValueCallback uploadMsg, String acceptType,
//
//                                String capture) {
//
//        openFileChooser(uploadMsg);
//
//    }
//
//
//    /**
//
//     * 进入本地图库
//
//     *
//
//     * @param select_image_max //设置选取最大值
//
//     */
//
//    private void goToPhotos(int select_image_max) {
//
//        Intent i;
//
//        if (photosType <= 0) {//进入自定义图库
//
//            i = new Intent(WebviewAct.this, MediaSelectActivity.class);
//
//            i.putExtra("select_mode", 2);
//
//            i.putExtra("select_image_max", select_image_max);
//
//            WebviewAct.this.startActivityForResult(i, FILECHOOSER_RESULTCODE);
//
//        } else {//进入系统图库
//
//            i = new Intent(Intent.ACTION_GET_CONTENT);
//
//            i.addCategory(Intent.CATEGORY_OPENABLE);
//
//            i.setType("image/*");
//
//            WebviewAct.this.startActivityForResult(Intent.createChooser(i, "File Chooser"), FILECHOOSER_RESULTCODE);
//
//        }
//
//    }
//
//
//    @Override
//
//    public void onProgressChanged(WebView view, int newProgress) {
//
//        if (newProgress == 100) {
//
//            progressbar.setVisibility(View.GONE);
//
//        } else {
//
//            if (progressbar.getVisibility() == View.GONE)
//
//                progressbar.setVisibility(View.VISIBLE);
//
//            progressbar.setProgress(newProgress);
//
//        }
//
//        super.onProgressChanged(view, newProgress);
//
//    }
//
//
//    @Override
//
//    public void onReceivedTitle(WebView view, String title) {
//
//        if (TextUtils.isEmpty(mytitle)) {
//
//            titleTextView.setText(title);
//
//        }
//
//        super.onReceivedTitle(view, title);
//
//    }
//
//}
//
//
//
//    @Override
//
//    protected void onActivityResult(int requestCode, int resultCode,
//
//                                    Intent intent) {
//
//        if (requestCode == FILECHOOSER_RESULTCODE) {
//
//            if (this.photosType <= 0) {//调用自定义图库
//
//                uploadImgFromMyPhotos();
//
//            } else {//调用系统图库
//
//                uploadImgFromSysPhotos(resultCode, intent);
//
//            }
//
//        }
//
//    }
//
//
//    /**
//
//     * 上传图片,调用系统图库 与h5 file标签交互
//
//     *
//
//     * @param resultCode
//
//     * @param intent
//
//     * @author linjinpeng 2015年11月30日 14:25:20
//
//     */
//
//    private void uploadImgFromSysPhotos(int resultCode, Intent intent) {
//
//        if (mUploadMessage != null) {//5.0以下
//
//            Uri result = intent == null || resultCode != RESULT_OK ? null
//
//                    : intent.getData();
//
//            mUploadMessage.onReceiveValue(result);
//
//            mUploadMessage = null;
//
//        } else if (mValueCallback != null) {//5.0+
//
//            Uri[] uris = new Uri[1];
//
//            uris[0] = intent == null || resultCode != RESULT_OK ? null
//
//                    : intent.getData();
//
//            if (uris[0]!=null){
//
//                mValueCallback.onReceiveValue(uris);
//
//            }
//
//            mValueCallback = null;
//
//        }
//
//    }
//
//
//    /**
//
//     * 上传图片，调用自己图库 与h5 file标签交互
//
//     *
//
//     * @author linjinpeng 2015年11月30日 12:22:23
//
//     */
//
//    private void uploadImgFromMyPhotos() {
//
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
//
//    }
//
//
//    /**
//
//     * js调用 setSelectImgMax 设置本地图片选取图片数量的最大值
//
//     *
//
//     * @param selectImgMax 默认值为1
//
//     * @param photosType   type<=0?调用自己的图库:调用系统图库
//
//     * @author linjinpeng 2015年11月30日 12:17:48
//
//     */
//
//    @JavascriptInterface
//
//    public void setSelectImgMax(int selectImgMax, int photosType) {
//
//        this.selectImgMax = selectImgMax;
//
//        this.photosType = photosType;
//
//    }
//
