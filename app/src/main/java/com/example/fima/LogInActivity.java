package com.example.fima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fima.models.DBHandler;
import com.example.fima.models.User;
import com.example.fima.ui.settings.FogetPassActivity;

import java.util.Map;

public class LogInActivity extends AppCompatActivity {
    EditText etLoginUser, etLoginPass;
    TextView tvNavigateSignIn, tvForgetPass;
    Button btnLogin;
    CheckBox cbRememberMe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        // Ánh xạ
        etLoginUser = findViewById(R.id.etUserLogin);
        etLoginPass = findViewById(R.id.etPassWordLogin);
        btnLogin = findViewById(R.id.btnLogIn);
        tvNavigateSignIn = findViewById(R.id.tvNavigateSignIn);
        cbRememberMe = findViewById(R.id.cbRememberMe);
        tvForgetPass = findViewById(R.id.tvForgetPass);

        // Handle event navigate home screen
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etLoginUser.getText().toString();
                String password = etLoginPass.getText().toString();

                DBHandler dbHandler = DBHandler.getInstance(LogInActivity.this);
                Map<String, String> userData = dbHandler.checkLogin(email, password);

                if (userData != null) {
                    int id = Integer.parseInt(userData.get("id"));
                    String firstname = userData.get("firstname");
                    String lastname = userData.get("lastname");
                    String userEmail = userData.get("email");
                    String userPassword = userData.get("password");

                    User.getInstance().initialize(id, firstname, lastname, userEmail, userPassword);

                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LogInActivity.this, "Wrong email or password!", Toast.LENGTH_SHORT).show();
                }
            }

        });
        // Handle event navigate sign up
        tvNavigateSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, signUpActivity.class);
                startActivity(intent);
            }
        });
        // Handle check box remember me
        cbRememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
        // Handle forget password
        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(LogInActivity.this, FogetPassActivity.class);
            startActivity(intent);
            }
        });
    }
}