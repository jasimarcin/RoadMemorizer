package com.marcin.jasi.roadmemorizer.main.di;


import android.app.Activity;

import com.marcin.jasi.roadmemorizer.di.scope.PerActivityScope;
import com.marcin.jasi.roadmemorizer.general.helpers.PermissionHelper;

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
    PermissionHelper providePermissionHelper(){
        return new PermissionHelper(activity);
    }
}
