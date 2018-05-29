package com.marcin.jasi.roadmemorizer.locationTracker;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.Application;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.GetCurrentEvent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.GetLocationEvent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.StartSavingRoad;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.StopSavingRoad;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.UnconnectReceiverEvent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.LocationResponseData;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.PointData;
import com.marcin.jasi.roadmemorizer.di.scope.PerServiceScope;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationProviderListener;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationProvidersHelper;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;
import com.marcin.jasi.roadmemorizer.general.common.data.entity.GpsProvider;
import com.marcin.jasi.roadmemorizer.general.common.data.entity.LocationProviderType;
import com.marcin.jasi.roadmemorizer.general.common.data.entity.NetworkProvider;
import com.marcin.jasi.roadmemorizer.locationTracker.data.LocationTrackerServiceDataSource;
import com.marcin.jasi.roadmemorizer.locationTracker.di.DaggerLocationTrackerComponent;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.SchedulerSupport;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.marcin.jasi.roadmemorizer.general.Constants.ENABLE_NETWORK_PROVIDER;

@PerServiceScope
public class LocationTrackerService extends Service {

    private static final String TAG = "LocationTrackerService";

    @Inject
    LocationTrackerMediator locationTrackerMediator;
    @Inject
    LocationTrackerServiceDataSource dataSource;
    @Inject
    LocationProvidersHelper locationProvidersHelper;

    private LocationProviderListener gpsProviderListener;
    private LocationProviderListener networkProviderListener;
    private CompositeDisposable disposable = new CompositeDisposable();


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

        gpsProviderListener = getProviderListener(new GpsProvider());
        networkProviderListener = getProviderListener(new NetworkProvider());

        locationProvidersHelper.tryConnectProvider(LocationManager.NETWORK_PROVIDER, networkProviderListener);
        locationProvidersHelper.tryConnectProvider(LocationManager.GPS_PROVIDER, gpsProviderListener);

        disposable.add(handleProvidersStateChange());
        disposable.add(dataSource.getEventsPublisher()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleEvents, Timber::d));
    }

    private void handleEvents(GetLocationEvent event) {
        Timber.e("got event" + event.getClass().getName());

        if (event instanceof StartSavingRoad) {
            if (dataSource.getLastLocationDirections() == null)
                return;

            dataSource.setIsRecorderRoad(true);
        }

        if (event instanceof StopSavingRoad) {
            dataSource.setIsRecorderRoad(false);
        }

        if (event instanceof GetCurrentEvent && !dataSource.getIsRecorderRoad()) {
            startProviders();
        }

        if (event instanceof UnconnectReceiverEvent && !dataSource.getIsRecorderRoad()) {
            stopProviders();
        }
    }

    private void startProviders() {
        locationProvidersHelper.tryConnectProvider(LocationManager.NETWORK_PROVIDER, networkProviderListener);
        locationProvidersHelper.tryConnectProvider(LocationManager.GPS_PROVIDER, gpsProviderListener);
    }

    private void initDependencies() {
        DaggerLocationTrackerComponent
                .builder()
                .applicationComponent(((Application) getApplication()).getApplicationComponent())
                .build()
                .inject(this);
    }

    @NonNull
    private LocationProviderListener getProviderListener(LocationProviderType type) {
        return new LocationProviderListener(type, LocationTrackerService.this::handleLocationChange);
    }

    private void handleLocationChange(Location location, LocationProviderType provider) {
        Timber.i("onLocationChanged: " + location);

        if (!ENABLE_NETWORK_PROVIDER && provider instanceof NetworkProvider)
            return;

        if (dataSource.getIsRecorderRoad()) {

        } else {
            LatLng locationDirections = new LatLng(location.getLatitude(), location.getLongitude());
            LocationResponseData locationResponseData = new PointData(locationDirections);

            dataSource.setLastLocationData(locationResponseData);
            dataSource.setLastLocationDirections(locationDirections);
            dataSource.getLocationResponsePublisher()
                    .onNext(locationResponseData);
        }
    }

    private Disposable handleProvidersStateChange() {
        return locationTrackerMediator
                .getLocationProviderChange()
                .subscribe(changeState -> handleProvidersChangeState(changeState));
    }

    private void handleProvidersChangeState(Pair<Boolean, LocationProviderType> changeState) {
        if (changeState.second instanceof GpsProvider) {
            locationProvidersHelper.setProviderState(changeState.first, LocationManager.GPS_PROVIDER, gpsProviderListener);
        } else if (changeState.second instanceof NetworkProvider) {
            locationProvidersHelper.setProviderState(changeState.first, LocationManager.NETWORK_PROVIDER, networkProviderListener);
        }
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
        stopProviders();

        super.onDestroy();
    }

    private void stopProviders() {
        locationProvidersHelper.setProviderState(false, LocationManager.GPS_PROVIDER, gpsProviderListener);
        locationProvidersHelper.setProviderState(false, LocationManager.NETWORK_PROVIDER, networkProviderListener);
    }

}