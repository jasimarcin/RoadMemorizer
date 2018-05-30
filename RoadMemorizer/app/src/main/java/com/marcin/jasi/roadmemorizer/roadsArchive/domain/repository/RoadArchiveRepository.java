package com.marcin.jasi.roadmemorizer.roadsArchive.domain.repository;

import com.marcin.jasi.roadmemorizer.roadsArchive.domain.entity.Road;

import java.util.List;

public interface RoadArchiveRepository {

    List<Road> getRoads();

}
