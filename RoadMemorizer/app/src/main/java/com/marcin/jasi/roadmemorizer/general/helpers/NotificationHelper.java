package com.marcin.jasi.roadmemorizer.general.helpers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.marcin.jasi.roadmemorizer.R;
import com.marcin.jasi.roadmemorizer.main.MainActivity;

public class NotificationHelper {

    private static final int IS_RECORDING_NOTIFICATION_ID = 123456;

    @NonNull
    private NotificationCompat.Builder getNotificationBuilder(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setContentTitle(context.getResources().getString(R.string.app_name));
        builder.setContentText(context.getString(R.string.road_is_saving));
        builder.setSmallIcon(R.drawable.circle);
        builder.setAutoCancel(false);
        builder.setOngoing(true);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        builder.setContentIntent(notificationPendingIntent);

        return builder;
    }

    public void hideIsRecordingNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager == null)
            return;

        notificationManager.cancel(IS_RECORDING_NOTIFICATION_ID);
    }


    public void showIsRecordingNotification(Context context) {
        NotificationCompat.Builder builder = getNotificationBuilder(context);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (notificationManager == null)
            return;

        notificationManager.notify(IS_RECORDING_NOTIFICATION_ID, builder.build());
    }

}
