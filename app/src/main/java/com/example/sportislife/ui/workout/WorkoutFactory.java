package com.example.sportislife.ui.workout;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportislife.repository.WorkoutRepository;

public class WorkoutFactory implements ViewModelProvider.Factory {

    private WorkoutRepository repository;
    private Application application;

    public WorkoutFactory(WorkoutRepository repository, Application application) {
        this.repository = repository;
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(WorkoutViewModel.class)) {
            return (T) new WorkoutViewModel(repository, application);
        }
        throw new IllegalArgumentException("Unknown View Model Class");
    }
}
