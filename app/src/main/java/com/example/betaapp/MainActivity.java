package com.example.betaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.betaapp.api.GitHubClient;
import com.example.betaapp.api.GitHubService;
import com.example.betaapp.api.actions.GetOAuthToken;
import com.example.betaapp.api.actions.GitHubRequest;
import com.example.betaapp.api.models.request.RequestDTO;
import com.example.betaapp.api.models.request.RequestOAuthToken;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private TextView responseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        responseView = findViewById(R.id.response);

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginIntoGitHubOAuth();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Uri uri = getIntent().getData();
        if (uri != null && uri.toString().startsWith(GitHubRequest.REDIRECT_URL)) {
            String code = uri.getQueryParameter("code");
            responseView.setText(code);
            GitHubService.getToken(code);
        } else {
            responseView.setText("ERROR");
        }
    }

    private void loginIntoGitHubOAuth() {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(GitHubRequest.BASE_HTTP_URL
                        + "/login/oauth/authorize"
                        + "?client_id=" + GitHubRequest.CLIENT_ID
                        + "&scope=user%20repo"
                        + "&redirect_url=" + GitHubRequest.REDIRECT_URL));
        startActivity(intent);
    }


    private void loginIntoGitHub() {
        String URL = "https://api.github.com/user";
        String base = Base64.encodeToString(("krumiliev" + ":" + "10004107@Horus").getBytes(), Base64.NO_WRAP);
        String authHeader = "Basic " + base;
//        new NetworkWork(URL, authHeader, new NetworkWork.OnRequestReadyListener() {
//            @Override
//            public void onRequestSuccess(String response) {
//                responseView.setText(response);
//            }
//
//            @Override
//            public void onRequestFail(String error) {
//                responseView.setText(error);
//            }
//        }).execute();
    }

//    private static class NetworkWork extends AsyncTask<Void, Void, Void> {
//
//        private final GitHubRequest request;
//
//        public NetworkWork(GitHubRequest request) {
//            this.request = request;
//        }
//
//        @Override
//        protected Void doInBackground(Void... v) {
//            GitHubClient.getInstance().send(request);
//            return null;
//        }
//    }
}