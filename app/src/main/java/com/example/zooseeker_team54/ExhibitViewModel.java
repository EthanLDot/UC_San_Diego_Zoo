package com.example.zooseeker_team54;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ExhibitViewModel extends AndroidViewModel {
    private final ExhibitItemDao exhibitItemDao;

    public ExhibitViewModel(@NonNull Application application) {
        super(application);
        Context context = getApplication().getApplicationContext();
        ExhibitDatabase db = ExhibitDatabase.getSingleton(context);
        exhibitItemDao = db.plannedExhibitsDao();
    }

    public List<ExhibitItem> getAll() { return exhibitItemDao.getAll(); }

    public LiveData<List<ExhibitItem>> getPlannedExhibits() {
        return exhibitItemDao.getAllPlannedLive();
    }

    public void addPlannedExhibit(ExhibitItem exhibitItem) {
        exhibitItem.planned = true;
        exhibitItemDao.update(exhibitItem);
    }

    public void removePlannedExhibit(ExhibitItem exhibitItem) {
        exhibitItem.planned = false;
        exhibitItemDao.update(exhibitItem);
    }

    public void clearPlannedExhibits() {
        List<ExhibitItem> allExhibits = exhibitItemDao.getAll();
        for (ExhibitItem exhibitItem : allExhibits) {
            exhibitItem.planned = false;
            exhibitItemDao.update(exhibitItem);
        }
    }

}

