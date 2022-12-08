package com.example.sportislife.ui.workout;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportislife.R;
import com.example.sportislife.adapter.WorkoutAdapter;
import com.example.sportislife.dao.DaoWorkout;
import com.example.sportislife.databinding.FragmentWorkoutBinding;
import com.example.sportislife.db.AppDatabase;
import com.example.sportislife.repository.WorkoutRepository;
import com.example.sportislife.ui.statistics.StatisticsFactory;
import com.example.sportislife.ui.statistics.StatisticsViewModel;

public class WorkoutFragment extends Fragment {

    private WorkoutViewModel viewModel;
    private FragmentWorkoutBinding binding;

    private AutoCompleteTextView typeExercise;
    private EditText editTextDate, editTextReps, editTextWeight;
    private ListView listViewHistoryExercise;

    private WorkoutAdapter workoutAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Application application = this.requireActivity().getApplication();
        DaoWorkout dao = AppDatabase.getInstance(application).daoWorkout();
        WorkoutRepository repository = new WorkoutRepository(dao);
        WorkoutFactory factory = new WorkoutFactory(repository, application);

        viewModel = new ViewModelProvider(this, factory).get(WorkoutViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_workout, container, false);
        binding.setWorkoutViewModel(viewModel);
        binding.setLifecycleOwner(this);

        typeExercise = binding.typeExercise;
        editTextDate = binding.editTextDate;
        editTextReps = binding.editTextReps;
        editTextWeight = binding.editTextWeight;
        listViewHistoryExercise = binding.listViewHistoryExercise;

        viewModel.getCurrentDate().observe(getViewLifecycleOwner(), editTextDate::setText);

        ArrayAdapter<String> adapterTypeExercise = new ArrayAdapter<>(application.getApplicationContext(), R.layout.item_physical_activity, viewModel.getItemTypeExercise());
        typeExercise.setAdapter(adapterTypeExercise);
        typeExercise.setOnItemClickListener((parent, view, position, id) -> {
            viewModel.inputTypeExercise.setValue(typeExercise.getText().toString());
        });

        viewModel.getWorkoutData().observe(getViewLifecycleOwner(), workoutData -> {
            workoutAdapter = new WorkoutAdapter(getActivity(), workoutData);
            listViewHistoryExercise.setAdapter(workoutAdapter);
        });
        listViewHistoryExercise.setOnItemClickListener((parent, view, position, id) -> {
            final int itemId = (int) workoutAdapter.getItemId(position);
            viewModel.deleteButton(itemId);
        });

        viewModel.getError().observe(getViewLifecycleOwner(), hasError -> {
            if (hasError) {
                emptyField();
                viewModel.doneError();
            }
        });

        return binding.getRoot();
    }

    private void emptyField() {
        if (typeExercise.getText().length() == 0) {
            typeExercise.setError(getString(R.string.error_this_field_cannot_be_empty));
        } else if (editTextDate.getText().length() == 0) {
            editTextDate.setError(getString(R.string.error_this_field_cannot_be_empty));
        } else if (editTextReps.getText().length() == 0) {
            editTextReps.setError(getString(R.string.error_this_field_cannot_be_empty));
        } else if (editTextWeight.getText().length() == 0) {
            editTextWeight.setError(getString(R.string.error_this_field_cannot_be_empty));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}