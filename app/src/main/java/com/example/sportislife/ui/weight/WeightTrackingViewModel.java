package com.example.sportislife.ui.weight;

import android.app.Application;
import android.graphics.Color;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sportislife.repository.WeightRepository;
import com.example.sportislife.repository.model.Weight;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class WeightTrackingViewModel extends ViewModel {

    private WeightRepository repository;
    private Application application;

    private MutableLiveData<String> currentDate;
    public MutableLiveData<String> getCurrentDate() {
        return currentDate;
    }
    public void setCurrentDate() {
        currentDate.setValue(initCurrentDate());
    }

    private MutableLiveData<List<Weight>> weightData;
    public MutableLiveData<List<Weight>> getWeightData() {
        return weightData;
    }
    public void setWeightData() {
        weightData.setValue(initWeightData());
    }

    private MutableLiveData<LineChart> lineChartData;
    public MutableLiveData<LineChart> getLineChart() {
        return lineChartData;
    }
    public void setLineChart() {
        lineChartData.setValue(initLineChart());
    }

    public MutableLiveData<String> inputDate;
    //public void setInputDate(String date) { inputDate.setValue(date); }
    public MutableLiveData<String> inputWeight;
    //public void setInputWeight(String weight) { inputDate.setValue(weight); }

    private MutableLiveData<Boolean> errorWeight;
    public MutableLiveData<Boolean> getErrorWeight() {
        return errorWeight;
    }
    public void doneErrorWeight() {
        errorWeight.setValue(false);
    }

    public WeightTrackingViewModel(WeightRepository repository, Application application) {
        this.repository = repository;
        this.application = application;

        currentDate = new MutableLiveData<>();
        weightData = new MutableLiveData<>();
        lineChartData = new MutableLiveData<>();

        inputDate = new MutableLiveData<>();
        inputWeight = new MutableLiveData<>();

        errorWeight = new MutableLiveData<>();

        updateData();
    }

    private void updateData() {
        setCurrentDate();
        setWeightData();
        setLineChart();
        inputWeight.setValue(null);
    }
    private String initCurrentDate() {
        return new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
    }
    private List<Weight> initWeightData() {
        List<Weight> weights = repository.getAll();
        return weights;
    }
    private LineChart initLineChart() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        LineChart lineChart = new LineChart(application.getApplicationContext());

        List<Weight> weights = repository.getAll();
        List<Entry> weightsData = new ArrayList<>();
        List<String> datesData = new ArrayList<>();

        if (weights == null) return null;

        int counter = 0;
        for (Weight weight : weights) {
            weightsData.add(new Entry(counter++, weight.getWeight()));
            datesData.add(simpleDateFormat.format(weight.getDate()));
        }

        LineDataSet lineDataSet = new LineDataSet(weightsData, "Вес");
        lineDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setValueTextSize(16f);

        lineChart.setData(new LineData(lineDataSet));

        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setGranularity(1f);
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(datesData));

        return lineChart;
    }

    public void submitButton() {
        try {
            if (inputDate.getValue() != null && inputWeight.getValue() != null) {
                if (inputDate.getValue().length() > 0 && inputWeight.getValue().length() > 0) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

                    Date date = simpleDateFormat.parse(Objects.requireNonNull(inputDate.getValue()));
                    Float weight = Float.parseFloat(Objects.requireNonNull(inputWeight.getValue()));

                    Weight weightEntity = new Weight();
                    weightEntity.setDate(date);
                    weightEntity.setWeight(weight);

                    repository.insert(weightEntity);

                    updateData();
                } else {
                    errorWeight.setValue(true);
                }
            } else {
                errorWeight.setValue(true);
            }
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
    }
    public void deleteButton(int id) {
        List<Weight> weights = repository.getAll();

        if (weights == null) return;

        int counter = 0;
        for (Weight weight : weights) {
            if (id == counter) {
                repository.delete((weight.getUid()));
            }
            counter++;
        }

        updateData();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}