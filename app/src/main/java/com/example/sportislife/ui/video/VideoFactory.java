package com.example.sportislife.ui.video;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class VideoFactory implements ViewModelProvider.Factory {

    private Application application;

    public VideoFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(VideoViewModel.class)) {
            return (T) new VideoViewModel(application);
        }
        throw new IllegalArgumentException("Unknown View Model Class");
    }
}
