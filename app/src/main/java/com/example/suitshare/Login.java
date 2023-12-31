package com.example.suitshare;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    EditText loginEmail, loginPassword;
    Button loginSubmitButton;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);

        loginEmail = findViewById(R.id.loginEmailEditText);
        loginPassword = findViewById(R.id.loginPasswordEditText);
        loginSubmitButton = findViewById(R.id.loginSubmitButton);

        loginSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String em = loginEmail.getText().toString().trim();
                String pwd = loginPassword.getText().toString().trim();

                if (em.isEmpty() || pwd.isEmpty()) {
                    Toast.makeText(Login.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Cursor res = db.checkUser(em, pwd);
                        if (res != null && res.getCount() == 1) {
                            // Login successful
                            Toast.makeText(Login.this, "Login successful!", Toast.LENGTH_SHORT).show();

                            // Sending email to Dashboard activity
                            Log.d("Login", "Passing Email to Dashboard: " + em);
                            Intent intent = new Intent(Login.this, Dashboard.class);
                            intent.putExtra("EMAIL", em);
                            startActivity(intent);

                        } else {
                            Toast.makeText(Login.this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
                        }
                        if (res != null) {
                            res.close();  // Close the cursor after use
                        }
                    } catch (SQLException e) {
                        Toast.makeText(Login.this, "Database error!", Toast.LENGTH_SHORT).show();
                        Log.e("Login", "Database error: " + e.getMessage());
                    }
                }
            }
        });
    }
}
