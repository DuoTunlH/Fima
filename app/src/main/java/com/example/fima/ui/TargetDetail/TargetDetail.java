package com.example.fima.ui.TargetDetail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.fima.R;
import com.example.fima.databinding.FragmentTargetDetailBinding;
import com.example.fima.models.DBHandler;
import com.example.fima.models.Target;
import com.example.fima.ui.UpdateTarget.UpdateTarget;
import com.example.fima.ui.planning.PlanningFragment;

public class TargetDetail extends Fragment {
    private FragmentTargetDetailBinding binding;
    Button btnDelete, btnUpdate;
    Target target;
    TextView textViewTargetName, textViewTargetDeadline, textViewProgressPercent;
    ProgressBar progressBar;

    public TargetDetail(Target target) {
        this.target = target;
    }

    public static TargetDetail newInstance(Target target) {
        TargetDetail fragment = new TargetDetail(target);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTargetDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setWidget();
        setDataForFragment();
        btnUpdateOnClick();
        btnDeleteOnClick();

    }

    private void setWidget() {
        btnUpdate = binding.btnUpdate;
        btnDelete = binding.btnDelete;
        textViewTargetName = binding.planName;
        textViewTargetDeadline = binding.DeadLine;
        textViewProgressPercent = binding.progressPercent;
        progressBar = binding.progressBar;
    }

    private void btnDeleteOnClick() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG", "onClick: " + target.getID());
                DBHandler.getInstance(getContext()).deleteTarget(target);
                Toast.makeText(getContext(), "Target deleted successfully", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, PlanningFragment.class, null)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void btnUpdateOnClick() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment_activity_main, UpdateTarget.newInstance(target), null)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void setDataForFragment() {
        textViewTargetName.setText(target.getPlanName());
        textViewTargetDeadline.setText("Deadline: " + target.getDeadline());
        int percent = (int) (target.getSavedBudget() / target.getTotalBudget() * 100);
        textViewProgressPercent.setText(percent + "%");
        binding.textViewTotalBudget.setText(String.valueOf("TotalBudget you plan to prepare:  " + target.getTotalBudget()));
        binding.textViewSavedBudget.setText(String.valueOf("SavedBudget you prepared :  " + target.getSavedBudget()));
        binding.textViewType.setText("Target type :" + target.getTargetType());
        binding.textViewPriority.setText("Priority:  " + target.getPriorityLevel());
        progressBar.setProgress(percent);
    }


}