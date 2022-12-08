package com.example.sportislife.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.sportislife.repository.model.Workout;

import java.util.List;

@Dao
public interface DaoWorkout {

    @Insert
    Void insert(Workout workout);

    @Query("SELECT * FROM Workout ORDER BY uid")
    List<Workout> getAll();

    @Query("SELECT * FROM Workout WHERE uid =:workoutId")
    Workout get(int workoutId);

    @Update
    void update(Workout workout);

    @Delete
    void delete(Workout workout);

}
