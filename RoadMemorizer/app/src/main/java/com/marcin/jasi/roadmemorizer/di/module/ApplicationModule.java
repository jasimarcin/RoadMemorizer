package com.marcin.jasi.roadmemorizer.di.module;


import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.res.Resources;
import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.Application;
import com.marcin.jasi.roadmemorizer.currentLocation.data.GetLocationRepositoryImpl;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.GetLocationRepository;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.GetLocationUseCase;
import com.marcin.jasi.roadmemorizer.database.AppDatabase;
import com.marcin.jasi.roadmemorizer.database.LocationDatabaseRepository;
import com.marcin.jasi.roadmemorizer.database.data.LocationDatabaseDataSource;
import com.marcin.jasi.roadmemorizer.database.data.LocationDatabaseRepositoryImpl;
import com.marcin.jasi.roadmemorizer.database.data.entities.LocationData;
import com.marcin.jasi.roadmemorizer.database.data.mapper.LocationDataMapper;
import com.marcin.jasi.roadmemorizer.di.annotation.FilesDir;
import com.marcin.jasi.roadmemorizer.di.scope.PerAppScope;
import com.marcin.jasi.roadmemorizer.general.Constants;
import com.marcin.jasi.roadmemorizer.general.common.data.DataMapper;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationProvidersHelper;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;
import com.marcin.jasi.roadmemorizer.general.helpers.BitmapSaveHelper;
import com.marcin.jasi.roadmemorizer.general.helpers.NotificationHelper;
import com.marcin.jasi.roadmemorizer.locationTracker.data.LocationSaverServiceDataSource;
import com.marcin.jasi.roadmemorizer.locationTracker.domain.interactor.SaveRoadUseCase;

import dagger.Module;
import dagger.Provides;

@Module
@PerAppScope
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @PerAppScope
    Application provideApplication() {
        return application;
    }

    @Provides
    @PerAppScope
    LocationTrackerMediator provideGpsTrackerMediator() {
        return new LocationTrackerMediator();
    }

    @Provides
    @PerAppScope
    AppDatabase provideRoomDatabase() {
        return Room.databaseBuilder(application, AppDatabase.class, Constants.DATABASE_NAME).build();
    }

    @Provides
    @PerAppScope
    GetLocationUseCase provideGetLocationUseCase(GetLocationRepository repository) {
        return new GetLocationUseCase(repository);
    }

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
    LocationProvidersHelper provideLocationProvidersHelper() {
        return new LocationProvidersHelper(application);
    }

    @Provides
    @PerAppScope
    BitmapSaveHelper provideBitmapSaveHelper(@FilesDir String filesDir) {
        return new BitmapSaveHelper(filesDir);
    }

    @Provides
    @FilesDir
    @PerAppScope
    String provideApplicationStoragePath() {
        return application.getFilesDir().getPath();
    }

    @Provides
    @PerAppScope
    SaveRoadUseCase provideSaveRoadUseCase(LocationDatabaseRepository repository) {
        return new SaveRoadUseCase(repository);
    }

    @Provides
    @PerAppScope
    LocationDatabaseRepository provideLocationDatabaseRepository(LocationDatabaseDataSource dataSource,
                                                                 DataMapper<Pair<LatLng, Long>, LocationData> entityMapper) {
        return new LocationDatabaseRepositoryImpl(dataSource, entityMapper);
    }

    @Provides
    @PerAppScope
    LocationDatabaseDataSource provideLocationDatabaseDataSource(AppDatabase database) {
        return new LocationDatabaseDataSource(database);
    }

    @Provides
    @PerAppScope
    DataMapper<Pair<LatLng, Long>, LocationData> provideLocationDbMapper() {
        return new LocationDataMapper();
    }

    @Provides
    @PerAppScope
    Resources provideResources() {
        return application.getResources();
    }

    @Provides
    @PerAppScope
    NotificationHelper provideNotificationHelper() {
        return new NotificationHelper();
    }

}
