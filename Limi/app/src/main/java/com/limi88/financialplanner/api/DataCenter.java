package com.limi88.financialplanner.api;

import android.util.Log;

import com.limi88.financialplanner.BuildConfig;
import com.limi88.financialplanner.pojo.user.User;
import com.limi88.financialplanner.util.Constants;

/**
 * Created by sunzhimeng on 10/17/16.
 */
public class DataCenter {
    public static boolean signedIn = false;

    public static User getCurrentUser() {
        return Constants.user;
    }

    public static void setCurrentUser(User currentUser) {
        Constants.user = currentUser;
    }

    public static boolean isSigned() {
        return signedIn;
    }

    public static boolean setSignedIn(String token) {
        Constants.CURRENT_TOKEN = token;
        if (token.equals("") || token == null) {
            signedIn = false;
        } else {
            signedIn = true;
        }
        Log.i("*********** isSigned", (signedIn ? "with token " + token : " false *********"));
        return signedIn;
    }

    public static boolean setSignedIn(String token, User user) {
        setCurrentUser(user);
        return setSignedIn(token);
    }

    public static void clearToken() {
        Constants.CURRENT_TOKEN = "";
        signedIn = false;
    }

    public static void logout() {
        clearToken();
        setCurrentUser(null);
    }

    public static String getDeviceToken() {
        return Constants.DIVICE_TOKEN;
    }
    public static String getAppVersion() {
        return BuildConfig.VERSION_NAME;
    }

    public static void setDeviceToken(String token) {
        Constants.DIVICE_TOKEN = token;
    }

    public static boolean isDownLoadNewVersion() {
        return Constants.IS_DOWNLOAD_APP;
    }

    public static void setIsDownLoadNewVersion(boolean isDownLoadNewVersion) {
        Constants.IS_DOWNLOAD_APP = isDownLoadNewVersion;
    }

    public static boolean isNewVersionExist() {
        return Constants.NEW_VERSION_EXIST;
    }

    public static void setNewVersionExist(boolean newVersionExist) {
        Constants.NEW_VERSION_EXIST = newVersionExist;
    }
}
