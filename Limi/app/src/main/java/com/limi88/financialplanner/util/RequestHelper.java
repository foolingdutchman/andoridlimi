package com.limi88.financialplanner.util;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Config;

import com.limi88.financialplanner.BuildConfig;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by xu on 16/5/31.
 */
public class RequestHelper {
    public static final MediaType MediaTypeJSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final MediaType MEDIA_TYPE_JPEG = MediaType.parse("image/jpeg");
    private static final MediaType MEDIA_TYPE_ZIP = MediaType.parse("zip");

    private Context mContext;
    @Inject
    @Singleton
    public RequestHelper(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 获取版本名
     */
    public String getVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    /*
    * 获取SDK版本号
    * */
    public String getAndroidVersionName() {
        return android.os.Build.VERSION.SDK;
    }


    /**
     * 时间戳
     */
    public String getTimestamp() {
        long timestamp = System.currentTimeMillis() / 1000L;
        return String.valueOf(timestamp);
    }
 /**
     * 时间戳
     */
    public long getTimestampLong() {
        long timestamp = System.currentTimeMillis() / 1000L;
        return timestamp;
    }

    /**
     * 流水号
     */
    public String getSeqNo() {
        long timestamp = System.currentTimeMillis();
        return String.valueOf(timestamp);
    }

    /**
     * 手机唯一标示
     */
    public String getDeviceId() {
        String deviceId;
        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm.getDeviceId() ==          null) {
            deviceId = getAndroidId();
        } else {
            deviceId = tm.getDeviceId();
        }
        return deviceId;
    }

    public String getAndroidId() {
        return Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * source
     */
    public String getSource() {
        String androidKey = "7d105f8207517d3f8307025ebd5d981f";
        String step1 = androidKey + getDeviceId(); //androidKey + 机器唯一标识码
        String step2 = SecurityUtils.getMD5(step1); //对step1的值md5
        String step3 = step2 + getTimestamp();//step2的值与时间错拼接
        String source = SecurityUtils.getMD5(step3);//对step3的值md5
        return source;
    }

    public Map<String, String> getHttpRequestMap() {
        Map<String, String> map = new HashMap<>();
        map.put("SeqNo", getSeqNo());
        map.put("Source", getSource());
        map.put("TerminalSN", getDeviceId());
        map.put("TimeStamp", getTimestamp());
        map.put("Version", getVersionName());
        return map;
    }

    public static RequestBody getMultiRequestBody(String url, String methodName,
                                                  Map<String, String> textParams,
                                                  Map<String, String> fileParams){
        MultipartBody.Builder requestBodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        requestBodyBuilder.addFormDataPart("ZMRequestMethod", methodName);

        if (textParams != null) {
            for (String key : textParams.keySet()) {
                String value = textParams.get(key);
                if (null != value && "".equals(value)){
                    if (key.contains("[]")) {
                        String[] values = value.split(",");
                        for (int i = 0; i < values.length; i++)
                            requestBodyBuilder.addFormDataPart(key, values[i]);
                    } else
                        requestBodyBuilder.addFormDataPart(key, value);
                }
            }
        }
        if (fileParams != null){
            for (String key:fileParams.keySet()){
                String fileName = fileParams.get(key);
                requestBodyBuilder.addFormDataPart(key, fileName, RequestBody.create(getMineTypeFromFileName(fileName), new File(fileName)));
            }
        }
        RequestBody body = requestBodyBuilder.build();
        return body;
    }

    public static MediaType getMineTypeFromFileName(String fileName){
        if (fileName.endsWith(".png")) {
            return MEDIA_TYPE_PNG;
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return MEDIA_TYPE_JPEG;
        } else {
            return MEDIA_TYPE_ZIP;
        }
    }

}
