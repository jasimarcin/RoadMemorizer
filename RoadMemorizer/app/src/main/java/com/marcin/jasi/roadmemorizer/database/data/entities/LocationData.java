package com.marcin.jasi.roadmemorizer.database.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = LocationData.Struct.TABLE_NAME)
public class LocationData {

    public static class Struct {
        public static final String TABLE_NAME = "locationData";
        public static final String ID_KEY = "id";
        public static final String ORDER_KEY = "order";
        public static final String ROAD_ID_KEY = "roadId";
        public static final String LATITUDE_KEY = "latitude";
        public static final String LONGITUDE_KEY = "longitude";
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Struct.ID_KEY)
    private long id;

    @ColumnInfo(name = Struct.ORDER_KEY)
    private long order;

    @ColumnInfo(name = Struct.ROAD_ID_KEY)
    private long roadId;

    @ColumnInfo(name = Struct.LATITUDE_KEY)
    private double latitude;

    @ColumnInfo(name = Struct.LONGITUDE_KEY)
    private double longitude;


    public long getOrder() {
        return order;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
