package com.marcin.jasi.roadmemorizer.di.module;

import com.marcin.jasi.roadmemorizer.di.scope.PerAppScope;
import com.marcin.jasi.roadmemorizer.roadLoader.data.dataSource.PlacesApi;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
@PerAppScope
public class ApiModule {

    @Provides
    @PerAppScope
    public PlacesApi providePlacesApi(Retrofit retrofit) {
        return retrofit.create(PlacesApi.class);
    }

}
