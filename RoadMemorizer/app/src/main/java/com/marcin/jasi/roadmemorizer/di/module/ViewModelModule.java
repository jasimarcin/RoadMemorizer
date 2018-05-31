package com.marcin.jasi.roadmemorizer.di.module;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.marcin.jasi.roadmemorizer.currentLocation.presentation.CurrentLocationViewModel;
import com.marcin.jasi.roadmemorizer.di.annotation.ViewModelKey;
import com.marcin.jasi.roadmemorizer.di.scope.PerActivityScope;
import com.marcin.jasi.roadmemorizer.general.common.presentation.AppViewModelFactory;
import com.marcin.jasi.roadmemorizer.roadLoader.presentation.ui.RoadLoaderViewModel;
import com.marcin.jasi.roadmemorizer.roadsArchive.presentation.viewModel.RoadsArchiveViewModel;


import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
@PerActivityScope
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(CurrentLocationViewModel.class)
    abstract ViewModel bindCurrentLocationFragmentViewModel(CurrentLocationViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RoadsArchiveViewModel.class)
    abstract ViewModel bindRoadsArchiveViewModel(RoadsArchiveViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RoadLoaderViewModel.class)
    abstract ViewModel bindRoadLoaderViewModel(RoadLoaderViewModel viewModel);

    @Binds
    @PerActivityScope
    abstract ViewModelProvider.Factory provideViewModelProvider(AppViewModelFactory appViewModelFactory);
}
