package com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class PointsData implements LocationSaverEvent {

    protected LatLng startLocation;
    protected LatLng endLocation;

    protected List<LatLng> points;

    protected PointsData(Builder builder) {
        startLocation = builder.startLocation;
        endLocation = builder.endLocation;
        points = builder.points;
    }

    public LatLng getStartLocation() {
        return startLocation;
    }

    public LatLng getEndLocation() {
        return endLocation;
    }

    public List<LatLng> getPoints() {
        return points;
    }

    public static class Builder {
        protected LatLng startLocation;
        protected LatLng endLocation;
        protected List<LatLng> points;

        public Builder() {
        }

        public Builder startLocation(LatLng val) {
            startLocation = val;
            return this;
        }

        public Builder endLocation(LatLng val) {
            endLocation = val;
            return this;
        }

        public Builder points(List<LatLng> val) {
            points = val;
            return this;
        }

        public PointsData build() {
            return new PointsData(this);
        }
    }
}
