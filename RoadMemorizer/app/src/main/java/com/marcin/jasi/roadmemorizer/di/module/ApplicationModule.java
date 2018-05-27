package com.marcin.jasi.roadmemorizer.di.module;


import com.marcin.jasi.roadmemorizer.di.scope.PerAppScope;
import com.marcin.jasi.roadmemorizer.general.common.data.GpsTrackerMediator;

import dagger.Module;
import dagger.Provides;

@Module
@PerAppScope
public class ApplicationModule {

    @Provides
    @PerAppScope
    GpsTrackerMediator provideGpsTrackerMediator() {
        return new GpsTrackerMediator();
    }

}
