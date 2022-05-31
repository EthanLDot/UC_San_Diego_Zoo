package com.example.zooseeker_team54;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Dao interface for our LocItems
 */
@Dao
public interface LocItemDao {
    @Insert
    long insert(LocItem locItem);

    @Insert
    List<Long> insertAll(List<LocItem> locItems);

    @Query("SELECT * FROM loc_items WHERE id=:id")
    LocItem get(String id);

    @Query("SELECT * FROM loc_items ORDER BY id")
    List<LocItem> getAll();

    @Query("SELECT * FROM loc_items WHERE kind = 'exhibit' ORDER BY id")
    List<LocItem> getAllExhibits();

    @Query("SELECT * FROM loc_items WHERE visited = 1 ORDER BY id")
    List<LocItem> getAllVisited();

    @Query("SELECT name FROM loc_items WHERE kind = 'exhibit' ORDER BY id")
    List<String> getAllExhibitNames();

    @Query("SELECT * FROM loc_items WHERE kind = 'exhibit' AND group_id is null ORDER BY id")
    List<LocItem> getAllNonGroup();

    @Query("SELECT * FROM loc_items ORDER BY id")
    LiveData<List<LocItem>> getAllLive();

    @Query("SELECT * FROM loc_items WHERE planned = 1 AND id != 'entrance_exit_gate' ORDER BY id")
    LiveData<List<LocItem>> getAllPlannedLive();

    @Update
    int update(LocItem locItem);

    @Delete
    int delete(LocItem locItem);

    @Query("SELECT * FROM loc_items WHERE planned = 1 AND visited = 0")
    List<LocItem> getAllPlannedUnvisited();

    @Query("SELECT * FROM loc_items WHERE planned = 1 AND visited = 0")
    LiveData<List<LocItem>> getAllPlannedUnvisitedLive();

    @Query("SELECT COUNT(*) FROM loc_items WHERE planned = 1 AND id != 'entrance_exit_gate' ORDER BY id")
    int countPlannedExhibits();

}
