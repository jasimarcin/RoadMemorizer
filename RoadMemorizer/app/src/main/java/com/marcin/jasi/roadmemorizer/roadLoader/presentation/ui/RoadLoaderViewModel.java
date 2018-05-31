package com.marcin.jasi.roadmemorizer.roadLoader.presentation.ui;

import android.arch.lifecycle.ViewModel;

import com.marcin.jasi.roadmemorizer.roadLoader.interactor.GetRoadPackUseCase;
import com.marcin.jasi.roadmemorizer.roadLoader.presentation.entity.RoadPack;


import javax.inject.Inject;

import io.reactivex.Observable;

public class RoadLoaderViewModel extends ViewModel {

    @Inject
    GetRoadPackUseCase getRoadPackUseCase;

    @Inject
    public RoadLoaderViewModel() {
    }

    public Observable<RoadPack> getRoadPack(long roadId) {
        return getRoadPackUseCase.getObservable(roadId);
    }

}
