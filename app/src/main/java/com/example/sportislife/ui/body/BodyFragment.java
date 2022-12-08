package com.example.sportislife.ui.body;

import android.app.AlertDialog;
import android.app.Application;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportislife.R;
import com.example.sportislife.dao.DaoBody;
import com.example.sportislife.databinding.FragmentBodyBinding;
import com.example.sportislife.db.AppDatabase;
import com.example.sportislife.repository.BodyRepository;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BodyFragment extends Fragment {

    private BodyViewModel viewModel;
    private FragmentBodyBinding binding;

    private EditText editTextName, editTextWeight, editTextHeight;
    private Button btnCalendar;
    private RadioGroup radGroupGender;
    private RadioButton radBtnMale, radBtnFemale;
    private AutoCompleteTextView physicalActivity;
    private TextView textViewBMI, textViewBMIStatus, textViewCalorieNorm;

    private DatePickerDialog datePickerDialog;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Application application = this.requireActivity().getApplication();
        DaoBody dao = AppDatabase.getInstance(application).daoBody();
        BodyRepository repository = new BodyRepository(dao);
        BodyFactory factory = new BodyFactory(repository, application);

        viewModel = new ViewModelProvider(this, factory).get(BodyViewModel.class);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_body, container, false);
        binding.setBodyViewModel(viewModel);
        binding.setLifecycleOwner(this);

        editTextName = binding.editTextName;
        editTextWeight = binding.editTextWeight;
        editTextHeight = binding.editTextHeight;
        btnCalendar = binding.btnCalendar;
        radGroupGender = binding.radGroupGender;
        radBtnMale = binding.radBtnMale;
        radBtnFemale = binding.radBtnFemale;
        physicalActivity = binding.physicalActivity;
        textViewBMI = binding.textViewBMI;
        textViewBMIStatus = binding.textViewBMIStatus;
        textViewCalorieNorm = binding.textViewCalorieNorm;

        initDatePicker();
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker(view);
            }
        });

        radGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == radBtnMale.getId()) {
                    viewModel.inputGender.setValue(radBtnMale.getText().toString());
                } else if (checkedId == radBtnFemale.getId()) {
                    viewModel.inputGender.setValue(radBtnFemale.getText().toString());
                }
            }
        });

        ArrayAdapter<String> adapterPhysicalActivity = new ArrayAdapter<>(application.getApplicationContext(), R.layout.item_physical_activity, viewModel.getItemPhysicalActivity());
        physicalActivity.setAdapter(adapterPhysicalActivity);
        physicalActivity.setOnItemClickListener((parent, view, position, id) -> {
            viewModel.inputPhysicalActivity.setValue(physicalActivity.getText().toString());
        });

        viewModel.getBodyData().observe(getViewLifecycleOwner(), bodyData -> {
            if (bodyData != null) {
                editTextName.setText(bodyData.getName());
                editTextWeight.setText(bodyData.getWeight().toString());
                editTextHeight.setText(bodyData.getHeight().toString());
                btnCalendar.setText(new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(bodyData.getDate()));

                if (bodyData.getGender().contentEquals(radBtnMale.getText())) {
                    radGroupGender.check(radBtnMale.getId());
                } else if (bodyData.getGender().contentEquals(radBtnFemale.getText())) {
                    radGroupGender.check(radBtnFemale.getId());
                }

//                int count = 1;
//                for (String item : viewModel.getItemPhysicalActivity()) {
//                    if (bodyData.getPhysicalActivity().contentEquals(item)) {
//                        physicalActivity.setText(count);
//                        break;
//                    }
//                    count++;
//                }
            }
        });

        viewModel.getBMI().observe(getViewLifecycleOwner(), bmi -> {
            if (bmi != null) {
                textViewBMI.setText(String.format("%.1f", bmi));
            }
        });
        viewModel.getBMIStatus().observe(getViewLifecycleOwner(), bmiStatus -> {
            if (bmiStatus != null) {
                textViewBMIStatus.setText(bmiStatus);
            }
        });
        viewModel.getCalorieNorm().observe(getViewLifecycleOwner(), calorieNorm -> {
            if (calorieNorm != null) {
                textViewCalorieNorm.setText(String.format("%.1f", calorieNorm));
            }
        });

        viewModel.getError().observe(getViewLifecycleOwner(), hasError -> {
            if (hasError) {
                emptyField();
            }
        });

        return binding.getRoot();
    }

    private void emptyField() {
        if (editTextName.getText().length() == 0) {
            editTextName.setError(getString(R.string.error_this_field_cannot_be_empty));
        } else if (btnCalendar.getText().length() == 0) {
            btnCalendar.setError(getString(R.string.error_this_field_cannot_be_empty));
        } else if (editTextWeight.getText().length() == 0) {
            editTextWeight.setError(getString(R.string.error_this_field_cannot_be_empty));
        } else if (editTextHeight.getText().length() == 0) {
            editTextHeight.setError(getString(R.string.error_this_field_cannot_be_empty));
        } else if (physicalActivity.getText().length() == 0) {
            physicalActivity.setError(getString(R.string.error_this_field_cannot_be_empty));
        } else if (radGroupGender.getCheckedRadioButtonId() != radBtnFemale.getId()) {
            radBtnFemale.setError(getString(R.string.error_this_field_cannot_be_empty));
        } else if (radGroupGender.getCheckedRadioButtonId() != radBtnMale.getId()) {
            radBtnMale.setError(getString(R.string.error_this_field_cannot_be_empty));
        }
        viewModel.doneError();
    }

    private String getDate() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int year = calendar.get(Calendar.YEAR);

        return day + "." + month + "." + year;
    }
    public void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = day + "." + month + "." + year;
                btnCalendar.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);
    }
    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}