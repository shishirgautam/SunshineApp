package com.android.example.firstprojectandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.example.firstprojectandroid.model.Weather;
import com.android.example.firstprojectandroid.model.WeatherAPIResult;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private TextView mtextViewresult,mtextViewname,mtextViewtem,mtextViewpopu;
    private ProgressBar loadingView;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_images);
        mtextViewresult = findViewById(R.id.texturl);
        mtextViewname = findViewById(R.id.textname);
        mtextViewpopu = findViewById(R.id.textpopulation);
        loadingView = findViewById(R.id.loadingView);
        Button buttonclick = findViewById(R.id.btnclick);

        mQueue = Volley.newRequestQueue(this);
        buttonclick.setOnClickListener(v -> {
            loadingView.setVisibility(View.VISIBLE);
            jsonFormat();
        });


    }

    private void jsonFormat() {
        String url = "https://andfun-weather.udacity.com/weather";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url, response -> {

            loadingView.setVisibility(View.GONE);
            WeatherAPIResult parsedResult = new Gson().fromJson(response, WeatherAPIResult.class);

            mtextViewresult.setText("City: " + parsedResult.getCity().country);
            mtextViewname.setText("Name: " + parsedResult.getCity().name);
            mtextViewpopu.setText("Population: " + parsedResult.getCity().population);
            Log.d("Code", response);
        },
                error -> {
                    loadingView.setVisibility(View.GONE);
                    mtextViewresult.setText("That didn't work!");
                });

        mQueue.add(request);

        //TODO: read this
        //anonymous class
        //lambda -> special type of interface with only one function
    }

}