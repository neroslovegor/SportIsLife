package com.example.sportislife.ui.statistics;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportislife.R;
import com.example.sportislife.dao.DaoWorkout;
import com.example.sportislife.databinding.FragmentStatisticsBinding;
import com.example.sportislife.db.AppDatabase;
import com.example.sportislife.repository.WorkoutRepository;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;

import java.util.List;

public class StatisticsFragment extends Fragment {

    private StatisticsViewModel viewModel;
    private FragmentStatisticsBinding binding;

    private AutoCompleteTextView typeExercise;
    private LineChart weightLineChart;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Application application = this.requireActivity().getApplication();
        DaoWorkout dao = AppDatabase.getInstance(application).daoWorkout();
        WorkoutRepository repository = new WorkoutRepository(dao);
        StatisticsFactory factory = new StatisticsFactory(repository, application);

        viewModel = new ViewModelProvider(this, factory).get(StatisticsViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false);
        binding.setStatisticsViewModel(viewModel);
        binding.setLifecycleOwner(this);

        typeExercise = binding.typeExercise;
        weightLineChart = binding.weightLineChart;

        viewModel.getLineChart().observe(getViewLifecycleOwner(), lineChart -> {
            weightLineChart.getDescription().setText("Weight/Date");
            weightLineChart.setData(lineChart.getData());
            weightLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            weightLineChart.getXAxis().setValueFormatter(lineChart.getXAxis().getValueFormatter());
            weightLineChart.getXAxis().setGranularity(1f);
        });

        viewModel.updateData();
        viewModel.getTypeExercise().observe(getViewLifecycleOwner(), itemTypeExerciseList -> {
            String[] itemTypeExercise = itemTypeExerciseList.toArray(new String[itemTypeExerciseList.size()]);
            ArrayAdapter<String> adapterTypeExercise = new ArrayAdapter<>(application.getApplicationContext(), R.layout.item_physical_activity, itemTypeExercise);
            typeExercise.setAdapter(adapterTypeExercise);
            typeExercise.setOnItemClickListener((parent, view, position, id) -> {
                viewModel.inputTypeExercise.setValue(typeExercise.getText().toString());
                viewModel.updateData();
            });
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}