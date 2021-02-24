package com.android.example.firstprojectandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.firstprojectandroid.adapter.DataAdapter;
import com.android.example.firstprojectandroid.model.Data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView mtextViewresult, mtextViewname, tvTemp, tvTime;
    private ProgressBar loadingView;
    RecyclerView recyclerView;
    private PageViewModel pageViewModel;


    private DataAdapter dataAdapter;
    private List<Data> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_images);

        //inversion of control principle
        pageViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(PageViewModel.class);

        mtextViewresult = findViewById(R.id.texturl);
        mtextViewname = findViewById(R.id.textname);
        tvTemp = findViewById(R.id.tvTemp);
        tvTime = findViewById(R.id.tvtime);
        loadingView = findViewById(R.id.loadingView);
        recyclerView = findViewById(R.id.recyclerView);

        dataList = new ArrayList<>();
        dataAdapter = new DataAdapter(this, dataList);
        recyclerView.setAdapter(dataAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        pageViewModel.getApiData().observe(this, weatherAPIResult -> {
            if (weatherAPIResult == null) return;

            mtextViewresult.setText("City: " + weatherAPIResult.getCity().country);
            mtextViewname.setText("Name: " + weatherAPIResult.getCity().name);
            tvTemp.setText(weatherAPIResult.getList().get(0).temp.getDay() + "°c");
            tvTime.setText(displayDate(weatherAPIResult.getList().get(0).dt));

            dataList.addAll(weatherAPIResult.getList());
            dataAdapter.notifyDataSetChanged();
        });

        pageViewModel.isLoading().observe(this, loading -> {
            if (loading == null) return;

            if (loading) {
                loadingView.setVisibility(View.VISIBLE);
            } else {
                loadingView.setVisibility(View.GONE);
            }
        });
    }


    private String displayDate(long dt) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dt);
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());

        return calendar.get(Calendar.DAY_OF_MONTH) + " " + month + ", " + calendar.get(Calendar.YEAR);
    }
}
