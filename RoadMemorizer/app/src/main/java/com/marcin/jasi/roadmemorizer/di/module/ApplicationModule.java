package com.marcin.jasi.roadmemorizer.di.module;


import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.marcin.jasi.roadmemorizer.Application;
import com.marcin.jasi.roadmemorizer.currentLocation.data.GetLocationRepositoryImpl;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.GetLocationRepository;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.GetLocationUseCase;
import com.marcin.jasi.roadmemorizer.database.AppDatabase;
import com.marcin.jasi.roadmemorizer.di.scope.PerAppScope;
import com.marcin.jasi.roadmemorizer.general.Constants;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationProvidersHelper;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;
import com.marcin.jasi.roadmemorizer.general.helpers.BitmapSaveHelper;
import com.marcin.jasi.roadmemorizer.locationTracker.data.LocationSaverServiceDataSource;

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
    RoomDatabase provideRoomDatabase() {
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
    BitmapSaveHelper provideBitmapSaveHelper() {
        return new BitmapSaveHelper();
    }

}
