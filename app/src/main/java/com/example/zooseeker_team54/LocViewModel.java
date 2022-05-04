package com.example.zooseeker_team54;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class LocViewModel extends AndroidViewModel {
    private final LocItemDao locItemDao;

    public LocViewModel(@NonNull Application application) {
        super(application);
        Context context = getApplication().getApplicationContext();
        LocDatabase db = LocDatabase.getSingleton(context);
        locItemDao = db.plannedLocsDao();
    }

    public List<LocItem> getAll() { return locItemDao.getAll(); }

    public LiveData<List<LocItem>> getPlannedLocs() {
        return locItemDao.getAllPlannedLive();
    }

    public void addPlannedLoc(LocItem locItem) {
        locItem.planned = true;
        locItemDao.update(locItem);
    }

    public void removePlannedLoc(LocItem locItem) {
        locItem.planned = false;
        locItemDao.update(locItem);
    }

    public void clearPlannedLocs() {
        List<LocItem> allLocs = locItemDao.getAll();
        for (LocItem locItem : allLocs) {
            locItem.planned = false;
            locItemDao.update(locItem);
        }
    }

    public int countPlannedExhibits() { return locItemDao.countPlannedExhibits(); }

}

