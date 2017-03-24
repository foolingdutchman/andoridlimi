package com.limi88.financialplanner.util;

import com.limi88.financialplanner.pojo.clients.Real;
import com.limi88.financialplanner.pojo.costumer.Province;
import com.limi88.financialplanner.pojo.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hehao on 16/5/31.
 */
public class Constants {

//     public static final String HOST = "https://www.limi88.com";
//    public static final String HOST = "http://192.168.31.166:3000/";
//    public static final String HOST = "http://192.168.31.188:3000/";
    public static final String HOST = "https://dev.sunanzq.com";
//    public static final String HOST = "http://sunanzq.com:8081";

//    public static final String HOST = "http://192.168.140.199:2800";

    public static final String PRE = "/api/v1/";

    public static final String HOST_WITH_PRE = HOST + PRE;
    public static final String VERSION_URL = HOST_WITH_PRE+"app_versions/";
    // webview urls

    public static final String CLIENTS_URL = HOST + "/customers";
    //个人页面
    public static final String MINE_URL = HOST + "/users/0";
    public static final String MINE_AUTHENTICATION_URL = MINE_URL +"/apply";
    public static final String MINE_DOC_URL = MINE_URL +"/law";
    public static final String MINE_SETTINGS_URL = MINE_URL +"/account";
    public static final String MINE_2DCODE_URL = HOST + "/users/0/qrcode";

    public static final String USER_PROFILE_URL = HOST + "/user_profiles/card";
    public static final String USER_MINE_URL = HOST + "/cards/0/edit_info";

    public static final String PRODUCTS_FAVOR_URL = HOST + "/fin_product/fav";

    //计算器链接
    public static final String CALCULATOR_RETERMENT_URL = HOST + "/retirement_calculator/new";
    public static final String CALCULATOR_EDUCATION_URL = HOST + "/education_calculator/index";
    public static final String INVEST_RISK_TEST_URL = HOST + "/invest_risk_test/index";
    public static final String CALCULATOR_IRR_URL = HOST + "/irr_calculator/index";

    public static final String PROVINCE_FLAG = "choosen_province";
    public static final String TAGS_FLAG = "choosen_tags";
    public static final String MODIFY_CUSTOMER_FLAG = "modify_customer";

    //版本相关
    public static final String APP_ID_FLAG = "app_id_flag";
    public static final String DOWNLOAD_NEW_VERSION_URL = "";
    public static final String PAG_NAME = "com.limi88.financialplanner";
    public static boolean IS_DOWNLOAD_APP=false;

    //首页
    public static final String HOME_NEWS_DETAIL_BASE=HOST+"/business_development/news_details?id=";
    public static final String HOME_TOPICS_DETAIL_BASE=HOST+"/business_development/topic_details?id=";
    public static final String HOME_TOPICS_NEW=HOST+"/business_development/topic_new";
    public static List<String> NEED_LOGIN_URLS = new ArrayList<>();


    static {
        NEED_LOGIN_URLS.add(CALCULATOR_RETERMENT_URL);
        NEED_LOGIN_URLS.add(CALCULATOR_EDUCATION_URL);
        NEED_LOGIN_URLS.add(CALCULATOR_IRR_URL);
        NEED_LOGIN_URLS.add(PRODUCTS_FAVOR_URL);
    }

    // api urls
    public static final String LOGIN_URL = HOST_WITH_PRE + "users/";
    public static final String HOME_URL = HOST_WITH_PRE ;
    public static final String INSURANCE_PATH = "insurances" ;
    public static final String SEARCH_PATH = "search" ;

    public static final String INIT_FLAG = "app_init_flag";
    public static final String APP_INIT="init_flag";
    public static final String USER_FLAG ="user_flag";
    public static final String TOKEN_FLAG ="token_flag";
    public static final String MOBILE_FLAG ="mobile_flag";
    public static final String SEARCH_CONDITION_FLAG ="condition";
    public static final String SEARCH_POSITION_FLAG ="position";
    public static final String SEARCH_ISSELECTED_FLAG ="isSelected";

    public static final String WEB_CALCULATOR_TAG_TITLE ="web_calculator_title";
    public static final String WEB_CALCULATOR_TAG_LINK ="web_calculator_link";
    public static final String WEB_CALCULATOR_TAG_ID ="web_calculator_ID";

    public static final String WEB_PAGE_TAG ="web_page_tag";
    public static final String WEB_PAGE_DETAIL_NAME ="web_product_name";
    public static final String WEB_PAGE_DETAIL_LINK ="web_product_link";
    //home页CODE
    public static final int CLIENTS_FLAG =2;
    public static final int PRODUCTS_FLAG =1;
    public static final int HOME_FLAG =0;
    public static final int MINE_FLAG =3;
    public static final int FROM_HOME_WEB = 11;

    //产品页CODE
    public static final int PRODUCTS_PAGE_FLAG=21;
    //客户页CODE
    public static final int TAG_PICK_CODE=31;
    public static final int TAG_PICK_SUCCESS=32;
    public static final int PICK_PROVINCE_CODE = 33;
    public static final int PROVINCE_PICK_SUCCESS=34;
    public static final int NEW_CLIENTS_CODE = 35;
    public static final int MODIFY_CUSTOMER_CODE = 36;
    public static final int NO_MODIFY_CUSTOMER_CODE = 37;
    public static final int DELETE_CUSTOMER_CODE = 38;
    //登录页CODE
    public static final int LOG_OUT_CODE = 51;
    public static final int LOGIN_SUCCESS_CODE =500;
    //个人页CODE
    public static final int UPDATE_USER_INFO_CODE = 41;
    public static final int PROFILE1_REQUEST_CODE = 42;
    public static final int PROFILE2_REQUEST_CODE = 43;
    public static final int PROFILE3_REQUEST_CODE = 44;
    public static final int FEEDBACK_CODE = 45;
    public static final int FEEDBACK_SUCCESS_CODE =46 ;
    public static final int NEWS_TAG = 47;

    //权限申请CODE
    public static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 911;
    public static final int CAMERA_REQUEST_CODE = 912;


    public static final int NO_LOGIN_TAG =101;
    public static final int NO_AUTHENTICATION_TAG =202;
    public static final int GO_DETAIL_TAG =220;
    public static final int WEB_CALCULATOR_TAG =333;
    public static final int WEB_FAVOR_TAG = 666;
    public static final int WEB_MINE_TAG = 888;
    public static final int CUSTOMER_DETAIL_TAG = 235;

    public static final int FILECHOOSER_RESULTCODE = 61;


    public static final String WEB_FAVOR_LINK = "web_favor_link";
    public static final String WEB_FAVOR_NAME = "web_favor_name";
    public static final String WEB_MINE_LINK = "web_mine_link";
    public static final String WEB_MINE_NAME = "web_mine_name";
    public static final String WEB_HEADER_TAG = "Limikey";

    public static final String SEARCH_KEY_FLAG = "PRODUCT_SEARCH_KEY";
    public static final String NEW_CLIENTS_TAG = "new_client_fragment";
    public static final String MINE_PROFILE_FRAGMENT_TAG = "mine_profile_fragment";
    public static final String CHOOSE_CUSTOMER_TAG = "choose_customer_tag";


    public static  String CURRENT_TOKEN ="";
    public static  boolean NEW_VERSION_EXIST =false;
    public static  int CURRENT_USER_ID =-1;
    public static  String DIVICE_TOKEN ="";
    public static  String CURRENT_USER_MOBILE ="";
    public static List<Province> PROVINCES_LIST=new ArrayList<>();

    public static Real CHOOSE_CUSTOMER=null;
    public static User user=null;


}
