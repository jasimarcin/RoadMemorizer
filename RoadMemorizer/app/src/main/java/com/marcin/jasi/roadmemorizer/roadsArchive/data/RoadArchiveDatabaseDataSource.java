package com.marcin.jasi.roadmemorizer.roadsArchive.data;

import com.marcin.jasi.roadmemorizer.database.AppDatabase;
import com.marcin.jasi.roadmemorizer.database.data.entities.RoadData;
import com.marcin.jasi.roadmemorizer.general.common.data.DataMapper;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.entity.Road;

import java.util.List;

import io.reactivex.Observable;

public class RoadArchiveDatabaseDataSource {

    private AppDatabase database;
    private DataMapper<RoadData, Road> entityMapper;


    public RoadArchiveDatabaseDataSource(AppDatabase database, DataMapper<RoadData, Road> entityMapper) {
        this.database = database;
        this.entityMapper = entityMapper;
    }

    public Observable<List<Road>> getRoads() {
        return Observable.fromCallable(() ->
                entityMapper.transform(database.roadDao().getRoads()));
    }

    public Observable<Road> getRoad(long roadId) {
        return Observable.fromCallable(() ->
                entityMapper.transform(database.roadDao().getRoad(roadId)));
    }

}
