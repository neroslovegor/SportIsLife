package com.example.sportislife.db;

import static com.example.sportislife.AppConstants.DB_NAME;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.sportislife.dao.DaoBody;
import com.example.sportislife.dao.DaoWeight;
import com.example.sportislife.dao.DaoWorkout;
import com.example.sportislife.repository.model.Body;
import com.example.sportislife.repository.model.Weight;
import com.example.sportislife.repository.model.Workout;

@Database(entities = {Body.class, Weight.class, Workout.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DaoBody daoBody();
    public abstract DaoWeight daoWeight();
    public abstract DaoWorkout daoWorkout();

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                     DB_NAME)
                    .allowMainThreadQueries()
                    .build();

        }
        return INSTANCE;
    }
}
