package com.example.zooseeker_team54;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

/**
 * Adapter class for RouteDirection for use with LocEdges
 */
public class RouteDirectionAdapter extends RecyclerView.Adapter<RouteDirectionAdapter.ViewHolder> {
    private List<LocEdge> locEdges = Collections.emptyList();

    /**
     * Create a new list of LocEdges from a given list and notify that the data set has changed
     * @param newLocEdges the list of LocEdges to be set
     */
    public void setLocEdges(List<LocEdge> newLocEdges) {
        this.locEdges.clear();
        this.locEdges = newLocEdges;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RouteDirectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.loc_edge, parent, false);
        return new RouteDirectionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteDirectionAdapter.ViewHolder holder, int position) {
        holder.setLocEdge(locEdges.get(position));
    }

    /**
     * Getter method for the number of LocEdges
     * @return size of locEdges
     */
    @Override
    public int getItemCount() { return locEdges.size(); }

    /**
     * Get an item at a given index
     * @param index index of item to be returned
     * @return LocEdge at given index
     */
    public LocEdge getItemAtIndex(int index) {
        return locEdges.get(index);
    }

    /**
     * Nested class for ViewHolder
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        private LocEdge  locEdge;
        private TextView directionGuideText;

        /**
         * Constructor for ViewHolder
         * @param itemView given View to be used
         */
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            this.directionGuideText = itemView.findViewById(R.id.direction);
        }

        /**
         * Setter method for a given LocEdge
         * @param locEdge given LocEdge to be set
         */
        public void setLocEdge(LocEdge locEdge){
            this.locEdge = locEdge;
            this.directionGuideText.setText(locEdge.toString());
        }
    }
}
