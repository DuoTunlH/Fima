package com.example.fima.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fima.ExpenseDetailActivity;
import com.example.fima.R;
import com.example.fima.common.ExpensesRecycleViewAdapter;
import com.example.fima.databinding.FragmentHomeBinding;
import com.example.fima.models.UserExpense;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private CalendarView calendarView;
    FloatingActionButton fab;
    List<UserExpense> expenses;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycleView);
        calendarView = view.findViewById(R.id.calendarView);
        fab = view.findViewById(R.id.fab);
        expenses = new ArrayList<>();
        UserExpense u = new UserExpense(1,1,1,"1","1",1);
        expenses.add(u);
        expenses.add(u);
        expenses.add(u);
        expenses.add(u);
        expenses.add(u);
        expenses.add(u);
        expenses.add(u);
        expenses.add(u);
        expenses.add(u);

        ExpensesRecycleViewAdapter adapter = new ExpensesRecycleViewAdapter(getContext(), expenses);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ExpenseDetailActivity.class);
                startActivity(intent);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}