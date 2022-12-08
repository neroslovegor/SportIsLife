package com.example.sportislife.ui.weight;

import android.graphics.Color;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sportislife.repository.WeightRepository;
import com.example.sportislife.repository.model.Weight;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeightTrackingViewModel extends ViewModel {

    private final WeightRepository weightRepository;

    private final MutableLiveData<String> currentDateLive;
    private final MutableLiveData<LineData> weightDataLive;

    public WeightTrackingViewModel(WeightRepository weightRepository) {
        this.weightRepository = weightRepository;

        currentDateLive = new MutableLiveData<>();
        weightDataLive = new MutableLiveData<>();

        currentDateLive.setValue(initCurrentDate());
        weightDataLive.setValue(initWeightData());
    }

    private String initCurrentDate() {
        return new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
    }

    private LineData initWeightData() {
        List<Weight> weights = weightRepository.getWeights().getValue();
        List<Entry> weightsData = new ArrayList<>();

        if (weights == null)  return initLineData(null);

        int counter = 1;
        for (Weight weight : weights) {
            weightsData.add(new Entry(counter++, weight.getWeight()));
        }

//        bodyData.add(new Entry(1, 85));
//        bodyData.add(new Entry(2, 80));
//        bodyData.add(new Entry(3, 85));
//        bodyData.add(new Entry(4, 70));
//
        return initLineData(weightsData);
    }

    private LineData initLineData (List<Entry> weightsData) {
        LineDataSet lineDataSet = new LineDataSet(weightsData, "Вес");
        lineDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setValueTextSize(32f);

        return new LineData(lineDataSet);
    }

    public void saveWeightData(Date date, Float weight) {
        weightRepository.insertWeight(date, weight);
    }

    public LiveData<String> getCurrentDate() {
        return currentDateLive;
    }
    public MutableLiveData<LineData> getWeightData() {
        return weightDataLive;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}