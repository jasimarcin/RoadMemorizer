package com.marcin.jasi.roadmemorizer.currentLocation.presentation;

import android.util.Pair;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class UpdateLocation implements CurrentLocationViewState {

    private Pair<LatLng, List<LatLng>> location;

    public UpdateLocation(Pair<LatLng, List<LatLng>> location) {
        this.location = location;
    }

    public Pair<LatLng, List<LatLng>> getLocation() {
        return location;
    }

    public void setLocation(Pair<LatLng, List<LatLng>> location) {
        this.location = location;
    }
}
