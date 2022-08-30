package com.example.betaapp.api.models.request;

import com.google.gson.annotations.SerializedName;

public class RequestOAuthToken {

    @SerializedName("client_id")
    private String clientId;

    @SerializedName("client_secret")
    private String clientSecret;

    @SerializedName("code")
    private String code;

    public RequestOAuthToken(String clientId, String clientSecret, String code) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.code = code;
    }
}
