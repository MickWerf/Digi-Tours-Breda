package com.mickwerf.digi_tours_breda.live_data.route_logic.ors;

import android.content.Context;
import android.util.Log;

import com.mickwerf.digi_tours_breda.live_data.route_logic.ors.models.Coordinate;
import com.mickwerf.digi_tours_breda.live_data.route_logic.ors.models.Metadata;
import com.mickwerf.digi_tours_breda.live_data.route_logic.ors.models.Query;
import com.mickwerf.digi_tours_breda.live_data.route_logic.ors.models.Segment;
import com.mickwerf.digi_tours_breda.live_data.route_logic.ors.models.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Response;

public class RouteCallGet {

    private final static String TAG = "DirectionsPost.class";
    private final static String BASE_URL = "https://api.openrouteservice.org/v2/directions/foot-walking?api_key=";

    private Segment segment;
    private ArrayList<Coordinate> coordinates;
    private double[] bbox;
    private Metadata metadata;

    public RouteCallGet() {
        this.segment = null;
        this.coordinates = new ArrayList<>();
        this.bbox = new double[4];
        this.metadata = null;
    }

    /**
     *
     * @param response The response from the API-call.
     * @throws JSONException The response content is invalid.
     */
    void initialise(JSONObject response) throws JSONException {
        // Initialise segment
        JSONObject jsonSegment = response.getJSONArray("features")
                .getJSONObject(0)
                .getJSONObject("properties")
                .getJSONArray("segments")
                .getJSONObject(0);
        double distance = jsonSegment.getDouble("distance");
        int duration = jsonSegment.getInt("duration");
        ArrayList<Step> steps = new ArrayList<>();
        JSONArray jsonSteps = jsonSegment.getJSONArray("steps");
        for (int i = 0; i < jsonSteps.length(); i++) {
            JSONObject content = jsonSteps.getJSONObject(i);
            int[] wayPoints = {
                    content.getJSONArray("way_points").getInt(0),
                    content.getJSONArray("way_points").getInt(1)
            };
            steps.add(new Step(
                    content.getDouble("distance"),
                    content.getDouble("duration"),
                    content.getString("instruction"),
                    content.getString("name"),
                    wayPoints
            ));
        }
        segment = new Segment(distance, duration, steps);

        // Initialise coordinates.
        JSONArray jsonCoordinates = response.getJSONArray("features")
                .getJSONObject(0)
                .getJSONObject("geometry")
                .getJSONArray("coordinates");
        ArrayList<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < jsonCoordinates.length(); i++) {
            System.out.println("SIZE2: "+jsonCoordinates.length());
            coordinates.add(new Coordinate(
                    jsonCoordinates.getJSONArray(i).getDouble(0),
                    jsonCoordinates.getJSONArray(i).getDouble(1)
            ));
        }

        this.coordinates = coordinates;

        // Initialise bbox.
        JSONArray jsonBbox = response.getJSONArray("bbox");
        for (int i = 0; i < jsonBbox.length(); i++) {
            bbox[i] = jsonBbox.getDouble(i);
        }

        // Initialise metadata
        JSONObject jsonMetadata = response.getJSONObject("metadata");
        String attribution = jsonMetadata.getString("attribution");
        String service = jsonMetadata.getString("service");
        long timestamp = jsonMetadata.getLong("timestamp");
        JSONObject jsonQuery = jsonMetadata.getJSONObject("query");
        String profile = jsonQuery.getString("profile");
        String format = jsonQuery.getString("format");
        ArrayList<Coordinate> queryCoordinates = new ArrayList<>();
        JSONArray jsonQueryCoordinates = jsonQuery.getJSONArray("coordinates");
        for (int i = 0; i < jsonQueryCoordinates.length(); i++) {
            queryCoordinates.add(new Coordinate(
                    jsonQueryCoordinates.getJSONArray(i).getDouble(0),
                    jsonQueryCoordinates.getJSONArray(i).getDouble(1)
                            ));
        }
        metadata = new Metadata(attribution, service, timestamp, new Query(
                queryCoordinates, profile, format
        ));
    }

    public Segment getSegment() {
        return segment;
    }

    public ArrayList<Coordinate> getCoordinates() {
        return coordinates;
    }

    public double[] getBbox() {
        return bbox;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    /**
     * Builder class used to initialise the RouteCallGet object.
     */
    public static class Builder {

        private Coordinate start;
        private Coordinate end;
        private Context context;

        public Builder(Coordinate start, Coordinate end, Context context) {
            this.start = start;
            this.end = end;
            this.context = context;
        }

        private String buildUrl() {
            String url = BASE_URL + HttpUtil.API_KEY
                    + "&start=" + start.getLongitude() + "," + start.getLatitude()
                    + "&end=" + end.getLongitude() + "," + end.getLatitude();
            return url;
        }

        /**
         * Send the API request and initialise the RouteCallGet object.
         * @param cb Callback to register a success or fail response.
         * @return
         */
        public void Call(ApiCallback cb) {
            RouteCallGet routeCallGet = new RouteCallGet();

            HttpUtil httpUtil = new HttpUtil(context);
            httpUtil.post(buildUrl(), null, new HttpCallback() {
                @Override
                public void onFailure(Response response, Throwable throwable) {
                    Log.d(TAG, "Failed to receive a response.");

                    // TODO Handle the failure in the UI.
                }

                @Override
                public void onSuccess(Response response) throws IOException, JSONException {
                    String body = response.body().string();
                    Log.d(TAG, "Response received: " + body);
                    Log.i(TAG, "Remaining call quota: " + response.header("x-ratelimit-remaining", "error"));

                    routeCallGet.initialise(new JSONObject(body));
                    cb.onInitialised(routeCallGet);
                }
            });
        }
    }

}
