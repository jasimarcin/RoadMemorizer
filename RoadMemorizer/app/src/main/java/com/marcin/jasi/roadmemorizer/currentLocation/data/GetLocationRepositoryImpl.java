package com.marcin.jasi.roadmemorizer.currentLocation.data;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.GetLocationRepository;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.LocationServiceIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.LocationSaverEvent;
import com.marcin.jasi.roadmemorizer.locationTracker.data.LocationSaverServiceDataSource;

import io.reactivex.subjects.PublishSubject;

public class GetLocationRepositoryImpl implements GetLocationRepository {

    private LocationSaverServiceDataSource dataSource;

    public GetLocationRepositoryImpl(LocationSaverServiceDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public LocationSaverEvent getLastResponse() {
        return dataSource.getLastLocationData();
    }

    @Override
    public LatLng getLastLocation() {
        return dataSource.getLastLocationDirections();
    }

    @Override
    public PublishSubject<LocationSaverEvent> getLocationEmitter() {
        return dataSource.getLocationSaverPublisher();
    }

    @Override
    public PublishSubject<LocationServiceIntent> getLocationEventPublisher() {
        return dataSource.getEventsPublisher();
    }

    @Override
    public boolean isRecordingRoad() {
        return dataSource.getIsRecordingRoad();
    }
}
