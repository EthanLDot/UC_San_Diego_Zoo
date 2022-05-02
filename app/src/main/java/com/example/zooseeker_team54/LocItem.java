package com.example.zooseeker_team54;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

@Entity(tableName = "loc_items")
public class LocItem {


    @PrimaryKey(autoGenerate = true)
    public long pk_id;

    @NonNull
    public String name, kind, id;
    public boolean planned;
    public List<String> tags;

    @NonNull
    @Override
    public String toString() {
        return "Loc{" +
                "id=" + id +
                ", name='" + name +
                "', planned=" + planned +
                '}';
    }


    LocItem(@NonNull String name, String id, String kind, List<String> tags) {
        this.name = name;
        this.kind = kind;
        this.id = id;
        this.tags = tags;
        this.planned = false;
    }

    public static List<LocItem> loadJSON(Context context, String path) {
        try {
            InputStream input = context.getAssets().open(path);
            Reader reader = new InputStreamReader(input);
            Gson gson = new Gson();
            Type type = new TypeToken<List<LocItem>>(){}.getType();
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
