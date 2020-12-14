package com.mickwerf.digi_tours_breda.gui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mickwerf.digi_tours_breda.R;

public class RouteItemAdapter extends RecyclerView.Adapter<RouteItemAdapter.RouteItemViewholder> {

    public RouteItemAdapter(){
        //TODO make constructor
    }

    @NonNull
    @Override
    public RouteItemViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route, parent, false);
        RouteItemViewholder viewholder = new RouteItemViewholder(itemView, this);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull RouteItemViewholder holder, int position) {

        //TODO set holders

    }

    @Override
    public int getItemCount() {
        //TODO
        return 0;
    }

    public class RouteItemViewholder extends RecyclerView.ViewHolder {
        private ImageView routeImage;
        private TextView routeText;
        private Button startRoute;
        private Button stopRoute;
        private Button deleteProgress;

        public RouteItemViewholder(@NonNull View itemView, RouteItemAdapter adapter) {
            super(itemView);
            this.routeImage = itemView.findViewById(R.id.routeImage);
            this.routeText = itemView.findViewById(R.id.routeTextView);
            this.startRoute = itemView.findViewById(R.id.startRouteIcon);
            this.stopRoute = itemView.findViewById(R.id.stopRouteIcon);
            this.deleteProgress = itemView.findViewById(R.id.deleteProgressIcon);
        }

    }
}
