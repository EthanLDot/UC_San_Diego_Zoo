package com.example.zooseeker_team54;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {ExhibitItem.class}, version = 1)
public abstract class PlannedTaskDatabase extends RoomDatabase {
    private static PlannedTaskDatabase singleton = null;

    public abstract PlannedExhibitsDao plannedExhibitsDao();

    public synchronized static PlannedTaskDatabase getSingleton(Context context) {
        if (singleton == null) {
            singleton = PlannedTaskDatabase.makeDatabase(context);
        }
        return singleton;
    }

    private static PlannedTaskDatabase makeDatabase(Context context) {
        return Room.databaseBuilder(context, PlannedTaskDatabase.class, "zoo_app.db")
                .allowMainThreadQueries()
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(() -> {
                            List<ExhibitItem> exhibits = ExhibitItem
                                    .loadJSON(context, "sample_exhibits.json");
                            getSingleton(context).plannedExhibitsDao().insertAll(exhibits);
                        });
                    }
                })
                .build();
    }

    @VisibleForTesting
    public static void injectTestDatabase(PlannedTaskDatabase testDatabase) {
        if (singleton != null) {
            singleton.close();
        }
        singleton = testDatabase;
    }
}
