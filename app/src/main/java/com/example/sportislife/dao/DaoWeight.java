package com.example.sportislife.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sportislife.repository.model.Weight;

import java.util.List;

@Dao
public interface DaoWeight {

    @Insert
    Void insert(Weight weight);

    @Query("SELECT * FROM Weight ORDER BY uid")
    List<Weight> getAll();

    @Query("SELECT * FROM Weight WHERE uid =:weightId")
    Weight get(int weightId);

    @Update
    void update(Weight weight);

    @Delete
    void delete(Weight weight);

}
