package com.example.betaapp.api.models.response;

import com.google.gson.annotations.SerializedName;

public class ResponseOAuthToken extends ResponseDTO {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }
}