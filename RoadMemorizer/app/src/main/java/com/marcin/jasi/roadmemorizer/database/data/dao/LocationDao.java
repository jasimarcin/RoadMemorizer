package com.marcin.jasi.roadmemorizer.database.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.marcin.jasi.roadmemorizer.database.data.entities.LocationData;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface LocationDao {

    @Query("SELECT * FROM locationData WHERE roadId == :roadId")
    Flowable<List<LocationData>> getLocations(long roadId);

    @Insert
    void insertRoad(List<LocationData> road);

    @Query("SELECT * FROM locationData WHERE roadId == :roadId")
    List<LocationData> getRoadPoints(long roadId);

}
