package com.marcin.jasi.roadmemorizer.di.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapterFactory;
import com.marcin.jasi.roadmemorizer.di.scope.PerAppScope;
import com.marcin.jasi.roadmemorizer.general.common.data.AutoValueGsonTypeAdapterFactory;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.marcin.jasi.roadmemorizer.general.Constants.GOOGLE_PLACES_URL;

@Module
@PerAppScope
public class RestClientModule {

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
