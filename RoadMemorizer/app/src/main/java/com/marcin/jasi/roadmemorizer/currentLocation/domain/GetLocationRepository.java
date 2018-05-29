package com.marcin.jasi.roadmemorizer.currentLocation.domain;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.GetLocationEvent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.LocationResponseData;

import io.reactivex.subjects.PublishSubject;

public interface GetLocationRepository {

    LocationResponseData getLastResponse();

    LatLng getLastLocation();

    PublishSubject<LocationResponseData> getLocationEmitter();

    PublishSubject<GetLocationEvent> getLocationEventPublisher();

}
