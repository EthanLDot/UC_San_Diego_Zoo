package com.example.zooseeker_team54;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExhibitItemDao {
    @Insert
    long insert(ExhibitItem exhibitItem);

    @Insert
    List<Long> insertAll(List<ExhibitItem> exhibitItems);

    @Query("SELECT * FROM `exhibit_items` WHERE `id`=:id")
    ExhibitItem get(long id);

    @Query("SELECT * FROM `exhibit_items` ORDER BY `id`")
    List<ExhibitItem> getAll();

    @Update
    int update(ExhibitItem exhibitItem);

    @Delete
    int delete(ExhibitItem exhibitItem);

    @Query("SELECT * FROM `exhibit_items` ORDER BY `id`")
    LiveData<List<ExhibitItem>> getAllLive();

    @Query("SELECT * FROM `exhibit_items` WHERE `planned`=1 ORDER BY `id`")
    LiveData<List<ExhibitItem>> getAllPlannedLive();
}
