package com.marcin.jasi.roadmemorizer.locationTracker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.Application;
import com.marcin.jasi.roadmemorizer.di.scope.PerServiceScope;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;
import com.marcin.jasi.roadmemorizer.general.common.data.entity.GpsProvider;
import com.marcin.jasi.roadmemorizer.general.common.data.entity.LocationProviderType;
import com.marcin.jasi.roadmemorizer.general.common.data.entity.NetworkProvider;
import com.marcin.jasi.roadmemorizer.locationTracker.di.DaggerLocationTrackerComponent;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import static com.marcin.jasi.roadmemorizer.general.Constants.LOCATION_DISTANCE;
import static com.marcin.jasi.roadmemorizer.general.Constants.LOCATION_INTERVAL;

@PerServiceScope
public class LocationTrackerService extends Service {

    private static final String TAG = "LocationTrackerService";

    @Inject
    LocationTrackerMediator locationTrackerMediator;

    private LocationListener gpsProviderListener;
    private LocationListener networkProviderListener;
    private CompositeDisposable disposable = new CompositeDisposable();
    private LocationManager locationManager = null;


    private class LocationListener implements android.location.LocationListener {
        private LocationProviderType provider;
        private LocationTrackerMediator locationTrackerMediator;

        public LocationListener(LocationProviderType provider, LocationTrackerMediator locationTrackerMediator) {
            this.provider = provider;
            this.locationTrackerMediator = locationTrackerMediator;
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.e(TAG, "onLocationChanged: " + location);

            locationTrackerMediator
                    .getLocationChange()
                    .onNext(new Pair<>(new LatLng(location.getLatitude(), location.getLongitude()), provider));
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


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        initDependencies();

        gpsProviderListener = new LocationListener(new GpsProvider(), locationTrackerMediator);
        networkProviderListener = new LocationListener(new NetworkProvider(), locationTrackerMediator);

        initializeLocationManager();
        tryConnectNetworkProvider();
        tryConnectGPSProvider();
        disposable.add(handleProvidersStateChange());
    }

    private void initDependencies() {
        DaggerLocationTrackerComponent
                .builder()
                .applicationComponent(((Application) getApplication()).getApplicationComponent())
                .build()
                .inject(this);
    }

    private void tryConnectNetworkProvider() {
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    networkProviderListener);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "network provider does not exist, " + ex.getMessage());
        }

    }

    private void tryConnectGPSProvider() {
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE,
                    gpsProviderListener);
        } catch (java.lang.SecurityException ex) {
            Log.i(TAG, "fail to request location update, ignore", ex);
        } catch (IllegalArgumentException ex) {
            Log.d(TAG, "gps provider does not exist " + ex.getMessage());
        }
    }

    private Disposable handleProvidersStateChange() {
        return locationTrackerMediator
                .getLocationProviderChange()
                .subscribe(changeState -> {

                    if (changeState.second instanceof GpsProvider) {
                        if (changeState.first) {
                            tryConnectGPSProvider();
                        } else {
                            tryDisconnectGPSProvider();
                        }
                    } else if (changeState.second instanceof NetworkProvider) {
                        if (changeState.first) {
                            tryConnectNetworkProvider();
                        } else {
                            tryDisconnectNetworkProvider();
                        }
                    }

                });
    }

    private void tryDisconnectGPSProvider() {
        try {
            locationManager.removeUpdates(gpsProviderListener);
        } catch (Exception ex) {
            Timber.d(ex, "fail to remove location listners, ignore");
        }
    }

    private void tryDisconnectNetworkProvider() {
        try {
            locationManager.removeUpdates(networkProviderListener);
        } catch (Exception ex) {
            Timber.d(ex, "fail to remove location listners, ignore");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        disposable.dispose();

        tryDisconnectGPSProvider();
        tryDisconnectNetworkProvider();
    }

    private void initializeLocationManager() {
        if (locationManager == null) {
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }
}