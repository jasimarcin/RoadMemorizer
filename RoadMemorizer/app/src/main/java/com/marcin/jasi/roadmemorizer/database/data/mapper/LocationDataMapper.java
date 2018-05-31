package com.marcin.jasi.roadmemorizer.database.data.mapper;

import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.database.data.entities.LocationData;
import com.marcin.jasi.roadmemorizer.general.common.data.DataMapper;

import java.util.ArrayList;
import java.util.List;

public class LocationDataMapper implements DataMapper<Pair<LatLng, Long>, LocationData> {

    private static final int FIRST_ROAD_ELEMENT = 0;

    @Override
    public LocationData transform(Pair<LatLng, Long> data) {
        LocationData locationData = new LocationData();

        locationData.setRoadId(data.second);
        locationData.setOrder(FIRST_ROAD_ELEMENT);
        locationData.setLatitude(data.first.latitude);
        locationData.setLongitude(data.first.longitude);

        return locationData;
    }

    @Override
    public List<LocationData> transform(List<Pair<LatLng, Long>> from) {
        List<LocationData> dataList = new ArrayList<>();

        for (int id = 0; id < from.size(); id++) {
            LocationData locationData = new LocationData();

            locationData.setRoadId(from.get(id).second);
            locationData.setOrder(id);
            locationData.setLatitude(from.get(id).first.latitude);
            locationData.setLongitude(from.get(id).first.longitude);

            dataList.add(locationData);
        }

        return dataList;
    }
}
