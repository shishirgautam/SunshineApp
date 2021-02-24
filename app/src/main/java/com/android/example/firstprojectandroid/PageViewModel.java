package com.android.example.firstprojectandroid;

import android.app.Application;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.example.firstprojectandroid.model.WeatherAPIResult;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class PageViewModel extends AndroidViewModel {
    private RequestQueue mQueue;
    private MutableLiveData<WeatherAPIResult> apiData = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public PageViewModel(@NonNull Application application) {
        super(application);
        mQueue = Volley.newRequestQueue(application);
        fetchApiData();
        isLoading.setValue(true);
    }

    public LiveData<WeatherAPIResult> getApiData() {
        return apiData;
    }

    public LiveData<Boolean> isLoading() {
        return isLoading;
    }

    private void fetchApiData() {
        String url = "https://andfun-weather.udacity.com/weather";

        StringRequest request = new StringRequest(Request.Method.GET, url, response -> {
            WeatherAPIResult parsedResult = new Gson().fromJson(response, WeatherAPIResult.class);
            apiData.postValue(parsedResult);
            isLoading.setValue(false);
        }, error -> {
            isLoading.setValue(false);
        });

        mQueue.add(request);
    }
}
