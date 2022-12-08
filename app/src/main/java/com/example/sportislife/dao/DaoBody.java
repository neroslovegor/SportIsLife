package com.example.sportislife.dao;

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
    Void insert(Body body);

    @Query("SELECT * FROM Body ORDER BY uid")
    List<Body> getAll();

    @Query("SELECT * FROM Body WHERE uid =:bodyId")
    Body get(int bodyId);

    @Update
    void update(Body body);

    @Delete
    void delete(Body body);
}
