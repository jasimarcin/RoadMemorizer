package com.marcin.jasi.roadmemorizer.locationTracker.domain.interactor;


import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.database.LocationDatabaseRepository;
import com.marcin.jasi.roadmemorizer.database.data.entities.RoadData;
import com.marcin.jasi.roadmemorizer.general.Constants;
import com.marcin.jasi.roadmemorizer.general.helpers.BitmapSaveHelper;

import java.util.Calendar;
import java.util.List;

import io.reactivex.Observable;

import static com.marcin.jasi.roadmemorizer.general.Constants.EMPTY_STRING;

// todo commonUseCase
public class SaveRoadUseCase {

    private LocationDatabaseRepository repository;


    public SaveRoadUseCase(LocationDatabaseRepository repository) {
        this.repository = repository;
    }

    public Observable<String> saveRoad(List<LatLng> points) {
        RoadData road = getRoadData(points);

        return repository.insertNewRoad(road)
                .flatMap(roadId -> repository
                        .saveRoad(points, roadId)
                        .flatMap(success -> handleSavedRoad(roadId, success))
                );
    }

    private Observable<String> handleSavedRoad(Long roadId, Boolean success) {
        if (!success)
            return Observable.just(EMPTY_STRING);
        else
            return Observable.just(BitmapSaveHelper.generateBitmapFilename(roadId))
                    .flatMap(filename -> repository.updateBitmapFilename(filename, roadId)
                            .map(updated -> updated ? filename : EMPTY_STRING));
    }

    @NonNull
    private RoadData getRoadData(List<LatLng> points) {
        RoadData road = new RoadData();

        road.setDate(Calendar.getInstance().getTimeInMillis());
        road.setFilename(Constants.EMPTY_STRING);
        road.setQuantity(points.size());

        return road;
    }

}
