package com.example.fima.ui.TargetDetail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fima.R;
import com.example.fima.models.DBHandler;
import com.example.fima.models.Target;
import com.example.fima.ui.UpdateTarget.UpdateTargetActivity;

public class TargetDetailActivity extends AppCompatActivity {

    Button btnDelete, btnUpdate;
    Target target;
    TextView textViewTargetName, textViewTargetDeadline, textViewProgressPercent, tvTotalBudget, tvSavedBudget, tvType, tvPriority;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_detail);
        setWidget();
        // get data
        target = (Target) getIntent().getParcelableExtra("Target");
        setDataForActivity();
        btnUpdateOnClick();
        btnDeleteOnClick();
    }

    private void setWidget() {
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        textViewTargetName = findViewById(R.id.planName);
        textViewTargetDeadline = findViewById(R.id.textViewDeadline);
        textViewProgressPercent = findViewById(R.id.progress_percent);
        progressBar = findViewById(R.id.progress_bar);
        tvTotalBudget = findViewById(R.id.textViewTotalBudget);
        tvSavedBudget = findViewById(R.id.textViewSavedBudget);
        tvType = findViewById(R.id.textViewType);
        tvPriority = findViewById(R.id.textViewPriority);
    }

    private void btnDeleteOnClick() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHandler.getInstance(TargetDetailActivity.this).deleteTarget(target);
                Toast.makeText(TargetDetailActivity.this, "Target deleted successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void btnUpdateOnClick() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TargetDetailActivity.this, UpdateTargetActivity.class);
                intent.putExtra("Target", target);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setDataForActivity() {
        textViewTargetDeadline.setText("Deadline: " + target.getDeadline());
        textViewTargetName.setText(target.getPlanName());
        int percent = (int) (target.getSavedBudget() / target.getTotalBudget() * 100);
        textViewProgressPercent.setText(percent + "%");
        tvTotalBudget.setText(String.valueOf("TotalBudget you plan to prepare:  " + target.getTotalBudget()));
        tvSavedBudget.setText(String.valueOf("SavedBudget you prepared :  " + target.getSavedBudget()));
        tvType.setText("Target type :" + target.getTargetType());
        tvPriority.setText("Priority:  " + target.getPriorityLevel());
        progressBar.setProgress(percent);
    }

}