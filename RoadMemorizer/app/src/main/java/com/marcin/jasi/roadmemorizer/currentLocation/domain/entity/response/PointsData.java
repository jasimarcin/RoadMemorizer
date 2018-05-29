package com.marcin.jasi.roadmemorizer.currentLocation.domain.entity.response;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

// todo autoValue
public class PointsData implements LocationSaverEvent {

    private LatLng startLocation;
    private LatLng endLocation;

    private List<LatLng> points;

    public PointsData(LatLng startLocation, LatLng endLocation, List<LatLng> points) {
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.points = points;
    }

    public LatLng getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LatLng startLocation) {
        this.startLocation = startLocation;
    }

    public LatLng getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LatLng endLocation) {
        this.endLocation = endLocation;
    }

    public List<LatLng> getPoints() {
        return points;
    }

    public void setPoints(List<LatLng> points) {
        this.points = points;
    }
}
