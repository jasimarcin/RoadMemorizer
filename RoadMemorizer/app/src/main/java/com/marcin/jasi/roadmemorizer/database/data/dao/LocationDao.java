package com.marcin.jasi.roadmemorizer.database.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.marcin.jasi.roadmemorizer.database.data.entities.LocationData;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface LocationDao {

    @Query("SELECT * FROM LocationData WHERE roadId == :roadId")
    Flowable<List<LocationData>> getLocations(long roadId);

    @Insert
    void insertRoad(List<LocationData> road);

    @Insert
    void insertLocation(LocationData data);

    @Delete
    void deleteAll(List<LocationData> list);

}
