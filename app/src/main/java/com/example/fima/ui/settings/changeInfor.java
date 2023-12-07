package com.example.fima.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fima.MainActivity;
import com.example.fima.R;
import com.example.fima.models.DBHandler;
import com.example.fima.models.User;

public class changeInfor extends AppCompatActivity {
    EditText etChgeFirstName, etchaneLastName;
    Button btnUpdatePro, btnCancelUpdatePro;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeprofile);
        etChgeFirstName = findViewById(R.id.etChangeFirstName);
        etchaneLastName = findViewById(R.id.etChangeLastName);
        btnUpdatePro = findViewById(R.id.btnUpdateProfile);
        btnCancelUpdatePro = findViewById(R.id.btnCancelUpdateProfile);

        btnCancelUpdatePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(changeInfor.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnUpdatePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = etChgeFirstName.getText().toString();
                String lastname = etchaneLastName.getText().toString();
                if(TextUtils.isEmpty(firstname) || TextUtils.isEmpty(lastname))
                {
                    Toast.makeText(changeInfor.this, "Please enter complete information!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(DBHandler.getInstance(changeInfor.this).updateProfile(User.getInstance().getEmail(), User.getInstance().getFirstname(), User.getInstance().getLastname()))
                {
                    Toast.makeText(changeInfor.this, "Update your profile successfully!", Toast.LENGTH_SHORT).show();
                    User.getInstance().setFirstname(firstname);
                    User.getInstance().setLastname(lastname);
                }
                else
                {
                    Toast.makeText(changeInfor.this, "An error occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
