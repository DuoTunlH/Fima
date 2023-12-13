package com.example.fima.ui.settings;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fima.LogInActivity;
import com.example.fima.MainActivity;
import com.example.fima.R;
import com.example.fima.models.DBHandler;
import com.example.fima.models.User;

public class changeInfor extends AppCompatActivity {
    EditText etChgeFirstName, etchaneLastName;
    Button btnUpdatePro, btnCancelUpdatePro, btnDeleteAccount;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changeprofile);
        etChgeFirstName = findViewById(R.id.etChangeFirstName);
        etchaneLastName = findViewById(R.id.etChangeLastName);
        btnUpdatePro = findViewById(R.id.btnUpdateProfile);
        btnCancelUpdatePro = findViewById(R.id.btnCancelUpdateProfile);
        btnDeleteAccount = findViewById(R.id.btnDeleteUser);

        btnCancelUpdatePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                if(DBHandler.getInstance(changeInfor.this).updateProfile(firstname, lastname))
                {
                    Toast.makeText(changeInfor.this, "Update your profile successfully!", Toast.LENGTH_SHORT).show();
                    User.getInstance().setFirstname(firstname);
                    User.getInstance().setLastname(lastname);
                    finish();
                }
                else
                {
                    Toast.makeText(changeInfor.this, "An error occurred!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder aler = new AlertDialog.Builder(changeInfor.this);
                aler.setTitle("Notification!");
                aler.setMessage("Are you sure to delete this account?");
                aler.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHandler.getInstance(changeInfor.this).deleteUser();
                        User.getInstance().initialize(0, "", "","", "");
                        Toast.makeText(changeInfor.this, "Account deleted successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(changeInfor.this, LogInActivity.class);
                        startActivity(intent);
                    }
                });
                aler.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                aler.show();
            }
        });
    }
}
