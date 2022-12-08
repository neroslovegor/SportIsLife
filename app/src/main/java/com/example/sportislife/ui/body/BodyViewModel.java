package com.example.sportislife.ui.body;

import android.graphics.Color;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BodyViewModel extends ViewModel {

    //rivate AppRepository appRepository;

    private final MutableLiveData<String> currentDateLive;
    private final MutableLiveData<LineData> resultWeightDataLive;

    public BodyViewModel() {
        //appRepository = new AppRepository(context);

        currentDateLive = new MutableLiveData<>();
        resultWeightDataLive = new MutableLiveData<>();

        currentDateLive.setValue(initCurrentDate());
        resultWeightDataLive.setValue(initLineWeightData());
    }

    public void save(Date date, Float weight) {
        //appRepository.insertBody(date, weight);
    }

    private String initCurrentDate() {
        String date = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
        return date;
    }

    private LineData initLineWeightData() {
        ArrayList<Entry> bodyData = new ArrayList<>();

        bodyData.add(new Entry(1, 85));
        bodyData.add(new Entry(2, 80));
        bodyData.add(new Entry(3, 85));
        bodyData.add(new Entry(4, 70));

        LineDataSet lineDataSet = new LineDataSet(bodyData, "Данные тела");
        lineDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setValueTextSize(32f);

        LineData lineData = new LineData(lineDataSet);

        return lineData;
    }

    public LiveData<String> getCurrentDate() {
        return currentDateLive;
    }
    public MutableLiveData<LineData> getWeightData() {
        return resultWeightDataLive;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}