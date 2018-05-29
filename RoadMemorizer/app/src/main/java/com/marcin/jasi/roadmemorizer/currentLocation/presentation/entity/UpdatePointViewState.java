package com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity;

import com.google.android.gms.maps.model.LatLng;

// todo autovalue
// todo datamapper
public class UpdatePointViewState extends CurrentLocationViewState {

    private LatLng point;
    private boolean align;

    public UpdatePointViewState(LatLng point, boolean align) {
        this.point = point;
        this.align = align;
    }

    public LatLng getPoint() {
        return point;
    }

    public boolean isAlign() {
        return align;
    }
}
