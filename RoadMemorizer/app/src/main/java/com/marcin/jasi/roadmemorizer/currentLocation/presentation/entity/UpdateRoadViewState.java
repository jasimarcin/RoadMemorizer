package com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;


public class UpdateRoadViewState extends CurrentLocationViewState {

    protected List<LatLng> road;
    protected LatLng startPoint;
    protected LatLng endPoint;
    protected boolean align;

    protected UpdateRoadViewState(Builder builder) {
        super(builder);
        road = builder.road;
        startPoint = builder.startPoint;
        endPoint = builder.endPoint;
        align = builder.align;
    }

    public List<LatLng> getRoad() {
        return road;
    }

    public LatLng getStartPoint() {
        return startPoint;
    }

    public LatLng getEndPoint() {
        return endPoint;
    }

    public boolean isAlign() {
        return align;
    }

    public static class Builder extends CurrentLocationViewState.Builder {
        protected List<LatLng> road;
        protected LatLng startPoint;
        protected LatLng endPoint;
        protected boolean align;

        public Builder() {
        }

        public Builder road(List<LatLng> val) {
            road = val;
            return this;
        }

        public Builder startPoint(LatLng val) {
            startPoint = val;
            return this;
        }

        public Builder endPoint(LatLng val) {
            endPoint = val;
            return this;
        }

        public Builder align(boolean val) {
            align = val;
            return this;
        }

        public UpdateRoadViewState build() {
            return new UpdateRoadViewState(this);
        }
    }
}
