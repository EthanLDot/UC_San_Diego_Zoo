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

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder> {
    private List<ExhibitItem> exhibitItems = Collections.emptyList();
    private Consumer<ExhibitItem> onSearchResultClicked;

    public void setExhibitItems(List<ExhibitItem> newExhibitItems) {
        this.exhibitItems.clear();
        this.exhibitItems = newExhibitItems;
        notifyDataSetChanged();
    }

    public void setOnSearchResultClicked(Consumer<ExhibitItem> onSearchResultClicked) {
        this.onSearchResultClicked = onSearchResultClicked;
    }

    @NonNull
    @Override
    public SearchResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.exhibit_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setExhibitItem(exhibitItems.get(position));
    }

    @Override
    public int getItemCount() {
        return exhibitItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView exhibitNameText;
        private ExhibitItem exhibitItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.exhibitNameText = itemView.findViewById(R.id.exhibit_name);

            this.exhibitNameText.setOnClickListener(view -> {
                if (onSearchResultClicked == null) return;
                onSearchResultClicked.accept(exhibitItem);
                setExhibitItems(Collections.emptyList());
            });
        }

        public void setExhibitItem(ExhibitItem exhibitItem) {
            this.exhibitItem = exhibitItem;
            this.exhibitNameText.setText(exhibitItem.name);
        }
    }
}
