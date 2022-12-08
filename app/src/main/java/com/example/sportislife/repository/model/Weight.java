package com.example.sportislife.repository.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.sportislife.util.DateConverter;

import java.util.Date;

@Entity
public class Weight {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "date")
    @TypeConverters({DateConverter.class})
    private Date date;

    @ColumnInfo(name = "weight")
    private float weight;


    public int getUid() {
        return uid;
    }
    public void setUid(int id) {
        this.uid = id;
    }

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public float getWeight() {
        return weight;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }

}
