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

    public boolean getPermission(String permission) {
        if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {

            if ((ActivityCompat.shouldShowRequestPermissionRationale(activity, permission))) {

                try {
                    PackageManager packageManager = activity.getPackageManager();

                    PermissionInfo pinfo = packageManager
                            .getPermissionInfo(permission, PackageManager.GET_META_DATA);

                    pinfo.loadLabel(packageManager).toString();

                    showMessageOKCancel(String.format("You need to allow access to ", pinfo.loadLabel(packageManager).toString()),
                            (dialog, which) -> ActivityCompat.requestPermissions(
                                    activity,
                                    new String[]{permission},
                                    Constants.REQUEST_CODE_ASK_PERMISSION));

                } catch (Exception e) {
                    return false;
                }

                return false;
            } else {

                ActivityCompat.requestPermissions(activity, new String[]{permission}, REQUEST_CODE_ASK_PERMISSION);
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

    public boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED;
    }

}
