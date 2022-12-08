package com.example.sportislife.ui.weight;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportislife.repository.WeightRepository;

public class WeightTrackingFactory implements ViewModelProvider.Factory {

    private final WeightRepository weightRepository;
    
    public WeightTrackingFactory(Context context) {
        this.weightRepository = new WeightRepository(context);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new WeightTrackingViewModel(weightRepository) ;
    }
}
