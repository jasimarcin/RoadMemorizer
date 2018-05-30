package com.marcin.jasi.roadmemorizer.database.data.mapper;

import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.database.data.entities.LocationData;
import com.marcin.jasi.roadmemorizer.general.common.data.DataMapper;

import java.util.ArrayList;
import java.util.List;

public class LocationDataMapper implements DataMapper<Pair<LatLng, Long>, LocationData> {

    @Override
    public LocationData transform(Pair<LatLng, Long> data) {
        return new LocationData(0, data.second, data.first.latitude, data.first.longitude);
    }

    @Override
    public List<LocationData> transform(List<Pair<LatLng, Long>> from) {
        List<LocationData> dataList = new ArrayList<>();

        for (int id = 0; id < from.size(); id++) {
            dataList.add(new LocationData(
                    id,
                    from.get(id).second,
                    from.get(id).first.latitude,
                    from.get(id).first.longitude));
        }

        return dataList;
    }
}
