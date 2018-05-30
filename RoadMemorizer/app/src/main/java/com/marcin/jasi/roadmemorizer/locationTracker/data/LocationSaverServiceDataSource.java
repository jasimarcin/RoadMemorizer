package com.marcin.jasi.roadmemorizer.locationTracker.data;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.LocationServiceIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.LocationSaverEvent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.subjects.PublishSubject;

public class LocationSaverServiceDataSource {

    private AtomicReference<LocationSaverEvent> lastLocationData = new AtomicReference<>(null);
    private AtomicReference<Location> lastLocation = new AtomicReference<>(null);
    private PublishSubject<LocationSaverEvent> locationSaverPublisher = PublishSubject.create();
    private PublishSubject<LocationServiceIntent> eventsPublisher = PublishSubject.create();
    private AtomicBoolean isRecorderRoad = new AtomicBoolean(false);


    public LocationSaverEvent getLastLocationData() {
        return lastLocationData.get();
    }

    public void setLastLocationData(LocationSaverEvent lastLocationData) {
        this.lastLocationData.set(lastLocationData);
    }

    public LatLng getLastLocationDirections() {
        if (lastLocation != null && lastLocation.get() != null)
            return new LatLng(lastLocation.get().getLatitude(), lastLocation.get().getLongitude());
        else
            return null;
    }

    public Location getLastLocation() {
        return lastLocation.get();
    }

    public void setLastLocationDirections(Location lastLocationDirections) {
        this.lastLocation.set(lastLocationDirections);
    }

    public PublishSubject<LocationSaverEvent> getLocationSaverPublisher() {
        return locationSaverPublisher;
    }

    public boolean getIsRecordingRoad() {
        return isRecorderRoad.get();
    }

    public void setIsRecorderRoad(boolean isRecorderRoad) {
        this.isRecorderRoad.set(isRecorderRoad);
    }

    public PublishSubject<LocationServiceIntent> getEventsPublisher() {
        return eventsPublisher;
    }
}
