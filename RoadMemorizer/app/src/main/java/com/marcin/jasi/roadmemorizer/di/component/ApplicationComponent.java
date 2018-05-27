package com.marcin.jasi.roadmemorizer.di.component;


import com.marcin.jasi.roadmemorizer.Application;
import com.marcin.jasi.roadmemorizer.di.module.ApplicationModule;
import com.marcin.jasi.roadmemorizer.di.scope.PerAppScope;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;

import dagger.Component;

@Component(modules = {
        ApplicationModule.class
})
@PerAppScope
public interface ApplicationComponent {

    void inject(Application application);

    LocationTrackerMediator gpsTrackerMediator();
}
