package com.example.sportislife.ui.weight;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sportislife.R;
import com.example.sportislife.adapter.WeightTrackingAdapter;
import com.example.sportislife.dao.DaoWeight;
import com.example.sportislife.databinding.FragmentWeightTrackingBinding;
import com.example.sportislife.db.AppDatabase;
import com.example.sportislife.repository.WeightRepository;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

public class WeightTrackingFragment extends Fragment {

    private WeightTrackingViewModel viewModel;
    private FragmentWeightTrackingBinding binding;

    private EditText editTextDate, editTextWeight;
    //private FloatingActionButton btnSaveWeight;
    private LineChart weightLineChart;
    private ListView listViewHistoryWeight;
    private WeightTrackingAdapter weightTrackingAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Application application = this.requireActivity().getApplication();
        DaoWeight dao = AppDatabase.getInstance(application).daoWeight();
        WeightRepository repository = new WeightRepository(dao);
        WeightTrackingFactory factory = new WeightTrackingFactory(repository, application   );

        viewModel = new ViewModelProvider(this, factory).get(WeightTrackingViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_weight_tracking, container, false);
        binding.setMyViewModel(viewModel);
        binding.setLifecycleOwner(this);

        editTextDate = binding.editTextDate;
        editTextWeight = binding.editTextWeight;
        weightLineChart = binding.weightLineChart;
        listViewHistoryWeight = binding.listViewHistoryWeight;

        viewModel.getCurrentDate().observe(getViewLifecycleOwner(), editTextDate::setText);

        //viewModel.getWeightData().observe(getViewLifecycleOwner(), weightLineChart::setData);
        viewModel.getLineChart().observe(getViewLifecycleOwner(), lineChart -> {
            weightLineChart.getDescription().setText("Weight/Date");
            weightLineChart.setData(lineChart.getData());
            weightLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            weightLineChart.getXAxis().setValueFormatter(lineChart.getXAxis().getValueFormatter());
            weightLineChart.getXAxis().setGranularity(1f);
        });

        viewModel.getWeightData().observe(getViewLifecycleOwner(), weightData -> {
            weightTrackingAdapter = new WeightTrackingAdapter(application.getApplicationContext(), weightData);
            listViewHistoryWeight.setAdapter(weightTrackingAdapter);
        });
        listViewHistoryWeight.setOnItemClickListener((parent, view, position, id) -> {
            final int itemId = (int) weightTrackingAdapter.getItemId(position);
            viewModel.deleteButton(itemId);
        });

        viewModel.getErrorWeight().observe(getViewLifecycleOwner(), hasError -> {
            if (hasError) {
                editTextWeight.setError(getString(R.string.error_this_field_cannot_be_empty));
                viewModel.doneErrorWeight();
            }
        });

//        editTextDate = binding.editTextDate;
//        viewModel.getCurrentDate().observe(getViewLifecycleOwner(), editTextDate::setText);
//
//        editTextWeight = binding.editTextWeight;
//
//        btnSaveWeight = binding.btnSaveWeight;
//        btnSaveWeight.setOnClickListener(this);
//
//        weightLineChart = binding.weightLineChart;
//        viewModel.getWeightData().observe(getViewLifecycleOwner(), weightLineChart::setData);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}