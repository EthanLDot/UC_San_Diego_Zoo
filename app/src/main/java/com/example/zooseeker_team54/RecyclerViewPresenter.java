package com.example.zooseeker_team54;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.function.Consumer;

public class RecyclerViewPresenter<T> {

    private GeneralRecyclerAdapter<T> adapter;
    private RecyclerView recyclerView;

    public RecyclerViewPresenter(GeneralRecyclerAdapter<T> adapter, RecyclerView recyclerView) {
        this.adapter = adapter;
        this.recyclerView = recyclerView;
        this.recyclerView.setLayoutManager(new LinearLayoutManager(null));
        this.recyclerView.setAdapter(adapter);
    }

    public void setItemOnClickListener(Consumer<T> itemOnClickListener) {
        adapter.setItemOnClickListener(itemOnClickListener);
    }

    public void setItemOnDeleteListener(Consumer<T> itemOnDeleteListener) {
        adapter.setItemOnDeleteListener(itemOnDeleteListener);
    }

    public GeneralRecyclerAdapter<T> getAdapter() {
        return adapter;
    }

    public List<T> getItems() { return adapter.getItems(); }

    public int getItemCount() { return adapter.getItemCount(); }

    public void setItems(List<T> items) {
        adapter.setItems(items);
    }
}
