package com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.event;


import android.graphics.Bitmap;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class ScreenshotGeneratedIntent implements LocationServiceIntent {

    public abstract String screenshotName();

    public abstract Bitmap bitmap();

    public static Builder builder() {
        return new AutoValue_ScreenshotGeneratedIntent.Builder();
    }


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder screenshotName(String screenshotName);

        public abstract Builder bitmap(Bitmap bitmap);

        public abstract ScreenshotGeneratedIntent build();
    }
}
