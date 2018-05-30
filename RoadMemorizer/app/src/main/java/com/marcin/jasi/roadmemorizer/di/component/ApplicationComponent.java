package com.marcin.jasi.roadmemorizer.di.component;


import android.arch.persistence.room.RoomDatabase;

import com.marcin.jasi.roadmemorizer.Application;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.GetLocationUseCase;
import com.marcin.jasi.roadmemorizer.di.module.ApplicationModule;
import com.marcin.jasi.roadmemorizer.di.module.ViewModelModule;
import com.marcin.jasi.roadmemorizer.di.scope.PerAppScope;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationProvidersHelper;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;
import com.marcin.jasi.roadmemorizer.general.helpers.BitmapSaveHelper;
import com.marcin.jasi.roadmemorizer.locationTracker.data.LocationSaverServiceDataSource;

import dagger.Component;

@Component(modules = {
        ApplicationModule.class
})
@PerAppScope
public interface ApplicationComponent {

    void inject(Application application);

    LocationTrackerMediator gpsTrackerMediator();

    LocationSaverServiceDataSource locationTrackerServiceDataSource();

    RoomDatabase roomDatabase();

    LocationProvidersHelper locationHelper();

    BitmapSaveHelper bitmapSaveHelper();

    GetLocationUseCase getLocationUseCase();

}
