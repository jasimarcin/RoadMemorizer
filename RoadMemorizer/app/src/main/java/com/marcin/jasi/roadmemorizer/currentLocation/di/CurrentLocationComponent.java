package com.marcin.jasi.roadmemorizer.currentLocation.di;

import android.arch.lifecycle.ViewModelProvider;

import com.marcin.jasi.roadmemorizer.currentLocation.presentation.CurrentLocationFragment;
import com.marcin.jasi.roadmemorizer.di.scope.PerFragment;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;
import com.marcin.jasi.roadmemorizer.general.helpers.PermissionHelper;
import com.marcin.jasi.roadmemorizer.main.di.MainActivityComponent;


import dagger.Component;

@Component(
        dependencies = MainActivityComponent.class
)
@PerFragment
public interface CurrentLocationComponent {

    void inject(CurrentLocationFragment fragment);

    LocationTrackerMediator gpsTrackerMediator();

    ViewModelProvider.Factory viewModelProviders();

    PermissionHelper permissionsHelper();

}
