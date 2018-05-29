package com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event;


// todo autovalue
public class ScreenshotGeneratedIntent implements LocationServiceIntent {

    private String screenshotName;

    public ScreenshotGeneratedIntent(String roadId) {
        this.screenshotName = screenshotName;
    }

    public String getRoadId() {
        return screenshotName;
    }
}
