package com.marcin.jasi.roadmemorizer.currentLocation.domain;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.LocationServiceIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.LocationSaverEvent;

import io.reactivex.subjects.PublishSubject;

public interface GetLocationRepository {

    LocationSaverEvent getLastResponse();

    LatLng getLastLocation();

    PublishSubject<LocationSaverEvent> getLocationEmitter();

    PublishSubject<LocationServiceIntent> getLocationEventPublisher();

    boolean isRecordingRoad();

}
