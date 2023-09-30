package com.example.suitshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

class Register extends AppCompatActivity {

    EditText username, email, password;
    Button registerSubmitButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);

        username = findViewById(R.id.usernameEditText);
        email = findViewById(R.id.emailEditText);
        password = findViewById(R.id.passwordEditText);
        registerSubmitButton = findViewById(R.id.registerSubmitButton);

        registerSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString().trim();
                String em = email.getText().toString().trim();
                String pwd = password.getText().toString().trim();

                if (user.isEmpty() || em.isEmpty() || pwd.isEmpty()) {
                    Toast.makeText(Register.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isInserted = db.insertData(user, em, pwd);
                    if (isInserted) {
                        Toast.makeText(Register.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this, Login.class));
                    } else {
                        Toast.makeText(Register.this, "Registration failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
