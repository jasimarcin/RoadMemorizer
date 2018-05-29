package com.marcin.jasi.roadmemorizer.locationTracker.data;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.GetLocationEvent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.LocationResponseData;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import io.reactivex.subjects.PublishSubject;

public class LocationTrackerServiceDataSource {

    private AtomicReference<LocationResponseData> lastLocationData = new AtomicReference<>(null);
    private AtomicReference<LatLng> lastLocationDirections = new AtomicReference<>(null);
    private PublishSubject<LocationResponseData> locationResponsePublisher = PublishSubject.create();
    private PublishSubject<GetLocationEvent> eventsPublisher = PublishSubject.create();
    private AtomicBoolean isRecorderRoad = new AtomicBoolean(false);


    public LocationResponseData getLastLocationData() {
        return lastLocationData.get();
    }

    public void setLastLocationData(LocationResponseData lastLocationData) {
        this.lastLocationData.set(lastLocationData);
    }

    public LatLng getLastLocationDirections() {
        return lastLocationDirections.get();
    }

    public void setLastLocationDirections(LatLng lastLocationDirections) {
        this.lastLocationDirections.set(lastLocationDirections);
    }

    public PublishSubject<LocationResponseData> getLocationResponsePublisher() {
        return locationResponsePublisher;
    }

    public boolean getIsRecorderRoad() {
        return isRecorderRoad.get();
    }

    public void setIsRecorderRoad(boolean isRecorderRoad) {
        this.isRecorderRoad.set(isRecorderRoad);
    }

    public PublishSubject<GetLocationEvent> getEventsPublisher() {
        return eventsPublisher;
    }
}
