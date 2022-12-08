package com.example.sportislife.repository;

import static com.example.sportislife.AppConstants.DB_NAME;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.sportislife.db.AppDatabase;
import com.example.sportislife.repository.model.Weight;

import java.util.Date;
import java.util.List;

public class WeightRepository {

    private AppDatabase appDatabase;

    public WeightRepository(Context context) {
        appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME).build();
    }

    public LiveData<Weight> getWeight(int id) {
        return appDatabase.daoWeight().getWeight(id);
    }

    public LiveData<List<Weight>> getWeights() {
        return appDatabase.daoWeight().fetchAllWeights();
    }

    public void insertWeight(Date date, float weight) {
        Weight weightModel = new Weight();
        weightModel.setDate(date);
        weightModel.setWeight(weight);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.daoWeight().insertWeight(weightModel);
                return null;
            }
        }.execute();
    }

    public void deleteWeight(int id) {
        LiveData<Weight> weight = getWeight(id);

        if(weight != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    appDatabase.daoWeight().deleteWeight(weight.getValue());
                    return null;
                }
            }.execute();
        }
    }
}
