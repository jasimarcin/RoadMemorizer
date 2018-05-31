package com.marcin.jasi.roadmemorizer.database.data;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.database.AppDatabase;
import com.marcin.jasi.roadmemorizer.database.data.entities.LocationData;
import com.marcin.jasi.roadmemorizer.database.data.entities.RoadData;
import com.marcin.jasi.roadmemorizer.general.common.data.DataMapper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import timber.log.Timber;

public class LocationDatabaseDataSource {

    private AppDatabase appDatabase;
    private DataMapper<LocationData, LatLng> entityMapper;

    public LocationDatabaseDataSource(AppDatabase appDatabase, DataMapper<LocationData, LatLng> entityMapper) {
        this.appDatabase = appDatabase;
        this.entityMapper = entityMapper;
    }

    public Observable<Long> insertNewRoad(RoadData data) {
        return Observable.fromCallable(() -> appDatabase.roadDao().insertRoad(data));
//                .subscribeOn(Schedulers.computation())
//                .observeOn(Schedulers.computation());
    }

    public Observable<Boolean> saveRoad(List<LocationData> data) {
        return Observable
                .fromCallable(() -> {
                    appDatabase.locationDao().insertRoad(data);
                    return true;
                })
//                .subscribeOn(Schedulers.computation())
//                .observeOn(Schedulers.computation())
                .onErrorReturn(error -> {
                    Timber.d(error);
                    return false;
                });
    }

    public Observable<Boolean> updateBitmapFilename(String filename, long roadId) {
        return Observable
                .fromCallable(() -> {
                    appDatabase.roadDao().uploadRoadFilename(filename, roadId);
                    return true;
                })
//                .subscribeOn(Schedulers.computation())
//                .observeOn(Schedulers.computation())
                .onErrorReturn(error -> {
                    Timber.d(error);
                    return false;
                });
    }

    Observable<List<LatLng>> getRoadPoints(long roadId) {
        return Observable
                .fromCallable(() -> appDatabase.locationDao().getRoadPoints(roadId))
                .map(list -> entityMapper.transform(list));
    }



}
