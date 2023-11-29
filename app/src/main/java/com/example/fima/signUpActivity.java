package com.example.fima;

import androidx.appcompat.app.AppCompatActivity;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fima.models.DBHandler;

public class signUpActivity extends AppCompatActivity {
    private static final String GMAIL_REGEX = "^[a-zA-Z0-9_]+@gmail\\.com$";
    EditText etFirstName, etLastName, etEmail, etPassWord, etRepeatPW;
    TextView tvRules;
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
        tvRules = findViewById(R.id.tvRules);


        // Handle event click rules
        tvRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogRules();
            }
        });
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
                String firstname = etFirstName.getText().toString();
                String lastname = etLastName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassWord.getText().toString();
                String rePassword = etRepeatPW.getText().toString();
                // Xử lý email
                if (isValidGmailAddress(email))
                {
                    if (cbRule.isChecked())
                    {
                        // String commandSql = "INSERT INTO "
                    }
                    else
                    {
                        Toast.makeText(signUpActivity.this, "You have not accepted the terms!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(signUpActivity.this, "Invalid email!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // Dialog rules
    private void openDialogRules()
    {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialogrules);

        Window window = dialog.getWindow();
        if(window == null)
        {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams wiLayoutParams = window.getAttributes();
        wiLayoutParams.gravity = Gravity.CENTER;
        window.setAttributes(wiLayoutParams);
        // Kich ra ngoai de an
        dialog.setCancelable(true);
        dialog.show();
    }
    private boolean isValidGmailAddress(String email)
    {
        Pattern pattern = Pattern.compile(GMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}