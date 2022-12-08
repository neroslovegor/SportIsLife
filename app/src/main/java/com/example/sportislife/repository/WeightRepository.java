package com.example.sportislife.repository;

import com.example.sportislife.dao.DaoWeight;
import com.example.sportislife.repository.model.Weight;

import java.util.List;

public class WeightRepository {

    private DaoWeight dao;

    public WeightRepository(DaoWeight dao) {
        this.dao = dao;
    }

    public Weight get(int id) {
        return dao.get(id);
    }

    public List<Weight> getAll() {
        return dao.getAll();
    }

    public void insert(Weight weight) {
        dao.insert(weight);
    }

    public void delete(int id) {
        Weight weight = get(id);

        if(weight != null) {
            dao.delete(weight);
        }
    }
}
