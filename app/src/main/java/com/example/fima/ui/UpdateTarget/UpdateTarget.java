package com.example.fima.ui.UpdateTarget;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.fima.R;
import com.example.fima.databinding.FragmentUpdateTargetBinding;
import com.example.fima.models.DBHandler;
import com.example.fima.models.Target;
import com.example.fima.ui.planning.PlanningFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UpdateTarget extends Fragment {
    private FragmentUpdateTargetBinding binding;
    Target target;
    EditText editTextTotalBudget, editTextSavedBudget, editTextDeadline;
    TextView textViewName;
    Spinner spinner;
    ProgressBar progressBar;
    ImageView imageViewDatePicker;
    Button btnUpdate, btnBack;
    SeekBar seekBar;
    List<String> type = Arrays.asList("Small", "Middle", "Big", "Short-term", "Long-term");

    public UpdateTarget(Target target) {
        this.target = target;
    }

    public UpdateTarget() {
    }

    public static UpdateTarget newInstance(Target target) {
        UpdateTarget fragment = new UpdateTarget(target);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUpdateTargetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setWidget();
        setupDataForFragment();
        editTextSavedBudgetEvent();
        setupSpinner();
        editTextDeadlineEvent();
        btnUpdateEvent();
        imageViewDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickDate();
            }
        });
    }

    private void btnUpdateEvent() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (editTextTotalBudget.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Total Budget can not empty", Toast.LENGTH_SHORT).show();
                    } else if (editTextSavedBudget.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Saved Budget can not empty", Toast.LENGTH_SHORT).show();
                    } else if (editTextDeadline.getText().toString().equals("")) {
                        Toast.makeText(getContext(), "Deadline can not empty", Toast.LENGTH_SHORT).show();
                    }

                    Target newtarget = new Target(target.getID(), textViewName.getText().toString(), Double.parseDouble(editTextTotalBudget.getText().toString()), Double.parseDouble(editTextSavedBudget.getText().toString()), editTextDeadline.getText().toString(), spinner.getSelectedItem().toString(), seekBar.getProgress(), "link_img");
                    DBHandler.getInstance(getContext()).updateTarget(newtarget);
                    Toast.makeText(getContext(), "New Target added successfully", Toast.LENGTH_SHORT).show();

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

    private void editTextDeadlineEvent() {
        editTextDeadline.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    pickDate();
                    view.clearFocus();
                }
            }
        });
    }

    private void setupSpinner() {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, type);
        spinner.setAdapter(spinnerAdapter);
    }

    private void editTextSavedBudgetEvent() {
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

    private void setupDataForFragment() {

        textViewName.setText(target.getPlanName());
        editTextSavedBudget.setText(String.valueOf(target.getSavedBudget()));
        editTextTotalBudget.setText(String.valueOf(target.getTotalBudget()));
        editTextDeadline.setText(target.getDeadline());
    }

    private void setWidget() {
        textViewName = binding.planname;
        editTextTotalBudget = binding.editTextTotalBudget;
        editTextSavedBudget = binding.editTextSavedBudget;
        editTextDeadline = binding.editTextTextDeadline;
        imageViewDatePicker = binding.imageView;
        spinner = binding.spinner;
        progressBar = binding.progressBar;
        btnBack = binding.btnBack;
        btnUpdate = binding.btnUpdate;
        seekBar = binding.seekBar;
    }

    public void pickDate() {
        SimpleDateFormat df = new SimpleDateFormat("dd-M-yyyy");
        Calendar calendar = Calendar.getInstance();
        int year, month, day;
        try {
            Date date = df.parse(editTextDeadline.getText().toString());
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
                        editTextDeadline.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

}