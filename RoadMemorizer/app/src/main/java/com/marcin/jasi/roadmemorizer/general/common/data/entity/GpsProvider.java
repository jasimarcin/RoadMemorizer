package com.marcin.jasi.roadmemorizer.general.common.data.entity;

import android.location.LocationManager;

import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;

public class GpsProvider implements LocationProviderType{
    @Override
    public String getName() {
        return LocationManager.GPS_PROVIDER;
    }
}
