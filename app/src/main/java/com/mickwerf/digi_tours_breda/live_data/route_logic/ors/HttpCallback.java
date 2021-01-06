package com.mickwerf.digi_tours_breda.live_data.route_logic.ors;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Response;

public interface HttpCallback {

    /**
     * called when the server response was not 2xx or when an exception was thrown in the process
     * @param response  - in case of server error (4xx, 5xx) this contains the server response
     *                  in case of IO exception this is null
     * @param throwable - contains the exception. in case of server error (4xx, 5xx) this is null
     */
    public void onFailure(Response response, Throwable throwable);

    /**
     * contains the server response
     * @param response A successful response from the API.
     */
    public void onSuccess(Response response) throws IOException, JSONException;
}
