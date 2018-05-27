package com.marcin.jasi.roadmemorizer.general.common.data.entity;

import android.location.LocationManager;

public class NetworkProvider implements LocationProviderType {
    @Override
    public String getName() {
        return LocationManager.NETWORK_PROVIDER;
    }
}
