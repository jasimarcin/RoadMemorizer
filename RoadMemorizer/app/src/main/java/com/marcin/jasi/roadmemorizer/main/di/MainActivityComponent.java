package com.marcin.jasi.roadmemorizer.main.di;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.RoomDatabase;

import com.marcin.jasi.roadmemorizer.di.component.ApplicationComponent;
import com.marcin.jasi.roadmemorizer.di.module.ViewModelModule;
import com.marcin.jasi.roadmemorizer.di.scope.PerActivityScope;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationProvidersHelper;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;
import com.marcin.jasi.roadmemorizer.general.helpers.BitmapSaveHelper;
import com.marcin.jasi.roadmemorizer.general.helpers.PermissionHelper;
import com.marcin.jasi.roadmemorizer.locationTracker.data.LocationSaverServiceDataSource;
import com.marcin.jasi.roadmemorizer.main.MainActivity;

import dagger.Component;

@Component(modules = {
        MainActivityModel.class,
        ViewModelModule.class
},
        dependencies = ApplicationComponent.class
)
@PerActivityScope
public interface MainActivityComponent {

    void inject(MainActivity activity);

    PermissionHelper permissionHelper();

    LocationTrackerMediator locationTrackerMediator();

    ViewModelProvider.Factory viewModelProvider();

    LocationSaverServiceDataSource locationTrackerServiceDataSource();

    RoomDatabase roomDatabase();

    LocationProvidersHelper locationHelper();

    BitmapSaveHelper provideBitmapSaveHelper();
}
