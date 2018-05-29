package com.marcin.jasi.roadmemorizer.currentLocation.data;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.GetLocationRepository;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.GetLocationEvent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.LocationResponseData;
import com.marcin.jasi.roadmemorizer.locationTracker.data.LocationTrackerServiceDataSource;

import io.reactivex.subjects.PublishSubject;

public class GetLocationRepositoryImpl implements GetLocationRepository {

    private LocationTrackerServiceDataSource dataSource;

    public GetLocationRepositoryImpl(LocationTrackerServiceDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public LocationResponseData getLastResponse() {
        return dataSource.getLastLocationData();
    }

    @Override
    public LatLng getLastLocation() {
        return dataSource.getLastLocationDirections();
    }

    @Override
    public PublishSubject<LocationResponseData> getLocationEmitter() {
        return dataSource.getLocationResponsePublisher();
    }

    @Override
    public PublishSubject<GetLocationEvent> getLocationEventPublisher() {
        return dataSource.getEventsPublisher();
    }

}
