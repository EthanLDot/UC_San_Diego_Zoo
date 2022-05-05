package com.example.zooseeker_team54;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class ShowRouteAdapter extends RecyclerView.Adapter<ShowRouteAdapter.ViewHolder> {
    private List<LocEdge> locEdges = Collections.emptyList();

    public void setLocEdges(List<LocEdge> newLocEdges) {
        this.locEdges.clear();
        this.locEdges = newLocEdges;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShowRouteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.loc_edge, parent, false);

        return new ShowRouteAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowRouteAdapter.ViewHolder holder, int position) {
        holder.setLocEdge(locEdges.get(position));
    }

    @Override
    public int getItemCount() { return locEdges.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LocEdge locEdge;
        private TextView targetText;
        private TextView directionText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.targetText = itemView.findViewById(R.id.target_name);
            this.directionText = itemView.findViewById(R.id.direction);
        }

        public void setLocEdge(LocEdge locEdge) {
            this.locEdge = locEdge;
            this.targetText.setText(locEdge.target);
            this.directionText.setText(locEdge.getDirection());
        }
    }
}
