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
import com.mickwerf.digi_tours_breda.data.entities.Route;

import java.util.List;

public class RouteItemAdapter extends RecyclerView.Adapter<RouteItemAdapter.RouteItemViewholder> {

    private List<Route> routes;

    public RouteItemAdapter(List<Route> routes){
        this.routes = routes;
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
        return this.routes.size();
    }

    public class RouteItemViewholder extends RecyclerView.ViewHolder {
        private ImageView routeImage;
        private TextView routeTitle;
        private TextView routeText;
        private Button startRoute;
        private Button stopRoute;
        private Button deleteProgress;

        public RouteItemViewholder(@NonNull View itemView, RouteItemAdapter adapter) {
            super(itemView);
            this.routeImage = itemView.findViewById(R.id.routeImage);
            this.routeTitle = itemView.findViewById(R.id.routeTextViewTitle);
            this.routeText = itemView.findViewById(R.id.routeTextViewText);
            this.startRoute = itemView.findViewById(R.id.startRouteIcon);
            this.stopRoute = itemView.findViewById(R.id.stopRouteIcon);
            this.deleteProgress = itemView.findViewById(R.id.deleteProgressIcon);
        }

    }
}
