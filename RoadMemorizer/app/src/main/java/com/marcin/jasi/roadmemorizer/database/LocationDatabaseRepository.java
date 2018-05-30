package com.marcin.jasi.roadmemorizer.database;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface LocationDatabaseRepository {

    Observable<Long> createNewRoad();

    Observable<Boolean> saveRoad(List<LatLng> list, long roadId);

}
