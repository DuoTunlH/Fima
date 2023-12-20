package com.example.fima.ui.AddNewTarget;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.fima.R;
import com.example.fima.databinding.FragmentAddNewTargetBinding;
import com.example.fima.models.DBHandler;
import com.example.fima.models.Target;
import com.example.fima.ui.planning.PlanningFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddNewTarget extends Fragment {
    private FragmentAddNewTargetBinding binding;
    Button btnCreate, btnDelete;
    ProgressBar progressBar;
    EditText editTextName, editTextTotalBudget, editTextSavedBudget, editTextDate;
    Spinner spinner;
    SeekBar seekBar;
    ImageView imageViewDatePicker;
    List<String> type = Arrays.asList("Small", "Middle", "Big", "Short-term", "Long-term");  // data ta for spinner item

    public static AddNewTarget newInstance() {
        AddNewTarget fragment = new AddNewTarget();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddNewTargetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setWidget();
        progressBar.setProgress(0);
        editTextDateOnFocusEvent();
        editTextSavedBudgetOnFocusEvent();
        setUpSpinner();
        btnCreateEvent();
        btnDeleteEvent();
        imageViewDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate();
            }
        });
    }

    private void btnCreateEvent() {
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (editTextName.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Name can not empty", Toast.LENGTH_SHORT).show();
                    } else if (editTextTotalBudget.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Total Budget can not empty", Toast.LENGTH_SHORT).show();
                    } else if (editTextSavedBudget.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Saved Budget can not empty", Toast.LENGTH_SHORT).show();
                    } else if (editTextDate.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Deadline can not empty", Toast.LENGTH_SHORT).show();
                    } else {
                        Target target = new Target(0, editTextName.getText().toString(), Double.parseDouble(editTextTotalBudget.getText().toString()), Double.parseDouble(editTextSavedBudget.getText().toString()), editTextDate.getText().toString(), spinner.getSelectedItem().toString(), seekBar.getProgress(), "link_img");
                        DBHandler.getInstance(getContext()).addTarget(target);
                        Toast.makeText(getContext(), "New Target added successfully", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Add target Error" + e.toString(), Toast.LENGTH_SHORT).show();

                }
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, PlanningFragment.class, null)
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    private void btnDeleteEvent() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextName.setText("");
                editTextDate.setText("");
                editTextSavedBudget.setText("0");
                editTextTotalBudget.setText("0");
            }
        });
    }

    private void setUpSpinner() {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, type);
        spinner.setAdapter(spinnerAdapter);
    }

    private void editTextSavedBudgetOnFocusEvent() {
        editTextSavedBudget.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (editTextSavedBudget.getText().toString().equals("")) {
                    editTextSavedBudget.setText("0");
                }

                int progressprecent = (int) (Double.parseDouble(editTextSavedBudget.getText().toString()) / Double.parseDouble(editTextTotalBudget.getText().toString()) * 100);
                progressBar.setProgress(progressprecent);
            }
        });
    }

    private void editTextDateOnFocusEvent() {
        editTextDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    pickDate();
                    view.clearFocus();
                }
            }
        });
    }

    private void setWidget() {
        btnCreate = binding.btnCreate;
        btnDelete = binding.btnDelete;
        editTextName = binding.editTextName;
        editTextTotalBudget = binding.editTextTotalBudget;
        editTextSavedBudget = binding.editTextSavedBudget;
        editTextDate = binding.editTextDate;
        spinner = binding.spinner;
        progressBar = binding.progressBar;
        seekBar = binding.seekBar;
        imageViewDatePicker = binding.imageViewDatePicker;
    }

    public void pickDate() {
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        Calendar calendar = Calendar.getInstance();
        int year, month, day;
        try {
            Date date = df.parse(editTextDate.getText().toString());
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        editTextDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                },
                year, month, day);
        datePickerDialog.show();
    }


}