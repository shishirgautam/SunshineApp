package com.android.example.firstprojectandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.example.firstprojectandroid.R;
import com.android.example.firstprojectandroid.model.Data;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.Temperture> {
    Context context;
    List<Data> weatherAPIResults;

    public DataAdapter(Context context, List<Data> weatherAPIResults) {
        this.context = context;
        this.weatherAPIResults = weatherAPIResults;
    }

    @NonNull
    @Override
    public Temperture onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_temperture, parent, false);
        return new Temperture(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Temperture holder, int position) {
        Data tempData = weatherAPIResults.get(position);
        holder.tvdate.setText(dateConvert(tempData.dt));
        holder.tvweather.setText(tempData.weather.get(0).getDescription());
        holder.tvmax.setText(tempData.temp.getMax().byteValue() + "°c");
        holder.tvmin.setText(tempData.temp.getMin().byteValue() + "°c");
    }

    @Override
    public int getItemCount() {
        return weatherAPIResults.size();
    }

    public class Temperture extends RecyclerView.ViewHolder {
        TextView tvdate, tvweather, tvmax, tvmin;

        public Temperture(@NonNull View itemView) {
            super(itemView);
            tvdate = itemView.findViewById(R.id.tvDate);
            tvweather = itemView.findViewById(R.id.tvWeather);
            tvmax = itemView.findViewById(R.id.tvMax);
            tvmin = itemView.findViewById(R.id.tvMin);

        }
    }

    private String dateConvert(long dateInMills) {
        Calendar ourCal = Calendar.getInstance();
        ourCal.setTimeInMillis(dateInMills);

        String amPm = ourCal.getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.getDefault());

        return  ourCal.get(Calendar.HOUR) + ":" + ourCal.get(Calendar.MINUTE) + " " + amPm;
    }
}