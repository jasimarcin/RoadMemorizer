package com.marcin.jasi.roadmemorizer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;

import com.marcin.jasi.roadmemorizer.Application;
import com.marcin.jasi.roadmemorizer.di.scope.PerServiceScope;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationProvidersHelper;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;
import com.marcin.jasi.roadmemorizer.general.common.data.entity.GpsProvider;
import com.marcin.jasi.roadmemorizer.general.common.data.entity.LocationProviderType;
import com.marcin.jasi.roadmemorizer.general.common.data.entity.NetworkProvider;
import com.marcin.jasi.roadmemorizer.receiver.di.DaggerGpsPrermissionReceiverComponent;

import javax.inject.Inject;

@PerServiceScope
public class LocationPermissionReceiver extends BroadcastReceiver {

    public static final String LOCATION_ENABLED_CHANGE_ACTION = "android.location.PROVIDERS_CHANGED";

    @Inject
    LocationTrackerMediator locationTrackerMediator;
    @Inject
    LocationProvidersHelper locationProvidersHelper;

    @Override
    public void onReceive(Context context, Intent intent) {
        initDependencies(context);

        if (intent.getAction().equals(LOCATION_ENABLED_CHANGE_ACTION)) {

            notifyProviderChange(new GpsProvider());
            notifyProviderChange(new NetworkProvider());
        }
    }

    private void notifyProviderChange(LocationProviderType provider) {
        Boolean enabled = locationProvidersHelper.tryCheckIfProviderEnabled(provider.getName());
        if (enabled != null)
            locationTrackerMediator.getLocationProviderChange().onNext(new Pair<>(enabled, new GpsProvider()));
    }

    private void initDependencies(Context context) {
        DaggerGpsPrermissionReceiverComponent
                .builder()
                .applicationComponent(((Application) context.getApplicationContext()).getApplicationComponent())
                .build()
                .inject(this);
    }

}
