package com.marcin.jasi.roadmemorizer.database.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.android.gms.maps.model.LatLng;

@Entity
public class LocationData {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long order;
    public long roadId;

    public double lattitude;
    public double longitude;

}
