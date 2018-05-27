package com.marcin.jasi.roadmemorizer.general.common.data;

import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.general.common.data.entity.LocationProviderType;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class LocationTrackerMediator {

    private PublishSubject<Pair<Boolean, LocationProviderType>> locationProviderChange = PublishSubject.create();
    private PublishSubject<Pair<LatLng, LocationProviderType>> locationChange = PublishSubject.create();

    public PublishSubject<Pair<Boolean, LocationProviderType>> getLocationProviderChange() {
        return locationProviderChange;
    }

    public PublishSubject<Pair<LatLng, LocationProviderType>> getLocationChange() {
        return locationChange;
    }
}
