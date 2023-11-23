package com.example.fima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

public class signUpActivity extends AppCompatActivity {
    EditText etFirstName, etLastName, etEmail, etPassWord, etRepeatPW;
    CheckBox cbRule;
    Button btnSignUp, btnBackLogIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etFirstName = findViewById(R.id.editFirstName);
        etLastName = findViewById(R.id.editLastName);
        etEmail = findViewById(R.id.editEmail);
        etPassWord = findViewById(R.id.editPassWord);
        etRepeatPW = findViewById(R.id.editRepeatPW);
        cbRule = findViewById(R.id.checkBox);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnBackLogIn = findViewById(R.id.btnBackLogIn);

        // Handle event click button back
        btnBackLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signUpActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });
        // Handle click button sign up
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // Handle agree rule
        cbRule.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }
}