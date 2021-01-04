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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class RouteItemAdapter extends RecyclerView.Adapter<RouteItemAdapter.RouteItemViewholder> {

    private List<Route> routes;
    private Context context;
    private String language;

    public RouteItemAdapter(List<Route> routes, Context context, String language){
        this.routes = routes;
        this.context = context;
        this.language = language;
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

        String text = "";
        String filename = "";

        System.out.println("TAAL"+this.language);
        switch(this.language){
            case "Nederlands":
                filename = routes.get(position).getRouteDescriptionNL();
                System.out.println("PATH: "+filename);
                break;
            case "Engels":
                filename = routes.get(position).getRouteDescriptionEN();
                break;
            case "Duits":
                filename = routes.get(position).getRouteDescriptionDE();
                break;
        }

        try (InputStream inputStream = context.getAssets().open(filename)) {
            Scanner reader = new Scanner(inputStream);
            while (reader.hasNext()) {
                text += reader.nextLine();
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.routeText.setText(text);

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
