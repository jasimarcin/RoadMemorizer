package com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity;

import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

// todo autovalue
// todo datamapper
public class UpdateRoadViewState implements CurrentLocationViewState {

    private List<LatLng> road;
    private LatLng startPoint;
    private LatLng endPoint;
    private boolean align;

    public UpdateRoadViewState(List<LatLng> road, LatLng startPoint, LatLng endPoint, boolean align) {
        this.road = road;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.align = align;
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
}
