package com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event;


import android.graphics.Bitmap;

// todo autovalue
public class ScreenshotGeneratedIntent implements LocationServiceIntent {

    private String screenshotName;
    private Bitmap bitmap;

    public ScreenshotGeneratedIntent(String screenshotName, Bitmap bitmap) {
        this.screenshotName = screenshotName;
        this.bitmap = bitmap;
    }

    public String getScreenshotName() {
        return screenshotName;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
