package com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity;


public class IdleViewState extends CurrentLocationViewState {

    private IdleViewState(Builder builder) {
        super(builder);
    }

    public static final class Builder extends CurrentLocationViewState.Builder {

        public Builder() {
        }

        public IdleViewState build() {
            return new IdleViewState(this);
        }
    }
}
