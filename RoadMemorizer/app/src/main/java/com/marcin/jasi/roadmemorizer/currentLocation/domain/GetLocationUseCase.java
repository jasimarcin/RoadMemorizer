package com.marcin.jasi.roadmemorizer.currentLocation.domain;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.LocationServiceIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.LocationSaverEvent;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class GetLocationUseCase {

    private PublishSubject<LocationServiceIntent> eventsReceiver = PublishSubject.create();
    private GetLocationRepository repository;


    public GetLocationUseCase(GetLocationRepository repository) {
        this.repository = repository;
    }

    private void receiveEvent(LocationServiceIntent event) {
        repository.getLocationEventPublisher()
                .onNext(event);
    }

    public void callEvent(LocationServiceIntent event) {
        eventsReceiver.onNext(event);
    }

    public Disposable connectEventsReceiver() {
        return eventsReceiver
                .observeOn(Schedulers.computation())
                .subscribe(this::receiveEvent, Timber::d);
    }

    public PublishSubject<LocationSaverEvent> getLocationEmitter() {
        return repository.getLocationEmitter();
    }

    public LatLng getLastLocation() {
        return repository.getLastLocation();
    }

    public LocationSaverEvent getLastResponse() {
        return repository.getLastResponse();
    }


    public boolean isSavingRoad() {
        return repository.isRecordingRoad();
    }

}
