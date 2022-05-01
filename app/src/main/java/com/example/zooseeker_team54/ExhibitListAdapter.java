package com.example.zooseeker_team54;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;


public class ExhibitListAdapter extends RecyclerView.Adapter<ExhibitListAdapter.ViewHolder> {

    private List<Pair<String, String>> selections = Collections.emptyList();
    private boolean clickable;
    public void setSelections(List<Pair<String, String>> places, boolean clickable) {
        this.selections.clear();
        this.selections = places;
        this.clickable = clickable;
        notifyDataSetChanged();
        //Log.d("selectionListAdapter", "test");
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.selection_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setPlace(selections.get(position), clickable);
    }

    @Override
    public int getItemCount() {
        return selections.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        private Pair<String, String> place;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.select_item_text);
        }

        public Pair<String, String> getPlace(){
            return place;
        }
        public void setPlace(Pair<String, String> place, boolean clickable) {
            this.place = place;
            this.textView.setTag(place.first);
            this.textView.setText(place.second);
            this.textView.setClickable(clickable);
        }


    }
}