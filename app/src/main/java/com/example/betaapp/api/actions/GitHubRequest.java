package com.example.betaapp.api.actions;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.example.betaapp.utils.Cache;
import com.example.betaapp.api.models.request.RequestDTO;
import com.example.betaapp.api.models.response.ResponseDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Class used as a blueprint for all GitHub actions.
 */
public abstract class GitHubRequest<R extends RequestDTO, T extends ResponseDTO> extends Request<T> {

    // -------------------------------------------------------------------------------
    // Fields
    // -------------------------------------------------------------------------------

    private static final String LOG_TAG = GitHubRequest.class.getSimpleName();

    public static final String BASE_API_URL = "https://api.github.com";
    public static final String BASE_HTTP_URL = "https://github.com";
    public static final String CLIENT_SECRET = "58e3406b37f34a19865a009c7e6462236bd25d8a";
    public static final String CLIENT_ID = "6a1c6c6de961e8655b5d";
    public static final String REDIRECT_URL = "oauth://callback";

    private final Gson gson;
    private final Class<? extends ResponseDTO> responseClass;
    private boolean addAccessToken = false;
    protected String requestJson;

    // -------------------------------------------------------------------------------
    // Instance creations
    // -------------------------------------------------------------------------------

    public GitHubRequest(int method, String url, Class<? extends ResponseDTO> responseClass) {
        this(method, url, responseClass, false);
    }

    public GitHubRequest (int method, String url, Class<? extends ResponseDTO> responseClass, boolean addAccessToken) {
        super(method, url, null);

        gson = new GsonBuilder().setPrettyPrinting().create();
        this.responseClass = responseClass;
        this.addAccessToken = addAccessToken;
    }

    // -------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        if (addAccessToken) {
            headers.put("Authorization", "token " + Cache.gitHubToken);
        }
        return headers;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        return gson.toJson(getRequest()).getBytes();
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success((T) gson.fromJson(json, responseClass), HttpHeaderParser.parseCacheHeaders(response));

        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        onRequestFailed(volleyError);
        return super.parseNetworkError(volleyError);
    }

    @Override
    protected void deliverResponse(T response) {
        onRequestSuccess(response);
    }

    // -------------------------------------------------------------------------------
    // Protected
    // -------------------------------------------------------------------------------

    protected abstract R getRequest();

    protected abstract void onRequestSuccess(T response);

    protected abstract void onRequestFailed(VolleyError volleyError);
}
