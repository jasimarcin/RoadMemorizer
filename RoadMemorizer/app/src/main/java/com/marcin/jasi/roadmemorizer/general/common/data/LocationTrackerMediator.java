package com.marcin.jasi.roadmemorizer.general.common.data;

import android.util.Pair;

import com.marcin.jasi.roadmemorizer.general.common.data.entity.LocationProviderType;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.subjects.PublishSubject;

public class LocationTrackerMediator {

    private PublishSubject<Boolean> locationPermissionChange = PublishSubject.create();
    private PublishSubject<Pair<Boolean, LocationProviderType>> locationProviderChange = PublishSubject.create();
    private AtomicBoolean currentLocationPermissionState = new AtomicBoolean(false);

    public PublishSubject<Pair<Boolean, LocationProviderType>> getLocationProviderChange() {
        return locationProviderChange;
    }

    public PublishSubject<Boolean> getLocationPermissionChange() {
        return locationPermissionChange;
    }

    public AtomicBoolean getCurrentLocationPermissionState() {
        return currentLocationPermissionState;
    }
}
