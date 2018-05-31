package com.marcin.jasi.roadmemorizer.di.module;


import android.arch.persistence.room.Room;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;

import com.marcin.jasi.roadmemorizer.Application;
import com.marcin.jasi.roadmemorizer.database.AppDatabase;
import com.marcin.jasi.roadmemorizer.di.annotation.ApiKey;
import com.marcin.jasi.roadmemorizer.di.annotation.FilesDir;
import com.marcin.jasi.roadmemorizer.di.scope.PerAppScope;
import com.marcin.jasi.roadmemorizer.general.Constants;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationProvidersHelper;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.PostExecutionThread;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.ThreadExecutor;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.UiThread;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.WorkingExecutor;
import com.marcin.jasi.roadmemorizer.general.helpers.BitmapSaveHelper;
import com.marcin.jasi.roadmemorizer.general.helpers.NotificationHelper;

import dagger.Module;
import dagger.Provides;

// todo divide on small modules
@Module
@PerAppScope
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @PerAppScope
    Application provideApplication() {
        return application;
    }

    @Provides
    @PerAppScope
    LocationTrackerMediator provideGpsTrackerMediator() {
        return new LocationTrackerMediator();
    }

    @Provides
    @PerAppScope
    AppDatabase provideRoomDatabase() {
        return Room.databaseBuilder(application, AppDatabase.class, Constants.DATABASE_NAME).build();
    }

    @Provides
    @PerAppScope
    LocationProvidersHelper provideLocationProvidersHelper() {
        return new LocationProvidersHelper(application);
    }

    @Provides
    @PerAppScope
    BitmapSaveHelper provideBitmapSaveHelper(@FilesDir String filesDir) {
        return new BitmapSaveHelper(filesDir);
    }

    @Provides
    @FilesDir
    @PerAppScope
    String provideApplicationStoragePath() {
        return application.getFilesDir().getPath();
    }

    @Provides
    @PerAppScope
    Resources provideResources() {
        return application.getResources();
    }

    @Provides
    @PerAppScope
    ThreadExecutor provideThreadExecutor() {
        return new WorkingExecutor();
    }

    @Provides
    @PerAppScope
    PostExecutionThread providePostExecutionThreads() {
        return new UiThread();
    }

    @Provides
    @PerAppScope
    NotificationHelper provideNotificationHelper() {
        return new NotificationHelper();
    }

    @Provides
    @PerAppScope
    @ApiKey
    public String provideApiKey(Application context) {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            if (appInfo.metaData != null) {
                return appInfo.metaData.getString("com.google.android.maps.v2.API_KEY");
            }
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }

        return "";
    }


}
