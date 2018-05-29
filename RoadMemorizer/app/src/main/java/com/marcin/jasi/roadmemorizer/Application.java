package com.marcin.jasi.roadmemorizer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.marcin.jasi.roadmemorizer.di.component.ApplicationComponent;
import com.marcin.jasi.roadmemorizer.di.component.DaggerApplicationComponent;
import com.marcin.jasi.roadmemorizer.di.module.ApplicationModule;
import com.marcin.jasi.roadmemorizer.di.scope.PerAppScope;
import com.marcin.jasi.roadmemorizer.locationTracker.LocationTrackerService;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

@PerAppScope
public class Application extends android.app.Application {

    private ApplicationComponent applicationComponent;
    private CompositeDisposable disposable = new CompositeDisposable();
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Timber.d("LocationTrackerService connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Timber.d("LocationTrackerService disconnected");
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        initDependencies();

        startService(new Intent(this, LocationTrackerService.class));
        bindService(new Intent(this, LocationTrackerService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void initDependencies() {
        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public void onTerminate() {
        unbindService(serviceConnection);
        stopService(new Intent(this, LocationTrackerService.class));
        super.onTerminate();
    }
}
