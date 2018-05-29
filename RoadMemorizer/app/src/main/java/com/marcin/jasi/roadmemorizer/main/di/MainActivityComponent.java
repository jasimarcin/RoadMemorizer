package com.marcin.jasi.roadmemorizer.main.di;

import com.marcin.jasi.roadmemorizer.di.component.ApplicationComponent;
import com.marcin.jasi.roadmemorizer.di.scope.PerActivityScope;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;
import com.marcin.jasi.roadmemorizer.general.helpers.PermissionHelper;
import com.marcin.jasi.roadmemorizer.main.MainActivity;

import dagger.Component;

@Component(modules = MainActivityModel.class,
        dependencies = ApplicationComponent.class
)
@PerActivityScope
public interface MainActivityComponent {

    void inject(MainActivity activity);

    PermissionHelper permissionHelper();

    LocationTrackerMediator locationTrackerMediator();
}
