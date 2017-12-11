package com.ruitukeji.zwbh.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;

import com.ruitukeji.zwbh.R;
import com.ruitukeji.zwbh.constant.NumericConstants;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2017/6/14.
 */

public class PermissionsHelper {

    private static PermissionsHelperCallBack callBack;//回调

    /**
     * 读写权限
     *
     * @param context
     */
    @AfterPermissionGranted(NumericConstants.READ_AND_WRITE_CODE)
    public static void readAndWriteTask(Context context) {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS};
        if (EasyPermissions.hasPermissions(context, perms)) {
            // Have permission, do the thing!
            if (callBack != null) {
                callBack.permissionsHelperBack(NumericConstants.READ_AND_WRITE_CODE);
            }
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions((Activity) context, context.getString(R.string.readAndWrite),
                    NumericConstants.READ_AND_WRITE_CODE, perms);
        }
    }


    public static void setPermissionsHelperCallBack(PermissionsHelperCallBack callBack) {
        PermissionsHelper.callBack = callBack;
    }

    public interface PermissionsHelperCallBack {
        void permissionsHelperBack(int flag);
    }


}
