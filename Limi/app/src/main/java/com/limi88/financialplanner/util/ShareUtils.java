package com.limi88.financialplanner.util;

import android.view.View;

/**
 * Created by hehao
 * Date on 16/12/1.
 * Email: hao.he@sunanzq.com
 */
public class ShareUtils {
    public static boolean isLinkShareAble(String url){
     boolean isShareAble=false;
        if (url.contains("insura") || url.contains("report_guide") ||

                (ValidatorUtils.isCardCanShare(url)) || url.contains("reports/")||url.contains("report_end")


                ||(url.contains("news_details") && !url.contains("comment"))||
                url.contains("mp.weixin.qq.com")) {
           isShareAble=true;
        }
        return isShareAble;
    }
}
