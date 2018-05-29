package com.marcin.jasi.roadmemorizer.currentLocation.di;

import android.arch.lifecycle.ViewModelProvider;

import com.marcin.jasi.roadmemorizer.currentLocation.presentation.CurrentLocationFragment;
import com.marcin.jasi.roadmemorizer.di.component.ApplicationComponent;
import com.marcin.jasi.roadmemorizer.di.scope.PerFragment;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;

import dagger.Component;

@Component(
        dependencies = ApplicationComponent.class
)
@PerFragment
public interface CurrentLocationComponent {

    void inject(CurrentLocationFragment fragment);

    LocationTrackerMediator gpsTrackerMediator();

    ViewModelProvider.Factory viewModelProviders();

}
