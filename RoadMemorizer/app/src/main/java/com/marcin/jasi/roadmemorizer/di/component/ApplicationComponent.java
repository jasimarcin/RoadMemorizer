package com.marcin.jasi.roadmemorizer.di.component;


import android.content.res.Resources;

import com.marcin.jasi.roadmemorizer.Application;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.GetLocationUseCase;
import com.marcin.jasi.roadmemorizer.database.AppDatabase;
import com.marcin.jasi.roadmemorizer.di.annotation.FilesDir;
import com.marcin.jasi.roadmemorizer.di.module.ApiModule;
import com.marcin.jasi.roadmemorizer.di.module.ApplicationModule;
import com.marcin.jasi.roadmemorizer.di.module.DataMapperModule;
import com.marcin.jasi.roadmemorizer.di.module.DataSourceModule;
import com.marcin.jasi.roadmemorizer.di.module.RestClientModule;
import com.marcin.jasi.roadmemorizer.di.module.UseCaseModule;
import com.marcin.jasi.roadmemorizer.di.scope.PerAppScope;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationProvidersHelper;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.PostExecutionThread;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.ThreadExecutor;
import com.marcin.jasi.roadmemorizer.general.helpers.BitmapSaveHelper;
import com.marcin.jasi.roadmemorizer.general.helpers.NotificationHelper;
import com.marcin.jasi.roadmemorizer.locationTracker.data.LocationSaverServiceDataSource;
import com.marcin.jasi.roadmemorizer.locationTracker.domain.interactor.SaveRoadUseCase;
import com.marcin.jasi.roadmemorizer.roadLoader.interactor.GetPlaceIdUseCase;
import com.marcin.jasi.roadmemorizer.roadLoader.interactor.GetRoadPackUseCase;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.interactor.GetRoadsListUseCase;

import dagger.Component;

@Component(modules = {
        ApplicationModule.class,
        RestClientModule.class,
        ApiModule.class,
        DataMapperModule.class,
        DataSourceModule.class,
        RestClientModule.class,
        UseCaseModule.class
})
@PerAppScope
public interface ApplicationComponent {

    void inject(Application application);

    LocationTrackerMediator gpsTrackerMediator();

    LocationSaverServiceDataSource locationTrackerServiceDataSource();

    LocationProvidersHelper locationHelper();

    BitmapSaveHelper bitmapSaveHelper();

    GetLocationUseCase getLocationUseCase();

    @FilesDir
    String applicationStoragePath();

    SaveRoadUseCase saveRoadUseCase();

    Resources resources();

    NotificationHelper notificationHelper();

    AppDatabase database();

    GetRoadPackUseCase getRoadPackUseCase();

    GetRoadsListUseCase getRoadsListUseCase();

    GetPlaceIdUseCase getPlaceIdUseCase();
}
