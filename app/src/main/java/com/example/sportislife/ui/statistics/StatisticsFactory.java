package com.example.sportislife.ui.statistics;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportislife.repository.WorkoutRepository;

public class StatisticsFactory implements ViewModelProvider.Factory {

    private WorkoutRepository repository;
    private Application application;

    public StatisticsFactory(WorkoutRepository repository, Application application) {
        this.repository = repository;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(StatisticsViewModel.class)) {
            return (T) new StatisticsViewModel(repository, application);
        }
        throw new IllegalArgumentException("Unknown View Model Class");
    }
}
