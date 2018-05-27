package com.marcin.jasi.roadmemorizer.di.module;


import com.marcin.jasi.roadmemorizer.di.scope.PerAppScope;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;

import dagger.Module;
import dagger.Provides;

@Module
@PerAppScope
public class ApplicationModule {

    @Provides
    @PerAppScope
    LocationTrackerMediator provideGpsTrackerMediator() {
        return new LocationTrackerMediator();
    }

}
