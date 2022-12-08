package com.example.sportislife.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sportislife.R;
import com.example.sportislife.repository.model.Workout;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class WorkoutAdapter extends BaseAdapter {
    private List<Workout> workouts;
    private LayoutInflater layoutInflater;

    public WorkoutAdapter(Context context, List<Workout> workouts) {
        this.workouts = workouts;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return workouts.size();
    }

    @Override
    public Object getItem(int position) {
        return workouts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_workout_history, null);
        }

        TextView textTypeExercise = (TextView) view.findViewById(R.id.typeExerciseHistory);
        TextView textDate = (TextView) view.findViewById(R.id.dateHistory);
        TextView textReps = (TextView) view.findViewById(R.id.repsHistory);
        TextView textWeight = (TextView) view.findViewById(R.id.weightHistory);

        Workout workout = getWorkout(position);

        textTypeExercise.setText(workout.getTypeExercise());
        textDate.setText(new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(workout.getDate()));
        textReps.setText(String.valueOf(workout.getReps()));
        textWeight.setText(String.valueOf(workout.getWeight()));

        return view;
    }

    private Workout getWorkout(int position) {
        return (Workout) getItem(position);
    }

}
