package com.limi88.financialplanner.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import javax.inject.Inject;

/**
 * Created by hehao
 * Date on 16/10/11.
 * Email: hao.he@sunanzq.com
 */
public class PermissionUtils {


    private  static Fragment context;
    private static PermissionUtils mPermissionUtils;

    private PermissionUtils(Fragment context) {
        this.context = context;
    }

    public static PermissionUtils getInstance(Fragment context) {
        if (mPermissionUtils == null) {
            mPermissionUtils = new PermissionUtils(context);
        } else {
            mPermissionUtils = null;
            mPermissionUtils = new PermissionUtils(context);
        }
        return mPermissionUtils;
    }

    public boolean checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(context.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
//            context.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    Constants.WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
          return false;
        }else return true;
    }
  public boolean checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(context.getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
//            context.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    Constants.WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
          return false;
        }else return true;
    }


}



