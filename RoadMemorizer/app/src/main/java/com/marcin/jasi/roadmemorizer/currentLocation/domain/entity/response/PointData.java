package com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response;

import com.google.android.gms.maps.model.LatLng;

public class PointData implements LocationSaverEvent {

    private LatLng point;

    private PointData(Builder builder) {
        point = builder.point;
    }

    public LatLng getPoint() {
        return point;
    }

    public static final class Builder {
        private LatLng point;

        public Builder() {
        }

        public Builder point(LatLng val) {
            point = val;
            return this;
        }

        public PointData build() {
            return new PointData(this);
        }
    }
}
