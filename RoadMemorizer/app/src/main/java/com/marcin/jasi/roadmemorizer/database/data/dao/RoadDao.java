package com.marcin.jasi.roadmemorizer.database.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.marcin.jasi.roadmemorizer.database.data.entities.RoadData;

import java.util.List;

@Dao
public interface RoadDao {

    @Insert
    Long insertRoad(RoadData data);

    @Query("SELECT * FROM roadData")
    List<RoadData> getRoads();

    @Query("SELECT * FROM roadData WHERE id == :roadId")
    RoadData getRoad(long roadId);

    @Query("UPDATE roadData SET filename = :filename WHERE id = :roadId")
    void uploadRoadFilename(String filename, Long roadId);
}
