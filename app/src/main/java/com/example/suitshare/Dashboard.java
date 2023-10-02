package com.example.suitshare;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Dashboard extends AppCompatActivity {

    private ImageView profileImage;
    private TextView usernameText;
    private TextView dateText;
    private CardView addItemCard;  // CardView reference

    DatabaseHelper myDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize database helper
        myDb = new DatabaseHelper(this);

        profileImage = findViewById(R.id.profileImage);
        usernameText = findViewById(R.id.usernameText);
        dateText = findViewById(R.id.dateText);

        // Initialize the CardView from the layout
        addItemCard = findViewById(R.id.addItemCard);

        Intent intent = getIntent();
        final String email = intent.getStringExtra("email");

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

                    Log.d("Dashboard", "Username: " + username + ", Avatar URI: " + avatarUri);

                    usernameText.setText(username);

                    if (avatarUri != null && !avatarUri.trim().isEmpty()) {
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

        // Setting up navigation to Additem activity
        // Start - This block is what you'll replicate for other actions

        addItemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Additem.class);
                intent.putExtra("email", email);  // Passing email to the next activity if needed
                startActivity(intent);
            }
        });

        // End - This block is what you'll replicate for other actions

        // Set current date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(calendar.getTime());
        dateText.setText(currentDate);
    }
}
