package com.android.example.firstprojectandroid;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.firstprojectandroid.adapter.DataAdapter;
import com.android.example.firstprojectandroid.model.Data;
import com.android.example.firstprojectandroid.model.WeatherAPIResult;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView mtextViewresult, mtextViewname, tvTemp;
    private ProgressBar loadingView;
    RecyclerView recyclerView;


    private RequestQueue mQueue;
    private DataAdapter dataAdapter;
    private List<Data> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_images);
        mtextViewresult = findViewById(R.id.texturl);
        mtextViewname = findViewById(R.id.textname);
        tvTemp = findViewById(R.id.tvTemp);
        loadingView = findViewById(R.id.loadingView);
        recyclerView = findViewById(R.id.recyclerView);

        mQueue = Volley.newRequestQueue(this);
        dataList = new ArrayList<>();
        dataAdapter = new DataAdapter(this, dataList);
        recyclerView.setAdapter(dataAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadingView.setVisibility(View.VISIBLE);
        jsonFormat();
    }

    private void jsonFormat() {
        String url = "https://andfun-weather.udacity.com/weather";

        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            loadingView.setVisibility(View.GONE);
            WeatherAPIResult parsedResult = new Gson().fromJson(response, WeatherAPIResult.class);

            mtextViewresult.setText("City: " + parsedResult.getCity().country);
            mtextViewname.setText("Name: " + parsedResult.getCity().name);
            tvTemp.setText(parsedResult.getList().get(0).temp.getDay() + "°c");

            dataList.addAll(parsedResult.getList());///
            dataAdapter.notifyDataSetChanged();///

            Log.d("Code", response);
        }, error -> {
            loadingView.setVisibility(View.GONE);
            mtextViewresult.setText("That didn't work!");
        });

        mQueue.add(request);
    }

}