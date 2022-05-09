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
 * Class to adapt our search results for use in MainActivity
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {
    private List<LocItem> locItems = Collections.emptyList();
    private Consumer<LocItem> onSearchResultClicked;

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
     * Pass in a search result that was clicked on
     * @param onSearchResultClicked
     */
    public void setOnSearchResultClicked(Consumer<LocItem> onSearchResultClicked) {
        this.onSearchResultClicked = onSearchResultClicked;
    }

    /**
     * Getter method for LocItems
     * @return a list of the current LocItems
     */
    public List<LocItem> getLocItems() { return locItems; }

    @NonNull
    @Override
    public SearchResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.undeletable_loc_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setLocItem(locItems.get(position));
    }

    /**
     * Getter method for the number of LocEdges
     * @return size of locItems
     */
    @Override
    public int getItemCount() {
        return locItems.size();
    }

    /**
     * Nested class for ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView locNameText;
        private LocItem locItem;

        /**
         * Constructor for ViewHolder with a given View
         * @param itemView given View to be used
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.locNameText = itemView.findViewById(R.id.loc_name);

            this.locNameText.setOnClickListener(view -> {
                if (onSearchResultClicked == null) return;
                onSearchResultClicked.accept(locItem);
                locItems.remove(locItem);
                notifyDataSetChanged();
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
