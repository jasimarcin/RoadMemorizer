package com.marcin.jasi.roadmemorizer.roadLoader.di;


import android.arch.lifecycle.ViewModelProvider;

import com.marcin.jasi.roadmemorizer.di.scope.PerFragment;
import com.marcin.jasi.roadmemorizer.main.di.MainActivityComponent;
import com.marcin.jasi.roadmemorizer.roadLoader.presentation.ui.RoadLoaderFragment;

import dagger.Component;

@Component(
        modules = RoadLoaderModule.class,
        dependencies = MainActivityComponent.class
)
@PerFragment
public interface RoadLoaderComponent {

    void inject(RoadLoaderFragment fragment);

    ViewModelProvider.Factory viewModelProvider();

}
