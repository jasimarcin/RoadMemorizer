package com.marcin.jasi.roadmemorizer.currentLocation.presentation.entity;

import com.google.android.gms.maps.model.LatLng;

// todo autovalue
// todo datamapper
public class AlignMap extends CurrentLocationViewState {

    private LatLng alignPoint;


    public AlignMap(LatLng alignPoint) {
        this.alignPoint = alignPoint;
    }

    public LatLng getAlignPoint() {
        return alignPoint;
    }
}
