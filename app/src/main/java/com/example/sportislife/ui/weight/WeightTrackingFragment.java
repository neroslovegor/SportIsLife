package com.example.sportislife.ui.weight;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.sportislife.R;
import com.example.sportislife.databinding.FragmentBodyBinding;
import com.example.sportislife.databinding.FragmentWeightTrackingBinding;
import com.example.sportislife.databinding.FragmentWorkoutBinding;
import com.example.sportislife.ui.body.BodyViewModel;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeightTrackingFragment extends Fragment implements View.OnClickListener {

    private WeightTrackingViewModel viewModel;
    private FragmentWeightTrackingBinding binding;

    private EditText editTextDate, editTextWeight;
    private FloatingActionButton btnSaveWeight;
    private LineChart weightLineChart;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this, new WeightTrackingFactory(getContext())).get(WeightTrackingViewModel.class);
        binding = FragmentWeightTrackingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        editTextDate = binding.editTextDate;
        viewModel.getCurrentDate().observe(getViewLifecycleOwner(), editTextDate::setText);

        editTextWeight = binding.editTextWeight;

        btnSaveWeight = binding.btnSaveWeight;
        btnSaveWeight.setOnClickListener(this);

        weightLineChart = binding.weightLineChart;
        viewModel.getWeightData().observe(getViewLifecycleOwner(), weightLineChart::setData);

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
            String textDate = editTextDate.getText().toString();
            String textWeight  = editTextWeight.getText().toString();

            if (!TextUtils.isEmpty(textDate) && !TextUtils.isEmpty(textWeight)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
                Date date = simpleDateFormat.parse(textDate);
                Float weight = Float.parseFloat(textWeight);
                viewModel.saveWeightData(date, weight);
            } else {
                editTextWeight.setError(getString(R.string.error_this_field_cannot_be_empty));
            }
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