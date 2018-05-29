package com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

// todo autovalue
public class GenerateScreenshot extends PointsData {

    private String screenshotFileName;

    public GenerateScreenshot(LatLng startLocation, LatLng endLocation, List<LatLng> points, String roadId) {
        super(startLocation, endLocation, points);
        this.screenshotFileName = roadId;
    }

    public String getScreenshotFileName() {
        return screenshotFileName;
    }
}
