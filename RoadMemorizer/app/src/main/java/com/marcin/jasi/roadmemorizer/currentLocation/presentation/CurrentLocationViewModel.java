package com.marcin.jasi.roadmemorizer.currentLocation.presentation;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;
import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;


public class CurrentLocationViewModel extends ViewModel {

    @Inject
    LocationTrackerMediator locationTrackerMediator;

    private CompositeDisposable disposable = new CompositeDisposable();
    private List<LatLng> roadPoints = new ArrayList<>();
    private ObservableBoolean cameraMoved = new ObservableBoolean(false);
    private PublishSubject<CurrentLocationViewState> viewStatePublisher = PublishSubject.create();

    @Inject
    public CurrentLocationViewModel() {
    }

    public List<LatLng> getRoadPoints() {
        return roadPoints;
    }

    public void setRoadPoints(List<LatLng> roadPoints) {
        this.roadPoints = roadPoints;
    }

    public ObservableBoolean isCameraMoved() {
        return cameraMoved;
    }

    public void setCameraMoved(boolean cameraMoved) {
        this.cameraMoved.set(cameraMoved);
    }

    public void onAlignClick() {
        this.cameraMoved.set(false);
        viewStatePublisher.onNext(new AlignMap());
    }

    public LatLng getLastPoint() {
        if (roadPoints != null && roadPoints.size() > 0)
            return roadPoints.get(roadPoints.size() - 1);
        else
            return null;
    }

    private Observable<Pair<LatLng, List<LatLng>>> getNewLocationObservable() {
        return locationTrackerMediator
                .getLocationChange()
//                .filter(newLocation -> newLocation.second instanceof GpsProvider)
                .map(newLocation -> newLocation.first)
                .map(newLocation -> {
                    roadPoints.add(newLocation);
                    return new Pair<>(newLocation, roadPoints);
                });
    }

    public Observable<CurrentLocationViewState> getMapEvents() {
        return viewStatePublisher;
    }

    public void connect() {
        disposable.add(getNewLocationObservable()
                .subscribe(location -> viewStatePublisher.onNext(new UpdateLocation(location))));
    }
}
