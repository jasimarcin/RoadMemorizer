package com.marcin.jasi.roadmemorizer.di.module;

import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.currentLocation.data.GetLocationRepositoryImpl;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.GetLocationRepository;
import com.marcin.jasi.roadmemorizer.database.AppDatabase;
import com.marcin.jasi.roadmemorizer.database.LocationDatabaseRepository;
import com.marcin.jasi.roadmemorizer.database.data.LocationDatabaseDataSource;
import com.marcin.jasi.roadmemorizer.database.data.LocationDatabaseRepositoryImpl;
import com.marcin.jasi.roadmemorizer.database.data.entities.LocationData;
import com.marcin.jasi.roadmemorizer.database.data.entities.RoadData;
import com.marcin.jasi.roadmemorizer.di.annotation.ApiKey;
import com.marcin.jasi.roadmemorizer.di.scope.PerAppScope;
import com.marcin.jasi.roadmemorizer.general.common.data.DataMapper;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.PostExecutionThread;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.ThreadExecutor;
import com.marcin.jasi.roadmemorizer.locationTracker.data.LocationSaverServiceDataSource;
import com.marcin.jasi.roadmemorizer.roadLoader.data.dataSource.PlacesApi;
import com.marcin.jasi.roadmemorizer.roadLoader.data.dataSource.PlacesCloudDataSource;
import com.marcin.jasi.roadmemorizer.roadLoader.interactor.GetPlaceIdUseCase;
import com.marcin.jasi.roadmemorizer.roadsArchive.data.RoadArchiveDatabaseDataSource;
import com.marcin.jasi.roadmemorizer.roadsArchive.data.RoadArchiveRepositoryImpl;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.entity.Road;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.repository.RoadArchiveRepository;

import dagger.Module;
import dagger.Provides;

@Module
@PerAppScope
public class DataSourceModule {

    @Provides
    @PerAppScope
    GetLocationRepository provideGetLocationRepository(LocationSaverServiceDataSource dataSource) {
        return new GetLocationRepositoryImpl(dataSource);
    }

    @Provides
    @PerAppScope
    LocationSaverServiceDataSource provideLocationTrackerServiceDataSource() {
        return new LocationSaverServiceDataSource();
    }

    @Provides
    @PerAppScope
    LocationDatabaseRepository provideLocationDatabaseRepository(LocationDatabaseDataSource dataSource,
                                                                 DataMapper<Pair<LatLng, Long>, LocationData> entityMapper) {
        return new LocationDatabaseRepositoryImpl(dataSource, entityMapper);
    }

    @Provides
    @PerAppScope
    LocationDatabaseDataSource provideLocationDatabaseDataSource(AppDatabase database, DataMapper<LocationData, LatLng> entityMapper) {
        return new LocationDatabaseDataSource(database, entityMapper);
    }

    @Provides
    @PerAppScope
    public RoadArchiveRepository provideRoadArchiveRepository(RoadArchiveDatabaseDataSource dataSource) {
        return new RoadArchiveRepositoryImpl(dataSource);
    }

    @Provides
    @PerAppScope
    public RoadArchiveDatabaseDataSource provideRoadArchiveDatabaseDataSource(AppDatabase database,
                                                                              DataMapper<RoadData, Road> entityMapper) {
        return new RoadArchiveDatabaseDataSource(database, entityMapper);
    }

    @Provides
    @PerAppScope
    public PlacesCloudDataSource provideCloudDataSource(PlacesApi api, @ApiKey String apiKey) {
        return new PlacesCloudDataSource(api, apiKey);
    }

}
