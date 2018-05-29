package com.marcin.jasi.roadmemorizer.currentLocation.presentation;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import com.marcin.jasi.roadmemorizer.currentLocation.domain.GetLocationUseCase;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.GetLocationEvent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.LocationResponseData;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.PointData;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.PointsData;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.AlignMap;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.CurrentLocationViewState;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.IdleViewState;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.UpdatePointViewState;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.UpdateRoadViewState;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;


public class CurrentLocationViewModel extends ViewModel {

    @Inject
    GetLocationUseCase getLocationUseCase;

    private CompositeDisposable disposable = new CompositeDisposable();
    private PublishSubject<CurrentLocationViewState> viewStatePublisher = PublishSubject.create();
    private ObservableBoolean cameraMoved = new ObservableBoolean(false);

    @Inject
    public CurrentLocationViewModel() {
    }

    public void callEvent(GetLocationEvent event) {
        getLocationUseCase.callEvent(event);
    }

    public ObservableBoolean isCameraMoved() {
        return cameraMoved;
    }

    public void setCameraMoved(boolean cameraMoved) {
        this.cameraMoved.set(cameraMoved);
    }

    public void onAlignClick() {
        this.cameraMoved.set(false);
        viewStatePublisher.onNext(new AlignMap(getLocationUseCase.getLastLocation()));
    }

    private Observable<LocationResponseData> getNewLocationObservable() {
        return getLocationUseCase
                .getLocationEmitter();
    }

    public Observable<CurrentLocationViewState> getMapEvents() {
        return viewStatePublisher;
    }

    public void init() {
        if (disposable != null)
            disposable.dispose();

        disposable = new CompositeDisposable();
        disposable.add(getNewLocationObservable()
                .subscribe(locationEvent -> viewStatePublisher.onNext(dataMapperMethod(locationEvent))));

        disposable.add(getLocationUseCase.connectEventsReceiver());
    }

    private CurrentLocationViewState dataMapperMethod(LocationResponseData locationEvent) {
        if (locationEvent instanceof PointData) {
            return new UpdatePointViewState(((PointData) locationEvent).getPoint(), shouldAlign());
        }

        if (locationEvent instanceof PointsData) {
            return new UpdateRoadViewState(
                    ((PointsData) locationEvent).getPoints(),
                    ((PointsData) locationEvent).getStartLocation(),
                    ((PointsData) locationEvent).getEndLocation(),
                    shouldAlign());
        }

        return new IdleViewState();
    }

    private boolean shouldAlign() {
        return !cameraMoved.get();
    }

    public CurrentLocationViewState getLastState() {
        return dataMapperMethod(getLocationUseCase.getLastResponse());
    }

    public boolean gotLastLocation() {
        return getLocationUseCase.getLastLocation() != null;
    }

    public void dispose() {
        disposable.dispose();
    }

}
