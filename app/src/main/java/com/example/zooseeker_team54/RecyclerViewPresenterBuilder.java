package com.example.zooseeker_team54;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.Consumer;

public class RecyclerViewPresenterBuilder<T> {

    private RecyclerView recyclerView;
    private GeneralRecyclerAdapter<T> adapter;
    private Consumer<T> itemOnClickListener;
    private Consumer<T> itemOnDeleteListener;

    public RecyclerViewPresenterBuilder() {
        adapter = null;
        recyclerView = null;
        itemOnClickListener = null;
        itemOnDeleteListener = null;
    }

    public RecyclerViewPresenterBuilder<T> setAdapter(GeneralRecyclerAdapter<T> adapter) {
        this.adapter = adapter;
        return this;
    }

    public RecyclerViewPresenterBuilder<T> setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        return this;
    }

    public RecyclerViewPresenterBuilder<T> setItemOnClickListener(Consumer<T> itemOnClickListener) {
        this.itemOnClickListener = itemOnClickListener;
        return this;
    }

    public RecyclerViewPresenterBuilder<T> setItemOnDeleteListener(Consumer<T> itemOnDeleteListener) {
        this.itemOnDeleteListener = itemOnDeleteListener;
        return this;
    }

    public RecyclerViewPresenter<T> getRecyclerViewPresenter() {
        RecyclerViewPresenter<T> presenter =  new RecyclerViewPresenter<>(adapter, recyclerView);

        if (itemOnClickListener != null)
            presenter.setItemOnClickListener(itemOnClickListener);

        if (itemOnDeleteListener != null)
            presenter.setItemOnDeleteListener(itemOnDeleteListener);

        return presenter;
    }
}
