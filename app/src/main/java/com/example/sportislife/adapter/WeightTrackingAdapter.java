package com.example.sportislife.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.sportislife.R;
import com.example.sportislife.repository.model.Weight;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeightTrackingAdapter extends BaseAdapter {
    List<Weight> weights;
    private LayoutInflater layoutInflater;

    public WeightTrackingAdapter(Context context, List<Weight> weights) {
        this.weights = weights;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return weights.size();
    }

    @Override
    public Object getItem(int position) {
        return weights.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_weight_history, null);
        }

        TextView textDate = (TextView) view.findViewById(R.id.dateHistory);
        TextView textWeight = (TextView) view.findViewById(R.id.weightHistory);

        Weight weight = getWeight(position);

        textDate.setText(new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(weight.getDate()));
        textWeight.setText(String.valueOf(weight.getWeight()));

        return view;
    }

    private Weight getWeight(int position) {
        return (Weight) getItem(position);
    }
}
