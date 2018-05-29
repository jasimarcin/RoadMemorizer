package com.marcin.jasi.roadmemorizer.currentLocation.domain;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.GetLocationEvent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.LocationResponseData;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class GetLocationUseCase {

    private PublishSubject<GetLocationEvent> eventsReceiver = PublishSubject.create();
    private GetLocationRepository repository;


    public GetLocationUseCase(GetLocationRepository repository) {
        this.repository = repository;
    }

    public void receiveEvent(GetLocationEvent event) {

        repository.getLocationEventPublisher()
                .onNext(event);
    }

    public void callEvent(GetLocationEvent event) {
        eventsReceiver.onNext(event);
    }

    public Disposable connectEventsReceiver() {
        return eventsReceiver
                .observeOn(Schedulers.computation())
                .subscribe(this::receiveEvent, Timber::d);
    }

    public PublishSubject<LocationResponseData> getLocationEmitter() {
        return repository.getLocationEmitter();
    }

    public LatLng getLastLocation() {
        return repository.getLastLocation();
    }

    public LocationResponseData getLastResponse() {
        return repository.getLastResponse();
    }

}
