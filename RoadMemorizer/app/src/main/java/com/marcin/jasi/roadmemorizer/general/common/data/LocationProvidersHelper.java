package com.marcin.jasi.roadmemorizer.general.common.data;

import android.content.Context;
import android.location.LocationManager;

import timber.log.Timber;

import static com.marcin.jasi.roadmemorizer.general.Constants.LOCATION_DISTANCE;
import static com.marcin.jasi.roadmemorizer.general.Constants.LOCATION_INTERVAL;

public class LocationProvidersHelper {

    private LocationManager locationManager;
    private Context context;

    public LocationProvidersHelper(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public void tryConnectProvider(String provider, LocationProviderListener listener) {
        try {
            locationManager
                    .requestLocationUpdates(
                            provider, LOCATION_INTERVAL, LOCATION_DISTANCE,
                            listener);
        } catch (java.lang.SecurityException ex) {
            Timber.d(ex, "fail to request location update, ignore");
        } catch (IllegalArgumentException ex) {
            Timber.d(ex, "network provider does not exist, ");
        } catch (Exception e) {
            Timber.d(e);
        }
    }

    public void setProviderState(boolean enable, String provider, LocationProviderListener listener) {
        if (enable) {
            tryConnectProvider(provider, listener);
        } else {
            tryDisconnectProvider(listener);
        }
    }

    private void tryDisconnectProvider(LocationProviderListener listener) {
        try {
            locationManager.removeUpdates(listener);
        } catch (Exception ex) {
            Timber.d(ex, "fail to remove location listners, ignore");
        }
    }

    public Boolean tryCheckIfProviderEnabled(String provider) {
        try {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            if (locationManager != null) {
                return locationManager.isProviderEnabled(provider);
            }

        } catch (Exception e) {
            Timber.d(e);
        }

        return null;
    }

}
