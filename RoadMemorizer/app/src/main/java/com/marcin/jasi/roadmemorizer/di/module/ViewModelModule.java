package com.marcin.jasi.roadmemorizer.di.module;


import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.marcin.jasi.roadmemorizer.currentLocation.presentation.CurrentLocationViewModel;
import com.marcin.jasi.roadmemorizer.di.annotation.ViewModelKey;
import com.marcin.jasi.roadmemorizer.di.scope.PerActivityScope;
import com.marcin.jasi.roadmemorizer.general.common.presentation.AppViewModelFactory;


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
    @PerActivityScope
    abstract ViewModelProvider.Factory provideViewModelProvider(AppViewModelFactory appViewModelFactory);
}
