package com.marcin.jasi.roadmemorizer.di.module;

import com.marcin.jasi.roadmemorizer.currentLocation.domain.GetLocationRepository;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.GetLocationUseCase;
import com.marcin.jasi.roadmemorizer.database.LocationDatabaseRepository;
import com.marcin.jasi.roadmemorizer.di.scope.PerAppScope;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.PostExecutionThread;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.ThreadExecutor;
import com.marcin.jasi.roadmemorizer.locationTracker.domain.interactor.SaveRoadUseCase;
import com.marcin.jasi.roadmemorizer.roadLoader.data.dataSource.PlacesCloudDataSource;
import com.marcin.jasi.roadmemorizer.roadLoader.interactor.GetPlaceIdUseCase;
import com.marcin.jasi.roadmemorizer.roadLoader.interactor.GetRoadPackUseCase;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.interactor.GetRoadsListUseCase;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.repository.RoadArchiveRepository;

import dagger.Module;
import dagger.Provides;

@Module
@PerAppScope
public class UseCaseModule {


    @Provides
    @PerAppScope
    public GetPlaceIdUseCase provideGetPlaceIdUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread
            , PlacesCloudDataSource dataSource) {
        return new GetPlaceIdUseCase(threadExecutor, postExecutionThread, dataSource);
    }

    @Provides
    @PerAppScope
    GetLocationUseCase provideGetLocationUseCase(GetLocationRepository repository) {
        return new GetLocationUseCase(repository);
    }

    @Provides
    @PerAppScope
    SaveRoadUseCase provideSaveRoadUseCase(LocationDatabaseRepository repository) {
        return new SaveRoadUseCase(repository);
    }

    @Provides
    @PerAppScope
    GetRoadPackUseCase provideGetRoadPackUseCase(ThreadExecutor threadExecutor,
                                                 PostExecutionThread postExecutionThread,
                                                 RoadArchiveRepository repository,
                                                 LocationDatabaseRepository pointsRepository) {
        return new GetRoadPackUseCase(threadExecutor,
                postExecutionThread,
                repository,
                pointsRepository);
    }

    @Provides
    @PerAppScope
    public GetRoadsListUseCase provideGetRoadsListUseCase(ThreadExecutor threadExecutor,
                                                          PostExecutionThread postExecutionThread,
                                                          RoadArchiveRepository repository) {
        return new GetRoadsListUseCase(threadExecutor,
                postExecutionThread,
                repository);
    }

}
