package com.marcin.jasi.roadmemorizer.locationTracker.di;

import com.marcin.jasi.roadmemorizer.di.component.ApplicationComponent;
import com.marcin.jasi.roadmemorizer.di.scope.PerServiceScope;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;
import com.marcin.jasi.roadmemorizer.locationTracker.LocationTrackerService;
import com.marcin.jasi.roadmemorizer.locationTracker.data.LocationTrackerServiceDataSource;

import dagger.Component;

@Component(dependencies = ApplicationComponent.class)
@PerServiceScope
public interface LocationTrackerComponent {

    void inject(LocationTrackerService locationTracker);

    LocationTrackerMediator gpsTrackerMediator();

    LocationTrackerServiceDataSource locationTrackerServiceDataSource();
}
