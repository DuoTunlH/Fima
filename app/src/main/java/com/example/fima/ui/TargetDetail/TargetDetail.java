package com.example.fima.ui.TargetDetail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fima.R;
import com.example.fima.databinding.FragmentPlanningBinding;
import com.example.fima.databinding.FragmentTargetDetailBinding;

public class TargetDetail extends Fragment {
    private FragmentTargetDetailBinding binding;


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
}