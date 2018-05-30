package com.marcin.jasi.roadmemorizer.roadsArchive.di;


import android.arch.lifecycle.ViewModelProvider;

import com.marcin.jasi.roadmemorizer.di.scope.PerFragment;
import com.marcin.jasi.roadmemorizer.main.di.MainActivityComponent;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.interactor.GetRoadsListUseCase;
import com.marcin.jasi.roadmemorizer.roadsArchive.presentation.RoadsArchiveFragment;
import com.marcin.jasi.roadmemorizer.roadsArchive.presentation.adapter.RoadsArchiveRecyclerAdapter;

import dagger.Component;

@Component(
        dependencies = MainActivityComponent.class,
        modules = RoadsArchiveModule.class
)
@PerFragment
public interface RoadsArchiveComponent {

    void inject(RoadsArchiveFragment fragment);

    ViewModelProvider.Factory viewModelProvider();

    RoadsArchiveRecyclerAdapter roadsArchiveRecyclerAdapter();

}
