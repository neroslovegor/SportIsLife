package com.example.sportislife.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.sportislife.dao.DaoBody;
import com.example.sportislife.model.Body;

@Database(entities = {Body.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DaoBody daoBody();
}
