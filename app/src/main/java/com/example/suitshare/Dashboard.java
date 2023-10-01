package com.example.suitshare;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Dashboard extends AppCompatActivity {

    private ImageView profileImage;
    private TextView usernameText;
    private TextView dateText;

    DatabaseHelper myDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);  // Ensure the XML layout name matches

        // Initialize database helper
        myDb = new DatabaseHelper(this);

        profileImage = findViewById(R.id.profileImage);
        usernameText = findViewById(R.id.usernameText);
        dateText = findViewById(R.id.dateText);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        // Log the received email for debugging
        Log.d("Dashboard", "Email received: " + email);

        if (email != null) {
            // Fetch user details
            Cursor cursor = myDb.getUserByEmail(email);

            if (cursor.moveToFirst()) {
                try {
                    int usernameColumnIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_3);
                    int avatarColumnIndex = cursor.getColumnIndexOrThrow(DatabaseHelper.COL_8);

                    String username = cursor.getString(usernameColumnIndex);
                    String avatarUri = cursor.getString(avatarColumnIndex);

                    // Log the retrieved data for debugging
                    Log.d("Dashboard", "Username: " + username + ", Avatar URI: " + avatarUri);

                    usernameText.setText(username);

                    // Load image using Glide or any other image loading library
                    if (avatarUri != null && !avatarUri.trim().isEmpty()) {  // check if avatarUri is not null and not empty
                        Glide.with(this)
                                .load(avatarUri)
                                .placeholder(R.mipmap.ic_launcher)
                                .into(profileImage);
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }

            cursor.close();
        }

        // Set current date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(calendar.getTime());
        dateText.setText(currentDate);
    }
}
