package com.example.sportislife.ui.body;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportislife.repository.BodyRepository;
import com.example.sportislife.ui.weight.WeightTrackingViewModel;

public class BodyFactory implements ViewModelProvider.Factory {

    private BodyRepository repository;
    private Application application;

    public BodyFactory(BodyRepository repository, Application application) {
        this.repository = repository;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BodyViewModel.class)) {
            return (T) new BodyViewModel(repository, application);
        }
        throw new IllegalArgumentException("Unknown View Model Class");
    }
}
