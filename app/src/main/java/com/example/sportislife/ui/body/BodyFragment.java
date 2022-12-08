package com.example.sportislife.ui.body;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportislife.databinding.FragmentBodyBinding;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BodyFragment extends Fragment implements View.OnClickListener {

    private BodyViewModel bodyViewModel;
    private FragmentBodyBinding binding;

    private EditText editTextDate, editTextWeight;
    private FloatingActionButton btnSaveWeight;
    private LineChart weightLineChart;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bodyViewModel = new ViewModelProvider(this).get(BodyViewModel.class);
        binding = FragmentBodyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        editTextDate = binding.editTextDate;
        bodyViewModel.getCurrentDate().observe(getViewLifecycleOwner(), editTextDate::setText);

        editTextWeight = binding.editTextWeight;

        btnSaveWeight = binding.btnSaveWeight;
        btnSaveWeight.setOnClickListener(this);

        weightLineChart = binding.weightLineChart;
        bodyViewModel.getWeightData().observe(getViewLifecycleOwner(), weightLineChart::setData);

        return root;
    }

    @Override
    public void onClick(View view) {
        if (view == btnSaveWeight) {
            save();
        }

    }

    private void save() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date date = simpleDateFormat.parse(editTextDate.getText().toString());
            Float weight = Float.parseFloat(editTextWeight.getText().toString());
            bodyViewModel.save(date, weight);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}