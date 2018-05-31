package com.marcin.jasi.roadmemorizer.roadsArchive.domain.repository;

import com.marcin.jasi.roadmemorizer.roadsArchive.domain.entity.Road;

import java.util.List;

import io.reactivex.Observable;

public interface RoadArchiveRepository {

    Observable<List<Road>> getRoads();

    Observable<Road> getRoad(long id);
}
