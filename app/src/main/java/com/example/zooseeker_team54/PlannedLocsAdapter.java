package com.example.zooseeker_team54;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class PlannedLocsAdapter extends GeneralRecyclerAdapter<LocItem> {
    private Consumer<LocItem> onDeleteClicked;

    public void setOnDeleteClicked(Consumer<LocItem> onDeleteClicked){
        this.onDeleteClicked = onDeleteClicked;
    }

    @NonNull
    @Override
    public PlannedLocsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.deletable_loc_item, parent, false);

        return new PlannedLocsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GeneralRecyclerAdapter.ViewHolder holder, int position) {
        ((PlannedLocsAdapter.ViewHolder) holder).setItem(super.getItems().get(position));
    }


    public class ViewHolder extends GeneralRecyclerAdapter.ViewHolder {
        private TextView locNameText;
        private LocItem locItem;
        private TextView delView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.locNameText = itemView.findViewById(R.id.loc_name);
            this.delView =itemView.findViewById(R.id.loc_delete_selected);

            this.delView.setOnClickListener(view ->{
                if(onDeleteClicked == null) return;
                onDeleteClicked.accept(locItem);
            });
        }

        public void setItem(LocItem locItem) {
            super.setItem(locItem);
            this.locItem = locItem;
            this.locNameText.setText(locItem.name);
        }
    }
}
