package com.marcin.jasi.roadmemorizer.database.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

// todo autovalue
@Entity
public class LocationData {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long order;
    public long roadId;

    public double lattitude;
    public double longitude;

    public LocationData(long order, long roadId, double lattitude, double longitude) {
        this.order = order;
        this.roadId = roadId;
        this.lattitude = lattitude;
        this.longitude = longitude;
    }

    public long getOrder() {
        return order;
    }

    public double getLattitude() {
        return lattitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public void setLattitude(double lattitude) {
        this.lattitude = lattitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getRoadId() {
        return roadId;
    }

    public void setRoadId(long roadId) {
        this.roadId = roadId;
    }
}
