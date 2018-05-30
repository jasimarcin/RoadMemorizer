package com.marcin.jasi.roadmemorizer.database.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

// todo autovalue
@Entity
public class RoadData {

    @PrimaryKey(autoGenerate = true)
    public long id;

}
