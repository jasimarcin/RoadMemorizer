package com.marcin.jasi.roadmemorizer.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.marcin.jasi.roadmemorizer.database.data.dao.LocationDao;
import com.marcin.jasi.roadmemorizer.database.data.dao.RoadDao;
import com.marcin.jasi.roadmemorizer.database.data.entities.LocationData;
import com.marcin.jasi.roadmemorizer.database.data.entities.RoadData;

import static com.marcin.jasi.roadmemorizer.general.Constants.DATABASE_VERSION;

@Database(
        entities = {LocationData.class, RoadData.class},
        version = DATABASE_VERSION,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    abstract public LocationDao locationDao();

    abstract public RoadDao roadDao();

}
