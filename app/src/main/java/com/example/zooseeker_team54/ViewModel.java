package com.example.zooseeker_team54;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {
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
    public LocItem getLocItemById(String id) { return locItemDao.get(id); }

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
     * Getter method to retrieve all of the live planned LocItems
     * @return List of planed LocItems wrapped in a LiveData
     */
    public LiveData<List<LocItem>> getAllPlannedLive() {
        return locItemDao.getAllPlannedLive();
    }

    /**
     * Getter method for the next unvisited exhibit
     * @return LocItem of the next unvisited exhibit
     */
    public LocItem getNextUnvisitedExhibit() { return locItemDao.getNextUnvisitedExhibit(); }

    /**
     * Getter method for the next target LocItem
     * @return LocItem that is the next target
     */
    public LocItem getNextTarget() {
        List<LocItem> allPlannedUnvisited = locItemDao.getAllPlannedUnvisited();
        // If there or less than 2 unvisited LocItems left in the plan, return null
        if (allPlannedUnvisited == null || allPlannedUnvisited.size() < 2) return null;
        return allPlannedUnvisited.get(1);
    }

    /**
     * Getter method for the current target LocItem
     * @return LocItem that is the current target LocItem
     */
    public LocItem getCurrTarget() {
        List<LocItem> allPlannedUnvisited = locItemDao.getAllPlannedUnvisited();
        // If there is less than 1 unvisited LocItems left in the plan, return null
        if (allPlannedUnvisited == null || allPlannedUnvisited.size() < 1) return null;
        return allPlannedUnvisited.get(0);
    }

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
        locItem.currDist = 0;
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
     * Updates a given LocItem's distance
     * @param locItem LocItem to be updated with a new distance
     * @param dist new distance of the LocItem as a double
     */
    public void updateLocCurrentDist(LocItem locItem, double dist) {
        locItem.currDist = dist;
        locItemDao.update(locItem);
    }

    /**
     * Clears all of the planned LocItems from the plan
     */
    public void clearPlannedLocs() {
        List<LocItem> allLocs = locItemDao.getAll();
        for (LocItem locItem : allLocs) {
            locItem.planned = false;
            locItem.visited = false;
            locItem.currDist = 0;
            locItemDao.update(locItem);
        }
    }

    /**
     * Counts all of the planned exhibits
     * @return int of the number of planned exhibits
     */
    public int countPlannedExhibits() { return locItemDao.countPlannedExhibits(); }

}

