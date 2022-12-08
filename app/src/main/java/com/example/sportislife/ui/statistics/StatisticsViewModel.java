package com.example.sportislife.ui.statistics;

import android.app.Application;
import android.graphics.Color;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sportislife.repository.WorkoutRepository;
import com.example.sportislife.repository.model.Workout;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StatisticsViewModel extends ViewModel {

    private WorkoutRepository repository;
    private Application application;

    private MutableLiveData<List<String>> typeExercise;
    public MutableLiveData<List<String>> getTypeExercise() {
        return typeExercise;
    }
    public void setTypeExercise() {
        typeExercise.setValue(initTypeExercise());
    }

    private MutableLiveData<LineChart> lineChartData;
    public MutableLiveData<LineChart> getLineChart() {
        return lineChartData;
    }
    public void setLineChart() {
        lineChartData.setValue(initLineChart());
    }

    public MutableLiveData<String> inputTypeExercise;

    public StatisticsViewModel(WorkoutRepository repository, Application application) {
        this.repository = repository;
        this.application = application;

        lineChartData = new MutableLiveData<>();
        typeExercise = new MutableLiveData<>();

        inputTypeExercise = new MutableLiveData<>();
        inputTypeExercise.setValue(null);

        updateData();
    }

    public void updateData() {
        setLineChart();
        setTypeExercise();
    }

    private List<String> initTypeExercise() {
        List<Workout> workouts = repository.getAll();
        List<String> exerciseTypes = new ArrayList<>();

        if (workouts == null) return null;

        for (Workout workout : workouts) {
            if (!exerciseTypes.contains(workout.getTypeExercise())) {
                exerciseTypes.add(workout.getTypeExercise());
            }
        }

        return exerciseTypes;
    }
    private LineChart initLineChart() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        LineChart lineChart = new LineChart(application.getApplicationContext());

        List<Workout> workouts = repository.getAll();
        List<Entry> workoutsData = new ArrayList<>();
        List<String> datesData = new ArrayList<>();

        if (workouts == null) return null;

        int counter = 0;
        for (Workout workout : workouts) {
            if (workout.getTypeExercise().equals(inputTypeExercise.getValue())) {
                workoutsData.add(new Entry(counter++, workout.getWeight()));
                datesData.add(simpleDateFormat.format(workout.getDate()));
            }
        }

        LineDataSet lineDataSet = new LineDataSet(workoutsData, "Вес");
        lineDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setValueTextSize(16f);

        lineChart.setData(new LineData(lineDataSet));

        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setGranularity(1f);
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(datesData));

        return lineChart;
    }

}