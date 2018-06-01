package com.marcin.jasi.roadmemorizer.roadLoader.interactor;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.general.common.interactor.CommonIOUseCase;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.PostExecutionThread;
import com.marcin.jasi.roadmemorizer.general.common.schedulers.ThreadExecutor;
import com.marcin.jasi.roadmemorizer.roadLoader.data.dataSource.PlacesCloudDataSource;

import io.reactivex.Observable;

public class GetPlaceAddresUseCase extends CommonIOUseCase<String, LatLng> {

    private PlacesCloudDataSource dataSource;


    public GetPlaceAddresUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread
            , PlacesCloudDataSource dataSource) {
        super(threadExecutor, postExecutionThread);
        this.dataSource = dataSource;
    }

    @Override
    public Observable<String> buildObservable(LatLng item) {
        return dataSource.getPlaceFormattedAddres(item);
    }

}
