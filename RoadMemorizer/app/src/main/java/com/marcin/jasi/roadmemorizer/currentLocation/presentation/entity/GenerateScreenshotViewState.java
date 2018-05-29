package com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

// todo autovalue
public class GenerateScreenshotViewState extends UpdateRoadViewState {

    private String screenshotFileName;

    public GenerateScreenshotViewState(List<LatLng> road, LatLng startPoint, LatLng endPoint, boolean align, String screenshotFileName) {
        super(road, startPoint, endPoint, align);
        this.screenshotFileName = screenshotFileName;
    }

    public String getScreenshotFileName() {
        return screenshotFileName;
    }
}
