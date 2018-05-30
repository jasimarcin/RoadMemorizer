package com.marcin.jasi.roadmemorizer.roadsArchive.data;

import com.marcin.jasi.roadmemorizer.database.AppDatabase;
import com.marcin.jasi.roadmemorizer.database.data.entities.RoadData;
import com.marcin.jasi.roadmemorizer.general.common.data.DataMapper;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.entity.Road;

import java.util.List;

public class RoadArchiveDatabaseDataSource {

    private AppDatabase database;
    private DataMapper<RoadData, Road> entityMapper;


    public RoadArchiveDatabaseDataSource(AppDatabase database, DataMapper<RoadData, Road> entityMapper) {
        this.database = database;
        this.entityMapper = entityMapper;
    }

    public List<Road> getRoads() {
        return entityMapper.transform(
                database.roadDao().getRoads()
        );
    }

}
