package com.example.sportislife.ui.body;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportislife.databinding.FragmentBodyBinding;
import com.github.mikephil.charting.charts.LineChart;

public class BodyFragment extends Fragment {

    private FragmentBodyBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        BodyViewModel bodyViewModel = new ViewModelProvider(this).get(BodyViewModel.class);

        binding = FragmentBodyBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final LineChart lineChart = binding.bodyLineChart;
        bodyViewModel.getBodyData().observe(getViewLifecycleOwner(), lineChart::setData);

        //final LineChart textView = binding.textBody;
        //bodyViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}