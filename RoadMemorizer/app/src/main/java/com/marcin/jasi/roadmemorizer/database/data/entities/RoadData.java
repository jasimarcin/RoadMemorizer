package com.marcin.jasi.roadmemorizer.database.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

// todo autovalue
@Entity
public class RoadData {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long date;
    public double startPointLattitude;
    public double startPointLongitude;
    public double endPointLattitude;
    public double endPointLongitude;
    public String filename;
    public long quantity;

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public double getStartPointLatitude() {
        return startPointLattitude;
    }

    public void setStartPointLattitude(double startPointLattitude) {
        this.startPointLattitude = startPointLattitude;
    }

    public double getStartPointLongitude() {
        return startPointLongitude;
    }

    public void setStartPointLongitude(double startPointLongitude) {
        this.startPointLongitude = startPointLongitude;
    }

    public double getEndPointLatitude() {
        return endPointLattitude;
    }

    public void setEndPointLattitude(double endPointLattitude) {
        this.endPointLattitude = endPointLattitude;
    }

    public double getEndPointLongitude() {
        return endPointLongitude;
    }

    public void setEndPointLongitude(double endPointLongitude) {
        this.endPointLongitude = endPointLongitude;
    }
}
