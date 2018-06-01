package com.marcin.jasi.roadmemorizer.roadLoader.presentation.ui;

import android.arch.lifecycle.ViewModel;
import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.marcin.jasi.roadmemorizer.general.Constants;
import com.marcin.jasi.roadmemorizer.roadLoader.interactor.GetPlaceAddresUseCase;
import com.marcin.jasi.roadmemorizer.roadLoader.interactor.GetRoadPackUseCase;
import com.marcin.jasi.roadmemorizer.roadLoader.presentation.entity.RoadPack;


import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class RoadLoaderViewModel extends ViewModel {

    public interface OnGetPointInfo {
        void getPointInfo(Pair<String, Marker> data);
    }

    @Inject
    GetRoadPackUseCase getRoadPackUseCase;
    @Inject
    GetPlaceAddresUseCase getPlaceAddres;

    @Inject
    public RoadLoaderViewModel() {
    }

    public Observable<RoadPack> getRoadPack(long roadId) {
        return getRoadPackUseCase.getObservable(roadId);
    }

    public Observable<String> getPlaceAddres(LatLng point) {
        return getPlaceAddres.getObservable(point);
    }

    // concat becauseof OVER_QUERY_LIMIT from googleApi
    public Disposable loadPointsInfo(List<Pair<LatLng, Marker>> data, OnGetPointInfo listener) {
        return Observable.fromIterable(data)
                .concatMap(item -> Observable.just(item).delay(2, TimeUnit.SECONDS))
                .flatMap(item -> getPlaceAddres(item.first)
                        .filter(id -> !id.equals(Constants.EMPTY_STRING))
                        .map(address -> new Pair<>(address, item.second))
                )
                .subscribe(listener::getPointInfo, Timber::d);
    }

}
