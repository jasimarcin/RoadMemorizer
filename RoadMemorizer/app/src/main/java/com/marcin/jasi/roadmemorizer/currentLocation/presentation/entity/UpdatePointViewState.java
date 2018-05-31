package com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity;

import com.google.android.gms.maps.model.LatLng;


public class UpdatePointViewState extends CurrentLocationViewState {

    private LatLng point;
    private boolean align;

    private UpdatePointViewState(Builder builder) {
        super(builder);
        point = builder.point;
        align = builder.align;
    }

    public LatLng getPoint() {
        return point;
    }

    public boolean isAlign() {
        return align;
    }

    public static final class Builder extends CurrentLocationViewState.Builder {
        private LatLng point;
        private boolean align;

        public Builder() {
        }

        public Builder point(LatLng val) {
            point = val;
            return this;
        }

        public Builder align(boolean val) {
            align = val;
            return this;
        }

        public UpdatePointViewState build() {
            return new UpdatePointViewState(this);
        }
    }
}
