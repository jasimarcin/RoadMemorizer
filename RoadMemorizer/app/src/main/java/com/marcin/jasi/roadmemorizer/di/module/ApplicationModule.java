package com.marcin.jasi.roadmemorizer.di.module;


import android.arch.persistence.room.Room;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.marcin.jasi.roadmemorizer.Application;
import com.marcin.jasi.roadmemorizer.currentLocation.data.GetLocationRepositoryImpl;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.GetLocationRepository;
import com.marcin.jasi.roadmemorizer.currentLocation.domain.GetLocationUseCase;
import com.marcin.jasi.roadmemorizer.database.AppDatabase;
import com.marcin.jasi.roadmemorizer.database.LocationDatabaseRepository;
import com.marcin.jasi.roadmemorizer.database.data.LocationDatabaseDataSource;
import com.marcin.jasi.roadmemorizer.database.data.LocationDatabaseRepositoryImpl;
import com.marcin.jasi.roadmemorizer.database.data.entities.LocationData;
import com.marcin.jasi.roadmemorizer.database.data.entities.RoadData;
import com.marcin.jasi.roadmemorizer.database.data.mapper.LatLngDataMapper;
import com.marcin.jasi.roadmemorizer.database.data.mapper.LocationDataMapper;
import com.marcin.jasi.roadmemorizer.di.annotation.ApiKey;
import com.marcin.jasi.roadmemorizer.di.annotation.FilesDir;
import com.marcin.jasi.roadmemorizer.di.scope.PerAppScope;
import com.marcin.jasi.roadmemorizer.general.Constants;
import com.marcin.jasi.roadmemorizer.general.common.data.AutoValueGsonTypeAdapterFactory;
import com.marcin.jasi.roadmemorizer.general.common.data.DataMapper;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationProvidersHelper;
import com.marcin.jasi.roadmemorizer.general.common.data.LocationTrackerMediator;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.PostExecutionThread;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.ThreadExecutor;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.UiThread;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.WorkingExecutor;
import com.marcin.jasi.roadmemorizer.general.helpers.BitmapSaveHelper;
import com.marcin.jasi.roadmemorizer.general.helpers.NotificationHelper;
import com.marcin.jasi.roadmemorizer.locationTracker.data.LocationSaverServiceDataSource;
import com.marcin.jasi.roadmemorizer.locationTracker.domain.interactor.SaveRoadUseCase;
import com.marcin.jasi.roadmemorizer.roadLoader.data.dataSource.PlacesApi;
import com.marcin.jasi.roadmemorizer.roadLoader.data.dataSource.PlacesCloudDataSource;
import com.marcin.jasi.roadmemorizer.roadLoader.interactor.GetPlaceIdUseCase;
import com.marcin.jasi.roadmemorizer.roadLoader.interactor.GetRoadPackUseCase;
import com.marcin.jasi.roadmemorizer.roadsArchive.data.RoadArchiveDatabaseDataSource;
import com.marcin.jasi.roadmemorizer.roadsArchive.data.RoadArchiveRepositoryImpl;
import com.marcin.jasi.roadmemorizer.roadsArchive.data.mapper.RoadDataMapper;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.entity.Road;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.interactor.GetRoadsListUseCase;
import com.marcin.jasi.roadmemorizer.roadsArchive.domain.repository.RoadArchiveRepository;

