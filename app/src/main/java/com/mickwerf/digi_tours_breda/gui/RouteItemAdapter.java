package com.mickwerf.digi_tours_breda.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mickwerf.digi_tours_breda.R;
import com.mickwerf.digi_tours_breda.data.entities.Route;
import com.mickwerf.digi_tours_breda.gui.activities.MainActivity;
import com.mickwerf.digi_tours_breda.live_data.MainViewModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class RouteItemAdapter extends RecyclerView.Adapter<RouteItemAdapter.RouteItemViewholder> {

    private List<Route> routes;
    private Context context;
    private String language;
    private MainViewModel mainViewModel;

    public RouteItemAdapter(List<Route> routes, Context context, MainViewModel mainViewModel){
        this.routes = routes;
        this.context = context;
        this.language = mainViewModel.getUserSettings2().getLanguage();
        this.mainViewModel = mainViewModel;
    }

    @NonNull
    @Override
    public RouteItemViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route, parent, false);
        return new RouteItemViewholder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteItemViewholder holder, int position) {
        String mCurrent = routes.get(position).getRouteName();
        holder.routeTitle.setText(mCurrent);
        holder.startRoute.setOnClickListener(view -> mainViewModel.setActiveRoute(routes.get(position)));

        StringBuilder text = new StringBuilder();
        String filename = "";
        switch (this.language) {
            case "Nederlands":
                filename = routes.get(position).getRouteDescriptionNL();
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
                text.append(reader.nextLine());
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        holder.routeText.setText(text.toString());
        int id = context.getResources().getIdentifier(routes.get(position).getRouteImagePath(), "drawable", context.getPackageName());
        holder.routeImage.setImageResource(id);

        Button startbutton = holder.itemView.findViewById(R.id.startRouteIcon);
        startbutton.setOnClickListener(new View.OnClickListener() {
            private Boolean canChangeActiveRoute;
            private boolean routeAlreadyCcompleted;
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) context;
                Runnable runnable = () -> {
                    this.canChangeActiveRoute = activity.getMainViewModel().setCurrentRoute(routes.get(position).getRouteName());
                    routeAlreadyCcompleted = activity.getMainViewModel().checkRouteCompletion();
                    if (routeAlreadyCcompleted){
                        activity.getMainViewModel().deleteRouteProgress(activity.getMainViewModel().getActiveRoute2().getRoute());
                    }
                };

                Thread t = new Thread(runnable);
                t.start();
                try {
                    t.join();
                    do {
                        if (canChangeActiveRoute) {
                            activity.toDirectionsView();
                            if (routeAlreadyCcompleted){
                                Toast.makeText(context, R.string.progressDeletedBecauseComplete, Toast.LENGTH_SHORT).show();
                            }
                        } else if (!canChangeActiveRoute) {
                            Toast.makeText(context, R.string.NotStartableRoute, Toast.LENGTH_SHORT).show();
                        }
                    }
                    while (canChangeActiveRoute == null);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Button stopbutton = holder.itemView.findViewById(R.id.stopRouteIcon);
        stopbutton.setOnClickListener(new View.OnClickListener() {

            private Boolean hasCompletedRoute;
            private boolean isSameRoute;
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) context;
                Runnable runnable = () -> {
                    isSameRoute = activity.getMainViewModel().checkRoute(routes.get(position).getRouteName());
                    if (isSameRoute) {
                        this.hasCompletedRoute = activity.getMainViewModel().stopCurrentRoute();
                    }
                };
                Thread t = new Thread(runnable);
                t.start();
                try {
                    t.join();
                    if (isSameRoute) {
                        do {
                            if (hasCompletedRoute) {
                                String completeStopText = activity.getString(R.string.NotifyCompleteRoute)+ routes.get(position).getRouteName();
                                Toast.makeText(context, completeStopText, Toast.LENGTH_SHORT).show();
                            } else if (!hasCompletedRoute) {
                                String incompletestopText = activity.getString(R.string.NotifyUncompleteRoute) + routes.get(position).getRouteName() + activity.getString(R.string.ContinueLater);
                                Toast.makeText(context,   incompletestopText , Toast.LENGTH_SHORT).show();
                            }
                        }
                        while (hasCompletedRoute == null);
                    }
                    else{
                        Toast.makeText(context, R.string.NotifyIllegalStopRoute, Toast.LENGTH_SHORT).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Button deletebutton = holder.itemView.findViewById(R.id.deleteProgressIcon);
        deletebutton.setOnClickListener(v -> {
            MainActivity activity = (MainActivity) context;
            Runnable runnable = () -> activity.getMainViewModel().deleteRouteProgress(routes.get(position));
            Thread t = new Thread(runnable);
            t.start();
            try {
                t.join();
                String text1 = activity.getString(R.string.NotifyDeleteProgress) + routes.get(position).getRouteName();
                Toast.makeText(context, text1, Toast.LENGTH_SHORT).show();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
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
