package com.mickwerf.digi_tours_breda.gui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
    private Context context;

    public RouteItemAdapter(List<Route> routes, Context context){
        this.routes = routes;
        this.context = context;
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

        String mCurrent = routes.get(position).getRouteName();
        holder.routeTitle.setText(mCurrent);


        int id = context.getResources().getIdentifier(routes.get(position).getRouteImagePath(), "drawable", context.getPackageName());

        holder.routeImage.setImageResource(id);


//        String mImage = routes.get(position).getRouteImagePath();
//        Bitmap myBitmap = BitmapFactory.decodeFile(mImage);
//        System.out.println("NULLCHECK: "+myBitmap);
//        holder.routeImage.setImageResource(id);

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
