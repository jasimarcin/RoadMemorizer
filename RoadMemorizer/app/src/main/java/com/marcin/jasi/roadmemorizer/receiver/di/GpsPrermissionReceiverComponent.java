package com.marcin.jasi.roadmemorizer.receiver.di;

import com.marcin.jasi.roadmemorizer.di.component.ApplicationComponent;
import com.marcin.jasi.roadmemorizer.di.scope.PerServiceScope;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;
import com.marcin.jasi.roadmemorizer.receiver.LocationPermissionReceiver;

import dagger.Component;

@Component(dependencies = ApplicationComponent.class)
@PerServiceScope
public interface GpsPrermissionReceiverComponent {

    void inject(LocationPermissionReceiver locationPermissionReceiver);

    LocationTrackerMediator gpsTrackerMediator();
}