import java.util.Set;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.marcin.jasi.roadmemorizer.general.Constants.GOOGLE_PLACES_URL;

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
    GetLocationUseCase provideGetLocationUseCase(GetLocationRepository repository) {
        return new GetLocationUseCase(repository);
    }

    @Provides
    @PerAppScope
    GetLocationRepository provideGetLocationRepository(LocationSaverServiceDataSource dataSource) {
        return new GetLocationRepositoryImpl(dataSource);
    }

    @Provides
    @PerAppScope
    LocationSaverServiceDataSource provideLocationTrackerServiceDataSource() {
        return new LocationSaverServiceDataSource();
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
    SaveRoadUseCase provideSaveRoadUseCase(LocationDatabaseRepository repository) {
        return new SaveRoadUseCase(repository);
    }

    @Provides
    @PerAppScope
    LocationDatabaseRepository provideLocationDatabaseRepository(LocationDatabaseDataSource dataSource,
                                                                 DataMapper<Pair<LatLng, Long>, LocationData> entityMapper) {
        return new LocationDatabaseRepositoryImpl(dataSource, entityMapper);
    }

    @Provides
    @PerAppScope
    DataMapper<LocationData, LatLng> provideLatLngDataMapper() {
        return new LatLngDataMapper();
    }

    @Provides
    @PerAppScope
    LocationDatabaseDataSource provideLocationDatabaseDataSource(AppDatabase database, DataMapper<LocationData, LatLng> entityMapper) {
        return new LocationDatabaseDataSource(database, entityMapper);
    }

    @Provides
    @PerAppScope
    DataMapper<Pair<LatLng, Long>, LocationData> provideLocationDbMapper() {
        return new LocationDataMapper();
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
    GetRoadPackUseCase provideGetRoadPackUseCase(ThreadExecutor threadExecutor,
                                                 PostExecutionThread postExecutionThread,
                                                 RoadArchiveRepository repository,
                                                 LocationDatabaseRepository pointsRepository) {
        return new GetRoadPackUseCase(threadExecutor,
                postExecutionThread,
                repository,
                pointsRepository);
    }

    @Provides
    @PerAppScope
    public GetRoadsListUseCase provideGetRoadsListUseCase(ThreadExecutor threadExecutor,
                                                          PostExecutionThread postExecutionThread,
                                                          RoadArchiveRepository repository) {
        return new GetRoadsListUseCase(threadExecutor,
                postExecutionThread,
                repository);
    }

    @Provides
    @PerAppScope
    public RoadArchiveRepository provideRoadArchiveRepository(RoadArchiveDatabaseDataSource dataSource) {
        return new RoadArchiveRepositoryImpl(dataSource);
    }

    @Provides
    @PerAppScope
    public RoadArchiveDatabaseDataSource provideRoadArchiveDatabaseDataSource(AppDatabase database,
                                                                              DataMapper<RoadData, Road> entityMapper) {
        return new RoadArchiveDatabaseDataSource(database, entityMapper);
    }

    @Provides
    @PerAppScope
    public DataMapper<RoadData, Road> provideRoadArchiveDataMapper(@FilesDir String directory) {
        return new RoadDataMapper(directory);
    }

    @Provides
    @PerAppScope
    public GetPlaceIdUseCase provideGetPlaceIdUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread
            , PlacesCloudDataSource dataSource) {
        return new GetPlaceIdUseCase(threadExecutor, postExecutionThread, dataSource);
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

    @Provides
    @PerAppScope
    public PlacesCloudDataSource provideCloudDataSource(PlacesApi api, @ApiKey String apiKey) {
        return new PlacesCloudDataSource(api, apiKey);
    }

    @Provides
    @PerAppScope
    public PlacesApi providePlacesApi(Retrofit retrofit) {
        return retrofit.create(PlacesApi.class);
    }

    @Provides
    @PerAppScope
    public OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        return builder.build();
    }

    @Provides
    @PerAppScope
    public Retrofit provideRetrofit(OkHttpClient okHttpClient,
                                    Converter.Factory converterFactory,
                                    CallAdapter.Factory callFactory) {

        Retrofit.Builder builder = new Retrofit.Builder();

        builder.client(okHttpClient);
        builder.baseUrl(GOOGLE_PLACES_URL);
        builder.addConverterFactory(converterFactory);
        builder.validateEagerly(false);
        builder.addCallAdapterFactory(callFactory);

        return builder.build();
    }

    @Provides
    @PerAppScope
    public Converter.Factory provideGsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @PerAppScope
    public CallAdapter.Factory provideRxCallFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Provides
    @PerAppScope
    public Gson provideGson(TypeAdapterFactory typeAdapterFactory) {

        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapterFactory(typeAdapterFactory);
        builder.setLenient();

        return builder.create();
    }

    @Provides
    static TypeAdapterFactory provideAutoValueTypeAdapterFactory() {
        return AutoValueGsonTypeAdapterFactory.create();
    }

}
