package com.example.sportislife.ui.workout;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sportislife.repository.WorkoutRepository;
import com.example.sportislife.repository.model.Workout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class WorkoutViewModel extends ViewModel {

    private WorkoutRepository repository;
    private Application application;

    public String[] getItemTypeExercise() {
        return new String[]{
                "Жим гантелей (плечи)", "Жим штанги (плечи)", "Тяга штанги к подбородку (плечи)",
                "Жим гантелей лежа (грудные)", "Жим штанги лежа (грудные)",
                "Подъём гантелей (бицепс)", "Подъём штанги (бицепс)",
                "Приседания со штангой (ноги)"};
    }

    public MutableLiveData<String> inputTypeExercise;
    public MutableLiveData<String> inputDate;
    public MutableLiveData<String> inputReps;
    public MutableLiveData<String> inputWeight;

    private MutableLiveData<String> currentDate;
    public MutableLiveData<String> getCurrentDate() {
        return currentDate;
    }
    public void setCurrentDate() {
        currentDate.setValue(initCurrentDate());
    }

    private MutableLiveData<List<Workout>> workoutData;
    public MutableLiveData<List<Workout>> getWorkoutData() {
        return workoutData;
    }
    public void setWorkoutData() {
        workoutData.setValue(initWorkoutData());
    }

    private MutableLiveData<Boolean> error;
    public MutableLiveData<Boolean> getError() {
        return error;
    }
    public void doneError() {
        error.setValue(false);
    }

    public WorkoutViewModel(WorkoutRepository repository, Application application) {
        this.repository = repository;
        this.application = application;

        currentDate = new MutableLiveData<>();
        workoutData = new MutableLiveData<>();

        inputTypeExercise = new MutableLiveData<>();
        inputDate = new MutableLiveData<>();
        inputReps = new MutableLiveData<>();
        inputWeight = new MutableLiveData<>();

        error = new MutableLiveData<>();

        updateData();
    }

    private void updateData() {
        setCurrentDate();
        setWorkoutData();

        inputTypeExercise.setValue(null);
        inputReps.setValue(null);
        inputWeight.setValue(null);
    }

    private String initCurrentDate() {
        return new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(new Date());
    }
    private List<Workout> initWorkoutData() {
        List<Workout> workouts = repository.getAll();
        return workouts;
    }

    public void submitButton() {
        try {
            if (inputTypeExercise.getValue() != null && inputDate.getValue() != null &&
                    inputReps.getValue() != null && inputWeight.getValue() != null) {

                if (inputTypeExercise.getValue().length() != 0 && inputDate.getValue().length() != 0 &&
                        inputReps.getValue().length() != 0 && inputWeight.getValue().length() != 0) {

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

                    String typeExercise = inputTypeExercise.getValue();
                    Date date = simpleDateFormat.parse(Objects.requireNonNull(inputDate.getValue()));
                    int reps = Integer.parseInt(Objects.requireNonNull(inputReps.getValue()));
                    Float weight = Float.parseFloat(Objects.requireNonNull(inputWeight.getValue()));

                    Workout workout = new Workout();
                    workout.setTypeExercise(typeExercise);
                    workout.setDate(date);
                    workout.setReps(reps);
                    workout.setWeight(weight);

                    repository.insert(workout);

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
    public void deleteButton(int id) {
        List<Workout> workouts = repository.getAll();

        if (workouts == null) return;

        int counter = 0;
        for (Workout workout : workouts) {
            if (id == counter) {
                repository.delete((workout.getUid()));
            }
            counter++;
        }

        updateData();
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }
}