package com.marcin.jasi.roadmemorizer.roadsArchive.di;

import com.marcin.jasi.roadmemorizer.di.scope.PerFragment;
import com.marcin.jasi.roadmemorizer.general.common.data.DataMapper;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.entity.Road;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.mapper.RoadViewModelDataMapper;
import com.marcin.jasi.roadmemorizer.roadsArchive.presentation.adapter.RoadsArchiveRecyclerAdapter;
import com.marcin.jasi.roadmemorizer.roadsArchive.presentation.viewModel.RoadRowViewModel;

import dagger.Module;
import dagger.Provides;

@Module
@PerFragment
public class RoadsArchiveModule {

    @Provides
    @PerFragment
    public RoadsArchiveRecyclerAdapter provideRoadsArchiveRecyclerAdapter(DataMapper<Road, RoadRowViewModel> entityMapper) {
        return new RoadsArchiveRecyclerAdapter(entityMapper);
    }

    @Provides
    @PerFragment
    public DataMapper<Road, RoadRowViewModel> provideRoadRowViewModelMapper() {
        return new RoadViewModelDataMapper();
    }

}
