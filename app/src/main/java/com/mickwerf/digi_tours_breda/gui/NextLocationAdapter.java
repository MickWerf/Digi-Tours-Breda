package com.mickwerf.digi_tours_breda.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.mickwerf.digi_tours_breda.R;
import com.mickwerf.digi_tours_breda.data.entities.Location;

import java.util.LinkedList;

public class NextLocationAdapter extends RecyclerView.Adapter<NextLocationAdapter.NextLocationViewHolder> {

    private final LinkedList<NextLocationItem> mLocationList;
    private final LayoutInflater mInflater;

    class NextLocationViewHolder extends RecyclerView.ViewHolder {
        public final TextView LocationNameItemView;
        public final TextView distanceItemView;

        private final Context context;
        final NextLocationAdapter mAdapter;

        /**
         * Creates a new custom view holder to hold the view to display in
         * the RecyclerView.
         *
         * @param itemView The view in which to display the data.
         * @param adapter The adapter that manages the the data and views
         *                for the RecyclerView.
         */
        public NextLocationViewHolder(View itemView, NextLocationAdapter adapter) {
            super(itemView);
            context= itemView.getContext();
            LocationNameItemView = itemView.findViewById(R.id.ItemLocationName);
            distanceItemView = itemView.findViewById(R.id.itemDistance);
            this.mAdapter = adapter;
        }
    }


    public NextLocationAdapter(Context context, LinkedList<NextLocationItem> projectList) {
        mInflater = LayoutInflater.from(context);
        this.mLocationList = projectList;
    }


    /**
     * Called when RecyclerView needs a new ViewHolder of the given type to
     * represent an item.
     *
     * This new ViewHolder should be constructed with a new View that can
     * represent the items of the given type. You can either create a new View
     * manually or inflate it from an XML layout file.
     *
     * The new ViewHolder will be used to display items of the adapter using
     * onBindViewHolder(ViewHolder, int, List). Since it will be reused to
     * display different items in the data set, it is a good idea to cache
     * references to sub views of the View to avoid unnecessary findViewById()
     * calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after
     *                 it is bound to an adapter position.
     * @param viewType The view type of the new View. @return A new ViewHolder
     *                 that holds a View of the given view type.
     */
    @Override
    public NextLocationAdapter.NextLocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate an item view.
        View mItemView = mInflater.inflate(
                R.layout.item_nextlocation, parent, false);
        return new NextLocationViewHolder(mItemView, this);
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * This method should update the contents of the ViewHolder.itemView to
     * reflect the item at the given position.
     *
     * @param holder   The ViewHolder which should be updated to represent
     *                 the contents of the item at the given position in the
     *                 data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(NextLocationAdapter.NextLocationViewHolder holder, int position) {
        NextLocationItem current = mLocationList.get(position);
        String mCurrent = current.getLocationName();
        holder.LocationNameItemView.setText(mCurrent);
        String pCurrent = "??? distance"; //TODO: retrieve distance to location
        holder.distanceItemView.setText(pCurrent);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mLocationList.size();
    }

}
