package com.marcin.jasi.roadmemorizer.roadLoader.data.dataSource;

import com.google.android.gms.maps.model.LatLng;
import com.marcin.jasi.roadmemorizer.general.Constants;

import io.reactivex.Observable;
import timber.log.Timber;

import static com.marcin.jasi.roadmemorizer.general.Constants.GOOGLE_PLACES_ID_URL;


public class PlacesCloudDataSource {

    private PlacesApi api;
    private String apiKey;

    public PlacesCloudDataSource(PlacesApi api, String apiKey) {
        this.api = api;
        this.apiKey = apiKey;
    }

    public Observable<String> getPlaceFormattedAddres(LatLng point) {

        if (apiKey == null)
            return Observable.just(Constants.EMPTY_STRING);

        return api.retrievePlacesId(getFormattedUrl(point))
                .map(response -> {
                    Timber.d(response.status());

                    if (response.results() != null && response.results().size() > 0) {
                        return response.results().get(0).address();
                    }

                    if (response.result() != null)
                        return response.result().address();

                    return Constants.EMPTY_STRING;
                });
    }

    private String getFormattedUrl(LatLng point) {
        return String.format(GOOGLE_PLACES_ID_URL, point.latitude, point.longitude);
    }

}
