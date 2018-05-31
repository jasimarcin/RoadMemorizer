package com.marcin.jasi.roadmemorizer.roadLoader.data.dataSource;


import com.marcin.jasi.roadmemorizer.roadLoader.data.response.PlaceIdResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface PlacesApi {

    @GET()
    Observable<PlaceIdResponse> retrievePlacesId(@Url String latitude);

}
