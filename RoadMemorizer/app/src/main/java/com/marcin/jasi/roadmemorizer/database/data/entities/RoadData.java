package com.marcin.jasi.roadmemorizer.database.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = RoadData.Struct.TABLE_NAME)
public class RoadData {

    static class Struct {
        public static final String TABLE_NAME = "roadData";
        public static final String ID_KEY = "id";
        public static final String DATE_KEY = "date";
        public static final String FILENAME_KEY = "filename";
        public static final String QUANTITY_KEY = "quantity";
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Struct.ID_KEY)
    private long id;

    @ColumnInfo(name = Struct.DATE_KEY)
    private long date;

    @ColumnInfo(name = Struct.FILENAME_KEY)
    private String filename;

    @ColumnInfo(name = Struct.QUANTITY_KEY)
    private long quantity;


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

}
