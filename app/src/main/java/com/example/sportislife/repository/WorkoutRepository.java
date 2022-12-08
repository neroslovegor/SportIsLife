package com.example.sportislife.repository;

import com.example.sportislife.dao.DaoWorkout;
import com.example.sportislife.repository.model.Workout;

import java.util.List;

public class WorkoutRepository {

    private DaoWorkout dao;

    public WorkoutRepository(DaoWorkout dao) {
        this.dao = dao;
    }

    public Workout get(int id) {
        return dao.get(id);
    }

    public List<Workout> getAll() {
        return dao.getAll();
    }

    public void insert(Workout workout) {
        dao.insert(workout);
    }

    public void delete(int id) {
        Workout workout = get(id);

        if(workout != null) {
            dao.delete(workout);
        }
    }
}
