package com.marcin.jasi.roadmemorizer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import com.marcin.jasi.roadmemorizer.Application;
import com.marcin.jasi.roadmemorizer.di.scope.PerServiceScope;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;
import com.marcin.jasi.roadmemorizer.receiver.di.DaggerGpsPrermissionReceiverComponent;

import javax.inject.Inject;

@PerServiceScope
public class LocationPermissionReceiver extends BroadcastReceiver {

    public static final String LOCATION_ENABLED_CHANGE_ACTION = "android.location.PROVIDERS_CHANGED";

    @Inject
    LocationTrackerMediator locationTrackerMediator;

    @Override
    public void onReceive(Context context, Intent intent) {
        initDependencies(context);

        if (intent.getAction().equals(LOCATION_ENABLED_CHANGE_ACTION)) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            if (locationManager != null) {
                boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                locationTrackerMediator.getLocationProviderEnableChange().onNext(enabled);
            }
        }
    }

    private void initDependencies(Context context) {
        DaggerGpsPrermissionReceiverComponent
                .builder()
                .applicationComponent(((Application)context.getApplicationContext()).getApplicationComponent())
                .build()
                .inject(this);
    }

}
