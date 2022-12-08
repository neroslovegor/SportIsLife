package com.example.sportislife.ui.body;

import android.app.Application;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.widget.DatePicker;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sportislife.repository.BodyRepository;
import com.example.sportislife.repository.model.Body;
import com.example.sportislife.repository.model.Weight;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class BodyViewModel extends ViewModel {

    private BodyRepository repository;
    private Application application;

    private MutableLiveData<Body> bodyData;
    public MutableLiveData<Body> getBodyData() {
        return bodyData;
    }
    private void setBodyData() {
        bodyData.setValue(initBodyData());
    }

    private final HashMap<String, Double> itemPhysicalActivity;
    private HashMap<String, Double> initItemPhysicalActivity() {
        return new HashMap<String, Double>() {{
            put("Физическая нагрузка отсутствует или минимальна", 1.2);
            put("Тренировки средней тяжести 3 раза в неделю", 1.38);
            put("Тренировки средней тяжести 5 раза в неделю", 1.46);
            put("Интенсивные тренировки 5 раза в неделю", 1.55);
            put("Тренировки каждый день", 1.64);
            put("Интенсивные тренировки каждый день или по 2 раза в день", 1.73);
            put("Ежедневная нагрузка и физическая работа", 1.9);
        }};
    }
    public String[] getItemPhysicalActivity() {
        return itemPhysicalActivity.keySet().toArray(new String[0]);
    }

    public MutableLiveData<String> inputName;
    public MutableLiveData<String> inputBirthDate;
    public MutableLiveData<String> inputWeight;
    public MutableLiveData<String> inputHeight;
    public MutableLiveData<String> inputGender;
    public MutableLiveData<String> inputPhysicalActivity;

    private MutableLiveData<Double> BMI;
    public MutableLiveData<Double> getBMI() {
        return BMI;
    }
    private void setBMI() {
        BMI.setValue(initBMI());
    }

    private MutableLiveData<String> BMIStatus;
    public MutableLiveData<String> getBMIStatus() {
        return BMIStatus;
    }
    private void setBMIStatus() {
        BMIStatus.setValue(initBMIStatus());
    }

    private MutableLiveData<Double> calorieNorm;
    public MutableLiveData<Double> getCalorieNorm() {
        return calorieNorm;
    }
    private void setCalorieNorm() {
        calorieNorm.setValue(initCalorieNorm());
    }

    private MutableLiveData<Boolean> error;
    public MutableLiveData<Boolean> getError() {
        return error;
    }
    public void doneError() {
        error.setValue(false);
    }

    public BodyViewModel(BodyRepository repository, Application application) {
        this.repository = repository;
        this.application = application;

        bodyData = new MutableLiveData<>();

        itemPhysicalActivity = initItemPhysicalActivity();

        inputName = new MutableLiveData<>();
        inputBirthDate = new MutableLiveData<>();
        inputWeight = new MutableLiveData<>();
        inputHeight = new MutableLiveData<>();
        inputGender = new MutableLiveData<>();
        inputPhysicalActivity = new MutableLiveData<>();

        BMI = new MutableLiveData<>();
        BMIStatus = new MutableLiveData<>();
        calorieNorm = new MutableLiveData<>();

        error = new MutableLiveData<>();

        updateData();
    }

    private void updateData() {
        setBodyData();
        setBMI();
        setBMIStatus();
        setCalorieNorm();
    }

    private Body initBodyData() {
        List<Body> bodies = repository.getAll();

        if (bodies.size() > 0) {
            return bodies.get(0);
        }

        return null;
    }

    private Double initBMI() {
        List<Body> bodies = repository.getAll();

        if (bodies.size() > 0) {
            Body body = bodies.get(0);
            Double bmi = body.getWeight() / Math.pow(body.getHeight() / 100, 2);
            return bmi;
        }

        return null;
    }
    private String initBMIStatus() {
        Double bmi = BMI.getValue();

        if (bmi != null) {
            if (bmi < 16) {
                return "Значительный дефицит массы тела";
            } else if (bmi >= 16 && bmi < 18.5) {
                return "Дефицит массы тела";
            } else if (bmi >= 18.5 && bmi < 25) {
                return "Норма";
            } else if (bmi >= 25 && bmi < 30) {
                return "Лишний вес";
            } else if (bmi >= 30 && bmi < 35) {
                return "Ожирение первой степени";
            } else if (bmi >= 35 && bmi < 40) {
                return "Ожирение второй степени";
            } else if (bmi > 40) {
                return "Ожирение третьей степени";
            }
        }

        return null;
    }
    private Double initCalorieNorm() {
        List<Body> bodies = repository.getAll();

        if (bodies.size() > 0) {
            Body body = bodies.get(0);

            int age = calculateAge(body.getDate(), new Date());
            Double cfActivity = itemPhysicalActivity.get(body.getPhysicalActivity());
            int cfGender = 0;

            if (body.getGender().contentEquals("Мужской")) {
                cfGender = 5;
            } else if (body.getGender().contentEquals("Женский")) {
                cfGender = -161;
            }

            Double calNorm = (body.getWeight() * 10 + body.getHeight() * 6.25 - age * 5 + cfGender) * cfActivity;
            return calNorm;
        }

        return null;
    }

    public int calculateAge(Date birthDate, Date currentDate) {
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int d1 = Integer.parseInt(formatter.format(birthDate));
        int d2 = Integer.parseInt(formatter.format(currentDate));
        int age = (d2 - d1) / 10000;
        return age;
    }

    public void submitButton() {
        try {
            if (inputName.getValue() != null && inputBirthDate.getValue() != null &&
                    inputWeight.getValue() != null && inputHeight.getValue() != null &&
                    inputGender.getValue() != null && inputPhysicalActivity.getValue() != null) {

                if (inputName.getValue().length() != 0 && inputBirthDate.getValue().length() != 0 &&
                        inputWeight.getValue().length() != 0 && inputHeight.getValue().length() != 0 &&
                        inputGender.getValue().length() != 0 && inputPhysicalActivity.getValue().length() != 0) {

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

                    String name = inputName.getValue();
                    Date birthDate = simpleDateFormat.parse(Objects.requireNonNull(inputBirthDate.getValue()));
                    Float weight = Float.parseFloat(Objects.requireNonNull(inputWeight.getValue()));
                    Float height = Float.parseFloat(Objects.requireNonNull(inputHeight.getValue()));
                    String gender = inputGender.getValue();
                    String physicalActivity = inputPhysicalActivity.getValue();

                    Body body = initBodyData();

                    if (body != null) {
                        body.setName(name);
                        body.setDate(birthDate);
                        body.setWeight(weight);
                        body.setHeight(height);
                        body.setGender(gender);
                        body.setPhysicalActivity(physicalActivity);

                        repository.update(body);
                    } else {
                        body = new Body();
                        body.setName(name);
                        body.setDate(birthDate);
                        body.setWeight(weight);
                        body.setHeight(height);
                        body.setGender(gender);
                        body.setPhysicalActivity(physicalActivity);

                        repository.insert(body);
                    }

                    updateData();
                } else {
                    error.setValue(true);
                }
            } else {
                error.setValue(true);
            }
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}