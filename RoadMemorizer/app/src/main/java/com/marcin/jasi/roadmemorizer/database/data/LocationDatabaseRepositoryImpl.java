package com.marcin.jasi.roadmemorizer.database.data;

import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.database.LocationDatabaseRepository;
import com.marcin.jasi.roadmemorizer.database.data.entities.LocationData;
import com.marcin.jasi.roadmemorizer.database.data.entities.RoadData;
import com.marcin.jasi.roadmemorizer.general.common.data.DataMapper;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class LocationDatabaseRepositoryImpl implements LocationDatabaseRepository {

    private LocationDatabaseDataSource dataSource;
    private DataMapper<Pair<LatLng, Long>, LocationData> entityMapper;

    public LocationDatabaseRepositoryImpl(LocationDatabaseDataSource dataSource,
                                          DataMapper<Pair<LatLng, Long>, LocationData> entityMapper) {
        this.dataSource = dataSource;
        this.entityMapper = entityMapper;
    }

    public Observable<Long> insertNewRoad(RoadData data) {
        return dataSource.insertNewRoad(data);
    }

    public Observable<Boolean> saveRoad(List<LatLng> list, long roadId) {
        return dataSource.saveRoad(entityMapper.transform(getPointsList(list, roadId)));
    }

    private List<Pair<LatLng, Long>> getPointsList(List<LatLng> list, long roadId) {
        List<Pair<LatLng, Long>> pointsList = new ArrayList<>();

        for (LatLng point : list)
            pointsList.add(new Pair<>(point, roadId));

        return pointsList;
    }

    @Override
    public Observable<Boolean> updateBitmapFilename(String filename, long roadId) {
        return dataSource.updateBitmapFilename(filename, roadId);
    }

    @Override
    public Observable<List<LatLng>> getRoadPoints(long roadId) {
        return dataSource.getRoadPoints(roadId);
    }
}
