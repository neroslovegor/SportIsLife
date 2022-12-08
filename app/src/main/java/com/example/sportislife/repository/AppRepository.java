package com.example.sportislife.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.sportislife.db.AppDatabase;
import com.example.sportislife.model.Body;

import java.util.Date;
import java.util.List;

public class AppRepository {

    private String DB_NAME = "db_sport_is_life";
    private AppDatabase appDatabase;

    public AppRepository(Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
    }

    public void insertBody(Date date, String gender, float weight, float height, String physicalActivity) {
        Body body = new Body();
        body.setDate(date);
        body.setGender(gender);
        body.setWeight(weight);
        body.setHeight(height);
        body.setPhysicalActivity(physicalActivity);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                appDatabase.daoBody().insertBody(body);
                return null;
            }
        }.execute();
    }

    public void deleteBody(int id) {
        LiveData<Body> body = getBody(id);
        if(body != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    appDatabase.daoBody().deleteBody(body.getValue());
                    return null;
                }
            }.execute();
        }
    }

    public LiveData<Body> getBody(int id) {
        return appDatabase.daoBody().getBody(id);
    }

    public LiveData<List<Body>> getBodys() {
        return appDatabase.daoBody().fetchAllTasks();
    }
}
