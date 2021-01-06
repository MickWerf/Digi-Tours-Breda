package com.mickwerf.digi_tours_breda.live_data.route_logic.ors;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {

    private final String TAG = getClass().getName();
    final static String API_KEY = "5b3ce3597851110001cf6248f2e5fccb08354400b1bb7bfbc7ab2a69"; // Key of Dennis
    private final int MAX_URL_LEN = 2048;

    private OkHttpClient client;
    private Request.Builder builder;
    private final Context context;

    public HttpUtil(Context context) {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(3000, TimeUnit.MILLISECONDS)
                .readTimeout(3000, TimeUnit.MILLISECONDS)
                .writeTimeout(3000, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .build();
        this.builder = new Request.Builder();
        this.context = context;
    }

    /**
     * GET API-call.
     * @param url URL containing both the query type and its parameters.
     * @param cb Callback to register a success or fail response.
     * @return
     */
    public boolean get(String url, HttpCallback cb) {
        if (url.length() <= MAX_URL_LEN) {
            call(url, cb, null);
            return true;
        } else {
            Log.e(TAG, "Url cannot be longer than " + MAX_URL_LEN + ".");
            return false;
        }
    }

    /**
     * Send API request with callback.
     * @param url The API URL.
     * @param body RequestBody with the parameters as JSON.
     * @param cb Callback to register a success or fail response.
     */
    public void post(String url, RequestBody body, HttpCallback cb) {
        call(url, cb, body);
    }

    /**
     * Send API request with callback.
     * @param url The API URL.
     * @param cb Callback to register a success or fail response.
     * @param body RequestBody of a POST API-call if applicable.
     */
    private void call(String url, final HttpCallback cb, RequestBody body) {

        Request request = body == null ?
                builder.url(url).get().build() :
                builder.url(url).addHeader("Authorization", API_KEY).put(body).build();

        client.newCall(request).enqueue(new Callback() {
            final Handler mainHandler = new Handler(context.getMainLooper());

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                mainHandler.post(() -> {
                    if (!response.isSuccessful()) {
                        cb.onFailure(response, null);
                        return;
                    }
                    try {
                        cb.onSuccess(response);
                    } catch (IOException | JSONException e) {
                        Log.e(TAG, "Error: " + e.getLocalizedMessage());
                    }
                });
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                mainHandler.post(() -> cb.onFailure(null, (Throwable) e));
            }
        });
    }
}


