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
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.UnconnectedReceiverIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.GenerateScreenshot;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.LocationSaverEvent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.PointData;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.PointsData;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.SavingRoadError;
import com.marcin.jasi.roadmemorizer.di.scope.PerServiceScope;
import com.marcin.jasi.roadmemorizer.general.Constants;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationProviderListener;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationProvidersHelper;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;
import com.marcin.jasi.roadmemorizer.general.common.data.entity.GpsProvider;
import com.marcin.jasi.roadmemorizer.general.common.data.entity.LocationProviderType;
import com.marcin.jasi.roadmemorizer.general.common.data.entity.NetworkProvider;
import com.marcin.jasi.roadmemorizer.general.helpers.BitmapSaveHelper;
import com.marcin.jasi.roadmemorizer.general.helpers.NotificationHelper;
import com.marcin.jasi.roadmemorizer.locationTracker.data.LocationSaverServiceDataSource;
import com.marcin.jasi.roadmemorizer.locationTracker.di.DaggerLocationTrackerComponent;
import com.marcin.jasi.roadmemorizer.locationTracker.domain.interactor.SaveRoadUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

import static com.marcin.jasi.roadmemorizer.general.Constants.ENABLE_NETWORK_PROVIDER;

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
    @Inject
    SaveRoadUseCase saveRoadUseCase;
    @Inject
    NotificationHelper notificationHelper;

    private LocationProviderListener gpsProviderListener;
    private LocationProviderListener networkProviderListener;
    private CompositeDisposable disposable = new CompositeDisposable();
    private List<LatLng> pointsList = new ArrayList<>();
    private boolean isSaving = false;

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
        } else if (event instanceof UnconnectedReceiverIntent && !dataSource.getIsRecordingRoad()) {
            stopProviders();
        }
    }

    private void handleScreenshotGenerated(ScreenshotGeneratedIntent event) {
        bitmapSaveHelper.trySaveBitmap(event.bitmap(), event.screenshotName());
        handleStopRecording();
    }

    private void handleSaveRoad() {
        if (isSaving)
            return;

        if (pointsList.size() < 2) {
            handleStopRecording();
            return;
        }

        disposable.add(saveRoad());
    }

    @NonNull
    private Disposable saveRoad() {
        isSaving = true;
        return saveRoadUseCase.saveRoad(pointsList)
                .subscribe(this::handleRoadSaved,
                        error -> {
                            Timber.d(error);
                            isSaving = false;
                        });
    }

    private void handleRoadSaved(String bitmapFilename) {
        if (bitmapFilename.equals(Constants.EMPTY_STRING)) {
            sendSavingError();
            return;
        }

        dataSource.getLocationSaverPublisher()
                .onNext(new GenerateScreenshot.Builder()
                        .screenshotFileName(bitmapFilename)
                        .endLocation(pointsList.get(pointsList.size() - 1))
                        .startLocation(pointsList.get(0))
                        .points(pointsList)
                        .build());
    }

    private void sendSavingError() {
        dataSource.getLocationSaverPublisher()
                .onNext(new SavingRoadError());
    }

    private void handleStartRecording() {
        if (dataSource.getLastLocationDirections() == null)
            return;

        callIsRecording();

        dataSource.getLocationSaverPublisher()
                .onNext(dataSource.getLastLocationData());

        handleRecordingLocationChange(dataSource.getLastLocation());
    }

    private void callIsRecording() {
        dataSource.setIsRecorderRoad(true);
        notificationHelper.showIsRecordingNotification(this);
    }

    private void handleStopRecording() {
        pointsList = new ArrayList<>();
        callIsStopRecording();

        isSaving = false;

        updateNewLocation(dataSource.getLastLocation(),
                new PointData.Builder()
                        .point(dataSource.getLastLocationDirections()).build());
    }

    private void callIsStopRecording() {
        dataSource.setIsRecorderRoad(false);
        notificationHelper.hideIsRecordingNotification(this);
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

        if (dataSource.getIsRecordingRoad()) {
            handleRecordingLocationChange(location);
        } else {
            handleCurrentLocationChange(location);
        }
    }

    private void handleRecordingLocationChange(Location location) {
        LatLng locationDirections = new LatLng(location.getLatitude(), location.getLongitude());
        pointsList.add(locationDirections);

        LocationSaverEvent locationSaverEvent = new PointsData.Builder()
                .endLocation(locationDirections)
                .startLocation(pointsList.get(0))
                .points(pointsList)
                .build();

        updateNewLocation(location, locationSaverEvent);
    }

    private void handleCurrentLocationChange(Location location) {
        LatLng locationDirections = new LatLng(location.getLatitude(), location.getLongitude());
        LocationSaverEvent locationSaverEvent = new PointData.Builder()
                .point(locationDirections).build();

        updateNewLocation(location, locationSaverEvent);
    }

    private void updateNewLocation(Location location, LocationSaverEvent locationSaverEvent) {
        dataSource.setLastLocationData(locationSaverEvent);
        dataSource.setLastLocationDirections(location);
        dataSource.getLocationSaverPublisher()
                .onNext(locationSaverEvent);
    }

    private Disposable handleProvidersStateChange() {
        return locationTrackerMediator
                .getLocationProviderChange()
                .subscribe(this::handleProvidersChangeState);
    }

    private void handleProvidersChangeState(Pair<Boolean, LocationProviderType> changeState) {
        if (changeState.second instanceof GpsProvider) {
            locationProvidersHelper.setProviderState(changeState.first, LocationManager.GPS_PROVIDER, gpsProviderListener);
        } else if (changeState.second instanceof NetworkProvider) {
            locationProvidersHelper.setProviderState(changeState.first, LocationManager.NETWORK_PROVIDER, networkProviderListener);
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        finishServiceComponents();
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onDestroy() {
        finishServiceComponents();
        super.onDestroy();
    }

    private void finishServiceComponents() {
        disposable.dispose();
        stopProviders();
        notificationHelper.hideIsRecordingNotification(this);
    }

}