package com.example.sportislife.ui.weight;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportislife.repository.WeightRepository;

public class WeightTrackingFactory implements ViewModelProvider.Factory {

    private WeightRepository repository;
    private Application application;
    
    public WeightTrackingFactory(WeightRepository repository, Application application) {
        this.repository = repository;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WeightTrackingViewModel.class)) {
            return (T) new WeightTrackingViewModel(repository, application);
        }
        throw new IllegalArgumentException("Unknown View Model Class");
    }
}
