package com.example.zooseeker_team54;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class PlannedLocsAdapter extends RecyclerView.Adapter<PlannedLocsAdapter.ViewHolder> {
    private List<LocItem> locItems = Collections.emptyList();
    private Consumer<LocItem> onDeleteClicked;

    public void setLocItems(List<LocItem> newLocItems) {
        this.locItems.clear();
        this.locItems = newLocItems;
        notifyDataSetChanged();
    }

    public void setOnDeleteClicked(Consumer<LocItem> onDeleteClicked){
        this.onDeleteClicked = onDeleteClicked;
    }

    @NonNull
    @Override
    public PlannedLocsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.loc_item, parent, false);

        return new PlannedLocsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setLocItem(locItems.get(position));
    }

    @Override
    public int getItemCount() { return locItems.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
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

        public void setLocItem(LocItem locItem) {
            this.locItem = locItem;
            this.locNameText.setText(locItem.name);
        }
    }
}
