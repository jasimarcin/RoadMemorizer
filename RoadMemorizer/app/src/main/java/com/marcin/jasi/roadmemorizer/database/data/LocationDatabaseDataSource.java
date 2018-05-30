package com.marcin.jasi.roadmemorizer.database.data;

import com.marcin.jasi.roadmemorizer.database.AppDatabase;
import com.marcin.jasi.roadmemorizer.database.data.entities.LocationData;
import com.marcin.jasi.roadmemorizer.database.data.entities.RoadData;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LocationDatabaseDataSource {

    private AppDatabase appDatabase;

    public LocationDatabaseDataSource(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    public Observable<Long> insertNewRoad(RoadData data) {
        return Observable.fromCallable(() -> appDatabase.roadDao().insertRoad(data))
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation());
    }

    public Observable<Boolean> saveRoad(List<LocationData> data) {
        return Observable
                .fromCallable(() -> {
                    appDatabase.locationDao().insertRoad(data);
                    return true;
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
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
                .subscribeOn(Schedulers.computation())
                .observeOn(Schedulers.computation())
                .onErrorReturn(error -> {
                    Timber.d(error);
                    return false;
                });
    }

}
