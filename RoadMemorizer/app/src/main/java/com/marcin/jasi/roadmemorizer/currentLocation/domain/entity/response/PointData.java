package com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response;

import com.google.android.gms.maps.model.LatLng;

public class PointData implements LocationSaverEvent {

    private LatLng point;

    public PointData(LatLng point) {
        this.point = point;
    }

    public LatLng getPoint() {
        return point;
    }

    public void setPoint(LatLng point) {
        this.point = point;
    }
}
