package com.marcin.jasi.roadmemorizer.database.data.mapper;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.database.data.entities.LocationData;
import com.marcin.jasi.roadmemorizer.general.common.data.DataMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LatLngDataMapper implements DataMapper<LocationData, LatLng> {


    @Override
    public LatLng transform(LocationData locationData) {
        return new LatLng(locationData.getLatitude(), locationData.getLongitude());
    }

    @Override
    public List<LatLng> transform(List<LocationData> from) {
        List<LatLng> list = new ArrayList<>();

        Collections.sort(from, locationDataComparator());

        for (LocationData data : from)
            list.add(transform(data));

        return list;
    }

    private Comparator<LocationData> locationDataComparator() {
        return (o1, o2) -> Long.compare(o1.getOrder(), o2.getOrder());
    }

}
