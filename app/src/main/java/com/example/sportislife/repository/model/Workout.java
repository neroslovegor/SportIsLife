package com.example.sportislife.repository.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.sportislife.util.DateConverter;

import java.util.Date;

@Entity
public class Workout {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "type_exercise")
    private String typeExercise;

    @ColumnInfo(name = "date")
    @TypeConverters({DateConverter.class})
    private Date date;

    @ColumnInfo(name = "reps")
    private int reps;

    @ColumnInfo(name = "weight")
    private Float weight;

    public int getUid() {
        return uid;
    }
    public void setUid(int id) {
        this.uid = id;
    }

    public String getTypeExercise() {
        return typeExercise;
    }
    public void setTypeExercise(String typeExercise) {
        this.typeExercise = typeExercise;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public int getReps() {
        return reps;
    }
    public void setReps(int reps) {
        this.reps = reps;
    }

    public Float getWeight() {
        return weight;
    }
    public void setWeight(Float weight) {
        this.weight = weight;
    }

}
