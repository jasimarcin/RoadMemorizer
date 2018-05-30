package com.marcin.jasi.roadmemorizer.database;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.database.data.entities.RoadData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public interface LocationDatabaseRepository {

    Observable<Long> insertNewRoad(RoadData data);

    Observable<Boolean> saveRoad(List<LatLng> list, long roadId);

    Observable<Boolean> updateBitmapFilename(String filename, long roadId);
}
