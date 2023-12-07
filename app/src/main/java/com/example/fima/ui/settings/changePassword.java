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

import com.example.fima.LogInActivity;
import com.example.fima.MainActivity;
import com.example.fima.R;
import com.example.fima.models.DBHandler;
import com.example.fima.models.User;

public class changePassword extends AppCompatActivity {
    EditText etOldPass, etNewPass, etConPass;
    Button btnUpdateNewPass, btnCancelUpdateNewPass;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepass);
        etOldPass = findViewById(R.id.etGiveOldPassword);
        etNewPass = findViewById(R.id.etGiveNewPassword);
        etConPass = findViewById(R.id.etGiveConfirmNewPassword);
        btnUpdateNewPass = findViewById(R.id.btnUpdatePass);
        btnCancelUpdateNewPass = findViewById(R.id.btnCancelUpdatePass);

        btnCancelUpdateNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(changePassword.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnUpdateNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = etOldPass.getText().toString();
                String newPass = etNewPass.getText().toString();
                String conPass = etConPass.getText().toString();
                if (TextUtils.isEmpty(oldPass) || TextUtils.isEmpty(newPass) || TextUtils.isEmpty(conPass)) {
                    Toast.makeText(changePassword.this, "Please enter complete information!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!newPass.equals(conPass)) {
                    Toast.makeText(changePassword.this, "Password and confirm password do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Check syntax password

                if(DBHandler.getInstance(changePassword.this).checkpassword(User.getInstance().getPassword()))
                {
                    if(DBHandler.getInstance(changePassword.this).updatePassword(User.getInstance().getEmail(), newPass))
                    {
                        Toast.makeText(changePassword.this, "Update password successfully!", Toast.LENGTH_SHORT).show();
                        User.getInstance().setPassword(newPass);
                        Intent intent = new Intent(changePassword.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
                else
                {
                    Toast.makeText(changePassword.this, "The old password is incorrect!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
