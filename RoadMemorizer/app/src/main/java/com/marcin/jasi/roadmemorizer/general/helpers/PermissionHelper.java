package com.marcin.jasi.roadmemorizer.general.helpers;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.pm.PermissionInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.marcin.jasi.roadmemorizer.general.Constants;

import static com.marcin.jasi.roadmemorizer.general.Constants.REQUEST_CODE_ASK_PERMISSION;

public class PermissionHelper {

    private Activity activity;

    public PermissionHelper(Activity activity) {
        this.activity = activity;
    }

    public boolean checkPermissions(String permissions) {
        if (ContextCompat.checkSelfPermission(activity, permissions) != PackageManager.PERMISSION_GRANTED) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions))) {

                try {
                    PackageManager packageManager = activity.getPackageManager();

                    PermissionInfo pinfo = packageManager
                            .getPermissionInfo(permissions, PackageManager.GET_META_DATA);

                    pinfo.loadLabel(packageManager).toString();

                    showMessageOKCancel(String.format("You need to allow access to ", pinfo.loadLabel(packageManager).toString()),
                            (dialog, which) -> ActivityCompat.requestPermissions(
                                    activity,
                                    new String[]{permissions},
                                    Constants.REQUEST_CODE_ASK_PERMISSION));

                } catch (Exception e) {
                    return false;
                }

                return false;
            } else {

                ActivityCompat.requestPermissions(activity, new String[]{permissions}, REQUEST_CODE_ASK_PERMISSION);
                return false;
            }
        }

        return true;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }

}
