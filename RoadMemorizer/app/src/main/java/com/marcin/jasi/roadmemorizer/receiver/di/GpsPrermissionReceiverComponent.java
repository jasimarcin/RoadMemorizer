package com.marcin.jasi.roadmemorizer.receiver.di;

import com.marcin.jasi.roadmemorizer.di.component.ApplicationComponent;
import com.marcin.jasi.roadmemorizer.di.scope.PerServiceScope;
import com.marcin.jasi.roadmemorizer.general.common.data.GpsTrackerMediator;
import com.marcin.jasi.roadmemorizer.receiver.GpsPermissionReceiver;

import dagger.Component;

@Component(dependencies = ApplicationComponent.class)
@PerServiceScope
public interface GpsPrermissionReceiverComponent {

    void inject(GpsPermissionReceiver gpsPermissionReceiver);

    GpsTrackerMediator gpsTrackerMediator();
}
