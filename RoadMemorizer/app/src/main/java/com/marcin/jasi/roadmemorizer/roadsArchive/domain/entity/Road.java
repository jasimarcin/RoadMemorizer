package com.marcin.jasi.roadmemorizer.roadsArchive.domain.entity;

public class Road {

    private long id;
    private double startPointLongitude;
    private double startPointLattitude;
    private double endPointLongitude;
    private double endPointLattitude;
    private String filePath;
    private long date;
    private long pointsQuantity;


    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getPointsQuantity() {
        return pointsQuantity;
    }

    public void setPointsQuantity(long pointsQuantity) {
        this.pointsQuantity = pointsQuantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getStartPointLongitude() {
        return startPointLongitude;
    }

    public void setStartPointLongitude(double startPointLongitude) {
        this.startPointLongitude = startPointLongitude;
    }

    public double getStartPointLatitude() {
        return startPointLattitude;
    }

    public void setStartPointLatitude(double startPointLattitude) {
        this.startPointLattitude = startPointLattitude;
    }

    public double getEndPointLongitude() {
        return endPointLongitude;
    }

    public void setEndPointLongitude(double endPointLongitude) {
        this.endPointLongitude = endPointLongitude;
    }

    public double getEndPointLatitude() {
        return endPointLattitude;
    }

    public void setEndPointLatitude(double endPointLattitude) {
        this.endPointLattitude = endPointLattitude;
    }

    public double getStartPointLattitude() {
        return startPointLattitude;
    }

    public void setStartPointLattitude(double startPointLattitude) {
        this.startPointLattitude = startPointLattitude;
    }

    public double getEndPointLattitude() {
        return endPointLattitude;
    }

    public void setEndPointLattitude(double endPointLattitude) {
        this.endPointLattitude = endPointLattitude;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
