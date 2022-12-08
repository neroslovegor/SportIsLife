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
    Void insertWeight(Weight weight);

    @Query("SELECT * FROM Weight ORDER BY date desc")
    LiveData<List<Weight>> fetchAllWeights();

    @Query("SELECT * FROM Weight WHERE uid =:WeightId")
    LiveData<Weight> getWeight(int WeightId);

    @Update
    void updateWeight(Weight weight);

    @Delete
    void deleteWeight(Weight weight);

}
