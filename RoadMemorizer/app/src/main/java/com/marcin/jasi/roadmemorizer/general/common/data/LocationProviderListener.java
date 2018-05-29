package com.marcin.jasi.roadmemorizer.general.common.data;

import android.location.Location;
import android.os.Bundle;

import com.marcin.jasi.roadmemorizer.general.common.data.entity.LocationProviderType;

public class LocationProviderListener implements android.location.LocationListener {

    public interface LocationListener {
        void handleLocationChange(Location location, LocationProviderType provider);
    }

    private LocationListener listener;
    private LocationProviderType provider;

    public LocationProviderListener(LocationProviderType provider, LocationListener listener) {
        this.provider = provider;
        this.listener = listener;
    }

    @Override
    public void onLocationChanged(Location location) {
        listener.handleLocationChange(location, provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }


}
