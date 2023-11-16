package com.example.fima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class signUpActivity extends AppCompatActivity {
    EditText etUserName, etPassWord, etRepeatPW;
    CheckBox cbRule;
    Button btnSignUp, btnBackLogIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        etUserName = findViewById(R.id.editUserName);
        etPassWord = findViewById(R.id.editPassWord);
        etRepeatPW = findViewById(R.id.editRepeatPW);
        cbRule = findViewById(R.id.checkBox);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnBackLogIn = findViewById(R.id.btnBackLogIn);

        btnBackLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(this, LogInActivity.class);
//                startActivity(intent);
            }
        });

    }
}