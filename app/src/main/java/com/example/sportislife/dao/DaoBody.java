package com.example.sportislife.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sportislife.repository.model.Body;

import java.util.List;

@Dao
public interface DaoBody {

    @Insert
    Void insertBody(Body body);

    @Query("SELECT * FROM Body ORDER BY date desc")
    LiveData<List<Body>> fetchAllBody();

    @Query("SELECT * FROM Body WHERE uid =:bodyId")
    LiveData<Body> getBody(int bodyId);

    @Update
    void updateBody(Body body);

    @Delete
    void deleteBody(Body body);
}
