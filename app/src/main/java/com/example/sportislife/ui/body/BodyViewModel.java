package com.example.sportislife.ui.body;

import android.graphics.Color;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class BodyViewModel extends ViewModel {

    //private final MutableLiveData<String> mText;
    private final MutableLiveData<LineData> resultBodyDataLive;

    public BodyViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is body fragment");
        resultBodyDataLive = new MutableLiveData<>();
        resultBodyDataLive.setValue(initLineBodyData());
    }

//    public LiveData<String> getText() {
//        return mText;
//    }

    private LineData initLineBodyData() {
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

    public MutableLiveData<LineData> getBodyData() {
        return resultBodyDataLive;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}