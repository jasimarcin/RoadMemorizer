package com.marcin.jasi.roadmemorizer.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.marcin.jasi.roadmemorizer.database.data.dao.LocationDao;
import com.marcin.jasi.roadmemorizer.database.data.entities.LocationData;

import static com.marcin.jasi.roadmemorizer.general.Constants.DATABASE_VERSION;

@Database(entities = {LocationData.class}, version = DATABASE_VERSION, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    abstract public LocationDao locationDao();

}
