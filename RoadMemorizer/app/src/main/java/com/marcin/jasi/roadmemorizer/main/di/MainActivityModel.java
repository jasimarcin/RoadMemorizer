package com.marcin.jasi.roadmemorizer.main.di;


import android.app.Activity;

import com.marcin.jasi.roadmemorizer.database.AppDatabase;
import com.marcin.jasi.roadmemorizer.database.data.entities.RoadData;
import com.marcin.jasi.roadmemorizer.di.annotation.FilesDir;
import com.marcin.jasi.roadmemorizer.di.scope.PerActivityScope;
import com.marcin.jasi.roadmemorizer.di.scope.PerFragment;
import com.marcin.jasi.roadmemorizer.general.common.data.DataMapper;
import com.marcin.jasi.roadmemorizer.general.helpers.PermissionHelper;
import com.marcin.jasi.roadmemorizer.roadsArchive.data.RoadArchiveDatabaseDataSource;
import com.marcin.jasi.roadmemorizer.roadsArchive.data.RoadArchiveRepositoryImpl;
import com.marcin.jasi.roadmemorizer.roadsArchive.data.mapper.RoadDataMapper;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.entity.Road;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.interactor.GetRoadsListUseCase;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.repository.RoadArchiveRepository;

import dagger.Module;
import dagger.Provides;

@Module
@PerActivityScope
public class MainActivityModel {

    private Activity activity;

    public MainActivityModel(Activity activity) {
        this.activity = activity;
    }


    @Provides
    @PerActivityScope
    PermissionHelper providePermissionHelper() {
        return new PermissionHelper(activity);
    }

    @Provides
    @PerActivityScope
    public GetRoadsListUseCase provideGetRoadsListUseCase(RoadArchiveRepository repository) {
        return new GetRoadsListUseCase(repository);
    }

    @Provides
    @PerActivityScope
    public RoadArchiveRepository provideRoadArchiveRepository(RoadArchiveDatabaseDataSource dataSource) {
        return new RoadArchiveRepositoryImpl(dataSource);
    }

    @Provides
    @PerActivityScope
    public RoadArchiveDatabaseDataSource provideRoadArchiveDatabaseDataSource(AppDatabase database,
                                                                              DataMapper<RoadData, Road> entityMapper) {
        return new RoadArchiveDatabaseDataSource(database, entityMapper);
    }

    @Provides
    @PerActivityScope
    public DataMapper<RoadData, Road> provideRoadArchiveDataMapper(@FilesDir String directory) {
        return new RoadDataMapper(directory);
    }

}
