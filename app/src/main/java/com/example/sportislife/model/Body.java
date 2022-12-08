package com.example.sportislife.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.sportislife.util.DateConverter;

import java.util.Date;

@Entity
public class Body {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "date")
    @TypeConverters({DateConverter.class})
    private Date date;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "weight")
    private float weight;

    @ColumnInfo(name = "height")
    private float height;

    @ColumnInfo(name = "physical_activity")
    private String physicalActivity;


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

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public float getWeight() {
        return weight;
    }
    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }
    public void setHeight(float height) {
        this.height = height;
    }

    public String getPhysicalActivity() {
        return physicalActivity;
    }
    public void setPhysicalActivity(String physicalActivity) {
        this.physicalActivity = physicalActivity;
    }
}
