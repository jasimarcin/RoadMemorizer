package com.marcin.jasi.roadmemorizer.roadLoader.presentation.ui;

import android.arch.lifecycle.ViewModel;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.roadLoader.interactor.GetPlaceIdUseCase;
import com.marcin.jasi.roadmemorizer.roadLoader.interactor.GetRoadPackUseCase;
import com.marcin.jasi.roadmemorizer.roadLoader.presentation.entity.RoadPack;


import javax.inject.Inject;

import io.reactivex.Observable;

public class RoadLoaderViewModel extends ViewModel {

    @Inject
    GetRoadPackUseCase getRoadPackUseCase;
    @Inject
    GetPlaceIdUseCase getPlaceId;

    @Inject
    public RoadLoaderViewModel() {
    }

    public Observable<RoadPack> getRoadPack(long roadId) {
        return getRoadPackUseCase.getObservable(roadId);
    }

    public Observable<String> getPlaceId(LatLng point) {
        return getPlaceId.getObservable(point);
    }

}
