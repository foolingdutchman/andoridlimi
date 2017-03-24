package com.limi88.financialplanner.pojo;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by sunzhimeng on 10/14/16.
 */
public class JsMessage {
    private String type;
    private HashMap<String,String> params;

    public final static String MODEL_TYPE = "model";
    public final static String F7_TYPE = "F7";
    public final static String BACK_TYPE = "BACK";
    public final static String NEW_WEB_TYPE = "new_webview";
    public final static String LOGIN_TYPE = "login";
    public final static String WEIXIN_SHARE_TYPE = "weixin_share";
    public final static String GOHOME_TYPE = "gohome";
    public final static String HIDEBAR_TYPE = "hidebar";
    public final static String SHOWBAR_TYPE = "showbar";

    public static JsMessage initFromString(String message){
        JsMessage jm = new JsMessage();
        JSONTokener tokener = new JSONTokener(message);
        try {
            JSONObject jsonObject = (JSONObject) tokener.nextValue();
            jm.setType(jsonObject.getString("type"));
            HashMap<String,String> hm = new HashMap<>();
            JSONObject jsonObjForParams = jsonObject.getJSONObject("params");
            for (Iterator<String> keys = jsonObjForParams.keys(); keys.hasNext();) {
                String key = keys.next();
                hm.put(key, jsonObjForParams.getString(key));
            }
            jm.setParams(hm);
        } catch (Exception e){
            e.printStackTrace();
        }
        return jm;
    }

    public static JsMessage initEmptyMessage(){
        JsMessage jm = new JsMessage();
        String type="";
        HashMap<String,String> params=new HashMap<>();
        jm.setParams(params);
        jm.setType(type);
        return jm;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }
}
