package com.example.zooseeker_team54;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ShowRouteAdapter extends RecyclerView.Adapter<ShowRouteAdapter.ViewHolder> {
    private List<LocItem> locItems = Collections.emptyList();

    public void setLocItems(List<LocItem> newLocItems) {
        this.locItems.clear();
        this.locItems = newLocItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShowRouteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.route_plan_loc_item, parent, false);

        return new ShowRouteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowRouteAdapter.ViewHolder holder, int position) {
        holder.setLocItem(locItems.get(position));
    }

    @Override
    public int getItemCount() { return locItems.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LocItem locItem;
        private TextView locNameText;
        private TextView distanceText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.locNameText = itemView.findViewById(R.id.loc_name);
            this.distanceText = itemView.findViewById(R.id.distance);
        }

        public void setLocItem(LocItem locItem) {
            this.locItem = locItem;
            this.locNameText.setText(locItem.name);
            this.distanceText.setText(locItem.getCurrDist());
        }
    }
}
