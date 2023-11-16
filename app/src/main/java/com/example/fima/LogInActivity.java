package com.example.fima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LogInActivity extends AppCompatActivity {
    EditText etLoginUser, etLoginPass;
    Button btnLogin, btnNavSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        etLoginUser = findViewById(R.id.etUserLogin);
        etLoginPass = findViewById(R.id.etPassWordLogin);
        btnLogin = findViewById(R.id.btnLogIn);
        btnNavSignUp =findViewById(R.id.btnNavSignUp);

        btnNavSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(this, signUpActivity.class);
//                startActivity(intent);
            }
        });
    }
}