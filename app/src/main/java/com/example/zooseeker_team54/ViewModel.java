package com.example.zooseeker_team54;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.stream.Collectors;

public class ViewModel extends AndroidViewModel {
    // Member variable LocItemDao
    private final LocItemDao locItemDao;

    /**
     * Constructor method for ViewModel
     * @param application Passed in application we are launching the ViewModel from
     */
    public ViewModel(@NonNull Application application) {
        super(application);
        Context context = getApplication().getApplicationContext();
        LocDatabase db = LocDatabase.getSingleton(context);
        locItemDao = db.LocItemDao();
    }

    /**
     * Getter method for a LocItem by ID
     * @param id ID of a wanted LocItem
     * @return LocItem retrieved from the Dao.get() method
     */
    public LocItem getLocItemById(String id) {
        if (id != null)
            return locItemDao.get(id);
        else
            return null;
    }

    /**
     * Getter method for retrieving all of the LocItems
     * @return List of all LocItems from the Dao
     */
    public List<LocItem> getAll() { return locItemDao.getAll(); }

    /**
     * Getter method to retrieve all the exhibits
     * @return List of LocItems representing all of the exhibits
     */
    public List<LocItem> getAllExhibits() { return locItemDao.getAllExhibits(); }

    /**
     * Getter method to retrieve all of the live planned LocItems except the entrance/exit
     * @return List of planed LocItems wrapped in a LiveData
     */
    public LiveData<List<LocItem>> getAllPlannedLive() {
        return locItemDao.getAllPlannedLive();
    }

    public List<LocItem> getAllVisited() { return locItemDao.getAllVisited(); }

    /**
     * Getter method to retrieve all of the unvisited LocItems in the plan
     * @return List of LocItems of unvisited LocItems in the plan
     */
    public List<LocItem> getAllPlannedUnvisited() {
        return locItemDao.getAllPlannedUnvisited();
    }

    /**
     * Getter method to retrieve all of the unvisited LocItems in the plan wrapped in a LiveData
     * @return List of planed LocItems that are unvisited, wrapped in a LiveData
     */
    public LiveData<List<LocItem>> getAllPlannedUnvisitedLive() {
        return locItemDao.getAllPlannedUnvisitedLive();
    }

    /**
     * Adds a LocItem to the plan
     * @param locItem LocItem to be added to the plan
     */
    public void addPlannedLoc(LocItem locItem) {
        locItem.planned = true;
        locItemDao.update(locItem);
    }

    /**
     * Removes a LocItem from the plan
     * @param locItem LocItem to be removed from the plan
     */
    public void removePlannedLoc(LocItem locItem) {
        locItem.planned = false;
        locItem.visited = false;
//        locItem.currDist = 0;
        locItemDao.update(locItem);
    }

    /**
     * Adds a visited LocItem to the plan
     * @param locItem Visited LocItem to be added
     */
    public void addVisitedLoc(LocItem locItem) {
        if (!locItem.planned) return;
        locItem.visited = true;
        locItemDao.update(locItem);
    }

    /**
     * Removes a visited LocItem from the plan
     * @param locItem Visited LocItem to be removed
     */
    public void removeVisitedLoc(LocItem locItem) {
        locItem.visited = false;
        locItemDao.update(locItem);
    }

    /**
     * Clears all of the planned LocItems from the plan
     */
    public void clearPlannedLocs() {
        List<LocItem> allLocs = locItemDao.getAll();
        // iterate through all of the LocItems
        for (LocItem locItem : allLocs) {
            locItem.planned = false;
            locItem.visited = false;
//            locItem.currDist = 0;
            locItemDao.update(locItem);
        }
    }

    /**
     * Counts all of the planned exhibits
     * @return int of the number of planned exhibits
     */
    public int countPlannedExhibits() { return locItemDao.countPlannedExhibits(); }

    /**
     *
     * @return
     */
    public List<String> getAllExhibitNames() {
        return locItemDao.getAllExhibitNames();
    }
}

