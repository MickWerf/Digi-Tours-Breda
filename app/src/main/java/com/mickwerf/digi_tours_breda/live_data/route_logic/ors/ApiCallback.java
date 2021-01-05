package com.mickwerf.digi_tours_breda.live_data.route_logic.ors;

public interface ApiCallback {
    /**
     * Call when Api object is initialised with the query's response.
     * @param apiResponse Initialised Api Response.
     */
    public void onInitialised(RouteCallGet apiResponse);
}
