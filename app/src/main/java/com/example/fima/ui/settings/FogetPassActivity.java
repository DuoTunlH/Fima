package com.example.fima.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fima.LogInActivity;
import com.example.fima.R;
import com.example.fima.models.DBHandler;
import com.example.fima.models.User;
import com.example.fima.signUpActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FogetPassActivity extends AppCompatActivity {
    EditText etFPFirstName, etFPLastName, etFPEmail, etFPNewPass, etFPConNewPass;
    Button btnUpdateNewPass, btnCancleNewpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foget_pass);
        etFPFirstName = findViewById(R.id.etForPassFName);
        etFPLastName = findViewById(R.id.etForPassLName);
        etFPEmail = findViewById(R.id.etForPassEmail);
        etFPNewPass = findViewById(R.id.etForPassNewPass);
        etFPConNewPass = findViewById(R.id.etForPassConNewPass);
        btnCancleNewpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FogetPassActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
        btnUpdateNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPass = etFPNewPass.getText().toString();
                String confNewPass = etFPConNewPass.getText().toString();
                String firstname = etFPFirstName.getText().toString();
                String lastname = etFPLastName.getText().toString();
                String email = etFPEmail.getText().toString();

                if (TextUtils.isEmpty(firstname) || TextUtils.isEmpty(lastname) ||
                        TextUtils.isEmpty(email) || TextUtils.isEmpty(newPass) || TextUtils.isEmpty(confNewPass)) {
                    Toast.makeText(FogetPassActivity.this, "Please enter complete information!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!newPass.equals(confNewPass)) {
                    Toast.makeText(FogetPassActivity.this, "Password and confirm password do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Check syntax password

                if(DBHandler.getInstance(FogetPassActivity.this).checkInforForgetPass(firstname, lastname, email))
                {
                    if(DBHandler.getInstance(FogetPassActivity.this).updatePassword(email,newPass))
                    {
                        Toast.makeText(FogetPassActivity.this, "Update password successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(FogetPassActivity.this, LogInActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(FogetPassActivity.this, "An error occurred!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(FogetPassActivity.this, "Account information is incorrect!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}