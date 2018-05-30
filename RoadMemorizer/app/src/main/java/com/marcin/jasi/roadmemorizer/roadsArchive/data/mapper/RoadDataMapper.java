package com.marcin.jasi.roadmemorizer.roadsArchive.data.mapper;

import com.marcin.jasi.roadmemorizer.database.data.entities.RoadData;
import com.marcin.jasi.roadmemorizer.general.common.data.DataMapper;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.entity.Road;

import java.util.ArrayList;
import java.util.List;

public class RoadDataMapper implements DataMapper<RoadData, Road> {

    private String directory;

    public RoadDataMapper(String directory) {
        this.directory = directory;
    }

    @Override
    public Road transform(RoadData roadData) {
        Road road = new Road();

        road.setDate(roadData.getDate());
        road.setEndPointLatitude(roadData.getEndPointLatitude());
        road.setEndPointLongitude(roadData.getEndPointLongitude());
        road.setStartPointLatitude(roadData.getStartPointLatitude());
        road.setStartPointLongitude(roadData.getStartPointLongitude());
        road.setFilePath(String.format("%s/%s", directory, roadData.getFilename()));
        road.setId(roadData.getId());
        road.setPointsQuantity(roadData.getQuantity());

        return road;
    }

    @Override
    public List<Road> transform(List<RoadData> from) {
        List<Road> list = new ArrayList<>();

        for (RoadData roadData : from)
            list.add(transform(roadData));

        return list;
    }

}
