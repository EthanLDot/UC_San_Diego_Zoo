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
     *
     * @param application
     */
    public ViewModel(@NonNull Application application) {
        super(application);
        Context context = getApplication().getApplicationContext();
        LocDatabase db = LocDatabase.getSingleton(context);
        locItemDao = db.LocItemDao();
    }

    /**
     *
     * @param id
     * @return
     */
    public LocItem getLocItemById(String id) { return locItemDao.get(id); }

    /**
     *
     * @return
     */
    public List<LocItem> getAll() { return locItemDao.getAll(); }

    /**
     *
     * @return
     */
    public List<LocItem> getAllExhibits() { return locItemDao.getAllExhibits(); }

    /**
     *
     * @return
     */
    public LiveData<List<LocItem>> getAllPlannedLive() {
        return locItemDao.getAllPlannedLive();
    }

    /**
     *
     * @return
     */
    public LocItem getNextUnvisitedExhibit() { return locItemDao.getNextUnvisitedExhibit(); }

    /**
     *
     * @return
     */
    public LocItem getNextTarget() {
        List<LocItem> allPlannedUnvisited = locItemDao.getAllPlannedUnvisited();
        if (allPlannedUnvisited == null || allPlannedUnvisited.size() < 2) return null;
        return allPlannedUnvisited.get(1);
    }

    /**
     *
     * @return
     */
    public LocItem getCurrTarget() {
        List<LocItem> allPlannedUnvisited = locItemDao.getAllPlannedUnvisited();
        if (allPlannedUnvisited == null || allPlannedUnvisited.size() < 1) return null;
        return allPlannedUnvisited.get(0);
    }

    /**
     *
     * @return
     */
    public List<LocItem> getAllPlannedUnvisited() {
        return locItemDao.getAllPlannedUnvisited();
    }

    /**
     *
     * @return
     */
    public LiveData<List<LocItem>> getAllPlannedUnvisitedLive() {
        return locItemDao.getAllPlannedUnvisitedLive();
    }

    /**
     *
     * @param locItem
     */
    public void addPlannedLoc(LocItem locItem) {
        locItem.planned = true;
        locItemDao.update(locItem);
    }

    /**
     *
     * @param locItem
     */
    public void removePlannedLoc(LocItem locItem) {
        locItem.planned = false;
        locItem.visited = false;
        locItem.currDist = 0;
        locItemDao.update(locItem);
    }

    /**
     *
     * @param locItem
     */
    public void addVisitedLoc(LocItem locItem) {
        if (!locItem.planned) return;
        locItem.visited = true;
        locItemDao.update(locItem);
    }

    /**
     *
     * @param locItem
     */
    public void removeVisitedLoc(LocItem locItem) {
        locItem.visited = false;
        locItemDao.update(locItem);
    }

    /**
     *
     * @param locItem
     * @param dist
     */
    public void updateLocCurrentDist(LocItem locItem, double dist) {
        locItem.currDist = dist;
        locItemDao.update(locItem);
    }

    /**
     *
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
     *
     * @return
     */
    public int countPlannedExhibits() { return locItemDao.countPlannedExhibits(); }

}

