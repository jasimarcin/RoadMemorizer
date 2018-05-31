package com.marcin.jasi.roadmemorizer.roadsArchive.data;

import com.marcin.jasi.roadmemorizer.roadsArchive.domain.entity.Road;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.repository.RoadArchiveRepository;

import java.util.List;

import io.reactivex.Observable;

public class RoadArchiveRepositoryImpl implements RoadArchiveRepository {

    private RoadArchiveDatabaseDataSource dataSource;

    public RoadArchiveRepositoryImpl(RoadArchiveDatabaseDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Observable<List<Road>> getRoads() {
        return dataSource.getRoads();
    }

    @Override
    public Observable<Road> getRoad(long id) {
        return dataSource.getRoad(id);
    }
}

