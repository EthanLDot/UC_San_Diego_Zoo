package com.example.zooseeker_team54;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {ExhibitItem.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class ExhibitDatabase extends RoomDatabase {
    private static ExhibitDatabase singleton = null;

    public abstract ExhibitItemDao plannedExhibitsDao();

    public synchronized static ExhibitDatabase getSingleton(Context context) {
        if (singleton == null) {
            singleton = ExhibitDatabase.makeDatabase(context);
        }
        return singleton;
    }

    private static ExhibitDatabase makeDatabase(Context context) {
        return Room.databaseBuilder(context, ExhibitDatabase.class, "zoo_app.db")
                .allowMainThreadQueries()
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(() -> {
                            List<ExhibitItem> exhibits = ExhibitItem
                                    .loadJSON(context, "sample_node_info.json");
                            getSingleton(context).plannedExhibitsDao().insertAll(exhibits);
                        });
                    }
                })
                .build();
    }

    @VisibleForTesting
    public static void injectTestDatabase(ExhibitDatabase testDatabase) {
        if (singleton != null) {
            singleton.close();
        }
        singleton = testDatabase;
    }
}
