package com.example.suitshare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    EditText fullname, username, email, phoneNumber, dob, password, confirmPassword;
    Button registerSubmitButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

        fullname = findViewById(R.id.fullnameEditText);
        username = findViewById(R.id.usernameEditText);
        email = findViewById(R.id.emailEditText);
        phoneNumber = findViewById(R.id.phoneNumberEditText);
        dob = findViewById(R.id.dobEditText);
        password = findViewById(R.id.passwordEditText);
        confirmPassword = findViewById(R.id.confirmPasswordEditText);
        registerSubmitButton = findViewById(R.id.registerSubmitButton);

        // Set the hint for DOB format
        dob.setHint("Date of Birth (YYYY-MM-DD)");

        registerSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullNameValue = fullname.getText().toString().trim();
                String user = username.getText().toString().trim();
                String em = email.getText().toString().trim();
                String phone = phoneNumber.getText().toString().trim();
                String dobValue = dob.getText().toString().trim();
                String pwd = password.getText().toString().trim();
                String confirmPwd = confirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(fullNameValue) || TextUtils.isEmpty(user) || TextUtils.isEmpty(em)
                        || TextUtils.isEmpty(phone) || TextUtils.isEmpty(dobValue)
                        || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(confirmPwd)) {
                    Toast.makeText(Register.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (pwd.equals(confirmPwd)) {
                    boolean isInserted = db.insertData(fullNameValue, user, em, phone, dobValue, pwd);
                    if (isInserted) {
                        Toast.makeText(Register.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this, Login.class));
                    } else {
                        Toast.makeText(Register.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Register.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                }
            }


        });
    }
}
