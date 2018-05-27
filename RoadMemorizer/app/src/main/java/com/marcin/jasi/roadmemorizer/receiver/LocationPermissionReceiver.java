package com.marcin.jasi.roadmemorizer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Pair;

import com.marcin.jasi.roadmemorizer.Application;
import com.marcin.jasi.roadmemorizer.di.scope.PerServiceScope;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;
import com.marcin.jasi.roadmemorizer.general.common.data.entity.GpsProvider;
import com.marcin.jasi.roadmemorizer.general.common.data.entity.NetworkProvider;
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
                boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                locationTrackerMediator.getLocationProviderChange().onNext(new Pair<>(gpsEnabled, new GpsProvider()));
                locationTrackerMediator.getLocationProviderChange().onNext(new Pair<>(networkEnabled, new NetworkProvider()));
            }
        }
    }

    private void initDependencies(Context context) {
        DaggerGpsPrermissionReceiverComponent
                .builder()
                .applicationComponent(((Application) context.getApplicationContext()).getApplicationComponent())
                .build()
                .inject(this);
    }

}
