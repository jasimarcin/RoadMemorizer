package com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity;

import com.google.android.gms.maps.model.LatLng;


public class AlignMap extends CurrentLocationViewState {

    private LatLng alignPoint;

    private AlignMap(Builder builder) {
        super(builder);
        alignPoint = builder.alignPoint;
    }

    public LatLng getAlignPoint() {
        return alignPoint;
    }

    public static final class Builder extends CurrentLocationViewState.Builder {
        private LatLng alignPoint;

        public Builder() {
        }

        public Builder alignPoint(LatLng val) {
            alignPoint = val;
            return this;
        }

        public AlignMap build() {
            return new AlignMap(this);
        }
    }
}
