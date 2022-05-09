package com.example.zooseeker_team54;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class RouteDirectionAdapter extends RecyclerView.Adapter<RouteDirectionAdapter.ViewHolder> {
    private List<LocEdge> locEdges = Collections.emptyList();
    public RouteDirectionAdapter(){
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
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

    @Override
    public int getItemCount() { return locEdges.size(); }

    public LocEdge getItemAtIndex(int index) {
        return locEdges.get(index);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private LocEdge  locEdge;
        private TextView directionGuideText;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            this.directionGuideText = itemView.findViewById(R.id.direction);
        }

        public void setLocEdge(LocEdge locEdge){
            this.locEdge = locEdge;
            this.directionGuideText.setText(locEdge.toString());
        }
    }
}
