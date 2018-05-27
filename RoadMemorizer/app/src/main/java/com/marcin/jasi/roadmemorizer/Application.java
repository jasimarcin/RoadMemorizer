package com.marcin.jasi.roadmemorizer;

import com.marcin.jasi.roadmemorizer.di.component.ApplicationComponent;
import com.marcin.jasi.roadmemorizer.di.component.DaggerApplicationComponent;
import com.marcin.jasi.roadmemorizer.di.scope.PerAppScope;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

@PerAppScope
public class Application extends android.app.Application {

    private ApplicationComponent applicationComponent;
    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
        initDependencies();

        disposable.add(
                applicationComponent.gpsTrackerMediator()
                        .getLocationProviderChange()
                        .subscribe(newItem -> {
                            Timber.d(String.format("GPS ENABLE = %s %s", newItem.first,
                                    newItem.second.getClass().getName()));
                        })
        );
    }

    private void initDependencies() {
        applicationComponent = DaggerApplicationComponent
                .builder()
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
