package com.marcin.jasi.roadmemorizer.main.di;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.RoomDatabase;
import android.content.res.Resources;

import com.marcin.jasi.roadmemorizer.di.annotation.FilesDir;
import com.marcin.jasi.roadmemorizer.di.component.ApplicationComponent;
import com.marcin.jasi.roadmemorizer.di.module.ViewModelModule;
import com.marcin.jasi.roadmemorizer.di.scope.PerActivityScope;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationProvidersHelper;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;
import com.marcin.jasi.roadmemorizer.general.helpers.BitmapSaveHelper;
import com.marcin.jasi.roadmemorizer.general.helpers.PermissionHelper;
import com.marcin.jasi.roadmemorizer.locationTracker.data.LocationSaverServiceDataSource;
import com.marcin.jasi.roadmemorizer.main.MainActivity;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.interactor.GetRoadsListUseCase;

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

    Resources resources();

    ViewModelProvider.Factory viewModelProvider();

    LocationSaverServiceDataSource locationTrackerServiceDataSource();

    LocationProvidersHelper locationHelper();

    BitmapSaveHelper provideBitmapSaveHelper();

    @FilesDir
    String applicationStoragePath();

    GetRoadsListUseCase getRoadListUseCase();
}
