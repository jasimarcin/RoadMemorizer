package com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity;


public class GenerateScreenshotViewState extends UpdateRoadViewState {

    private String screenshotFileName;

    private GenerateScreenshotViewState(Builder builder) {
        super(builder);
        screenshotFileName = builder.screenshotFileName;
    }

    public String getScreenshotFileName() {
        return screenshotFileName;
    }

    public static final class Builder extends UpdateRoadViewState.Builder {
        private String screenshotFileName;

        public Builder() {
        }

        public Builder screenshotFileName(String val) {
            screenshotFileName = val;
            return this;
        }

        public GenerateScreenshotViewState build() {
            return new GenerateScreenshotViewState(this);
        }
    }
}
