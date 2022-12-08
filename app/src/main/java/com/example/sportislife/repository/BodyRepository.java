package com.example.sportislife.repository;

import static com.example.sportislife.AppConstants.DB_NAME;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.sportislife.db.AppDatabase;
import com.example.sportislife.repository.model.Body;

import java.util.Date;
import java.util.List;

public class BodyRepository {

//    public void insertBody(Date date, String gender, float weight, float height, String physicalActivity) {
//        Body body = new Body();
//        body.setDate(date);
//        body.setGender(gender);
//        body.setWeight(weight);
//        body.setHeight(height);
//        body.setPhysicalActivity(physicalActivity);
//
//        appDatabase.daoBody().insertBody(body);
//    }
//
//    public void deleteBody(int id) {
//        LiveData<Body> body = getBody(id);
//        appDatabase.daoBody().deleteBody(body.getValue());
//    }
//
//    public LiveData<Body> getBody(int id) {
//        return appDatabase.daoBody().getBody(id);
//    }
//
//    public LiveData<List<Body>> getBodys() {
//        return appDatabase.daoBody().fetchAllBody();
//    }
}
