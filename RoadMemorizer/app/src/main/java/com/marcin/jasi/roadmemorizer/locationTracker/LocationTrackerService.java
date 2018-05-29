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
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.CurrentLocationIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.LocationServiceIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.SavingButtonClickIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.ScreenshotGeneratedIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.UnconnectReceiverIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.GenerateScreenshot;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.LocationSaverEvent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.PointData;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.PointsData;
import com.marcin.jasi.roadmemorizer.database.data.LocationDatabaseDataSource;
import com.marcin.jasi.roadmemorizer.di.scope.PerServiceScope;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationProviderListener;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationProvidersHelper;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;
import com.marcin.jasi.roadmemorizer.general.common.data.entity.GpsProvider;
import com.marcin.jasi.roadmemorizer.general.common.data.entity.LocationProviderType;
import com.marcin.jasi.roadmemorizer.general.common.data.entity.NetworkProvider;
import com.marcin.jasi.roadmemorizer.general.helpers.BitmapSaveHelper;
import com.marcin.jasi.roadmemorizer.locationTracker.data.LocationSaverServiceDataSource;
import com.marcin.jasi.roadmemorizer.locationTracker.di.DaggerLocationTrackerComponent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import static com.marcin.jasi.roadmemorizer.general.Constants.ENABLE_NETWORK_PROVIDER;

// todo refactor
@PerServiceScope
public class LocationTrackerService extends Service {

    @Inject
    LocationTrackerMediator locationTrackerMediator;
    @Inject
    LocationSaverServiceDataSource dataSource;
    @Inject
    LocationProvidersHelper locationProvidersHelper;
    @Inject
    BitmapSaveHelper bitmapSaveHelper;
//    @Inject
//    LocationDatabaseDataSource databaseDataSource;

    private LocationProviderListener gpsProviderListener;
    private LocationProviderListener networkProviderListener;
    private CompositeDisposable disposable = new CompositeDisposable();
    private List<LatLng> pointsList = new ArrayList<>();


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

    private void handleEvents(LocationServiceIntent event) {
        if (event instanceof SavingButtonClickIntent) {
            if (!dataSource.getIsRecordingRoad()) {
                handleStartRecording();
            } else {
                handleSaveRoad();
            }
        } else if (event instanceof ScreenshotGeneratedIntent) {
            handleScreenshotGenerated((ScreenshotGeneratedIntent) event);
        } else if (event instanceof CurrentLocationIntent && !dataSource.getIsRecordingRoad()) {
            startProviders();
        } else if (event instanceof UnconnectReceiverIntent && !dataSource.getIsRecordingRoad()) {
            stopProviders();
        }
    }

    private void handleScreenshotGenerated(ScreenshotGeneratedIntent event) {
        bitmapSaveHelper.trySaveBitmap(event.getBitmap(),
                event.getScreenshotName());
        handleStopRecording();
    }

    private void handleSaveRoad() {
        if (pointsList.size() < 2) {
            handleStopRecording();
            return;
        }

        // save db
//                int roadId = db.saveList(list);
        String filename = "filename";

//        databaseDataSource.

        dataSource.getLocationSaverPublisher()
                .onNext(new GenerateScreenshot(pointsList.get(0),
                        pointsList.get(pointsList.size() - 1), pointsList,
                        filename));
    }

    private void handleStartRecording() {
        if (dataSource.getLastLocationDirections() == null)
            return;

        dataSource.setIsRecorderRoad(true);
        dataSource.getLocationSaverPublisher()
                .onNext(dataSource.getLastLocationData());
    }

    private void handleStopRecording() {
        pointsList = new ArrayList<>();
        dataSource.setIsRecorderRoad(false);

        updateNewLocation(dataSource.getLastLocationDirections(),
                new PointData(dataSource.getLastLocationDirections()));
    }

    private void startProviders() {
        locationProvidersHelper.tryConnectProvider(LocationManager.NETWORK_PROVIDER, networkProviderListener);
        locationProvidersHelper.tryConnectProvider(LocationManager.GPS_PROVIDER, gpsProviderListener);
    }

    private void stopProviders() {
        locationProvidersHelper.setProviderState(false, LocationManager.GPS_PROVIDER, gpsProviderListener);
        locationProvidersHelper.setProviderState(false, LocationManager.NETWORK_PROVIDER, networkProviderListener);
    }

    private void handleLocationChange(Location location, LocationProviderType provider) {
        Timber.i("onLocationChanged: %s", location);

        if (!ENABLE_NETWORK_PROVIDER && provider instanceof NetworkProvider)
            return;

        // todo remove temporary
        if (dataSource.getIsRecordingRoad()) {
            for (int i = 1; i < 10; i++) {
                Location tmp = new Location(location);
                tmp.setLongitude(tmp.getLongitude() + i * 0.01);
                tmp.setLatitude(tmp.getLatitude() + i * 0.01);
                handleRecordingLocationChange(tmp);
            }

        } else {
            handleCurrentLocationChange(location);
        }
    }

    private void handleRecordingLocationChange(Location location) {
        LatLng locationDirections = new LatLng(location.getLatitude(), location.getLongitude());
        pointsList.add(locationDirections);

        LocationSaverEvent locationSaverEvent = new PointsData(
                pointsList.get(0),
                locationDirections,
                pointsList);

        updateNewLocation(locationDirections, locationSaverEvent);
    }

    private void handleCurrentLocationChange(Location location) {
        LatLng locationDirections = new LatLng(location.getLatitude(), location.getLongitude());
        LocationSaverEvent locationSaverEvent = new PointData(locationDirections);

        updateNewLocation(locationDirections, locationSaverEvent);
    }

    private void updateNewLocation(LatLng locationDirections, LocationSaverEvent locationSaverEvent) {
        dataSource.setLastLocationData(locationSaverEvent);
        dataSource.setLastLocationDirections(locationDirections);
        dataSource.getLocationSaverPublisher()
                .onNext(locationSaverEvent);
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

}