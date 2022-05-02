package com.example.zooseeker_team54;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlannedExhibitsDao {
    @Insert
    long insert(ExhibitItem exhibitItem);

    @Query("SELECT * FROM `exhibit_items` WHERE `id`=:id")
    ExhibitItem get(long id);

    @Query("SELECT * FROM `exhibit_items` ORDER BY `id`")
    List<ExhibitItem> getAll();

    @Delete
    int delete(ExhibitItem exhibitItem);
}
