package com.example.sportislife.repository;

import static com.example.sportislife.AppConstants.DB_NAME;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.example.sportislife.dao.DaoBody;
import com.example.sportislife.dao.DaoWeight;
import com.example.sportislife.db.AppDatabase;
import com.example.sportislife.repository.model.Body;
import com.example.sportislife.repository.model.Weight;

import java.util.Date;
import java.util.List;

public class BodyRepository {

    private DaoBody dao;

    public BodyRepository(DaoBody dao) {
        this.dao = dao;
    }

    public Body get(int id) {
        return dao.get(id);
    }

    public List<Body> getAll() {
        return dao.getAll();
    }

    public void insert(Body body) {
        dao.insert(body);
    }

    public void update(Body body) {
        if (body != null) {
            dao.update(body);
        }
    }

    public void delete(int id) {
        Body body = get(id);

        if(body != null) {
            dao.delete(body);
        }
    }
}
