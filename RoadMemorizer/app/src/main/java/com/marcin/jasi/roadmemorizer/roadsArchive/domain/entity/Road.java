package com.marcin.jasi.roadmemorizer.roadsArchive.domain.entity;

public class Road {

    private long id;
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

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
