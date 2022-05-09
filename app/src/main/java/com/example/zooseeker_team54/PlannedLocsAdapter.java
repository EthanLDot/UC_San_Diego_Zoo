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

/**
 * Adapter class for use in MainActivity to give us easier access of LocItems and its containers
 */
public class PlannedLocsAdapter extends RecyclerView.Adapter<PlannedLocsAdapter.ViewHolder> {
    private List<LocItem> locItems = Collections.emptyList();
    private Consumer<LocItem> onDeleteClicked;

    /**
     * Create a new list of LocItems from a given list and notify that the data set has changed
     * @param newLocItems the list of LocItems to be set
     */
    public void setLocItems(List<LocItem> newLocItems) {
        this.locItems.clear();
        this.locItems = newLocItems;
        notifyDataSetChanged();
    }

    /**
     * Pass in a LocItem to be deleted
     * @param onDeleteClicked Consumer wrapped around a LocItem to be deleted
     */
    public void setOnDeleteClicked(Consumer<LocItem> onDeleteClicked){
        this.onDeleteClicked = onDeleteClicked;
    }

    /**
     * Getter method for LocItems
     * @return Returns the list of current LocItems
     */
    public List<LocItem> getLocItems() { return locItems; }

    @NonNull
    @Override
    public PlannedLocsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.deletable_loc_item, parent, false);

        return new PlannedLocsAdapter.ViewHolder(view);
    }

    /**
     * Sets a given LocItem from a ViewHolder to a given position
     * @param holder ViewHolder passed in
     * @param position int position to be placed at
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setLocItem(locItems.get(position));
    }

    /**
     * Gets number of items in the current list of LocItems
     * @return returns the size of locItems
     */
    @Override
    public int getItemCount() { return locItems.size(); }

    /**
     * Nested class for ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView locNameText;
        private LocItem locItem;
        private TextView delView;

        /**
         * ViewHolder constructor method from a given View
         * @param itemView View to be used
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.locNameText = itemView.findViewById(R.id.loc_name);
            this.delView = itemView.findViewById(R.id.loc_delete_selected);

            this.delView.setOnClickListener(view ->{
                if(onDeleteClicked == null) return;
                onDeleteClicked.accept(locItem);
            });
        }

        /**
         * Setter method for a LocItem
         * @param locItem LocItem to be set
         */
        public void setLocItem(LocItem locItem) {
            this.locItem = locItem;
            this.locNameText.setText(locItem.name);
        }
    }
}
