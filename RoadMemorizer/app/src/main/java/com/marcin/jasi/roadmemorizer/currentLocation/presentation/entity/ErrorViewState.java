package com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity;


public class ErrorViewState extends CurrentLocationViewState {

    private String message;

    private ErrorViewState(Builder builder) {
        super(builder);
        message = builder.message;
    }

    public String getMessage() {
        return message;
    }

    public static final class Builder extends CurrentLocationViewState.Builder {
        private String message;

        public Builder() {
        }

        public Builder message(String val) {
            message = val;
            return this;
        }

        public ErrorViewState build() {
            return new ErrorViewState(this);
        }
    }
}
