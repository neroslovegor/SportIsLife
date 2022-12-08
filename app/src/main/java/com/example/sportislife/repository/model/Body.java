package com.example.sportislife.repository.model;

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

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "weight")
    private Float weight;

    @ColumnInfo(name = "height")
    private Float height;

    @ColumnInfo(name = "birthday_date")
    @TypeConverters({DateConverter.class})
    private Date date;

    @ColumnInfo(name = "gender")
    private String gender;

    @ColumnInfo(name = "physical_activity")
    private String physicalActivity;


    public int getUid() {
        return uid;
    }
    public void setUid(int id) {
        this.uid = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Float getWeight() {
        return weight;
    }
    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getHeight() {
        return height;
    }
    public void setHeight(Float height) {
        this.height = height;
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

    public String getPhysicalActivity() {
        return physicalActivity;
    }
    public void setPhysicalActivity(String physicalActivity) {
        this.physicalActivity = physicalActivity;
    }
}
