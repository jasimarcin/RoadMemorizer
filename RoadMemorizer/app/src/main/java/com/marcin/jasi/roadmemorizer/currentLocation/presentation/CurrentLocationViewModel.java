package com.marcin.jasi.roadmemorizer.currentLocation.presentation;

import android.arch.lifecycle.ViewModel;

import com.marcin.jasi.roadmemorizer.currentLocation.domain.GetLocationUseCase;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.AlignClickIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.LocationServiceIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.MoveCameraIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.GenerateScreenshot;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.LocationSaverEvent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.PointData;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.PointsData;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.AlignMap;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.CurrentLocationViewState;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.GenerateScreenshotViewState;
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
    private boolean cameraMoved = false;


    @Inject
    public CurrentLocationViewModel() {
    }

    public void init() {
        if (disposable != null)
            disposable.dispose();

        disposable.add(getNewLocationObservable()
                .subscribe(locationEvent -> publishWrappedViewState(dataMapperMethod(locationEvent))));

        disposable.add(getLocationUseCase.connectEventsReceiver());
    }

    private Observable<LocationSaverEvent> getNewLocationObservable() {
        return getLocationUseCase.getLocationEmitter();
    }

    public void callEvent(LocationServiceIntent event) {

        if (event instanceof MoveCameraIntent)
            handleMoveCameraEvent();
        else if (event instanceof AlignClickIntent)
            handleAlignClick();
        getLocationUseCase.callEvent(event);
    }

    private void handleMoveCameraEvent() {
        if (gotLastLocation()) {

            CurrentLocationViewState viewState = getLastState();
            viewState.setShowAligmButton(true);

            publishWrappedViewState(viewState);
            cameraMoved = true;
        }
    }

    private void handleAlignClick() {
        this.cameraMoved = false;
        publishWrappedViewState(new AlignMap(getLocationUseCase.getLastLocation()));
    }

    private void publishWrappedViewState(CurrentLocationViewState viewState) {
        viewStatePublisher.onNext(wrapButtonsStats(viewState));
    }

    private CurrentLocationViewState dataMapperMethod(LocationSaverEvent locationEvent) {
        if (locationEvent instanceof GenerateScreenshot) {
            return new GenerateScreenshotViewState(
                    ((PointsData) locationEvent).getPoints(),
                    ((PointsData) locationEvent).getStartLocation(),
                    ((PointsData) locationEvent).getEndLocation(),
                    canAlignMap(),
                    ((GenerateScreenshot) locationEvent).getScreenshotFileName());
        }

        if (locationEvent instanceof PointData) {
            return new UpdatePointViewState(
                    ((PointData) locationEvent).getPoint(),
                    canAlignMap());
        }

        if (locationEvent instanceof PointsData) {
            return new UpdateRoadViewState(
                    ((PointsData) locationEvent).getPoints(),
                    ((PointsData) locationEvent).getStartLocation(),
                    ((PointsData) locationEvent).getEndLocation(),
                    canAlignMap());
        }

        return new IdleViewState();
    }

    private boolean canAlignMap() {
        return !cameraMoved;
    }

    public CurrentLocationViewState getLastState() {
        return wrapButtonsStats(dataMapperMethod(getLocationUseCase.getLastResponse()));
    }

    private CurrentLocationViewState wrapButtonsStats(CurrentLocationViewState viewState) {
        viewState.setShowAligmButton(cameraMoved);

        viewState.setShowSaveButton(validateIfShowSaveButton());
        viewState.setShowStopSaveingButton(validateIfShowStopSavingButton());

        return viewState;
    }

    private boolean validateIfShowStopSavingButton() {
        return getLocationUseCase.getLastLocation() != null && !getLocationUseCase.isSavingRoad();
    }

    private boolean validateIfShowSaveButton() {
        return getLocationUseCase.getLastLocation() != null && getLocationUseCase.isSavingRoad();
    }

    public Observable<CurrentLocationViewState> getMapEvents() {
        return viewStatePublisher;
    }

    public boolean gotLastLocation() {
        return getLocationUseCase.getLastLocation() != null;
    }

    public void dispose() {
        disposable.dispose();
    }

}
