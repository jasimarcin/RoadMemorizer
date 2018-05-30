package com.marcin.jasi.roadmemorizer.currentLocation.presentation;

import android.arch.lifecycle.ViewModel;
import android.content.res.Resources;

import com.marcin.jasi.roadmemorizer.R;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.GetLocationUseCase;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.AlignClickIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.LocationServiceIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.MoveCameraIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event.SavingButtonClickIntent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.GenerateScreenshot;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.LocationSaverEvent;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.PointData;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.PointsData;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response.SavingRoadError;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.AlignMap;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.CurrentLocationViewState;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.ErrorViewState;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.GenerateScreenshotViewState;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.IdleViewState;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.UpdatePointViewState;
import com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity.UpdateRoadViewState;
import com.marcin.jasi.roadmemorizer.general.helpers.PermissionHelper;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

// todo refactor
public class CurrentLocationViewModel extends ViewModel {

    @Inject
    GetLocationUseCase getLocationUseCase;
    @Inject
    PermissionHelper permissionHelper;
    @Inject
    Resources resources;

    private CompositeDisposable disposable;
    private PublishSubject<CurrentLocationViewState> viewStatePublisher = PublishSubject.create();
    private boolean cameraMoved = false;
    private boolean isAnimating = false;


    @Inject
    public CurrentLocationViewModel() {
    }

    public void init() {
        if (disposable != null)
            disposable.dispose();

        disposable = new CompositeDisposable();

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

        // todo
        // it could be other types of event
        // dedicated for useCase
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
        if (locationEvent instanceof SavingRoadError) {
            return new ErrorViewState(resources.getString(R.string.save_road_error_message));
        }

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

    private boolean gotLastLocation() {
        return getLocationUseCase.getLastLocation() != null;
    }

    public boolean isAnimating() {
        return isAnimating;
    }

    public void setAnimating(boolean animating) {
        isAnimating = animating;
    }

    public void dispose() {
        disposable.dispose();
    }

}
