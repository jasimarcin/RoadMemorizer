package com.marcin.jasi.roadmemorizer.di.module;

import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.database.data.entities.LocationData;
import com.marcin.jasi.roadmemorizer.database.data.entities.RoadData;
import com.marcin.jasi.roadmemorizer.database.data.mapper.LatLngDataMapper;
import com.marcin.jasi.roadmemorizer.database.data.mapper.LocationDataMapper;
import com.marcin.jasi.roadmemorizer.di.annotation.FilesDir;
import com.marcin.jasi.roadmemorizer.di.scope.PerAppScope;
import com.marcin.jasi.roadmemorizer.general.common.data.DataMapper;
import com.marcin.jasi.roadmemorizer.roadsArchive.data.mapper.RoadDataMapper;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.entity.Road;

import dagger.Module;
import dagger.Provides;

@Module
@PerAppScope
public class DataMapperModule {

    @Provides
    @PerAppScope
    DataMapper<LocationData, LatLng> provideLatLngDataMapper() {
        return new LatLngDataMapper();
    }

    @Provides
    @PerAppScope
    DataMapper<Pair<LatLng, Long>, LocationData> provideLocationDbMapper() {
        return new LocationDataMapper();
    }

    @Provides
    @PerAppScope
    public DataMapper<RoadData, Road> provideRoadArchiveDataMapper(@FilesDir String directory) {
        return new RoadDataMapper(directory);
    }

}
