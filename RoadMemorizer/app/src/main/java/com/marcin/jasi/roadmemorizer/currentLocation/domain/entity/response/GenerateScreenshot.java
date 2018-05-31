package com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response;


public class GenerateScreenshot extends PointsData {

    private String screenshotFileName;

    private GenerateScreenshot(Builder builder) {
        super(builder);
        screenshotFileName = builder.screenshotFileName;
    }

    public String getScreenshotFileName() {
        return screenshotFileName;
    }

    public static final class Builder extends PointsData.Builder {

        private String screenshotFileName;

        public Builder() {
        }

        public Builder screenshotFileName(String val) {
            screenshotFileName = val;
            return this;
        }

        public GenerateScreenshot build() {
            return new GenerateScreenshot(this);
        }
    }
}
