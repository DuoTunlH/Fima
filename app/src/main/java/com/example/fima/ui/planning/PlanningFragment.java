package com.example.fima.ui.planning;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fima.Adapter.PriorityLevelAdapter;
import com.example.fima.Adapter.TargetAdapter;
import com.example.fima.Adapter.TypeTargetAdapter;
import com.example.fima.R;
import com.example.fima.databinding.FragmentPlanningBinding;
import com.example.fima.models.ListTypeTarget;
import com.example.fima.models.Target;
import com.example.fima.ui.AddNewTarget.AddNewTarget;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PlanningFragment extends Fragment {
    private RecyclerView.Adapter typeAdapter, listTargetAdapter, priorityLevelAdapter;
    private RecyclerView recyclerViewtype, recyclerViewlistTarget, recyclerViewlevel;
    ImageView ic_plus;

    private FragmentPlanningBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentPlanningBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        initRecyclerviewType();
        initRecyclerviewTarget();
        initRecyclerviewPriority();
        // xử lý dự kiện click vào phần tử con hiển thị detail
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ic_plus = binding.icPlus;
        ic_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                xử lý sự kiện chuyể màn
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.planning_fragment, new AddNewTarget() );
//
//                fragmentTransaction.addToBackStack(null);
//
//                fragmentTransaction.commit();

            }
        });


    };

    private void initRecyclerviewPriority() {
        ArrayList<Target> items = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JANUARY, 1); // January 1, 2023
        Date specificDate = calendar.getTime();
        items.add(new Target("abc", "Mua điện thoại", 15_000_000, 3_000_000, specificDate, 8, "Vừa", "ic_phone"));
        items.add(new Target("abd", "Mua điện thoại 2", 15_000_000, 6_000_000, specificDate, 8, "Vừa", "ic_phone"));
        recyclerViewlevel = binding.listviewLevelTarget;
        recyclerViewlevel.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        priorityLevelAdapter= new PriorityLevelAdapter(items);
        recyclerViewlevel.setAdapter(priorityLevelAdapter);
    }

    private void initRecyclerviewTarget() {
        ArrayList<Target> items = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JANUARY, 1); // January 1, 2023
        Date specificDate = calendar.getTime();
        items.add(new Target("abc", "Mua điện thoại", 15_000_000, 3_000_000, specificDate, 8, "Vừa", "ic_phone"));
        items.add(new Target("abd", "Mua điện thoại 2", 15_000_000, 6_000_000, specificDate, 8, "Vừa", "ic_phone"));

        recyclerViewlistTarget = binding.listviewTarget;
        recyclerViewlistTarget.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        listTargetAdapter = new TargetAdapter(items);
        recyclerViewlistTarget.setAdapter(listTargetAdapter);
    }

    private void initRecyclerviewType() {
        ArrayList<ListTypeTarget> items = new ArrayList<>();
        items.add(new ListTypeTarget("piggy_bank", "Nhỏ"));
        items.add(new ListTypeTarget("piggy_bank", "Vừa"));
        items.add(new ListTypeTarget("piggy_bank", "Lớn"));
        items.add(new ListTypeTarget("piggy_bank", "Ngắn hạn"));
        items.add(new ListTypeTarget("piggy_bank", "Dài hạn"));
        recyclerViewtype = binding.listviewType;
        recyclerViewtype.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));

        typeAdapter = new TypeTargetAdapter(items);
        recyclerViewtype.setAdapter(typeAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}