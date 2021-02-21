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

import java.util.List;

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
        holder.tvTempp.setText(String.valueOf(tempData.temp.getDay()));
        holder.tvCity.setText(String.valueOf(tempData.clouds));
        holder.tvname.setText(String.valueOf(tempData.dt));
    }

    @Override
    public int getItemCount() {
        return weatherAPIResults.size();
    }

    public class Temperture extends RecyclerView.ViewHolder {
        TextView tvname, tvTempp, tvCity;

        public Temperture(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.textNamee);
            tvCity = itemView.findViewById(R.id.textcity);
            tvTempp = itemView.findViewById(R.id.tvTemperture);

        }
    }
}
