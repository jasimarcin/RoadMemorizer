package com.marcin.jasi.roadmemorizer.locationTracker.domain.interactor;


import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.database.LocationDatabaseRepository;
import com.marcin.jasi.roadmemorizer.general.helpers.BitmapSaveHelper;

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
        return repository.createNewRoad()
                .flatMap(roadId -> repository
                        .saveRoad(points, roadId)
                        .map(success -> {
                            if (!success)
                                return EMPTY_STRING;
                            else
                                return BitmapSaveHelper.generateBitmapFilename(roadId);
                        })
                );
    }

}
